#include "Message.h"

Message::Message(int messageType, const std::string &user, const std::string &message, const std::string &tag)
		: messageType(messageType), message(message), tag(tag), user(user) {
	this->digest = Message::createDigest(this->message);
	this->time = Message::getCurrentTime();
}

Message::Message(int messageType, const std::string &user, const std::string &message, const std::string &tag, int digest)
		: messageType(messageType), message(message), tag(tag), digest(digest), user(user) {
	this->time = Message::getCurrentTime();
}

Message::Message(int messageType, const std::string &user, const std::string &time, const std::string &message, const std::string &tag, int digest)
				: time(time), messageType(messageType), message(message), tag(tag), digest(digest), user(user) {}

int Message::getMessageType() const { return this->messageType; }
const std::string &Message::getTime() const { return time; }
const std::string &Message::getMessage() const { return message; }
const std::string &Message::getTag() const { return tag; }
int Message::getDigest() const { return digest; }
const std::string &Message::getUser() const { return user; }

std::ostream &operator<<(std::ostream &os, const Message &message) {
	os << "[" << message.time << "]";
	os << "[" << message.getMessageTypeAsString() << "]";
	os << "[" << message.tag << "]";
	os << " " << message.user << " :";
	os << " " << message.message;

	return os;
}

std::string Message::getMessageTypeAsString() const {
	switch(this->messageType){
		case POST_QUESTION:
			return std::string("Question");
		case ANSWER_QUESTION:
			return std::string("Réponse");
		case POST_EVENT:
			return std::string("Event");
		default:
			return std::string();
	}
}

int Message::createDigest(std::string msg){
	int hash = 0;

	for(int i=0; i<msg.size(); i++)
		hash += msg.at(i);

	return hash%67;
}

std::string Message::getCurrentTime() {
	std::stringstream buffer;
	time_t tt = std::chrono::system_clock::to_time_t(std::chrono::system_clock::now());
	struct tm *ptm = localtime(&tt);
	buffer << std::put_time(ptm, "%H:%M:%S");

	return buffer.str();
}

std::string Message::generateTag(std::vector<Message> messageList) {
	bool dispo = false;
	int random;
	srand(static_cast<unsigned int>(std::time(nullptr)));

	while(!dispo) {
		dispo = true;
		random = rand() % 10000 - 1;

		for (int i = 0; i<messageList.size() && dispo; ++i) {
			dispo = (messageList[i].tag) == std::to_string(random);
		}
	}

	return std::to_string(random);
}
