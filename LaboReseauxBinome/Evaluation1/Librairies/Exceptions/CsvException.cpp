#include "CsvException.h"

CsvException::CsvException(const string msg) : exception(), message(msg) {}

CsvException::CsvException(const char *msg) : exception(), message(msg) {}

CsvException::CsvException(const CsvException &orig) : exception(orig), message(orig.message) {}

CsvException::~CsvException() throw() {}

const char* CsvException::what() const throw(){
	return this->message.c_str();
}
