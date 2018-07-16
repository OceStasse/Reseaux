#include "LoginCsvHandler.h"

LoginCsvHandler::LoginCsvHandler(const string filePath, const string sep) {
	this->csvHandler = new CsvHandler(filePath, sep);
}

LoginCsvHandler::~LoginCsvHandler() {
	delete this->csvHandler;
}

const bool LoginCsvHandler::isValid(string username, string password) const {
	bool isValid = false;
	string credential;

	this->csvHandler->resetPos();
	while(this->csvHandler->hasNextRecord() && !isValid){
		credential = this->csvHandler->getNextRecord();
		if( this->csvHandler->getFields(credential).size() ==2 &&
				username == this->csvHandler->getFields(credential)[0] &&
				password == this->csvHandler->getFields(credential)[1]) {
			isValid = true;
		}
	}

	return isValid;
}
