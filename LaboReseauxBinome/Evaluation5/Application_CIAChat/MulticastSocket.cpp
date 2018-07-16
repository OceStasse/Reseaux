#include "MulticastSocket.h"


MulticastSocket::MulticastSocket() : socketHandle(-1) {}

MulticastSocket::MulticastSocket(unsigned int portNumber, const HostInfo &infoLocal, const HostInfo &infoOther, std::string sep, std::string finTrame) throw(ErrnoException, SocketException) {
	/* Création de la socket */
	if( (this->socketHandle = socket(AF_INET, SOCK_DGRAM, 0)) == -1)
		throw ErrnoException(errno, "MulticastSocket::MulticastSocket(int portNumber, const HostInfo &info, string endingSeparator) >> error trying to create MulticastSocket().");

	if(portNumber==0)
		throw SocketException("MulticastSocket::MulticastSocket(int portNumber, const HostInfo &info, string endingSeparator) >> the TCPport number can't be 0.");
	this->portNumber = portNumber;

	if(finTrame.empty() || finTrame.length()==0)
		throw SocketException("MulticastSocket::MulticastSocket(int portNumber, const HostInfo &info, string endingSeparator) >> the finTrame is empty.");
	this->finTrame = finTrame;

	if(sep.empty() || sep.length()==0)
		throw SocketException("MulticastSocket::MulticastSocket(int portNumber, const HostInfo &info, string endingSeparator) >> the separator is empty.");
	this->separator = sep;

	/* Préparation de la structure sockaddr_in du Local */
	memset(&(this->socketAddressLocal), 0, sizeof(struct sockaddr_in));
	this->socketAddressLocal.sin_family = AF_INET;
	this->socketAddressLocal.sin_addr.s_addr = inet_addr(infoLocal.getHostIpAddress());

	/* Préparation de la structure sockaddr_in du Other */
	memset(&(this->socketAddressOther), 0, sizeof(struct sockaddr_in));
	this->socketAddressOther.sin_family = AF_INET;
	this->socketAddressOther.sin_port = htons(this->portNumber);
	this->socketAddressOther.sin_addr.s_addr = inet_addr(infoOther.getHostIpAddress());

	std::cout << "socketHandle1=" << this->socketHandle << std::endl;
	if( bind(this->socketHandle, (struct sockaddr *) &(this->socketAddressLocal), sizeof(this->socketAddressLocal)) < 0)
		throw ErrnoException(errno, "MulticastSocket::MulticastSocket(int portNumber, const HostInfo &info, string endingSeparator) >> error trying to create MulticastSocket().");

	int reuse=1;
	setsockopt(this->socketHandle, SOL_SOCKET, SO_REUSEPORT, (char *)reuse, sizeof(reuse));

	unsigned char ttl = 2;
	setsockopt(this->socketHandle, IPPROTO_IP, IP_MULTICAST_TTL, &ttl, sizeof(ttl));

	memcpy(&(this->mreq.imr_multiaddr), &(this->socketAddressOther.sin_addr), sizeof(this->socketAddressOther));
	this->mreq.imr_interface.s_addr = inet_addr("255.255.255.255");
	setsockopt(this->socketHandle, IPPROTO_IP, IP_ADD_MEMBERSHIP, &(this->mreq), sizeof(this->mreq));
}

MulticastSocket::MulticastSocket(const MulticastSocket &orig) {
	this->socketHandle = orig.socketHandle;
	this->portNumber = orig.portNumber;
	memcpy(&(this->socketAddressLocal), &(orig.socketAddressLocal), sizeof(struct sockaddr_in));
	memcpy(&(this->socketAddressOther), &(orig.socketAddressOther), sizeof(struct sockaddr_in));
	this->separator = orig.separator;
	this->finTrame = orig.finTrame;
}

MulticastSocket::~MulticastSocket() {
	if(this->socketHandle!= -1) {
		setsockopt(this->socketHandle, IPPROTO_IP, IP_DROP_MEMBERSHIP, &(this->mreq), sizeof(this->mreq));
		close(this->socketHandle);
	}
}

