#include <iostream>
#include "ErrnoException.h"

ErrnoException::ErrnoException(int errorCode, const std::string msg) : std::exception() {
	this->message = msg + "[" + strerror(errorCode) + "]";
	this->errnoCode = errorCode;
}

ErrnoException::ErrnoException(int errorCode, const char *msg) : std::exception() {
	this->message = std::string(msg) + "[" + strerror(errorCode) + "]";
	this->errnoCode = errorCode;
}

ErrnoException::ErrnoException(const ErrnoException &orig) : std::exception(orig) {
	this->message = orig.message + "[" + strerror(orig.errnoCode) + "]";
	this->errnoCode = orig.errnoCode;
}

ErrnoException::~ErrnoException() throw() {}

const char* ErrnoException::what() const throw(){
	return this->message.c_str();
}

