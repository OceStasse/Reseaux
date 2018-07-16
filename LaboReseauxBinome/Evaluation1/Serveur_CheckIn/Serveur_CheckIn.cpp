#include "Serveur_CheckIn.h"


#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wmissing-noreturn"
Serveur_CheckIn::Serveur_CheckIn(const char *propertiesFilePath) {
	this->readConfigFile(propertiesFilePath);

	affServeur(2, "Acquiring host information...");
	this->hostInfo = new HostInfo(this->host.c_str());
	cout << *(this->hostInfo) << endl;

	affServeur(2, "Getting SocketEcoute ready...");
	this->socketEcoute = new ServerSocket(this->port, *(this->hostInfo), this->tramEnding, this->maxNbClient);
	cout << *(this->socketEcoute) << endl;
}

Serveur_CheckIn::~Serveur_CheckIn() {
	printf("\n");
	affServeur(2, "Shutting down server...");

	/*** Envoie le signal de fermeture aux threads (pas immédiat) ***/
	affServeur(2, "Sending kill signal to all threads...");
	for(pthread_t thread : this->tabThread){
		pthread_kill(thread, SIGUSR1);
//		pthread_detach(thread);
	}

	/*** Attend que les threads soient tous arrêté ***/
//	affServeur(2, "Waiting for all threads to die...");
//	for(pthread_t thread : this->tabThread){
//		pthread_join(thread, nullptr);
//	}

	affServeur(2, "Destroying CIMP...");
	delete this->cimp;

	affServeur(2, "Destroying socketService...");
	delete this->socketService;

	affServeur(2, "Destroying mutex and cond...");
	pthread_cond_destroy(&(this->condNbConnectedClients));
	pthread_mutex_destroy(&(this->mutexNbConnectedClients));

	affServeur(2, "Destroying socketEcoute...");
	delete this->socketEcoute;

	affServeur(2, "Destroying hostInfo...");
	delete this->hostInfo;
}

void Serveur_CheckIn::readConfigFile(const char *propertiesFilePath) throw(ServerException) {
	string temp;
	affServeur(1, "Opening configuration file...");
	Properties properties(propertiesFilePath, "=");

	if((this->host=properties.getValue("HOST")).length() == 0)
		throw ServerException("Serveur_CheckIn::readConfigFile() >> Properties.getValue() : HOST");

	if((temp=properties.getValue("PORT_CHCK")).length() == 0)
		throw ServerException("Serveur_CheckIn::readConfigFile() >> Properties.getValue() : PORT_CHCK");
	this->port = static_cast<unsigned int>(atoi(temp.c_str()));
	if(this->port == 0)
		throw ServerException("Serveur_CheckIn::readConfigFile() >> Error, the value for PORT_CHCK is either invalid or 0.");

	if((this->tramEnding=properties.getValue("TRAM_END")).length() == 0)
		throw ServerException("Serveur_CheckIn::readConfigFile() >> Properties.getValue() : TRAM_END");

	if((this->tramSeparator=properties.getValue("TRAM_SEP")).length() == 0)
		throw ServerException("Serveur_CheckIn::readConfigFile() >> Properties.getValue() : TRAM_SEP");

	if((temp=properties.getValue("MAX_NB_CLIENTS")).length() == 0)
		throw ServerException("Serveur_CheckIn::readConfigFile() >> Properties.getValue() : MAX_NB_CLIENTS");
	this->maxNbClient = static_cast<unsigned int>(atoi(temp.c_str()));
	if(maxNbClient == 0)
		throw ServerException("Serveur_CheckIn::readConfigFile() >> Error, the value for MAX_NB_CLIENTS is either invalid or 0.");

	if((this->csvSeparator=properties.getValue("CSV_SEP")).length() == 0)
		throw ServerException("Serveur_CheckIn::readConfigFile() >> Properties.getValue() : CSV_SEP");

	if((this->dbPath=properties.getValue("DB_PATH")).length() == 0)
		throw ServerException("Serveur_CheckIn::readConfigFile() >> Properties.getValue() : DB_PATH");

	if((this->loginFilePath=properties.getValue("LOGIN_DB_FILE")).length() == 0)
		throw ServerException("Serveur_CheckIn::readConfigFile() >> Properties.getValue() : LOGIN_DB_FILE");
	this->loginFilePath = this->dbPath + this->loginFilePath;

	if((this->ticketFilePath=properties.getValue("TICKET_DB_FILE")).length() == 0)
		throw ServerException("Serveur_CheckIn::readConfigFile() >> Properties.getValue() : TICKET_DB_FILE");
	this->ticketFilePath = this->dbPath + this->ticketFilePath;
}

