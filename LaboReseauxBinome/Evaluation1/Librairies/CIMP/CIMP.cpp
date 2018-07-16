#include "CIMP.h"

CIMP::CIMP(const string separator) throw(CIMPException) {
	if(separator.empty())
		throw CIMPException("CIMP::CIMP(const string separator) >> separator is empty.");
	this->separator = separator;
}

CIMP::CIMP(const CIMP &orig){
	this->separator = orig.separator;
}


/*****************************
 **	        EOC/DOC	        **
*****************************/
const string CIMP::encodeEOC() const {
	return encodeNoParam(EOC);
}

const string CIMP::encodeDOC(string reasonWhy) const {
	return encodeOneParam(DOC, reasonWhy);
}

const string CIMP::encodeOK() const {
	return encodeNoParam(OK);
}

const string CIMP::encodeKO(string reasonWhy) const {
	return encodeNoParam(KO);
}


/*****************************
 **	     LOGIN/LOGOUT	    **
*****************************/
const string CIMP::encodeLOGIN_OFFICER(const loginStruct credentials) const throw(CIMPException) {
	if(credentials.login.empty())
		throw CIMPException("CIMP::encodeLOGIN_OFFICER(const loginStruct credentials) >> Error, the login is empty.");

	vector<string> params;
	params.push_back(credentials.login);
	params.push_back(credentials.password);

	return encodeManyParams(LOGIN_OFFICER, params);
}

const string CIMP::encodeLOGIN_OFFICER_OK() const {
	return encodeNoParam(LOGIN_OFFICER_OK);
}

const string CIMP::encodeLOGIN_OFFICER_KO(string reasonWhy) const {
	return encodeOneParam(LOGIN_OFFICER_KO, reasonWhy);
}

const string CIMP::encodeLOGOUT_OFFICER() const {
	return encodeNoParam(LOGOUT_OFFICER);
}

const string CIMP::encodeLOGOUT_OFFICER_OK() const {
	return encodeNoParam(LOGOUT_OFFICER_OK);
}

const string CIMP::encodeLOGOUT_OFFICER_KO(string reasonWhy) const {
	return encodeOneParam(LOGOUT_OFFICER_KO, reasonWhy);
}


/*****************************
 **      CHECK_TICKET       **
*****************************/
const string CIMP::encodeCHECK_TICKET(ticketStruct ticket) const throw(CIMPException) {
	if(ticket.ticketNumber.empty())
		throw CIMPException("CIMP::encodeCHECK_TICKET(ticketStruct ticket) >> Error, the ticket number is empty.");

	vector<string> params;
	params.push_back(ticket.ticketNumber);
	params.push_back(ticket.nbCompanions);

	return encodeManyParams(CHECK_TICKET, params);
}

const string CIMP::encodeCHECK_TICKET_OK() const {
	return encodeNoParam(CHECK_TICKET_OK);
}

const string CIMP::encodeCHECK_TICKET_KO(string reasonWhy) const {
	return encodeOneParam(CHECK_TICKET_KO, reasonWhy);
}


/*****************************
 **      CHECK_LUGGAGE      **
*****************************/
const string CIMP::encodeCHECK_LUGGAGE(vector<luggageStruct> vectorLuggage) const throw(CIMPException) {
	if(vectorLuggage.size()==0)
		return encodeNoParam(CHECK_LUGGAGE);

	vector<string> params;

	for(luggageStruct luggage : vectorLuggage) {
		params.push_back(luggage.ticketNumber);
		params.push_back(luggage.weight);
		params.push_back(luggage.isLuggage ? "TRUE" : "FALSE");
	}

	return encodeManyParams(CHECK_LUGGAGE, params);
}

const string CIMP::encodeCHECK_LUGGAGE_OK(luggageSumStruct luggageResult) const {
	if(luggageResult.ticketNumber.empty())
		throw CIMPException("CIMP::encodeCHECK_LUGGAGE_OK(luggageSumStruct luggageResult) >> Error, the luggage's id is empty.");

	vector<string> params;
	params.push_back(luggageResult.ticketNumber);
	params.push_back(luggageResult.totalWeight);
	params.push_back(luggageResult.excessWeight);
	params.push_back(luggageResult.priceToPay);

	return encodeManyParams(CHECK_LUGGAGE_OK, params);
}

const string CIMP::encodeCHECK_LUGGAGE_KO(string reasonWhy) const {
	return encodeOneParam(CHECK_LUGGAGE_KO, reasonWhy);
}


