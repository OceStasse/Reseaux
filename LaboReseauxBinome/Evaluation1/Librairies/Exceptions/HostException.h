#ifndef EXCEPTIONS_HOSTEXCEPTION_H
#define EXCEPTIONS_HOSTEXCEPTION_H

#include <string>
#include <cstring>

class HostException : public std::exception {
protected:
	std::string message;

public:
	HostException(const std::string msg);
	HostException(const char *msg);
	HostException(const HostException &orig);
	virtual ~HostException() throw();
	virtual const char* what() const throw();
};
#endif //EXCEPTIONS_HOSTEXCEPTION_H
