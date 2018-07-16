#ifndef APPLICATIONCHECKIN_APPLICATION_CHECKIN_H
#define APPLICATIONCHECKIN_APPLICATION_CHECKIN_H

#include <iostream>
#include <iomanip>

#include "../Librairies/Properties/Properties.h"
#include "../Librairies/SocketUtilities/HostInfo.h"
#include "../Librairies/SocketUtilities/ClientSocket.h"
#include "../Librairies/CIMP/CIMP.h"
#include "ClientException.h"
#include "QuitApp.h"

#define affEmptyLine() cout << endl
#define affOut(msg) cout << "\e[34;1m[" << __FUNCTION__ << "] \e[34;0m" << msg << "\e[0m" << endl
#define affErr(msg) cout << "\e[31;1m[" << __FUNCTION__ << "] \e[34;0m" << msg << "\e[0m" << endl

using namespace std;

class Application_CheckIn {
private:
	string tramSeparator;
	string tramEnding;
	string host;
	unsigned int port;
	HostInfo *hostInfo;
	ClientSocket *clientSocket;
	CIMP *cimp;
	ticketStruct ticket;
	luggageSumStruct luggageSum;

	void initSocket() throw(ClientException, QuitApp);
	void readConfigFile(const char *propertiesFilePath) throw(ClientException);

	void showTitle() const;
	void showFlight() const;
	void pushToContinue();
	void login() throw(QuitApp,ClientException);
	void transaction() throw(QuitApp,ClientException);

	string getInput(string invit);
	string getInputWithEmpty(const string invit);
	void quitAppAfterErr(string where, string what);
public:
	Application_CheckIn(const char *propertiesFilePath);
	virtual ~Application_CheckIn();

	void run();

};


#endif //APPLICATIONCHECKIN_APPLICATION_CHECKIN_H
