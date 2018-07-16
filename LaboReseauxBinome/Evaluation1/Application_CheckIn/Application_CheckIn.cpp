#include "Application_CheckIn.h"

#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wmissing-noreturn"
Application_CheckIn::Application_CheckIn(const char *propertiesFilePath) {
	this->readConfigFile(propertiesFilePath);

	affOut("Initializing CIMP protocol...");
	this->cimp = new CIMP(this->tramSeparator);

	affOut("Acquiring server host information...");
	this->hostInfo = new HostInfo(this->host.c_str());
	cout << *(this->hostInfo) << endl;

	this->initSocket();
//	this->pushToContinue();
}

Application_CheckIn::~Application_CheckIn() {
	delete this->cimp;
	delete this->hostInfo;
	delete this->clientSocket;
}

void Application_CheckIn::pushToContinue() {
	char buffer[20];
	affOut("Press ENTER to continue...");
	cin.getline(buffer,20);
}

void Application_CheckIn::showTitle() const {
	affEmptyLine();
	cout << "*******************************************************" << endl;
	cout << "*                                                     *" << endl;
	cout << "*                 APPLICATION CHECKIN                 *" << endl;
	cout << "*                                                     *" << endl;
	cout << "*******************************************************" << endl;
}

void Application_CheckIn::showFlight() const {
	affEmptyLine();
	cout << "*******************************************************" << endl;
	cout << "* VOL 362 POWDER-AIRLINES - Peshawar 6h30             *" << endl;
	if(!this->ticket.ticketNumber.empty()) {
		cout << "* Numéro de billet : " << this->ticket.ticketNumber << "                *" << endl;
		cout << "* Nombre d'accompagnants : " << this->ticket.nbCompanions << "                         *" << endl;
	}
	cout << "*                                                     *" << endl;
	if(!this->luggageSum.ticketNumber.empty()) {
		cout << "* Poids total bagages : " << setprecision(2) << this->luggageSum.totalWeight << "kg                       *" << endl;
		cout << "* Excédent poids : " << setprecision(2) << this->luggageSum.excessWeight << "kg                               *" << endl;
		cout << "* Supplément à payer : " << setprecision(2) << this->luggageSum.priceToPay << "EUR                       *" << endl;
	}
	cout << "*******************************************************" << endl;
}

void Application_CheckIn::readConfigFile(const char *propertiesFilePath) throw(ClientException) {
	string temp;

	affOut("Opening configuration file...");
	Properties properties(propertiesFilePath, "=");

	if((this->tramSeparator=properties.getValue("TRAM_SEP")).length() == 0)
		throw ClientException("Application_CheckIn::readConfigFile() >> Properties.getValue() : TRAM_SEP");

	if((this->host=properties.getValue("HOST")).length() == 0)
		throw ClientException("Application_CheckIn::readConfigFile() >> Properties.getValue() : HOST");

	if((temp=properties.getValue("PORT_CHCK")).length() == 0)
		throw ClientException("Application_CheckIn::readConfigFile() >> Properties.getValue() : PORT_CHCK");
	this->port = static_cast<unsigned int>(atoi(temp.c_str()));
	if(this->port == 0)
		throw ClientException("Application_CheckIn::readConfigFile() >> Error, the value for PORT_CHCK is either invalid or 0.");

	if((this->tramEnding=properties.getValue("TRAM_END")).length() == 0)
		throw ClientException("Application_CheckIn::readConfigFile() >> Properties.getValue() : TRAM_END");
}

void Application_CheckIn::initSocket() throw(ClientException, QuitApp) {
	stringstream ss;
	affOut("Connecting to server...");
	this->clientSocket = new ClientSocket(this->port, *(this->hostInfo), this->tramEnding);
	cout << *(this->clientSocket) << endl;

	this->clientSocket->connectToServer();

	void *parsedData;
	string receivedMessage;
	this->clientSocket->receiveMessage(receivedMessage);

	cout << "AFTER RECEIVE" << endl;
	int responseType = this->cimp->parse(receivedMessage, &parsedData);
	cout << "responseType = " << responseType << endl;
	switch(responseType){
		case DOC:
			quitAppAfterErr("Application_CheckIn::initSocket() (received DOC)", *((string *)parsedData));
		case OK:
			affOut("Connected successfully to server!");
			break;
		default:
			ss << "Unkown answer received [request type = " << responseType << "]";
			affErr(ss.str().c_str());
			throw ClientException(ss.str().c_str());
	}
}

