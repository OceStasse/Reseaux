#include "HostInfo.h"

HostInfo::HostInfo() : HostInfo("localhost"){}

HostInfo::HostInfo(const char* hostDescriptor) {
	this->openHostDatabase();

	/* Acquisition des informations sur l'ordinateur local */
	if((this->hostPtr = gethostbyname(hostDescriptor)) == NULL)
		throw HostException("HostInfo::HostInfo(string hostDescriptor) >> Could not get the TCPhost by name.");
}

HostInfo::HostInfo(const HostInfo &orig) {
	this->databaseIsOpen = orig.databaseIsOpen;
	this->hostPtr = orig.hostPtr;
}

HostInfo::~HostInfo() {
	if(databaseIsOpen)
		endhostent();
}

void HostInfo::openHostDatabase() {
	endhostent();
	this->databaseIsOpen = true;
	sethostent(true);
}

const char* HostInfo::getHostIpAddress() const {
	if(this->hostPtr == nullptr)
		return "";

	struct in_addr* addr_ptr;
	addr_ptr = (struct in_addr *) *hostPtr->h_addr_list;
	return inet_ntoa(*addr_ptr);
}

const char* HostInfo::getHostName() const {
	if(this->hostPtr == nullptr)
		return "";

	return this->hostPtr->h_name;
}

std::ostream &operator<<(std::ostream &os, const HostInfo &info) {
	os << "       ---------------  HostInfo  -------------------" << std::endl;
	os << "                   Name:     "	<< info.getHostName() << std::endl;
	os << "                 Adress:     " << info.getHostIpAddress() << std::endl;
	os << "       ---------------     Fin     ------------------" << std::endl;

	return os;
}