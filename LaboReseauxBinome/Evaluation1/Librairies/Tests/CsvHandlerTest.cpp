#include <iostream>
#include <vector>
#include "../CSV/CsvHandler.h"

#define csvFilePath "Librairies/Tests/test.csv"
#define csvWrongFilePath "Librairies/Tests/FICHIERINEXISTANT.csv"

int main(){
	vector<string> fields;
	string record;

	cout << "Début des tests..." << endl;

	CsvHandler *csvHandler = new CsvHandler(csvFilePath, ";");

	cout << "***** Test de lecture d'un fichier existant *****" << endl;
	cout << "    Nombre de lignes du fichier CSV : " << csvHandler->getNbRecords() << endl << endl;

	while(csvHandler->hasNextRecord()) {
		record = csvHandler->getNextRecord();
		cout << "    tuple : " << record << endl;

		fields = csvHandler->getFields(record);
		for (string field : fields) {
			cout << "        champ : " << field << endl;
		}
	}

	cout << endl <<  "***** Test d'insertion de record dans un fichier existant *****" << endl;
	cout << "    Nombre de lignes du fichier CSV (AVANT) : " << csvHandler->getNbRecords() << endl << endl;
	fields.clear();
	fields.push_back("valeur1");
	fields.push_back("valeur2");
	fields.push_back("valeur3");

	record.clear();
	record = csvHandler->createRecord(fields);
	cout << "    Record à insérer : " << record << endl;

	csvHandler->writeRecord(record);
	cout << "    Nombre de lignes du fichier CSV (APRES) : " << csvHandler->getNbRecords() << endl << endl;


	delete csvHandler;

	cout << endl << "***** test fichier inexistant *****" << endl;
	csvHandler = new CsvHandler(csvWrongFilePath, ";");

	try{
		csvHandler->hasNextRecord();
	}catch(const CsvException& ex){
		cout << "     l'exception est bien réceptionnée : " << ex.what() << endl;
	}

	cout << endl <<  "***** Test d'insertion de record dans un fichier inexistant *****" << endl;
	fields.clear();
	fields.push_back("valeur1");
	fields.push_back("valeur2");
	fields.push_back("valeur3");

	record.clear();
	record = csvHandler->createRecord(fields);
	cout << "    Record à insérer : " << record << endl;

	try{
		csvHandler->writeRecord(record);
		cout << "    Nombre de lignes du fichier CSV (APRES) : " << csvHandler->getNbRecords() << endl << endl;
	}catch(const CsvException& ex){
		cout << "Erreur lors de l'écriture du record : " << ex.what() << endl;
	}


	return 0;
}