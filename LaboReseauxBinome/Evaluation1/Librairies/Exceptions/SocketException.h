#ifndef EXCEPTIONS_SOCKETEXCEPTION_H
#define EXCEPTIONS_SOCKETEXCEPTION_H

#include <string>
#include <cstring>

class SocketException : public std::exception {
protected:
	std::string message;

public:
	SocketException(std::string msg);
	SocketException(const char *msg);
	SocketException(const SocketException &orig);
	virtual ~SocketException() throw();
	virtual const char* what() const throw();
};


#endif //EXCEPTIONS_SOCKETEXCEPTION_H
