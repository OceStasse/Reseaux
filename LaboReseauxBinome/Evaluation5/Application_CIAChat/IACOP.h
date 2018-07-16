#ifndef APPLICATION_CIACHAT_IACOP_H
#define APPLICATION_CIACHAT_IACOP_H

#include <cstdlib>
#include <string>
#include "IACOPException.h"

#define POST_QUESTION	0
#define ANSWER_QUESTION	1
#define POST_EVENT		2

#define LOGIN_GROUP		10
#define LOGIN_GROUP_OK	11
#define LOGIN_GROUP_KO	12

typedef struct{
	std::string login;
	std::string password;
	long time;
	double rand;
} loginStruct;

typedef struct{
	std::string ip;
	int port;
} ipPortStruct;


class IACOP {
private:
	std::string separator;

	const std::string getNextToken(std::string &msg) const;

	const std::string encodeManyParams(int requestType, std::vector<std::string> params) const throw(IACOPException);
	const std::string encodeNoParam(const int requestType) const;
	const std::string encodeOneParam(int requestType, std::string msg) const;

public:
	explicit IACOP(std::string separator) throw(IACOPException);
	IACOP(const IACOP &orig);

	const std::string encodeLOGIN_GROUP(loginStruct loginstruct) const throw(IACOPException);

	const int parse(std::string msg, void **parsedData) const throw(IACOPException);
};


#endif //APPLICATION_CIACHAT_IACOP_H
