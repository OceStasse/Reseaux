#include "LuggageCsvHandler.h"

LuggageCsvHandler::LuggageCsvHandler(const string filePath, const string sep) {
	this->csvHandler = new CsvHandler(filePath, sep);
}

LuggageCsvHandler::~LuggageCsvHandler() {
	delete this->csvHandler;
}

bool LuggageCsvHandler::saveLuggageEntry(luggageStruct luggage) {
	stringstream ss1;

	ss1 << luggage.ticketNumber + this->csvHandler->getSeparator();
 	ss1 << luggage.weight + this->csvHandler->getSeparator();
	ss1 << (luggage.isLuggage? "VALISE" : "PASVALISE");

	this->csvHandler->writeRecord(ss1.str());

	return true;
}

bool LuggageCsvHandler::saveLuggageEntries(vector<luggageStruct> luggages) {
	stringstream ss1;
	vector<string> records;

	int i=1;
	for(luggageStruct luggage : luggages) {
		ss1 << luggage.ticketNumber + this->csvHandler->getSeparator() << setfill('0') << setw(10) << i++;
		ss1 << luggage.weight + this->csvHandler->getSeparator();
		ss1 << (luggage.isLuggage ? "VALISE" : "PASVALISE");
		records.reserve(1);
		records.push_back(ss1.str());
		ss1.clear();
		ss1.str(string());
	}

	this->csvHandler->writeRecords(records);

	return true;
}
