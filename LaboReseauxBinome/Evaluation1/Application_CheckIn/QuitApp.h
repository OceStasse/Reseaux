#ifndef LIBRAIRIES_QUITAPP_H
#define LIBRAIRIES_QUITAPP_H

#include <string>
#include <cstring>

using namespace std;

class QuitApp : public exception {
protected:
	string message;

public:
	QuitApp();
	QuitApp(string msg);
	QuitApp(const QuitApp &orig);
	~QuitApp() throw();
	virtual const char* what() const throw();
};



#endif //LIBRAIRIES_QUITAPP_H
