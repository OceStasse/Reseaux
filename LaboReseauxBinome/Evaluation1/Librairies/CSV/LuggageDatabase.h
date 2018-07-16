#ifndef CSV_LUGGAGE_DATABASE_H
#define CSV_LUGGAGE_DATABASE_H


#include "../CIMP/CIMP.h"

class LuggageDatabase {
public:
	virtual bool saveLuggageEntry(luggageStruct luggage) = 0;
	virtual bool saveLuggageEntries(vector<luggageStruct> luggages) = 0;
};


#endif //CSV_LUGGAGE_DATABASE_H