std::ostream &operator<<(std::ostream &os, const MulticastSocket &socket) {
	os << "       --------------- Socket ---------------" << std::endl;
	os << "       		Socket Id:     " << socket.getSocketHandle() << std::endl;
	os << "       		   Port #:     " << socket.getPortNumber() << std::endl;
	os << "       ---------------  Fin   ---------------" << std::endl;
	return os;
}

int MulticastSocket::getSocketHandle() const {
	return this->socketHandle;
}

int MulticastSocket::getPortNumber() const {
	return this->portNumber;
}

unsigned int MulticastSocket::sendMessage(const Message &message) const throw(ErrnoException) {
	std::stringstream ss;

	ss << message.getMessageType() << this->separator;
	ss << message.getUser() << this->separator;
	ss << message.getTime() << this->separator;
	ss << message.getTag() << this->separator;
	ss << message.getMessage() << this->separator;
	ss << message.getDigest() << this->finTrame;

	std::string msgToSend = ss.str();
	std::string msgtoShow = "Message to send : " + msgToSend;
	affSocket(msgtoShow.c_str());
	char *buffer = new char[msgToSend.length()+1];
	memcpy(buffer, msgToSend.c_str(), msgToSend.length());
	buffer[msgToSend.length()] = '\0';
	sendto(this->socketHandle, buffer, msgToSend.length(), 0, (struct sockaddr *)(&this->socketAddressOther), sizeof(struct sockaddr_in));

	delete[] buffer;

	return msgToSend.length();
}

Message* MulticastSocket::receiveMessage() throw(ErrnoException) {
	size_t sizeToFetch, totalSizeFetched;
	ssize_t sizeFetched;
	char *buffer;

	buffer = new char[1000];
	memset(buffer, '\0', 1000);
	std::cout << "socketHandle2=" << this->socketHandle << std::endl;
	if( recvfrom(this->socketHandle, buffer, 1000, 0, (struct sockaddr*)&(this->socketAddressLocal), (socklen_t  *)sizeof(this->socketAddressLocal)) ==-1 ) {
		printf("Erreur sur le recvfrom de la socket %d\n", errno);
		throw ErrnoException(EPIPE,
							 "MulticastSocket::receiveMessage(char* receivedBytes, int flag) >> The connection closed without warning.");
	}
	{
		std::stringstream ss;
		ss << "Received message's total size : " << totalSizeFetched << "(endSep included).";
		affSocket(ss.str().c_str());
	}
	totalSizeFetched -= this->separator.length();

	// messageSplit[0] : type message (question, réponse, evenement => voir ProtocolMUCOP_UDP)
	// messageSplit[1] : utilisateur qui envoie le message
	// messageSplit[2] : heure d'envoi
	// messageSplit[3] : tag
	// messageSplit[4] : message
	// messageSplit[5] : digest

	/*** On copie les bytes dans une instance de Message***/
	std::stringstream ss;
	ss << buffer;
	std::string messageString = ss.str();

	int count = 0;
	size_t nPos = messageString.find("hello", 0); // fist occurrence
	while(nPos != std::string::npos)
	{
		count++;
		nPos = messageString.find("hello", nPos+1);
	}

	if(count==5) {
		int typeMessage = atoi(getNextToken(messageString).c_str());
		std::string user = getNextToken(messageString);
		std::string time = getNextToken(messageString);
		std::string tag = getNextToken(messageString);
		std::string mes = getNextToken(messageString);
		int digest = atoi(getNextToken(messageString).c_str());

		if (typeMessage == POST_QUESTION || typeMessage == ANSWER_QUESTION || typeMessage == POST_EVENT)
			return new Message(typeMessage, user, time, tag, digest);
	}
	return nullptr;
}

const std::string MulticastSocket::getNextToken(std::string &msg) const {
	size_t pos = 0;
	std::string token;

	if ((pos = msg.find(this->separator)) != std::string::npos) {
		token = msg.substr(0, pos);
		msg.erase(0, pos + this->separator.length());
	}
	return token;
}