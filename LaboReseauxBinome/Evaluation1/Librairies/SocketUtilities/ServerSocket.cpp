#include "ServerSocket.h"

ServerSocket::ServerSocket() : Socket() {}

ServerSocket::ServerSocket(unsigned int portNumber, const HostInfo &info, const string separator) throw(ErrnoException, SocketException)
		: Socket(portNumber, info, separator) {
	cout << __PRETTY_FUNCTION__ << endl;
	this->maxConnection = SOMAXCONN;
}

ServerSocket::ServerSocket(unsigned int portNumber, const HostInfo &info, const string separator, unsigned int maxConnection) throw(ErrnoException, SocketException)
		: Socket(portNumber, info, separator) {
	if(maxConnection > SOMAXCONN)
		throw SocketException("ServerSocket::ServerSocket(int portNumber, const HostInfo &info, const string endingSeparator, int maxConnection) >> Error, maxConnection is greater than SOMAXCONN.");

	this->maxConnection = maxConnection;
}

ServerSocket::ServerSocket(const ServerSocket &orig) : Socket(orig), maxConnection(orig.maxConnection) {}

ServerSocket::~ServerSocket() {
	if(this->socketHandle!= -1)
		close(this->socketHandle);
}

void ServerSocket::bind() throw(ErrnoException) {
	int sizeStruct = sizeof(struct sockaddr_in);

	if(::bind(this->socketHandle, (struct sockaddr *)&(this->socketAddress), sizeStruct) == -1)
		throw ErrnoException(errno, "void ServerSocket::bind() >> Error binding socket.");
}

void ServerSocket::listen() throw(SocketException, ErrnoException){
	if(this->maxConnection == 0) {
		close(this->socketHandle);
		throw SocketException("void ServerSocket::listen() >> Error, maxConnection is not valid).");
	}

	if( ::listen(this->socketHandle, this->maxConnection) == -1) {
		close(this->socketHandle);
		throw ErrnoException(errno, "void ServerSocket::listen() >> Error trying to listen().");
	}
}

Socket* ServerSocket::accept() {
	int serviceSocketHandle;
	struct sockaddr_in clientAddress;
	int clientAddressLength = sizeof(struct sockaddr_in);

	serviceSocketHandle = ::accept(this->socketHandle, (struct sockaddr *)&clientAddress, (socklen_t *)&clientAddressLength);

	if(serviceSocketHandle == -1) {
		close(this->socketHandle);
		throw ErrnoException(errno, "const Socket *ServerSocket::accept() >> Error accepting connection.");
	}

	ServerSocket *serviceSocket = new ServerSocket(*this);
	serviceSocket->socketHandle = serviceSocketHandle;

	return serviceSocket;
}
