#ifndef CSV_CSVHANDLER_H
#define CSV_CSVHANDLER_H


#include <sstream>
#include <fstream>
#include <vector>
#include <string>
#include <algorithm>
#include "../Exceptions/CsvException.h"

using namespace std;

class CsvHandler {
private:
    string filePath;
    string separator;
	streampos lastPos;
    mutable pthread_mutex_t mutexCsvFile;

public:
    CsvHandler(string filePath, string sep);
    virtual ~CsvHandler();

	void resetPos();
    int getNbRecords() const throw(CsvException);
    const string getNextRecord()throw(CsvException);
	const bool hasNextRecord() const throw(CsvException);
	void writeRecord(string record) const throw(CsvException);
	void writeRecords(vector<string> records) const throw(CsvException);
    const string createRecord(const vector<string> &fields) const;

	const vector<string> getFields(string record) const;

	const string &getSeparator() const;
};


#endif //CSV_CSVHANDLER_H
