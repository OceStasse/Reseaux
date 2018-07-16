#include "CsvHandler.h"

CsvHandler::CsvHandler(const string filePath, const string sep) {
    this->filePath = filePath;
    this->separator = sep;
	this->lastPos = 0;

    pthread_mutex_init(&(this->mutexCsvFile), NULL);
}

CsvHandler::~CsvHandler() {
    pthread_mutex_destroy(&(this->mutexCsvFile));
}


void CsvHandler::resetPos(){
	this->lastPos = 0;
}

int CsvHandler::getNbRecords() const throw(CsvException){
    int nbrecord = 0;
    string record;

    pthread_mutex_lock(&(this->mutexCsvFile));

    ifstream fileStream(this->filePath.c_str(), ios::in);

    if(!fileStream.is_open()){
		pthread_mutex_unlock(&(this->mutexCsvFile));
        throw CsvException("CsvHandler::getNbRecords() >> Could not open the file : " + this->filePath);
    }

    while(!fileStream.eof()){
        record.clear();
        getline(fileStream, record);

        if(record.length() != 0){
            nbrecord++;
        }
    }

	fileStream.close();

    pthread_mutex_unlock(&(this->mutexCsvFile));
    return nbrecord;
}

const string CsvHandler::getNextRecord() throw(CsvException){
	string record = "";

	pthread_mutex_lock(&(this->mutexCsvFile));

	ifstream fileStream(this->filePath.c_str(), ios::in);

	if(!fileStream.is_open()){
		pthread_mutex_unlock(&(this->mutexCsvFile));
		throw CsvException("CsvHandler::getNextRecord() >> Could not open the file : " + this->filePath);
	}

	fileStream.seekg(this->lastPos);

	while(record.length() < 2){
		getline(fileStream, record);
	}
	this->lastPos = fileStream.tellg();

	fileStream.close();

	pthread_mutex_unlock(&(this->mutexCsvFile));
    return record;
}

const bool CsvHandler::hasNextRecord() const throw(CsvException){
	bool hasNext = false;
	streampos end;
	pthread_mutex_lock(&(this->mutexCsvFile));

	ifstream fileStream(this->filePath.c_str(), ios::in);

	if(!fileStream.is_open()){
		pthread_mutex_unlock(&(this->mutexCsvFile));
		throw CsvException("CsvHandler::hasNextRecord() >> Could not open the file : " + this->filePath);
	}
	fileStream.seekg(this->lastPos, ios::beg);

	if(fileStream.peek() != EOF)
		hasNext = true;

	fileStream.close();

	pthread_mutex_unlock(&(this->mutexCsvFile));

	return hasNext;
}

const vector<string> CsvHandler::getFields(const string record) const {
	size_t pos = 0;
	string field, tempRecord;
	vector<string> fields;

	tempRecord = record;
	while ((pos = tempRecord.find(this->separator)) != string::npos) {
		field = tempRecord.substr(0, pos);
		fields.push_back(field);
		tempRecord.erase(0, pos + (this->separator).length());
		field.clear();
	}
	fields.push_back(tempRecord);

	return fields;
}

const string CsvHandler::createRecord(const vector<string> &fields) const {
	stringstream ss;
	size_t i;

	for(i=0; i< fields.size(); i++){
		ss << fields[i];

		if(i!=fields.size()-1){
			ss << ";";
		}
	}

	return ss.str();
}

void CsvHandler::writeRecord(const string record) const throw(CsvException){
	pthread_mutex_lock(&(this->mutexCsvFile));

	ofstream fileStream(this->filePath.c_str(), ios::out | ios::app);

	if(!fileStream.is_open()){
		pthread_mutex_unlock(&(this->mutexCsvFile));
		throw CsvException("CsvHandler::writeRecord(const string record) >> Could not open the file : " + this->filePath);
	}

	fileStream << record << "\n";

	if(fileStream.bad()){
		fileStream.close();
		pthread_mutex_unlock(&(this->mutexCsvFile));
		throw CsvException("CsvHandler::writeRecord(const string record) >> Could not write in the file : " + this->filePath);
	}

	fileStream.close();

	pthread_mutex_unlock(&(this->mutexCsvFile));
}

void CsvHandler::writeRecords(vector<string> records) const throw(CsvException){
	pthread_mutex_lock(&(this->mutexCsvFile));

	ofstream fileStream(this->filePath.c_str(), ios::out | ios::app);

	if(!fileStream.is_open()){
		pthread_mutex_unlock(&(this->mutexCsvFile));
		throw CsvException("CsvHandler::writeRecord(const string record) >> Could not open the file : " + this->filePath);
	}

	for(string record : records) {
		fileStream << record << "\n";

		if(fileStream.bad()){
			fileStream.close();
			pthread_mutex_unlock(&(this->mutexCsvFile));
			throw CsvException("CsvHandler::writeRecord(const string record) >> Could not write in the file : " + this->filePath);
		}
	}

	fileStream.close();

	pthread_mutex_unlock(&(this->mutexCsvFile));
}

const string& CsvHandler::getSeparator() const {
	return separator;
}