void Application_CheckIn::run() {
	while(true) {
		this->login();
		this->transaction();
	}
}


void Application_CheckIn::login() throw(QuitApp,ClientException) {
	void *parsedData;
	stringstream ss;
	string receivedMessage;
	bool isLoggedIn = false;

	while(!isLoggedIn){
//		system("clear");

		try{
			this->showTitle();
			affEmptyLine();
			affOut("Hello officer, What do you want to do today?");

			affOut("1. LOGIN");
			affOut("0. EXIT");
			string input = getInput("Your choice : ");

			if(input == "1"){
				loginStruct loginStruct;

				affEmptyLine();
				loginStruct.login = getInput("Login : ");
				loginStruct.password = getInput("Password : ");

				this->clientSocket->sendMessage(this->cimp->encodeLOGIN_OFFICER(loginStruct));

				receivedMessage.clear();
				this->clientSocket->receiveMessage(receivedMessage);

				int responseType = this->cimp->parse(receivedMessage, &parsedData);
				switch(responseType){
					case LOGIN_OFFICER_OK:
						isLoggedIn = true;
						affOut("Well done! you are now logged in!");
						break;
					case LOGIN_OFFICER_KO:
						ss << "Could not login : " << *((string *)parsedData);
						affErr(ss.str().c_str());
						break;
					default:
						ss << "Unkown answer received [" << responseType << "]";
						affErr(ss.str().c_str());
						throw ClientException(ss.str().c_str());
				}
			}
			else{
				if(input == "0"){
					this->clientSocket->sendMessage(this->cimp->encodeEOC());
					affOut("The server has been notified!");
					throw QuitApp();
				}
				else{
					affErr("This choice is not available!");
				}
			}
		}catch(const ErrnoException &ex){
			quitAppAfterErr("Application_CheckIn::login()", ex.what());
		}
		catch(const SocketException &ex){
			quitAppAfterErr("Application_CheckIn::login()", ex.what());
		}

//		this->pushToContinue();
		ss.clear();
		ss.str(string());
	}
}

