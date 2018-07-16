#ifndef SERVEURCHECKIN_SERVEUR_CHECKIN_H
#define SERVEURCHECKIN_SERVEUR_CHECKIN_H

#include <vector>

#include "Serveur_CheckInThread.h"
#include "ServerException.h"
#include "../Librairies/Exceptions/ErrnoException.h"
#include "../Librairies/SocketUtilities/HostInfo.h"
#include "../Librairies/SocketUtilities/ServerSocket.h"
#include "../Librairies/CIMP/CIMP.h"
#include "../Librairies/CSV/LoginCsvHandler.h"
#include "../Librairies/Properties/Properties.h"

//#define affServeur(lvl, msg) printf("\e[33;1m|%.*s>\e[0;1m [%s] \e[0m%s\n", (lvl)*2, "========================", __PRETTY_FUNCTION__, msg);fflush(stdout)
#define affServeur(lvl, msg) printf("\e[33;1m|%.*s>\e[0;1m [%s] \e[0m%s\n", (lvl)*2, "========================", __FUNCTION__, msg);fflush(stdout)

using namespace std;

class Serveur_CheckIn {
private:
	string tramSeparator;
	string tramEnding;
	string host;

	unsigned int port;
	unsigned int maxNbClient;

	HostInfo *hostInfo;
	ServerSocket *socketEcoute;
	Socket *socketService;
	vector<pthread_t> tabThread;

public:
	bool newSocketHasBeenAdded;
	string dbPath;
	string loginFilePath;
	string ticketFilePath;
	string csvSeparator;
	CIMP *cimp;
	vector<Socket *> tabConnectedSocket;
	mutable pthread_mutex_t mutexNbConnectedClients;
	mutable pthread_cond_t condNbConnectedClients;

	explicit Serveur_CheckIn(const char *propertiesFilePath);
	virtual ~Serveur_CheckIn();

	void readConfigFile(const char *propertiesFilePath) throw(ServerException);
	void init() throw(ErrnoException);
	void run() throw(ServerException);
};


#endif //SERVEURCHECKIN_SERVEUR_CHECKIN_H
