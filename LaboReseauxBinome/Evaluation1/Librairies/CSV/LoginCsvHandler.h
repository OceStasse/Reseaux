#ifndef CSV_LOGINCSVHANDLER_H
#define CSV_LOGINCSVHANDLER_H

#include <string>
#include <iostream>
#include "CsvHandler.h"

using namespace std;

class LoginCsvHandler {
private:
	CsvHandler *csvHandler;

public:
	LoginCsvHandler(string filePath, string sep);
	virtual ~LoginCsvHandler();

	const bool isValid(string username, string password) const;
};


#endif //CSV_LOGINCSVHANDLER_H