void Application_CheckIn::transaction() throw(QuitApp,ClientException) {
	void *parsedData;
	stringstream ss;
	string receivedMessage;
	int next=CHECK_TICKET;
	vector<luggageStruct> vectorLuggageStruct;

	while(true){
//		system("clear");

		try{
			this->showFlight();
			affEmptyLine();

			affOut("1. CONTINUE");
			affOut("2. LOGOUT");
			affOut("0. EXIT");
			string input = getInput("Your choice : ");

			if(input == "0"){
				this->clientSocket->sendMessage(this->cimp->encodeEOC());

				receivedMessage.clear();
				this->clientSocket->receiveMessage(receivedMessage);

				int responseType = this->cimp->parse(receivedMessage, &parsedData);
				if(responseType==OK){
					affOut("The server has been notified!");
					throw QuitApp();
				}
				else quitAppAfterErr("Application_CheckIn::transaction()", "Received something else than an OK after EOC.");
			}
			else{
				if(input == "2"){
					this->clientSocket->sendMessage(this->cimp->encodeLOGOUT_OFFICER());

					receivedMessage.clear();
					this->clientSocket->receiveMessage(receivedMessage);

					int responseType = this->cimp->parse(receivedMessage, &parsedData);
					switch(responseType){
						case LOGOUT_OFFICER_OK:
							vectorLuggageStruct.clear();
							this->ticket = ticketStruct();
							this->luggageSum = luggageSumStruct();
							return;
						case CHECK_TICKET_KO:
							ss << "Could not logout : " << *((string *)parsedData);
							affErr(ss.str().c_str());
							break;
						default:
							ss << "Unkown answer received [" << responseType << "]";
							affErr(ss.str().c_str());
							throw ClientException(ss.str().c_str());
					}
				}
				else {
					if (input != "1") affErr("This choice is not available!");
					else {
						affEmptyLine();

						switch (next) {
							case CHECK_TICKET : {
								ticketStruct tickTemp;
								tickTemp.ticketNumber = getInput("Numéro de billet ? ");
								tickTemp.nbCompanions = getInput("Nombre d'accompagnants ? ");

								this->clientSocket->sendMessage(this->cimp->encodeCHECK_TICKET(tickTemp));

								receivedMessage.clear();
								this->clientSocket->receiveMessage(receivedMessage);

								int responseType = this->cimp->parse(receivedMessage, &parsedData);
								switch (responseType) {
									case CHECK_TICKET_OK:
										next = CHECK_LUGGAGE;
										this->ticket.ticketNumber = tickTemp.ticketNumber;
										this->ticket.nbCompanions = tickTemp.nbCompanions;
										break;
									case CHECK_TICKET_KO:
										ss << "The ticket isn't valid : " << *((string *) parsedData);
										affErr(ss.str().c_str());
										break;
									default:
										ss << "Unkown answer received [" << responseType << "]";
										affErr(ss.str().c_str());
										throw ClientException(ss.str().c_str());
								}
							}
							break;
							case CHECK_LUGGAGE : {
								bool stop = false;
								int i=1;
								do {
									stringstream ss1;
									ss1 << "Poids du bagage n°" << i++ << " <Enter si fini> : ";
									string temp = getInputWithEmpty(ss1.str());
									luggageStruct luggTemp;

									stop = temp.empty();
									if( !stop ) {
										luggTemp.weight = temp;
										luggTemp.ticketNumber = this->ticket.ticketNumber;
										luggTemp.isLuggage = getInput("Valise ? (O/N)") == "O";
									} else
										luggTemp.ticketNumber = this->ticket.ticketNumber;

									vectorLuggageStruct.reserve(1);
									vectorLuggageStruct.push_back(luggTemp);
								} while (!stop);

								this->clientSocket->sendMessage(this->cimp->encodeCHECK_LUGGAGE(vectorLuggageStruct));

								receivedMessage.clear();
								this->clientSocket->receiveMessage(receivedMessage);

								int responseType = this->cimp->parse(receivedMessage, &parsedData);
								switch (responseType) {
									case CHECK_LUGGAGE_OK:
										next = PAYMENT_DONE;
										this->luggageSum.ticketNumber = ((luggageSumStruct *)parsedData)->ticketNumber;
										this->luggageSum.totalWeight = ((luggageSumStruct *)parsedData)->totalWeight;
										this->luggageSum.excessWeight = ((luggageSumStruct *)parsedData)->excessWeight;
										this->luggageSum.priceToPay = ((luggageSumStruct *)parsedData)->priceToPay;
										break;
									case CHECK_LUGGAGE_KO:
										affErr((*((string *) parsedData)).c_str());
										break;
									default:
										ss << "Unkown answer received [" << responseType << "]";
										affErr(ss.str().c_str());
										throw ClientException(ss.str().c_str());
								}
							}
							break;
							case PAYMENT_DONE : {
								bool done;
								done = getInput("Paiement effectué (Y/N)? ") == "Y";

								this->clientSocket->sendMessage(this->cimp->encodePAYMENT_DONE(done));

								receivedMessage.clear();
								this->clientSocket->receiveMessage(receivedMessage);

								int responseType = this->cimp->parse(receivedMessage, &parsedData);
								switch (responseType) {
									case PAYMENT_DONE_OK:
										next = 0;
										vectorLuggageStruct.clear();
										break;
									case PAYMENT_DONE_KO:
										affErr(*((string *) parsedData));
										break;
									default:
										ss << "Unkown answer received [" << responseType << "]";
										affErr(ss.str().c_str());
										throw ClientException(ss.str().c_str());
								}
							}
							break;
							default:
								ss << "Unkown answer received [" << next << "]";
								affErr(ss.str().c_str());
								throw ClientException(ss.str().c_str());
						}
					}
				}
			}
		}catch(const ErrnoException &ex){
			quitAppAfterErr("Application_CheckIn::login()", ex.what());
		}
		catch(const SocketException &ex){
			quitAppAfterErr("Application_CheckIn::login()", ex.what());
		}

//		this->pushToContinue();
		ss.clear();
		ss.str(string());
	}
}

string Application_CheckIn::getInput(const string invit) {
	string input;
	while((input = getInputWithEmpty(invit)).empty());
	return input;
}

string Application_CheckIn::getInputWithEmpty(const string invit) {
	string input;

	affOut(invit.c_str());
	getline(cin, input);

	return input;
}

void Application_CheckIn::quitAppAfterErr(string where, string what) {
	stringstream ss;
	ss << where << " >> " << what;
	affErr(ss.str().c_str());
	throw QuitApp();
}

#pragma clang diagnostic pop