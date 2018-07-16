#include "ThreadReception.h"
#include "Application_CIAChat_IN.h"

ThreadReception::ThreadReception(MulticastSocket *mcs, std::vector<Message> *allMessages, std::vector<Message> *questions) {
	this->multicastSocket = mcs;
	this->allMessages = allMessages;
	this->questions = questions;
}

ThreadReception::~ThreadReception() {
	stop_thread = true;
	if(unThread.joinable())
		unThread.join();
}

void ThreadReception::Start() {
    unThread = std::thread(&ThreadReception::ThreadMain,this);
}

void ThreadReception::ThreadMain() {
	while(!stop_thread) {
		Message *message = nullptr;
		message = this->multicastSocket->receiveMessage();
		if(message != nullptr) {
			this->allMessages->push_back(*message);
			if (message->getMessageType() == POST_QUESTION) {
				this->questions->push_back(*message);
			}
		}
    }
}