#include <iostream>
#include "../Exceptions/CsvException.h"

void throwException() throw(CsvException){
	//fait quelque chose, et puis *BAM*
	throw CsvException("il s'est passé quelque chose... mais quoi?");
}

int main() {

	cout << "on fait un peu de travail!" << endl;

try{
		throwException();
	}catch(const CsvException& e){
		cerr << "CsvException() : une erreur est survenue... what(" << e.what() << ")" << endl;
	}
	catch(const exception& e){
		cerr << "une erreur inattendue est survenue... what(" << e.what() << ")" << endl;
	}

	cout << "fin de la fonction." << endl;

	return 0;
}

