#include <vector>
#include <sstream>
#include "IACOP.h"

IACOP::IACOP(const std::string separator) throw(IACOPException) {
	if(separator.empty())
		throw IACOPException("IACOP::IACOP(const string separator) >> separator is empty.");
	this->separator = separator;
}

IACOP::IACOP(const IACOP &orig) {
		this->separator = orig.separator;
}

/*****************************
 **	         LOGIN  	    **
*****************************/
const std::string IACOP::encodeLOGIN_GROUP(const loginStruct credentials) const throw(IACOPException) {
	if(credentials.login.empty())
		throw IACOPException("IACOP::encodeLOGIN_GROUP(const loginStruct credentials) >> Error, the login is empty.");

	std::vector<std::string> params;
	params.push_back(credentials.login);
	params.push_back(credentials.password);
	params.push_back(std::to_string(credentials.time));
	params.push_back(std::to_string(credentials.rand));

	return encodeManyParams(LOGIN_GROUP, params);
}


/*****************************
 **         PARSER          **
*****************************/
const std::string IACOP::getNextToken(std::string &msg) const {
	size_t pos = 0;
	std::string token;

	if ((pos = msg.find(this->separator)) != std::string::npos) {
		token = msg.substr(0, pos);
		msg.erase(0, pos + this->separator.length());
	}
	return token;
}

const int IACOP::parse(std::string msg, void **parsedData) const throw(IACOPException) {
	int requestType = -1;
	std::vector<std::string> data;
	std::string temp;

	temp = getNextToken(msg);
	requestType = atoi(temp.c_str());

	switch(requestType){
		case LOGIN_GROUP_OK:
			*parsedData = new ipPortStruct;

			((ipPortStruct *)*parsedData)->ip = getNextToken(msg);
			((ipPortStruct *)*parsedData)->port = atoi(msg.c_str());
			break;
		case LOGIN_GROUP_KO:
			*parsedData = new std::string(msg);
			break;
	}
	return requestType;
}


/*****************************
 **	       ENCODERS  	    **
*****************************/
const std::string IACOP::encodeManyParams(int requestType, std::vector<std::string> params) const throw(IACOPException) {
	if(params.empty())
		throw IACOPException("IACOP::encodeManyParams(int requestType, vector<string> params) >> Error, the vector params is empty.");

	std::stringstream ss;
	ss << requestType;
	for(const std::string &param : params)
		ss  << this->separator << param;

	return ss.str();
}

const std::string IACOP::encodeOneParam(int requestType, std::string msg) const {
	std::stringstream ss;
	ss << requestType << this->separator << msg;
	return ss.str();
}

const std::string IACOP::encodeNoParam(const int requestType) const {
	std::stringstream ss;
	ss << requestType << this->separator;
	return ss.str();
}