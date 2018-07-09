#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include "Network.h"

int Sockette(int* soc, int port)
{
	struct sockaddr_in s_addr;
	int yes = 1;
	if((*soc = socket(AF_INET, SOCK_STREAM, 0)) == -1) // Crée la socket TCP
		return -1;

	bzero((char *)&s_addr, sizeof(s_addr)); // Initialise la variable s_addr

	s_addr.sin_family = AF_INET; // Toutes les cartes réseaux peuvent intercepter les paquets
	s_addr.sin_addr.s_addr = htonl(INADDR_ANY); // Convertit l'adresse en Big Endian
	s_addr.sin_port = htons(port); // Convertit le port en Big Endian

	setsockopt(*soc, SOL_SOCKET, SO_REUSEADDR, &yes, sizeof(int)); // Paramétrer la socket de manière à ce qu'elle soit réutilisable

	if(bind(*soc, (struct sockaddr *)&s_addr, sizeof(struct sockaddr_in)) == -1) // Lier l'adresse au socket
               return -1;

	printf("Socket TCP accepté: En écoute !\n");
}


int Waiting(int* soc, int* conn_desc, struct sockaddr_in* c_addr ,char* buffer)
{

	int size = sizeof(*c_addr);
	int taille_lu = 0;

	listen(*soc, 1); // Ecoute les connexions sur la socket

	*conn_desc = accept(*soc, (struct sockaddr *)c_addr, &size); // Accepte une connexion sur la socket

	#ifdef DEBUG
	printf("Message reçu: %s\n", buffer);
	#endif
}
