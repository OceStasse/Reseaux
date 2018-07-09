#ifndef NETWORK_H
int Sockette(int* soc, int port);
int Waiting(int* soc, int* conn_desc, struct sockaddr_in* c_addr ,char* buffer);
#endif

