#include "CIMPException.h"

CIMPException::CIMPException(const string msg) : exception(), message(msg) {}

CIMPException::CIMPException(const char *msg) : exception(), message(msg) {}

CIMPException::CIMPException(const CIMPException &orig) : exception(orig), message(orig.message) {}

CIMPException::~CIMPException() throw() {}

const char* CIMPException::what() const throw(){
	return this->message.c_str();
}