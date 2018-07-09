#include <stdio.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <errno.h>
#include <netinet/in.h>
#include <netinet/tcp.h>
#include <arpa/inet.h>


class Socket
{

private:
	int _socket;
	struct hostent *_infoHost;
	struct in_addr _addIp;
	struct sockaddr_in _addrSock;
	int tailleSock;
public:
	Socket()
	{
		_socket= -1;
		_infoHost = NULL;
		memcpy(_addIp,0);
		memcpy(_addrSock,0);
		tailleSock = -1;
	}
}