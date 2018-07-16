#include <iostream>
#include "../CSV/LoginCsvHandler.h"

#define csvFilePath "Librairies/Tests/logins.csv"
#define csvWrongFilePath "Librairies/Tests/fauxLogins.csv"

int main(){
	cout << "Début des tests..." << endl;

	try {
		cout << "***** Test de lecture d'un fichier existant *****" << endl;
		LoginCsvHandler *loginCsvHandler = new LoginCsvHandler(csvFilePath, ";");

		cout << "    recherche du login laurent (existant) avec le bon mot-de-passe: " << endl << "    resultat : ";
		loginCsvHandler->isValid("laurent", "monpwd") ? cout << "TRUE" : cout << "FALSE";
		cout << endl;

		cout << "    recherche du login charlet (existant) avec le bon mot-de-passe: " << endl << "    resultat : ";
		loginCsvHandler->isValid("charlet", "maitre") ? cout << "TRUE" : cout << "FALSE";
		cout << endl;

		cout << "    recherche du login laurent (existant) avec un mauvais mot-de-passe: " << endl << "    resultat : ";
		loginCsvHandler->isValid("laurent", "pasbonca") ? cout << "TRUE" : cout << "FALSE";
		cout << endl;
		cout << "    recherche du login toto (inexistant) : " << endl << "    resultat : ";
		loginCsvHandler->isValid("toto", "mdp") ? cout << "TRUE" : cout << "FALSE";
		cout << endl;

		delete loginCsvHandler;

		cout << "***** Test de lecture d'un fichier existant *****" << endl;
		loginCsvHandler = new LoginCsvHandler(csvWrongFilePath, ";");
		cout << "    recherche du login laurent (existant) avec le bon mot-de-passe: " << endl << "    resultat : ";
		loginCsvHandler->isValid("laurent", "monpwd") ? cout << "TRUE" : cout << "FALSE";
		cout << endl;
	}catch(const CsvException& ex){
		cout << "Une erreur est survenue! Message : " << ex.what() << endl;
	}
	return 0;
}