#include "Application_CIAChat_IN.h"
#include "IACOP.h"

#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wmissing-noreturn"
Application_CIAChat_IN::Application_CIAChat_IN(const char *propertiesFilePath) {
	this->readConfigFile(propertiesFilePath);

	affOut("Initializing IACOP protocol...");
	this->iacop = new IACOP(this->tramSeparator);

	affOut("Acquiring hostInfos...");
	this->hostInfoUDPLocal = new HostInfo("LaurentMac");
	std::cout << *(this->hostInfoUDPLocal) << std::endl;
	this->hostInfoTCPServeur = new HostInfo(this->TCPhost.c_str());
	std::cout << *(this->hostInfoTCPServeur) << std::endl;
}

Application_CIAChat_IN::~Application_CIAChat_IN() {
	if(this->clientSocket != nullptr)
		delete this->clientSocket;

	if(this->hostInfoTCPServeur != nullptr)
		delete this->hostInfoTCPServeur;

	if(this->hostInfoUDPLocal != nullptr)
		delete this->hostInfoUDPLocal;

	if(this->hostInfoUDPOther != nullptr)
		delete this->hostInfoUDPOther;

	if(this->iacop != nullptr)
		delete this->iacop;
}

void Application_CIAChat_IN::run() {
	int choix;
	while(true) {
		this->login();

		this->multicastSocket = new MulticastSocket(this->UDPport, *(this->hostInfoUDPLocal), *(this->hostInfoUDPOther), this->tramSeparator, this->tramEnding);

		this->threadReception = new ThreadReception(this->multicastSocket, &(this->allMessages), &(this->questions));
		this->threadReception->Start();

		Message *message = new Message(POST_EVENT, this->user, "Vient de se connecter", "Info");
		this->multicastSocket->sendMessage(*message);

		while((choix=this->chooseAction()) != 0){
			switch(choix){
				case 1 :
					afficherConversation();
					break;
				case 2:
					changerFiltre();
					break;
				case 3:
					envoyerMessage();
					break;
			}
		}

		delete this->multicastSocket;
	}
}

void Application_CIAChat_IN::readConfigFile(const char *propertiesFilePath) throw(ClientException) {
	std::string temp;

	affOut("Opening configuration file...");
	Properties properties(propertiesFilePath, "=");

	if((this->tramSeparator=properties.getValue("TRAM_SEP")).length() == 0)
		throw ClientException("Application_CIAChat_IN::readConfigFile() >> Properties.getValue() : TRAM_SEP");

	if((this->TCPhost=properties.getValue("HOST")).length() == 0)
		throw ClientException("Application_CIAChat_IN::readConfigFile() >> Properties.getValue() : HOST");

	if((temp=properties.getValue("PORT")).length() == 0)
		throw ClientException("Application_CIAChat_IN::readConfigFile() >> Properties.getValue() : PORT");
	this->TCPport = static_cast<unsigned int>(atoi(temp.c_str()));
	if(this->TCPport==0)
		throw ClientException("Application_CIAChat_IN::readConfigFile() >> Error, the value for PORT is either invalid or 0.");

	if((this->tramEnding=properties.getValue("TRAM_END")).length() == 0)
		throw ClientException("Application_CIAChat_IN::readConfigFile() >> Properties.getValue() : TRAM_END");
}

void Application_CIAChat_IN::pushToContinue() const {
	char buffer[20];
	affOut("Press ENTER to continue...");
	std::cin.getline(buffer,20);
}

void Application_CIAChat_IN::showTitle() const {
	affEmptyLine();
	affEmptyLine();
	affEmptyLine();
	affEmptyLine();
	std::cout << "*******************************************************" << std::endl;
	std::cout << "*                                                     *" << std::endl;
	std::cout << "*              APPLICATION AIRPORT CHAT               *" << std::endl;
	std::cout << "*                                                     *" << std::endl;
	std::cout << "*******************************************************" << std::endl;
	affEmptyLine();
	affEmptyLine();
}

