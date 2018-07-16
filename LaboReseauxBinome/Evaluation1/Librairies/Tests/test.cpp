#include <iostream>
#include <iomanip>
#include <ctime>
#include <regex>
#include <cstring>
#include <sstream>

using namespace std;

bool ticketNumberIsValid(const string ticketNumber);

int main() {
	string ok = "362-02102017-0070";
	string pasOk = "362-22082017-0070";
	string pasOk2 = "362-22082017-00";
	string pasOk3 = "36-22208201700-70";

	cout << boolalpha << "OK = " << ticketNumberIsValid(ok) << endl;
	cout << boolalpha << "pasOk = " << ticketNumberIsValid(pasOk) << endl;
	cout << boolalpha << "pasOk2 = " << ticketNumberIsValid(pasOk2) << endl;
	cout << boolalpha << "pasOk3 = " << ticketNumberIsValid(pasOk3) << endl;

	return 0;
}

bool ticketNumberIsValid(const string ticketNumber){
//	362-22082017-0070
	regex pieces_regex("([0-9]{3})\\-([0-9]{8})\\-([0-9]{4})");
	smatch pieces_match;
	auto t = time(nullptr);
	auto tm = *localtime(&t);
	ostringstream oss;


	oss << put_time(&tm, "%d%m%Y");
	auto str = oss.str();

	return regex_match(ticketNumber, pieces_match, pieces_regex) && ticketNumber.substr(4, 8) == str;
}