/*****************************
 **      PAYMENT_DONE       **
*****************************/
const string CIMP::encodePAYMENT_DONE(bool done) const {
	return encodeOneParam(PAYMENT_DONE, (done?"TRUE":"FALSE"));
}

const string CIMP::encodePAYMENT_DONE_OK() const {
	return encodeNoParam(PAYMENT_DONE_OK);
}

const string CIMP::encodePAYMENT_DONE_KO(string reasonWhy) const {
	return encodeOneParam(PAYMENT_DONE_KO, reasonWhy);
}


/*****************************
 **         PARSER          **
*****************************/
const string CIMP::getNextToken(string &msg) const {
	size_t pos = 0;
	string token;

	if ((pos = msg.find(this->separator)) != string::npos) {
		token = msg.substr(0, pos);
		msg.erase(0, pos + this->separator.length());
	}
	return token;
}

const int CIMP::parse(string msg, void **parsedData) const throw(CIMPException) {
	int requestType = -1;
	vector<string> data;
	string temp;

	temp = getNextToken(msg);
	requestType = atoi(temp.c_str());

	switch(requestType){
		case LOGIN_OFFICER:
			*parsedData = new loginStruct;

			((loginStruct *)*parsedData)->login = getNextToken(msg);
			((loginStruct *)*parsedData)->password = msg;
			break;

		case CHECK_TICKET:
			*parsedData = new ticketStruct;

			((ticketStruct *)*parsedData)->ticketNumber = getNextToken(msg);
			((ticketStruct *)*parsedData)->nbCompanions = msg;
			break;
		case CHECK_LUGGAGE: {
			*parsedData = new vector<luggageStruct>;
			vector<string> v;

			while (!(temp = getNextToken(msg)).empty()) {
				v.reserve(1);
				v.push_back(temp);
			}
			v.reserve(1);
			v.push_back(msg);

			for(unsigned int i=0; i<v.size(); i+=3){
				luggageStruct luggage;
				luggage.ticketNumber = v[i];
				luggage.weight = v[i+1];
				luggage.isLuggage = v[i+2]=="TRUE";
				((vector<luggageStruct> *)*parsedData)->reserve(1);
				((vector<luggageStruct> *)*parsedData)->push_back(luggage);
			}
		}
			break;
		case CHECK_LUGGAGE_OK:
			*parsedData = new luggageSumStruct;

			((luggageSumStruct *)*parsedData)->ticketNumber = getNextToken(msg);
			((luggageSumStruct *)*parsedData)->totalWeight = getNextToken(msg);
			((luggageSumStruct *)*parsedData)->excessWeight = getNextToken(msg);
			((luggageSumStruct *)*parsedData)->priceToPay = msg;
			break;

		/*** Returns void ****/
		case EOC:
		case OK:
		case LOGIN_OFFICER_OK:
		case LOGOUT_OFFICER:
		case LOGOUT_OFFICER_OK:
		case CHECK_TICKET_OK:
		case PAYMENT_DONE_OK:
			*parsedData = nullptr;
			break;

		/*** Returns a string ****/
		case DOC:
		case KO:
		case LOGIN_OFFICER_KO:
		case LOGOUT_OFFICER_KO:
		case CHECK_TICKET_KO:
		case CHECK_LUGGAGE_KO:
		case PAYMENT_DONE_KO:
			*parsedData = new string(msg);
			break;

		/*** Returns a boolean ****/
		case PAYMENT_DONE:
			*parsedData = new char;
			cout << "PAYEMENT_DONE=" << msg << endl;
			*((char *)*parsedData) = string(msg) == "TRUE";
			break;

		default:
			throw CIMPException("CIMP::parse(const char *msg, void **parsedData) >> Error, the value for atoi(requestType) is unkown.");
	}

	return requestType;
}


/*****************************
 **	       ENCODERS  	    **
*****************************/
const string CIMP::encodeManyParams(int requestType, vector<string> params) const throw(CIMPException) {
	if(params.empty())
		throw CIMPException("CIMP::encodeManyParams(int requestType, vector<string> params) >> Error, the vector params is empty.");

	stringstream ss;
	ss << requestType;
	for(const string &param : params)
		ss  << this->separator << param;

	return ss.str();
}

const string CIMP::encodeOneParam(int requestType, string msg) const {
	stringstream ss;
	ss << requestType << this->separator << msg;
	return ss.str();
}

const string CIMP::encodeNoParam(const int requestType) const {
	stringstream ss;
	ss << requestType << this->separator;
	return ss.str();
}
