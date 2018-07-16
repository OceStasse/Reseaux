#include "ClientSocket.h"

ClientSocket::ClientSocket() : Socket() {
	std::cout << __PRETTY_FUNCTION__ << std::endl;
}

ClientSocket::ClientSocket(unsigned int portNumber, const HostInfo &info, const std::string &separator) throw(ErrnoException, SocketException)
		: Socket(portNumber, info, separator) {
	std::cout << __PRETTY_FUNCTION__ << std::endl;
}

ClientSocket::ClientSocket(const Socket &orig) : Socket(orig) {
	std::cout << __PRETTY_FUNCTION__ << std::endl;
}

void ClientSocket::connectToServer() const throw(ErrnoException) {
	std::cout << __PRETTY_FUNCTION__ << std::endl;
	if(connect(this->getSocketHandle(), (struct sockaddr *) &(this->socketAddress), sizeof(struct sockaddr_in)) == -1)
		throw ErrnoException(errno, "void ClientSocket::connectToTCPServer() >> Error trying to connect to server.");
}

ClientSocket::~ClientSocket() {
	if(this->socketHandle!= -1)
		close(this->socketHandle);
}
