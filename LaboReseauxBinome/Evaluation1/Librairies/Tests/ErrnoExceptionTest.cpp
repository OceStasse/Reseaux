#include <iostream>

#include "../Exceptions/ErrnoException.h"

using namespace std;

void lancerException() throw(ErrnoException){
	cout << "throwing the exception..." << endl;

	throw ErrnoException(9, "un message quelconque");
}

int main(){
	cout << "Testing ErrnoException..." << endl;
	try{
		lancerException();
	}catch (ErrnoException& ex){
		cout << "Catched an exception!" << endl;
		cout << "Message : " << ex.what() << endl;
	}

	return 0;
}
