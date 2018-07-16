#ifndef APPLICATION_CIACHAT_APPLICATION_CIACHAT_IN_H
#define APPLICATION_CIACHAT_APPLICATION_CIACHAT_IN_H

#include <iostream>
#include <string>
#include <chrono>
#include <random>
#include <cfloat>
#include <vector>

#include "../../Evaluation1/Librairies/SocketUtilities/HostInfo.h"
#include "../../Evaluation1/Librairies/SocketUtilities/ClientSocket.h"
#include "../../Evaluation1/Librairies/Properties/Properties.h"
#include "ClientException.h"
#include "IACOP.h"
#include "QuitApp.h"
#include "Message.h"
#include "MulticastSocket.h"
#include "ThreadReception.h"

#define affEmptyLine() std::cout << std::endl
#define affOut(msg) std::cout << "\e[34;1m[" << __FUNCTION__ << "] \e[34;0m" << msg << "\e[0m" << std::endl
#define affErr(msg) std::cout << "\e[31;1m[" << __FUNCTION__ << "] \e[34;0m" << msg << "\e[0m" << std::endl

class Application_CIAChat_IN {
private:
	std::string tramSeparator;
	std::string tramEnding;
	std::string TCPhost;
	unsigned int TCPport;
	std::string UDPhost;
	unsigned int UDPport;
	HostInfo *hostInfoTCPServeur;
	HostInfo *hostInfoUDPLocal;
	HostInfo *hostInfoUDPOther;
	ClientSocket *clientSocket;
	IACOP *iacop;

	MulticastSocket *multicastSocket;
	std::string user;
	std::vector<Message> allMessages;
	std::vector<Message> questions;
	int filtreListe=-1;
	ThreadReception *threadReception;

	void readConfigFile(const char *propertiesFilePath) throw(ClientException);
	void connectToTCPServer() throw(ClientException, QuitApp);
	void login() throw(ClientException, QuitApp);

	void showTitle() const;
	int chooseAction();
	void pushToContinue() const;
	std::string getInput(std::string invit);
	std::string getInputWithEmpty(const std::string invit);
	void quitAppAfterErr(std::string where, std::string what);

public:
	explicit Application_CIAChat_IN(const char *propertiesFilePath);
	virtual ~Application_CIAChat_IN();

	void run();

	void changerFiltre();

	void afficherConversation();

	void envoyerMessage();
};


#endif //APPLICATION_CIACHAT_APPLICATION_CIACHAT_IN_H
