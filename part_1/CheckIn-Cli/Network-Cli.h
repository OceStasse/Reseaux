#ifndef NETWORK_CLI_H
int Sockette(int* soc, int port);
int Sending(int* soc, char* buffer, int sizebuf);
int Receiving(int* soc, char* buffer);
#endif
