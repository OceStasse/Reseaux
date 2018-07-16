#include <iostream>
#include <csignal>
#include "Application_CheckIn.h"
#include "QuitApp.h"

void fonctionSig(int);

Application_CheckIn *application_checkIn;

using namespace std;

int main(int argc, char* argv[]) {
	if(argc < 2) {
		cerr << "Please provide the properties file as argument when launching me!" << endl;
		exit(1);
	}
	affOut("Launcher starting...");


	struct sigaction sa;
	sa.sa_handler = fonctionSig;
	sa.sa_flags = 0;
	sigemptyset(&sa.sa_mask);
	sigaction(SIGINT, &sa, nullptr);

	try{
		application_checkIn = new Application_CheckIn(argv[1]);
		application_checkIn->run();
	}catch(const QuitApp &ex){}
	catch(const exception &ex){
		affErr(ex.what());
	}

	delete application_checkIn;
	affOut("Application is closing.");
	affOut("Good bye!");
	affEmptyLine();

	return 0;
}

void fonctionSig(int signal){
	affOut("Signal received : calling ~application_checkIn()...");

	delete application_checkIn;

	affOut("Application is closing.");
	affOut("Good bye!");
	affEmptyLine();

	exit(0);
}