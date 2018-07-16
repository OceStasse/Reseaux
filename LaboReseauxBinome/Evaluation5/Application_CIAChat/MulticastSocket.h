#ifndef APPLICATION_CIACHAT_MULTICASTSOCKET_H
#define APPLICATION_CIACHAT_MULTICASTSOCKET_H

#include "../../Evaluation1/Librairies/Exceptions/SocketException.h"
#include "../../Evaluation1/Librairies/Exceptions/ErrnoException.h"
#include "../../Evaluation1/Librairies/SocketUtilities/HostInfo.h"
#include "../../Evaluation1/Librairies/SocketUtilities/Socket.h"
#include "Message.h"

//#define affSocket(msg) printf("\e[32;1m******\e[0;1m [%s] \e[0m%s\n", __PRETTY_FUNCTION__, msg);fflush(stdout)
#define affSocket(msg) printf("\e[32;1m******\e[0;1m [%s] \e[0m%s\n", __FUNCTION__, msg);fflush(stdout)

class MulticastSocket {
private:
	int socketHandle;
	unsigned int portNumber;
	std::string separator;
	std::string finTrame;
	struct sockaddr_in socketAddressLocal;
	struct sockaddr_in socketAddressOther;
	struct ip_mreq mreq;

	const std::string getNextToken(std::string &msg) const;
	char* findDelimiter(char *buffer, int size);

public:
	MulticastSocket();
	MulticastSocket(unsigned int portNumber, const HostInfo &infoLocal, const HostInfo &infoOther, std::string sep, std::string finTrame) throw(ErrnoException, SocketException);
	MulticastSocket(const MulticastSocket &orig);
	virtual ~MulticastSocket();

	unsigned int sendMessage(const Message &message) const throw(ErrnoException);
	Message* receiveMessage() throw(ErrnoException);

	int getSocketHandle() const;
	int getPortNumber() const;

	friend std::ostream &operator<<(std::ostream &os, const MulticastSocket &socket);
};


#endif //APPLICATION_CIACHAT_MULTICASTSOCKET_H
