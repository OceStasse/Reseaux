#include "ClientException.h"

ClientException::ClientException(const std::string msg) : exception(), message(msg) {}

ClientException::ClientException(const char *msg) : exception(), message(msg) {}

ClientException::ClientException(const ClientException &orig) : exception(orig), message(orig.message) {}

ClientException::~ClientException() throw() {}

const char *ClientException::what() const throw() {
	char *returnVal;

	returnVal = new char[this->message.length() + 1];
	strcat(returnVal, this->message.c_str());

	return returnVal;
}
