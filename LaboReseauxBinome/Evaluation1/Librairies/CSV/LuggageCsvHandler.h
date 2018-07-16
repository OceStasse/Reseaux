#ifndef CSV_LUGGAGECSVHANDLER_H
#define CSV_LUGGAGECSVHANDLER_H

#include <iomanip>

#include "LuggageDatabase.h"
#include "CsvHandler.h"

using namespace std;

class LuggageCsvHandler : public LuggageDatabase {
protected:
	CsvHandler *csvHandler;

public:
	LuggageCsvHandler(string filePath, string sep);
	virtual ~LuggageCsvHandler();

	virtual bool saveLuggageEntry(luggageStruct luggage);
	virtual bool saveLuggageEntries(vector<luggageStruct> luggages);
};


#endif //CSV_LUGGAGECSVHANDLER_H
