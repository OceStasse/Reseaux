#ifndef EXCEPTIONS_PROPERTIESEXCEPTION_H
#define EXCEPTIONS_PROPERTIESEXCEPTION_H

#include <string>
#include <cstring>

class PropertiesException : public std::exception {
protected:
	std::string message;

public:
	PropertiesException(std::string msg);
	PropertiesException(const char *msg);
	PropertiesException(const PropertiesException &orig);
	virtual ~PropertiesException() throw();
	virtual const char* what() const throw();
};

#endif //EXCEPTIONS_PROPERTIESEXCEPTION_H
