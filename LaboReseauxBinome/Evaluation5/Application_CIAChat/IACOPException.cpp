#include "IACOPException.h"

IACOPException::IACOPException(const std::string msg) : exception(), message(msg) {}

IACOPException::IACOPException(const char *msg) : exception(), message(msg) {}

IACOPException::IACOPException(const IACOPException &orig) : exception(orig), message(orig.message) {}

IACOPException::~IACOPException() throw() {}

const char* IACOPException::what() const throw(){
	return this->message.c_str();
}