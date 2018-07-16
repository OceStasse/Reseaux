#include <iostream>
#include "../Properties/Properties.h"

#define correctFilePath "Librairies/Tests/Properties.prop"
#define erronousFilePath "Librairies/Tests/nope.prop"

int main(){
	cout << "Début des tests...." << endl;

	try{
		cout << "***** Test de lecture d'un fichier existant *****" << endl;
		Properties *properties = new Properties(correctFilePath, "=");

		cout << "    Test de getValue avec clé existante : " << endl;
		cout << "        résultat : clé=LIVRAISONS_SEPARATOR_ZONE       valeur=" << properties->getValue("LIVRAISONS_SEPARATOR_ZONE") << endl;

		cout << "    Test de getValue avec clé existante : " << endl;
		cout << "        résultat : clé=HOST                            valeur=" << properties->getValue("HOST") << endl;

		cout << "    Test de getValue avec clé existante mais sans valeur : " << endl;
		cout << "        résultat : clé=SANSVALEUR                      valeur=" << properties->getValue("SANSVALEUR") << endl;
		cout << "        is empty :";
		(!properties->getValue("SANSVALEUR").length()) ? cout << "TRUE" : cout << "FALSE";
		cout << endl;

		cout << "    Test de getValue avec clé inexistante : " << endl;
		cout << "        résultat : clé=LIVRAISONS                      valeur=" << properties->getValue("LIVRAISONS") << endl;
		cout << "        is empty :";
		(!properties->getValue("LIVRAISONS").length()) ? cout << "TRUE" : cout << "FALSE";
		cout << endl;

		delete properties;
	}catch (PropertiesException& ex){
		cout << "Une erreur est survenue lors de l'instantiation de la classe Properties : " << ex.what() << endl;
	}

	try{
		cout << endl << "***** Test de lecture d'un fichier inexistant *****" << endl;
		Properties *properties = new Properties(erronousFilePath, "=");
		cout << "fin lecture de fichier inexistant" << endl;
		delete properties;
	}catch (PropertiesException& ex){
		cout << ex.what() << endl;
	}

	return 0;
}