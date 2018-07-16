#ifndef SOCKETUTILITIES_SOCKET_H
#define SOCKETUTILITIES_SOCKET_H

#include <string>
#include <ostream>
#include <sstream>
#include <iostream>
#include <netinet/tcp.h>
#include <unistd.h>

#include "../Exceptions/ErrnoException.h"
#include "../Exceptions/SocketException.h"
#include "HostInfo.h"

//#define affSocket(msg) printf("\e[32;1m******\e[0;1m [%s] \e[0m%s\n", __PRETTY_FUNCTION__, msg);fflush(stdout)
#define affSocket(msg) printf("\e[32;1m******\e[0;1m [%s] \e[0m%s\n", __FUNCTION__, msg);fflush(stdout)


class Socket {
protected:
	int socketHandle;
	unsigned int portNumber;
	struct sockaddr_in socketAddress;
	std::string separator;
	size_t mtuSize;

	void fetchMTU() throw(ErrnoException);
	char* findDelimiter(char *buffer, int size);

public:
	Socket();
	Socket(unsigned int portNumber, const HostInfo &info, std::string sep) throw(ErrnoException, SocketException);
	Socket(const Socket &orig);
	virtual ~Socket();

	unsigned int sendMessage(const char* bytesToSend, unsigned int nbBytesToSend) const throw(ErrnoException);
	unsigned int sendMessage(const std::string &messageToSend) const throw(ErrnoException);
	void receiveMessage(char** receivedBytes) throw(ErrnoException);
	void receiveMessage(std::string &messageReceived) throw(ErrnoException);

	int getSocketHandle() const;
	int getPortNumber() const;
	const sockaddr_in &getSocketAddress() const;
	const std::string getSeparator() const;

	friend std::ostream &operator<<(std::ostream &os, const Socket &socket);

};


#endif //SOCKETUTILITIES_SOCKET_H
