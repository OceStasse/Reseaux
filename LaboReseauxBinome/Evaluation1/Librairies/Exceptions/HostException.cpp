#include "HostException.h"

HostException::HostException(const std::string msg) : std::exception(), message(msg) {}

HostException::HostException(const char *msg) : std::exception(), message(msg) {}

HostException::HostException(const HostException &orig) : std::exception(orig), message(orig.message) {}

HostException::~HostException() throw() {}

const char* HostException::what() const throw(){
	return this->message.c_str();
}
