#include "Socket.h"

Socket::Socket() : socketHandle(-1) {}

Socket::Socket(unsigned int portNumber, const HostInfo &info, std::string sep) throw(ErrnoException, SocketException){
	/* Création de la socket */
	if( (this->socketHandle = socket(AF_INET, SOCK_STREAM, 0)) == -1)
		throw ErrnoException(errno, "Socket::Socket(int portNumber, const HostInfo &info, string endingSeparator) >> error trying to create socket().");

	if(portNumber==0)
		throw SocketException("Socket::Socket(int portNumber, const HostInfo &info, string endingSeparator) >> the TCPport number can't be 0.");
	this->portNumber = portNumber;

	if(sep.empty())
		throw SocketException("Socket::Socket(int portNumber, const HostInfo &info, string endingSeparator) >> the separator is empty.");
	this->separator = sep;

	/* Préparation de la structure sockaddr_in */
	this->socketAddress.sin_family = AF_INET;
	this->socketAddress.sin_port = htons(this->portNumber);
	this->socketAddress.sin_addr.s_addr = inet_addr(info.getHostIpAddress());
}

Socket::Socket(const Socket &orig) {
	this->socketHandle = orig.socketHandle;
	this->portNumber = orig.portNumber;
	this->socketAddress = orig.socketAddress;
	this->separator = orig.separator;
	this->mtuSize = orig.mtuSize;
}

Socket::~Socket() {
	if(this->socketHandle != -1)
		close(this->socketHandle);
}

int Socket::getSocketHandle() const {
	return this->socketHandle;
}

int Socket::getPortNumber() const {
	return this->portNumber;
}

const sockaddr_in &Socket::getSocketAddress() const {
	return this->socketAddress;
}

const std::string Socket::getSeparator() const {
	return this->separator;
}

void Socket::fetchMTU() throw(ErrnoException) {
	std::stringstream ss;
	int intSize = sizeof(int);
	size_t temp = 0;


	if ((getsockopt(this->socketHandle, IPPROTO_TCP, TCP_MAXSEG, &temp, (socklen_t *) &intSize)) == -1)
		throw ErrnoException(errno, "void Socket::initMTU() >> Error getting MTU.");

	this->mtuSize = temp;
	ss << "MTU size : " << this->mtuSize << ".";
	affSocket(ss.str().c_str());
}

void Socket::receiveMessage(std::string &messageReceived) throw(ErrnoException) {
	char *message;
	receiveMessage(&message);

	messageReceived =  message;

	std::stringstream ss;
	ss << "Received message : " << messageReceived << ".";
	affSocket(ss.str().c_str());
}

void Socket::receiveMessage(char **receivedBytes) throw(ErrnoException) {
	affSocket("Receiving message...");

	size_t sizeToFetch, totalSizeFetched;
	ssize_t sizeFetched;
	char *buffer, *msg, *pos;


	/*** Recherche du MTU pour la taille du buffer ***/
	fetchMTU();

	sizeToFetch = this->mtuSize;
	sizeFetched = totalSizeFetched = 0;
	msg = new char[totalSizeFetched+1];
	do{
		buffer = new char[sizeToFetch+1];
		memset(buffer, '\0', sizeToFetch+1);

		if((sizeFetched = recv(this->socketHandle, (void *) buffer, sizeToFetch, 0)) <=0)
			throw ErrnoException(EPIPE, "Socket::receiveMessage(char* receivedBytes, int flag) >> The connection closed without warning.");

//		on stocke le tout dans msg en réadaptant la taille
		char *temp = new char[totalSizeFetched+1];
		memcpy(temp, msg, totalSizeFetched);
		delete [] msg;
		msg = new char[totalSizeFetched + sizeFetched];
		memcpy(msg, temp, totalSizeFetched);
		delete [] temp;
		memcpy(msg+totalSizeFetched, buffer, sizeFetched);
		delete [] buffer;
		totalSizeFetched += sizeFetched;
	}while((pos=findDelimiter(msg, totalSizeFetched))== nullptr);

	{
		std::stringstream ss;
		ss << "Received message's total size : " << totalSizeFetched << "(endSep included).";
		affSocket(ss.str().c_str());
	}
	totalSizeFetched -= this->separator.length();

	/*** On copie les bytes dans le pointeur de retour ***/
	*receivedBytes = (char *) malloc(totalSizeFetched+1);
	memset(*receivedBytes, '\0', totalSizeFetched+1);
	memcpy(*receivedBytes, msg, totalSizeFetched);
	delete [] msg;
}

unsigned int Socket::sendMessage(const std::string &messageToSend) const throw(ErrnoException){
	std::stringstream ss;
	ss << "Message to send : '" << messageToSend << "'.";
	affSocket(ss.str().c_str());

	return sendMessage(messageToSend.c_str(), static_cast<int>(messageToSend.length()));
}

unsigned int Socket::sendMessage(const char* bytesToSend, unsigned int nbBytesToSend) const throw(ErrnoException){
	affSocket("Sending message...");

	char *buffer, *ptr;
	int nbBytesSent;

	/*** CONSTRUCTION DU MESSAGE À ENVOYER ***/
	// format : bytesSEPFIN
	buffer = new char[nbBytesToSend+this->separator.length()];
	ptr = buffer;
	memcpy(ptr, bytesToSend, nbBytesToSend);
	memcpy(buffer+nbBytesToSend, this->separator.c_str(), this->separator.length());
	nbBytesToSend += this->separator.length();

	nbBytesSent = send(this->socketHandle, (void *) buffer, nbBytesToSend, 0);

	if (nbBytesSent == -1)
		throw ErrnoException(errno, "Socket::sendMessage(const char* bytesToSend, unsigned int nbBytesToSend, int flag) >> could not send message.");

	delete [] buffer;

	std::stringstream ss;
	ss << "Message sent successfully! (" << nbBytesSent << " bytes in total).";
	affSocket(ss.str().c_str());

	return nbBytesSent;
}

std::ostream &operator<<(std::ostream &os, const Socket &socket) {
	os << "       --------------- Socket ---------------" << std::endl;
	os << "       		Socket Id:     " << socket.getSocketHandle() << std::endl;
	os << "       		   Port #:     " << socket.getPortNumber() << std::endl;
	os << "       ---------------  Fin   ---------------" << std::endl;
	return os;
}

char *Socket::findDelimiter(char *buffer, int size) {
	std::string msg = std::string(buffer);

	size_t found = msg.find(this->separator);

	if (found!=std::string::npos)
		return buffer+found;

	return nullptr;
}
