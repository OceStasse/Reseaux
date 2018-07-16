#include "SocketException.h"

SocketException::SocketException(const std::string msg) : std::exception(), message(msg) {}

SocketException::SocketException(const char *msg) : std::exception(), message(msg) {}

SocketException::SocketException(const SocketException &orig) : std::exception(orig), message(orig.message) {}

SocketException::~SocketException() throw() {}

const char* SocketException::what() const throw(){
	return this->message.c_str();
}
