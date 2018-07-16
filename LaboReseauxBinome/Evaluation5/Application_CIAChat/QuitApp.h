#ifndef LIBRAIRIES_QUITAPP_H
#define LIBRAIRIES_QUITAPP_H

#include <string>
#include <cstring>

class QuitApp : public std::exception {
protected:
	std::string message;

public:
	QuitApp();
	QuitApp(std::string msg);
	QuitApp(const QuitApp &orig);
	~QuitApp() throw();
	virtual const char* what() const throw();
};



#endif //LIBRAIRIES_QUITAPP_H