int Application_CIAChat_IN::chooseAction(){
	bool valid = false;
	std::string input = "";

	while(!valid) {
		affEmptyLine();
		affOut("choisissez quoi faire :");
		affEmptyLine();
		affOut("1. Afficher la conversation");
		affOut("2. Changer le filtre de conversation");
		affOut("3. Envoyer un message");
		affOut("0. Se déconnecter");
		input = getInput("Votre choix : ");

		if(input=="1" || input=="2" || input=="3" || input=="0")
			valid = true;
		else
			affErr("Ce choix n'est pas valable!");
	}

	return atoi(input.c_str());
}

void Application_CIAChat_IN::connectToTCPServer() throw(ClientException, QuitApp) {
	std::stringstream ss;
	affOut("Connecting to server...");

	this->clientSocket = new ClientSocket(this->TCPport, *(this->hostInfoTCPServeur), this->tramEnding);
	std::cout << *(this->clientSocket) << std::endl;

	this->clientSocket->connectToServer();
}

void Application_CIAChat_IN::login() throw(ClientException, QuitApp) {
	void *parsedData;
	std::stringstream ss;
	std::string receivedMessage;
	bool isLoggedIn = false;

	while(!isLoggedIn){
		try{
			this->pushToContinue();
			this->showTitle();
			affEmptyLine();
			affOut("Bonjour, que voulez-vous faire aujourd'hui?");
			affEmptyLine();
			affOut("1. LOGIN");
			affOut("0. EXIT");
			std::string input = getInput("Votre choix : ");

			if(input == "1"){
				loginStruct loginStruct;

				affEmptyLine();
				loginStruct.login = getInput("Login(ou numéro de ticket) : ");
				loginStruct.password = getInputWithEmpty("Password(vide avec le numéro de ticket) : ");

				std::chrono::milliseconds ms = std::chrono::duration_cast<std::chrono::milliseconds>(std::chrono::system_clock::now().time_since_epoch());
				loginStruct.time = ms.count();

				srand(static_cast<unsigned int>(time(nullptr)));
				loginStruct.rand = rand()%10000-1;

				this->connectToTCPServer();

				this->clientSocket->sendMessage(this->iacop->encodeLOGIN_GROUP(loginStruct));

				receivedMessage.clear();
				this->clientSocket->receiveMessage(receivedMessage);
				delete this->clientSocket;

				int responseType = this->iacop->parse(receivedMessage, &parsedData);
				switch(responseType){
					case LOGIN_GROUP_OK:
						isLoggedIn = true;
						this->user = loginStruct.login;
						this->UDPhost = ((ipPortStruct *) parsedData)->ip;
						this->UDPport = static_cast<unsigned int>(((ipPortStruct *) parsedData)->port);
						this->hostInfoUDPOther = new HostInfo(this->UDPhost.c_str());
						affOut("C'est bon, vous êtes bien connecté!");
						break;
					case LOGIN_GROUP_KO:
						ss << "Impossible de se connecter (raison : " << *((std::string *)parsedData) << ")";
						affErr(ss.str().c_str());
						break;
					default:
						ss << "La réponse reçue est inconnue  [" << responseType << "]";
						affErr(ss.str().c_str());
						throw ClientException(ss.str().c_str());
				}
			}
			else if(input == "0"){
				throw QuitApp();
			}
			else{
				affErr("Ce choix n'est pas valable!");
			}
		}catch(const ErrnoException &ex){
			quitAppAfterErr("Application_CIAChat_IN::login()", ex.what());
		}
		catch(const SocketException &ex){
			quitAppAfterErr("Application_CIAChat_IN::login()", ex.what());
		}

		ss.clear();
		ss.str(std::string());
	}
}

std::string Application_CIAChat_IN::getInput(const std::string invit) {
	std::string input;
	while((input = getInputWithEmpty(invit)).empty());
	return input;
}

std::string Application_CIAChat_IN::getInputWithEmpty(const std::string invit) {
	std::string input;

	affOut(invit.c_str());
	getline(std::cin, input);

	return input;
}