void Serveur_CheckIn::init() throw(ErrnoException) {
	affServeur(2, "Initializing server...");
	stringstream ss;

	this->socketService = nullptr;
	this->socketEcoute->bind();

	pthread_mutex_init(&(this->mutexNbConnectedClients), nullptr);
	pthread_cond_init(&(this->condNbConnectedClients), nullptr);

	affServeur(2, "Initializing CIMP protocol...");
	cimp = new CIMP(this->tramSeparator);


	affServeur(2, "Creating pool of threads...");
	pthread_mutex_lock(&(this->mutexNbConnectedClients));
	this->tabThread.reserve(this->maxNbClient);
	cout << "MAXNB=" << this->maxNbClient;
	for (unsigned int i=0; i < this->maxNbClient; i++) {
		this->tabThread.push_back(pthread_t());
		if(pthread_create(&this->tabThread[i], nullptr, fctThread, (void *) this) !=0) {
			ss.clear();
			ss << "void Serveur_CheckIn::init() >> Failed to create thread[" << i + 1 << "].";
			throw ErrnoException(errno, ss.str());
		}

		ss.clear();
		ss.str(string());
		ss << "Thread n°" << i+1 << " created.";
		affServeur(2, ss.str().c_str());
	}
	pthread_mutex_unlock(&(this->mutexNbConnectedClients));
	pthread_cond_signal(&(this->condNbConnectedClients));

	affServeur(2, "Initializing done!");
}

void Serveur_CheckIn::run() throw(ServerException) {
	affServeur(2, "Starting server...");
	stringstream ss;
	unsigned long i;

	do{
		affServeur(2, "Starting listening for incoming connection...");
		this->socketEcoute->listen();

		affServeur(2, "Waiting for a client...");
		this->socketService = this->socketEcoute->accept();
		affServeur(2, "A client just connected!");

		affServeur(2, "Searching for a free socket...");
		pthread_mutex_lock(&(this->mutexNbConnectedClients));
		i = this->tabConnectedSocket.size() + this->maxNbClient;

		if(i > SOMAXCONN){
			affServeur(2, "No available socket has been found.");

			try{
				this->socketService->sendMessage(cimp->encodeDOC("Sorry, no available socket has been found."));
			}catch(const ErrnoException &ex){
				stringstream ss1;
				ss1 << "Serveur_CheckIn::run() >> Failed to send DOC (no free socket available).";
				ss1 << "[" << ex.what() << "]";
				throw ServerException(ss1.str());
			}
			close(this->socketService->getSocketHandle());
		}
		else{
			affServeur(2, "A socket is available for the client!");
			try{
				this->socketService->sendMessage(cimp->encodeOK());
			}catch(const ErrnoException &ex){
				ss.clear();
				ss << "Serveur_CheckIn::run() >> Failed to send OK after finding a free socket.";
				ss << "[" << ex.what() << "]";
				throw ServerException(ss.str());
			}

			affServeur(2, "Letting a thread continue the conversation with the client...");

			this->tabConnectedSocket.push_back(new Socket(*(this->socketService)));
			this->newSocketHasBeenAdded = true;
			pthread_mutex_unlock(&(this->mutexNbConnectedClients));
			pthread_cond_signal(&(this->condNbConnectedClients));
		}
	}while(true);
}

#pragma clang diagnostic pop