#include <iostream>
#include "Application_CIAChat_IN.h"

Application_CIAChat_IN *application_ciaChat_in;

int main(int argc, char* argv[]) {
	if(argc < 2) {
		std::cerr << "Please provide the properties file as argument when launching me!" << std::endl;
		exit(1);
	}
	affOut("Launcher starting...");

	try{
		application_ciaChat_in = new Application_CIAChat_IN(argv[1]);
		application_ciaChat_in->run();
	}catch(const QuitApp &ex){
	}catch(const std::exception &ex){
		affErr(ex.what());
	}

	delete application_ciaChat_in;
	affOut("L'application se ferme.");
	affOut("Au revoir!");
	affEmptyLine();

	return 0;
}