#include "TicketCsvHandler.h"

TicketCsvHandler::TicketCsvHandler(const string filePath, const string sep) {
	this->csvHandler = new CsvHandler(filePath, sep);
}

TicketCsvHandler::~TicketCsvHandler() {
	delete this->csvHandler;
}

bool TicketCsvHandler::reservationExists(ticketStruct ticket) {
	bool exists = false;
	string line, encodedLine;

	encodedLine = ticket.ticketNumber + this->csvHandler->getSeparator() + ticket.nbCompanions;
	this->csvHandler->resetPos();
	while (this->csvHandler->hasNextRecord() && !exists){
		line = this->csvHandler->getNextRecord();
		exists = this->csvHandler->getFields(line).size()==2 && encodedLine==line;
	}
	return exists;
}
