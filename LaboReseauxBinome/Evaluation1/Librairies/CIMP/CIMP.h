#ifndef CIMP_CIMP_H
#define CIMP_CIMP_H

#include <string>
#include <iostream>
#include <sstream>
#include <vector>
#include <stdlib.h>

#include "../Exceptions/CIMPException.h"

#define EOC                1
#define DOC                2
#define OK                 3
#define KO                 4

#define LOGIN_OFFICER     10
#define LOGIN_OFFICER_OK  11
#define LOGIN_OFFICER_KO  12

#define LOGOUT_OFFICER    20
#define LOGOUT_OFFICER_OK 21
#define LOGOUT_OFFICER_KO 22

#define CHECK_TICKET      30
#define CHECK_TICKET_OK   31
#define CHECK_TICKET_KO   32

#define CHECK_LUGGAGE     40
#define CHECK_LUGGAGE_OK  41
#define CHECK_LUGGAGE_KO  42

#define PAYMENT_DONE      50
#define PAYMENT_DONE_OK   51
#define PAYMENT_DONE_KO   52

using namespace std;


typedef struct{
	string login;
	string password;
} loginStruct;

typedef struct{
	string ticketNumber;
	string nbCompanions;
} ticketStruct;

typedef struct{
	string ticketNumber;
	string weight;
	bool isLuggage;
} luggageStruct;

typedef struct{
	string ticketNumber;
	string totalWeight;
	string excessWeight;
	string priceToPay;
} luggageSumStruct;


class CIMP {
private:
	string separator;

public:
	explicit CIMP(string separator) throw(CIMPException);
	CIMP(const CIMP &orig);

	const string encodeEOC() const;
	const string encodeDOC(string msg) const;
	const string encodeOK() const;
	const string encodeKO(string reasonWhy) const;

	const string encodeLOGIN_OFFICER(loginStruct credentials) const throw(CIMPException);
	const string encodeLOGIN_OFFICER_OK() const;
	const string encodeLOGIN_OFFICER_KO(string reasonWhy) const;

	const string encodeLOGOUT_OFFICER() const;
	const string encodeLOGOUT_OFFICER_OK() const;
	const string encodeLOGOUT_OFFICER_KO(string reasonWhy) const;

	const string encodeCHECK_TICKET(ticketStruct ticket) const throw(CIMPException);
	const string encodeCHECK_TICKET_OK() const;
	const string encodeCHECK_TICKET_KO(string reasonWhy) const;

	const string encodeCHECK_LUGGAGE(vector<luggageStruct> vectorLuggage) const throw(CIMPException);
	const string encodeCHECK_LUGGAGE_OK(luggageSumStruct luggageResult) const;
	const string encodeCHECK_LUGGAGE_KO(string reasonWhy) const;

	const string encodePAYMENT_DONE(bool done) const;
	const string encodePAYMENT_DONE_OK() const;
	const string encodePAYMENT_DONE_KO(string reasonWhy) const;

	const string getNextToken(string &msg) const;
	const int parse(string msg, void **parsedData) const throw(CIMPException);

	const string encodeManyParams(int requestType, vector<string> params) const throw(CIMPException);
	const string encodeOneParam(int requestType, string msg) const;
	const string encodeNoParam(int requestType) const;
};


#endif //CIMP_CIMP_H
