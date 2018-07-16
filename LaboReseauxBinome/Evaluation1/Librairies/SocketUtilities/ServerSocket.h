#ifndef SOCKETUTILITIES_SERVERSOCKET_H
#define SOCKETUTILITIES_SERVERSOCKET_H

#include "Socket.h"

class ServerSocket : public Socket {
private:
	unsigned int maxConnection;

public:
	ServerSocket();
	ServerSocket(unsigned int portNumber, const HostInfo &info, string separator) throw(ErrnoException, SocketException);
	ServerSocket(unsigned int portNumber, const HostInfo &info, string separator, unsigned int maxConnection) throw(ErrnoException, SocketException);
	ServerSocket(const ServerSocket& orig);
	virtual ~ServerSocket();

	void bind() throw(ErrnoException);
	void listen() throw(SocketException, ErrnoException);
	Socket* accept();
};

#endif //SOCKETUTILITIES_SERVERSOCKET_H
