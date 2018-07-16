#ifndef SOCKETUTILITIES_SOCKETCLIENT_H
#define SOCKETUTILITIES_SOCKETCLIENT_H

#include "Socket.h"

class ClientSocket : public Socket{
public:
	ClientSocket();
	ClientSocket(unsigned int portNumber, const HostInfo &info, const std::string &separator) throw(ErrnoException, SocketException);
	explicit ClientSocket(const Socket &orig);
	virtual ~ClientSocket();

	void connectToServer() const throw(ErrnoException);
};


#endif //SOCKETUTILITIES_SOCKETCLIENT_H
