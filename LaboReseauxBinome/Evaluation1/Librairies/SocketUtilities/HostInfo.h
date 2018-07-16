#ifndef SOCKETUTILITIES_HOSTINFO_H
#define SOCKETUTILITIES_HOSTINFO_H

#include <netdb.h>
#include <string>
#include <ostream>
#include <arpa/inet.h>
#include <iostream>

#include "../Exceptions/HostException.h"

// PLUS D'INFOS : http://pubs.opengroup.org/onlinepubs/009695399/functions/endhostent.html

class HostInfo {
private:
	bool databaseIsOpen;
	struct hostent *hostPtr;

public:
	HostInfo();
	HostInfo(const  char* hostDescriptor);
	HostInfo(const HostInfo& orig);
	virtual ~HostInfo();

	void openHostDatabase();
	const char* getHostIpAddress() const;
	const char* getHostName() const;

	friend std::ostream& operator<<(std::ostream &os, const HostInfo &info);
};


#endif //SOCKETUTILITIES_HOSTINFO_H
