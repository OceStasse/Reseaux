#include <iostream>

#include "../SocketUtilities/HostInfo.h"

int main(){
	HostInfo *hostInfo;
	cout << "Début des tests..." << endl;

	cout << "    Instantiation par constructeur par défaut : " << endl;
	hostInfo = new HostInfo();
	cout << "host IP adress : " << hostInfo->getHostIpAddress() << "." << endl;
	cout << "host Name : " << hostInfo->getHostName() << "." << endl;
	cout << *hostInfo << endl;

	delete hostInfo;

	cout << "    Instantiation par constructeur avec string : " << endl;
	hostInfo = new HostInfo("localhost");
//	hostInfo = new HostInfo("10.59.22.27");
//	hostInfo = new HostInfo("127.0.0.1");
	cout << "host IP adress : " << hostInfo->getHostIpAddress() << "." << endl;
	cout << "host Name : " << hostInfo->getHostName() << "." << endl;
	cout << *hostInfo << endl;


	delete hostInfo;

	cout << endl << "Fin des tests!" << endl;
	return 0;
}