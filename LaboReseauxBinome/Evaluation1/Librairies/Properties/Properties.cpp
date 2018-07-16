#include "Properties.h"

Properties::Properties(const std::string filePath, const std::string sep) throw(PropertiesException) {
	std::string line;
	unsigned long pos;

	std::ifstream fileStream(filePath.c_str(), std::ios::in);

	if(!fileStream.is_open()){
		throw PropertiesException("Properties::Properties() >> Could not open the file : " + filePath);
	}

	while(!fileStream.eof()){
		getline(fileStream, line);
		pos = line.find(sep);

		if(pos == std::string::npos || line[0]=='#')
			continue;

		std::string key = line.substr(0, pos);
		std::string value = line.substr(pos + sep.length());
		mapProperties.insert(std::map<std::string, std::string>::value_type(key, value));
		line.clear();
	}
}

Properties::~Properties() = default;

const std::string Properties::getValue(const std::string key) const {
	std::map<std::string, std::string>::const_iterator i;
	std::string value;

	i = mapProperties.find(key);

	if(i != mapProperties.end()) {
		value = i->second;
	}
	return value;
}
