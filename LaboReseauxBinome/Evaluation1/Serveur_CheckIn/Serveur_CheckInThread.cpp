#include "Serveur_CheckInThread.h"
#include "../Librairies/CIMP/CIMP.h"
#include "../Librairies/CSV/LuggageCsvHandler.h"

#pragma clang diagnostic push
#pragma ide diagnostic ignored "OCDFAInspection"
#pragma clang diagnostic ignored "-Wmissing-noreturn"
void* fctThread(void *param) {
	bool clientIsLoggedIn, finishTransaction;
	int previousState;
	stringstream ss;
	string flightNumber = "362";
	string receivedMessage;
	Socket *serverSocket;
	vector<luggageStruct> luggageVector;
	auto *serveur_checkIn = (Serveur_CheckIn *) param;
	auto CIMP = serveur_checkIn->cimp;
	auto loginCsvHandler = new LoginCsvHandler(serveur_checkIn->loginFilePath, serveur_checkIn->csvSeparator);
	auto ticketCsvHandler = new TicketCsvHandler(serveur_checkIn->ticketFilePath, serveur_checkIn->csvSeparator);


	sigset_t emptyMask, interruptMask;
	struct sigaction sa;
	sa.sa_handler = fctSIGUSR1;
	sa.sa_flags = 0;
	sigemptyset(&sa.sa_mask);
	sigaddset(&sa.sa_mask, SIGINT);
	sigaction(SIGUSR1, &sa, NULL);

	sigemptyset(&emptyMask);
	sigemptyset(&interruptMask);
	sigaddset(&interruptMask, SIGINT);

	pthread_cleanup_push(fctEndOfThread, serverSocket);

	affThread(1, "Thread started.");
	while(true){
		affThread(1, "Waiting for a client...");
		pthread_mutex_lock(&(serveur_checkIn->mutexNbConnectedClients));
		while( !serveur_checkIn->newSocketHasBeenAdded )
			pthread_cond_wait(&(serveur_checkIn->condNbConnectedClients), &(serveur_checkIn->mutexNbConnectedClients) );
		serverSocket = serveur_checkIn->tabConnectedSocket.front();
		serveur_checkIn->tabConnectedSocket.erase(serveur_checkIn->tabConnectedSocket.begin());
		serveur_checkIn->newSocketHasBeenAdded = false;
		pthread_mutex_unlock(&(serveur_checkIn->mutexNbConnectedClients));

		finishTransaction = false;
		clientIsLoggedIn = false;
		previousState = 0;

		affThread(1, "Starting transaction with client...");
		do{
			pthread_sigmask(SIG_SETMASK, &emptyMask, nullptr);
			pthread_sigmask(SIG_SETMASK, &interruptMask, nullptr);

			affThread(1, "Waiting for client's request...");
			receivedMessage.clear();
			try {
				serverSocket->receiveMessage(receivedMessage);

				/*** On parse le message dans le type adapté ***/
				void *parsedData;
				int protocolType = CIMP->parse(receivedMessage, &parsedData);
				switch (protocolType){
					case EOC :
					affThread(1, "Received an EOC request...");

						serverSocket->sendMessage(CIMP->encodeOK());
						finishTransaction = true;
						clientIsLoggedIn = false;
						previousState = 0;
						luggageVector.clear();
						break;
					case LOGIN_OFFICER :
					affThread(1, "Received a LOGIN_OFFICER request...");
						if(clientIsLoggedIn){
							affThread(1, "The client was already connected!");

							delete ((loginStruct *) parsedData);
							serverSocket->sendMessage(CIMP->encodeLOGIN_OFFICER_KO("You are already connected!"));
						}
						else{
							try {
								affThread(1, "Connecting to login database to verify credentials...");
								if( loginCsvHandler->isValid( ((loginStruct *)parsedData)->login, ((loginStruct *)parsedData)->password ) ){
									delete ((loginStruct *) parsedData);

									affThread(1, "The client connected successfuly!");
									clientIsLoggedIn = true;
									previousState = LOGIN_OFFICER;

									serverSocket->sendMessage(CIMP->encodeLOGIN_OFFICER_OK());
								}
								else{
									delete ((loginStruct *) parsedData);
									affThread(1, "The client entered wrong credentials!");
									serverSocket->sendMessage(CIMP->encodeLOGIN_OFFICER_KO("Wrong credentials!"));
								}
							} catch(const CsvException &ex) {
								affThread(1, "Could not connect to login database!");
								affThread(1, ex.what());
								clientIsLoggedIn = false;
								finishTransaction = true;
								previousState = 0;
								kill(getpid(), SIGINT);
							}
						}
						break;
					case LOGOUT_OFFICER :
					affThread(1, "Received a LOGOUT_OFFICER request...");
							serverSocket->sendMessage(CIMP->encodeLOGOUT_OFFICER_OK());

							previousState = 0;
							clientIsLoggedIn = false;
						break;
					case CHECK_TICKET :{
							affThread(1, "Received a CHECK_TICKET request...");
							if(!clientIsLoggedIn){
								affThread(1, "The client wasn't even logged in!");
								serverSocket->sendMessage(CIMP->encodeCHECK_TICKET_KO("Please log in before using our features!"));
							}
							else {
								string toReturn;

								if (ticketNumberIsValid(((ticketStruct *) parsedData)->ticketNumber) &&
									ticketCsvHandler->reservationExists(*((ticketStruct *) parsedData))) {
									toReturn = CIMP->encodeCHECK_TICKET_OK();
								} else {
									toReturn = CIMP->encodeCHECK_TICKET_KO( "The format of the ticket number is wrong (xxx-xxxxxxxx-xxxx)!");
								}

								serverSocket->sendMessage(toReturn);
								previousState = CHECK_TICKET;
							}
						}
						break;
					case CHECK_LUGGAGE :
						affThread(1, "Received a CHECK_LUGGAGE request...");
						if(!clientIsLoggedIn){
							affThread(1, "The client wasn't even logged in!");
							serverSocket->sendMessage(CIMP->encodeCHECK_LUGGAGE_KO("Please log in before using our features!"));
						} else{
							if(previousState != CHECK_TICKET){
								affThread(1, "The client isn't following the script!");
								serverSocket->sendMessage(CIMP->encodeCHECK_LUGGAGE_KO("Please encode the ticket first!"));
							}
							else{
								luggageSumStruct luggageSum;
								float totalWeight, excessWeight;
								string tickNb;

								totalWeight = excessWeight = 0;
								tickNb = ((vector<luggageStruct> *)parsedData)->back().ticketNumber;
								((vector<luggageStruct> *)parsedData)->pop_back();
								for(luggageStruct luggage : *((vector<luggageStruct> *)parsedData)){
									luggageVector.reserve(1);
									luggageVector.push_back(luggage);
									float weight = static_cast<float>(atof(luggage.weight.c_str()));
									totalWeight = totalWeight + weight;
									if(weight>20.0)
										excessWeight += weight-20;
								}

								luggageSum.ticketNumber = tickNb;
								luggageSum.totalWeight = to_string(totalWeight);
								luggageSum.excessWeight = to_string(excessWeight);
								luggageSum.priceToPay = to_string(excessWeight*2.95);

								serverSocket->sendMessage(CIMP->encodeCHECK_LUGGAGE_OK(luggageSum));
								previousState = CHECK_LUGGAGE;
							}
						}
						break;
					case PAYMENT_DONE :
						affThread(1, "Received a PAYMENT_DONE request...");
						if(!clientIsLoggedIn){
							affThread(1, "The client wasn't even logged in!");
							serverSocket->sendMessage(CIMP->encodePAYMENT_DONE_KO("Please log in before using our features!"));
						} else{
							if(previousState != CHECK_LUGGAGE){
								affThread(1, "The client isn't following the script!");
								serverSocket->sendMessage(CIMP->encodePAYMENT_DONE_KO("Please encode the luggages first or empty!"));
							}
							else{
								cout << "serveur : " << boolalpha << *((bool *)parsedData) << endl;
								if(*((bool *)parsedData)) {
									//362_22082017_lug.csv
									stringstream ss2;
									ss2 << serveur_checkIn->dbPath << flightNumber << "_" << getTodayString()
										<< "_lug.csv";
									auto *luggageCsvHandler = new LuggageCsvHandler(ss2.str(),
																					serveur_checkIn->csvSeparator);

									if (luggageCsvHandler->saveLuggageEntries(luggageVector))
										serverSocket->sendMessage(CIMP->encodePAYMENT_DONE_OK());
									else
										serverSocket->sendMessage(CIMP->encodePAYMENT_DONE_KO( "Error trying to save it in database!"));
									previousState = CHECK_LUGGAGE;
								}
								else{
									previousState = 0;
									luggageVector.clear();
									serverSocket->sendMessage(CIMP->encodePAYMENT_DONE_KO( "Reverted to beginning of transaction."));
								}
							}
						}
						break;
					default:
						affThread(1, "Received an unknown request...");

						break;
				}
			} catch (const ErrnoException &ex) {
				affThread(1, ex.what());
				clientIsLoggedIn = false;
				finishTransaction = true;
				previousState = 0;
				luggageVector.clear();
				serverSocket->sendMessage(CIMP->encodeEOC());
			} catch (const CIMPException &ex) {
				affThread(1, ex.what());
				clientIsLoggedIn = false;
				finishTransaction = true;
				previousState = 0;
				luggageVector.clear();
				serverSocket->sendMessage(CIMP->encodeEOC());
			} catch(const CsvException &ex) {
				affThread(1, "Could not connect to database!");
				affThread(1, ex.what());
				clientIsLoggedIn = false;
				finishTransaction = true;
				previousState = 0;
				luggageVector.clear();
				serverSocket->sendMessage(CIMP->encodeEOC());
				kill(getpid(), SIGINT);
			}
		}while(!finishTransaction);

		affThread(1, "End of transaction with the client.");

		delete serverSocket;
	}

	pthread_cleanup_pop(1);
	return param;
}

const char* getThreadId() {
	stringstream ss;
	char *threadId;

	ss << getpid() << "." << pthread_self();
	threadId = (char *) malloc(ss.str().length()+1);
	strcat(threadId, ss.str().c_str());

	return threadId;
}

bool ticketNumberIsValid(const string ticketNumber){
//	362-22082017-0070
	regex pieces_regex("([0-9]{3})\\-([0-9]{8})\\-([0-9]{4})");
	smatch pieces_match;

	return regex_match(ticketNumber, pieces_match, pieces_regex) && ticketNumber.substr(4, 8) == getTodayString();
}

string getTodayString(){
	auto t = time(nullptr);
	auto tm = *localtime(&t);
	ostringstream oss;


	oss << put_time(&tm, "%d%m%Y");
	return oss.str();
}

void fctSIGUSR1(int) {
	affThread(1, "Main Thread is asking me to shutdown...");
	pthread_exit(0);
}

void fctEndOfThread(void *param) {
	delete((Socket *)param);
	affThread(1, "Good bye!");
}

#pragma clang diagnostic pop