#ifndef SERVEURCHECKIN_SERVEREXCEPTION_H
#define SERVEURCHECKIN_SERVEREXCEPTION_H

#include <string>
#include <cstring>

using namespace std;

class ServerException : public exception {
protected:
	string message;
public:
	ServerException(string msg);
	ServerException(const char *msg);
	ServerException(const ServerException &orig);
	~ServerException() throw();
	virtual const char* what() const throw();
};


#endif //SERVEURCHECKIN_SERVEREXCEPTION_H
