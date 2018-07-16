#ifndef Thread_h
#define Thread_h

#include <thread>
#include "MulticastSocket.h"
#include "Message.h"

class ThreadReception{
private:
    std::thread unThread;
    bool stop_thread = false;
    MulticastSocket *multicastSocket;
	std::vector<Message> *allMessages;
	std::vector<Message> *questions;

public:
    ThreadReception(MulticastSocket *mcs, std::vector<Message> *allMessages, std::vector<Message> *questions);
	virtual ~ThreadReception();
    void Start();
    void ThreadMain();
};
#endif 
