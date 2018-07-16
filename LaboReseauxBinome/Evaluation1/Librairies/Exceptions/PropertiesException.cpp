#include "PropertiesException.h"

PropertiesException::PropertiesException(const std::string msg) : std::exception(), message(msg) {}

PropertiesException::PropertiesException(const char *msg) : std::exception(), message(msg) {}

PropertiesException::PropertiesException(const PropertiesException &orig) : std::exception(orig), message(orig.message) {}

PropertiesException::~PropertiesException() throw() {}

const char* PropertiesException::what() const throw(){
	return this->message.c_str();
}
