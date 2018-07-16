#ifndef APPLICATIONCHECKIN_CLIENTAPPLICATION_H
#define APPLICATIONCHECKIN_CLIENTAPPLICATION_H

#include <string>
#include <cstring>

using namespace std;

class ClientException : public exception {
protected:
	string message;

public:
	ClientException(string msg);
	ClientException(const char *msg);
	ClientException(const ClientException &orig);
	~ClientException() throw();
	virtual const char* what() const throw();
};


#endif //APPLICATIONCHECKIN_CLIENTAPPLICATION_H
