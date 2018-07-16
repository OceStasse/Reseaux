#ifndef EXCEPTIONS_CSVEXCEPTION_H
#define EXCEPTIONS_CSVEXCEPTION_H

#include <cstring>
#include <string>

using namespace std;

class CsvException : public exception {
protected:
	string message;

public:
	CsvException(string msg);
	CsvException(const char *msg);
	CsvException(const CsvException &orig);
	virtual ~CsvException() throw();
	virtual const char* what() const throw();
};

#endif //EXCEPTIONS_CSVEXCEPTION_H