void Application_CIAChat_IN::quitAppAfterErr(std::string where, std::string what) {
	std::stringstream ss;
	ss << where << " >> " << what;
	affErr(ss.str().c_str());
	throw QuitApp();
}

void Application_CIAChat_IN::changerFiltre() {
	std::string input;
	bool valid = false;
	affEmptyLine();
	affEmptyLine();

	while(!valid) {
		affEmptyLine();

		affOut("Quel filtre voulez-vous appliquer?");
		affEmptyLine();
		affOut("1. Tout afficher");
		affOut("2. Seulement les questions");
		affOut("3. Seulement les réponses");
		affOut("4. Seulement les Events");
		input = getInput("Votre choix : ");

		if (input == "1"){
			valid = true;
			this->filtreListe = -1;
		}
		else if (input == "2"){
			valid = true;
			this->filtreListe = POST_QUESTION;
		}
		else if (input == "3"){
			valid = true;
			this->filtreListe = ANSWER_QUESTION;
		}
		else if (input == "4") {
			valid = true;
			this->filtreListe = POST_EVENT;
		}
		else
			affErr("Ce choix n'est pas valable!");
	}
}

void Application_CIAChat_IN::afficherConversation() {
	affEmptyLine();
	affEmptyLine();
	affEmptyLine();
	if(this->allMessages.empty())
		affOut("Pas de message pour l'instant.");
	else{
		for(int i=0; i<this->allMessages.size(); i++){
			if(this->filtreListe==-1 || this->filtreListe==this->allMessages[i].getMessageType())
				std::cout << this->allMessages[i] << std::endl;
		}
	}
}

void Application_CIAChat_IN::envoyerMessage() {
	std::string input;
	bool valid = false;

	affEmptyLine();
	affEmptyLine();
	while(!valid) {
		affEmptyLine();

		affOut("Choisissez le type de message à envoyer?");
		affEmptyLine();
		affOut("1. Question");
		affOut("2. Réponse");
		affOut("3. Event");
		affOut("0. annuler");
		input = getInput("Votre choix : ");

		if (input == "0" || input == "1" || input == "2" || input == "3")
			valid = true;
		else
			affErr("Ce choix n'est pas valable!");
	}


	if(input == "1") {
		affOut("Quel question voulez-vous poser?");
		std::string msg = getInput("Votre question : ");

		Message message(POST_QUESTION, this->user, msg, Message::generateTag(this->allMessages), Message::createDigest(msg));
		this->multicastSocket->sendMessage(message);
	}
	else if(input == "2") {
		//choisir question pour avoir tag & digest
		affEmptyLine();
		if(this->questions.empty()){
			affOut("Il n'y a pas de question à laquelle répondre!");
			return;
		}

		affOut("Choisissez la question à laquelle vous voulez répondre : ");
		for(int i=0; i<this->questions.size(); i++){
			std::cout << (i+1) << ". " << this->questions.at(i) << std::endl;
		}
		affEmptyLine();
		std::string choix = getInput("Votre choix : ");

		int qu = std::atoi(choix.c_str());
		if(qu <1 || qu <= this->questions.size()){
			Message question = this->questions.at(qu-1);

			if(question.getDigest() == Message::createDigest(question.getMessage())){
				affOut("Que voulez-vous répondre?");
				std::string msg = getInput("Votre réponse : ");

				Message message(ANSWER_QUESTION, this->user, msg, question.getTag(), question.getDigest());
				this->multicastSocket->sendMessage(message);
			}
			else{
				affErr("La question ne vérifie pas son digest!");
			}
		}
		else{
			affErr("Votre choix n'est pas valable!");
		}
	}
	else if(input == "3") {
		affOut("Quel évenement voulez-vous envoyer?");
		std::string msg = getInput("Votre thème : ");

		Message message(POST_EVENT, this->user, msg, "Info", Message::createDigest(msg));
		this->multicastSocket->sendMessage(message);
	}
}


#pragma clang diagnostic pop