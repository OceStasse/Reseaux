#ifndef APPLICATION_CIACHAT_MESSAGE_H
#define APPLICATION_CIACHAT_MESSAGE_H

#include <string>
#include <ctime>
#include <iomanip>
#include <sstream>
#include <ostream>
#include <random>
#include <cfloat>
#include <vector>


#define POST_QUESTION	0
#define ANSWER_QUESTION 1
#define POST_EVENT		2

class Message {
private:
	std::string time;
	int messageType;
	std::string message;
	std::string tag;
	int digest;
	std::string user;

	std::string getMessageTypeAsString() const;

public:
	Message(int messageType, const std::string &user, const std::string &message, const std::string &tag);
	Message(int messageType, const std::string &user, const std::string &message, const std::string &tag, int digest);
	Message(int messageType, const std::string &user, const std::string &time, const std::string &message, const std::string &tag, int digest);

	int getMessageType() const;

	const std::string &getTime() const;

	const std::string &getMessage() const;

	const std::string &getTag() const;

	int getDigest() const;

	const std::string &getUser() const;

	static int createDigest(std::string msg);
	static std::string getCurrentTime();
	static std::string generateTag(std::vector<Message> messageList);
	friend std::ostream &operator<<(std::ostream &os, const Message &message);
};


#endif //APPLICATION_CIACHAT_MESSAGE_H
