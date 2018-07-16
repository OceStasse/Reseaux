#ifndef SERVEURCHECKIN_SERVEUR_CHECKINTHREAD_H
#define SERVEURCHECKIN_SERVEUR_CHECKINTHREAD_H

#include <string>
#include <sstream>
#include <pthread.h>
#include <unistd.h>
#include <csignal>
#include <iomanip>
#include <ctime>
#include <regex>

#include "Serveur_CheckIn.h"
#include "../Librairies/CSV/TicketCsvHandler.h"
#include "../Librairies/SocketUtilities/Socket.h"
#include "../Librairies/CIMP/CIMP.h"

//#define affThread(lvl, msg) printf("\e[34;1m|%.*s>\e[0;1m [th_%s] [%s] \e[0m%s\n", (lvl)*8, "========================", getThreadId(), __PRETTY_FUNCTION__, msg);fflush(stdout)
#define affThread(lvl, msg) printf("\e[34;1m|%.*s>\e[0;1m [th_%s] [%s] \e[0m%s\n", (lvl)*8, "========================", getThreadId(), __FUNCTION__, msg);fflush(stdout)

using namespace std;

void * fctThread(void* param);
const char* getThreadId();
bool ticketNumberIsValid(const string ticketNumber);
string getTodayString();
void fctSIGUSR1(int sign);
void fctEndOfThread(void * param);


#endif //SERVEURCHECKIN_SERVEUR_CHECKINTHREAD_H
