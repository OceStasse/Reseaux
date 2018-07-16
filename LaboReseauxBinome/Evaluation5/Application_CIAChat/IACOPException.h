#ifndef EXCEPTIONS_IACOPEXCEPTION_H
#define EXCEPTIONS_IACOPEXCEPTION_H

#include <string>
#include <cstring>

class IACOPException : public std::exception {
protected:
	std::string message;

public:
	IACOPException(std::string msg);
	IACOPException(const char *msg);
	IACOPException(const IACOPException &orig);
	~IACOPException() throw();
	virtual const char* what() const throw();
};


#endif //EXCEPTIONS_IACOPEXCEPTION_H
