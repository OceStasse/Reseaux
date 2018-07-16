#ifndef PROPERTIES_PROPERTIES_H
#define PROPERTIES_PROPERTIES_H

#include <string>
#include <map>
#include <fstream>
#include "../Exceptions/PropertiesException.h"

class Properties {
private:
	std::map<std::string, std::string> mapProperties;

public:
	explicit Properties(std::string filePath, std::string sep) throw(PropertiesException);
	virtual ~Properties();

	const std::string getValue(std::string key) const;
};


#endif //PROPERTIES_PROPERTIES_H
