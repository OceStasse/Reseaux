#include "QuitApp.h"

QuitApp::QuitApp() : exception(), message("quitting...") {}

QuitApp::QuitApp(const string msg) : exception(), message(msg) {}

QuitApp::QuitApp(const QuitApp &orig) : exception(orig), message(orig.message) {}

QuitApp::~QuitApp() throw() {}

const char *QuitApp::what() const throw() {
	char *returnVal;

	returnVal = new char[this->message.length() + 1];
	strcat(returnVal, this->message.c_str());

	return returnVal;
}
