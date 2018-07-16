#include "ServerException.h"

ServerException::ServerException(const string msg) : exception(), message(msg) {}

ServerException::ServerException(const char *msg) : exception(), message(msg) {}

ServerException::ServerException(const ServerException &orig) : exception(orig), message(orig.message) {}

ServerException::~ServerException() throw() {}

const char *ServerException::what() const throw() {
	char *returnVal;

	returnVal = new char[this->message.length() + 1];
	strcat(returnVal, this->message.c_str());

	return returnVal;
}
