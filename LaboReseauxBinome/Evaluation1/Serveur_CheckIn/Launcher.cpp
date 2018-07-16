#include <iostream>
#include <csignal>

#include "Serveur_CheckIn.h"

void fonctionSig(int);

Serveur_CheckIn *serveur_checkIn = nullptr;

using namespace std;

int main(int argc, char* argv[]) {
	if (argc < 2) {
		cerr << "Please provide the properties file as argument when launching me!" << endl;
		exit(1);
	}
	affServeur(1, "Launcher starting...");

	struct sigaction sa;
	sa.sa_handler = fonctionSig;
	sa.sa_flags = 0;
	sigemptyset(&sa.sa_mask);
	sigaddset(&sa.sa_mask, SIGUSR1);
	sigaction(SIGINT, &sa, nullptr);

	try {
		serveur_checkIn = new Serveur_CheckIn(argv[1]);
		serveur_checkIn->init();
		serveur_checkIn->run();
	}catch(const exception &e){
		affServeur(1, e.what());
	}

	delete serveur_checkIn;

	affServeur(1, "Server is closing.\nGood bye!\n\n");
	return 0;
}

void fonctionSig(int signal){
	affServeur(1, "Signal received : calling ~Serveur_CheckIn()...");

	delete serveur_checkIn;

	affServeur(1, "Server is closing.\nGood bye!\n\n");
	exit(0);
}