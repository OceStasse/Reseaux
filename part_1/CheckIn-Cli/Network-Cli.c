#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netdb.h>
#include "Network-Cli.h"



    
int Sockette(int* soc, int port)
{
    struct hostent* infosHost;
    struct in_addr s_addr;
    struct sockaddr_in c_addr;
    unsigned int taillesoc;
    
    if(((*soc) = socket(AF_INET, SOCK_STREAM, 0)) == -1) // Crée la socket TCP
    {
        return -1;
    }
    
    if((infosHost = gethostbyname("Chokotoff")) == 0) // Va rechercher l'adresse du hostname dans le fichier /etc/hosts
    {
        printf("Err d'acquisition sur l'ordi distant\n");
        return -1;
    }
    
    bzero((char*)&c_addr, sizeof(c_addr)); // initialise c_addr
    c_addr.sin_family = AF_INET; // Toutes les cartes réseaux peuvent intercepter les paquets
    c_addr.sin_port = htons(port); // Convertit le port en big endian
    memcpy(&c_addr.sin_addr, infosHost->h_addr, infosHost->h_length);
    
    
    if(connect(*soc, (struct sockaddr*)&c_addr, sizeof(struct sockaddr_in)) == -1) // Initialise la connexion au socket
    {
        return -1;
    }
    
    printf("SUCCESS !\n");
    
    return 1;
    
    
}

int Sending(int* soc, char* buffer, int sizebuf)
{
    
    if(send(*soc, buffer, sizebuf, 0) == -1)
    {
        return -1;
    }
    
    
    return 1;
}

int Receiving(int* soc, char* buffer)
{
    if(recv(*soc, buffer, 50, 0) == -1)
    {
        return -1;
    }
    
    
    return 1;
}
