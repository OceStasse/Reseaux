#ifndef APPLICATIONCHECKIN_CLIENTAPPLICATION_H
#define APPLICATIONCHECKIN_CLIENTAPPLICATION_H

#include <string>
#include <cstring>

class ClientException : public std::exception {
protected:
	std::string message;

public:
	ClientException(std::string msg);
	ClientException(const char *msg);
	ClientException(const ClientException &orig);
	~ClientException() throw();
	virtual const char* what() const throw();
};


#endif //APPLICATIONCHECKIN_CLIENTAPPLICATION_H
