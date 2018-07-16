#ifndef EXCEPTIONS_CIMPEXCEPTION_H
#define EXCEPTIONS_CIMPEXCEPTION_H

#include <string>
#include <cstring>

using namespace std;

class CIMPException : public exception {
protected:
	string message;

public:
	CIMPException(string msg);
	CIMPException(const char *msg);
	CIMPException(const CIMPException &orig);
	~CIMPException() throw();
	virtual const char* what() const throw();
};


#endif //EXCEPTIONS_CIMPEXCEPTION_H
