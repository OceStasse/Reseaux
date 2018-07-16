#ifndef TEST_PROJECT_TICKETCSVHANDLER_H
#define TEST_PROJECT_TICKETCSVHANDLER_H

#include <string>
#include <iostream>
#include "CsvHandler.h"
#include "../CIMP/CIMP.h"

class TicketCsvHandler {
private:
	CsvHandler *csvHandler;

public:
	TicketCsvHandler(string filePath, string sep);
	virtual ~TicketCsvHandler();

	bool reservationExists(ticketStruct ticket);
};


#endif //TEST_PROJECT_TICKETCSVHANDLER_H
