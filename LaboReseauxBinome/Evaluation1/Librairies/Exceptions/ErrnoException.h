#ifndef EXCEPTIONS_ERRNOEXCEPTION_H
#define EXCEPTIONS_ERRNOEXCEPTION_H

#include <cerrno>
#include <cstring>

class ErrnoException : public std::exception {
protected:
	std::string message;
	int errnoCode;

public:
	ErrnoException(int errorCode, std::string msg);
	ErrnoException(int errorCode, const char *msg);
	ErrnoException(const ErrnoException& orig);
	virtual ~ErrnoException() throw();
	virtual const char* what() const throw();
};


#endif //EXCEPTIONS_ERRNOEXCEPTION_H
