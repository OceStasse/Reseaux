#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <pthread.h>

#include "Libcsv.h"
#include "Network.h"

void* TraitementClient(void* arg);
void* Serveur(void* arg);

int main()
{
        {
            FILE* config;

            config = fopen("Serveur.conf", "r+");
            fseek(config, 5, SEEK_SET);
            char port[6], nbcli[6];
            fgets(port, 6, config);
            PORT = atoi(port);
            printf("%d\n", PORT);

            fseek(config, 7, SEEK_CUR);
            fgets(nbcli, 2, config);
            NBCLI = atoi(nbcli);
            printf("%d\n", NBCLI);
            fclose(config);
        }


	connexions = (int*)malloc(NBCLI*sizeof(int));
	for(int i = 0; i < NBCLI; i+=1)
		*(connexions+i) = 0;

	// SEQUENCE DE TESTS CSV
	/*char login[100][25], password[100][25];
	int n = 0;
	int fd;

	fd = open("Login.csv", O_RDONLY);
	lseek(fd, 0, SEEK_SET);
	for(n = 0; ReadByte(fd, &login[n][0], &password[n][0]); n+=1);

	close(fd);

	for(int j = 0; j < n; j+=1)
	{
		printf("%s\n", &login[j][0]);
		printf("%s\n", &password[j][0]);
	}*/

	int Stop = 0;
	int end = 0;
	char choix = 0;
	pthread_t pidServ;
	char id[25], pass[25];
	int fd;

	pthread_mutex_init(&MutexNewUser, NULL);
	pthread_mutex_init(&MutexTickets, NULL);

	do
	{
		printf("**********************************************\nCheckIn - Inpres Airport - Server - Ver. Alpha\
	\n**********************************************\n\n");
		printf("Menu Principal:\n\n");
		printf("1. Lancer le serveur\n");
		printf("   Administration:\n");
		printf("2. Ajouter un Compte utilisateur\n");
		printf("3. Quitter\n");

		fgets(&choix, 2, stdin);

		switch(choix)
		{
			case '1':
				if(Stop == 0)
				{
					Stop = 1;
					pthread_create(&pidServ, NULL, Serveur, NULL);
				}
				else
				{
					printf("\n\n*** Serveur déjà démarré ! ***\n\n");
				}
				break;
			case '2': printf("Identifiant:\n");
				  scanf("%s", &id);
				  printf("Password:\n");
				  while((pass[0] = getchar()) != '\n' && pass[0] != EOF);
				  scanf("%s", &pass);
				  pthread_mutex_lock(&MutexNewUser);
				  fd = open("Login.csv", O_CREAT | O_RDWR, "777");
				  lseek(fd, 0, SEEK_END);

		  		  if(WriteByte(fd, id, pass))
				  {
					  printf("\n\n*** Utilisateur enregistré ! ***\n\n");
				  }
				  else
				  {
					  perror("Err. WriteByte()");
				  }
				  close(fd);
				  pthread_mutex_unlock(&MutexNewUser);

				  break;
			case '3': end = 1;
				  pthread_mutex_lock(&MutexConnexions);
				  for(int i = 0; i < NBCLI; i+= 1)
				  {

				  	if(*(connexions + i) != 0)
				  	{
				  		printf("Connexion %d fermée !\n", *(connexions+i));
				  		send(*(connexions+i), "Logout", 50, 0);
				  		close(*(connexions+i));
				  	}

				  }

				  pthread_mutex_unlock(&MutexConnexions);
				  break;

		}
		while((pass[0] = getchar()) != '\n' && pass[0] != EOF);

	}
	while(end != 1);

	if(Stop == 1)
	{
		close(soc);
		pthread_cancel(pidServ);
		pthread_join(pidServ, NULL);
		printf("\n\n*** Serveur Fermé! ***\n\n");
		free(connexions);
	}
}

void* TraitementClient(void* arg)
{
	struct sockaddr_in c_addr;
	char buffer[50];
	printf("Thread de traitements lancé !\n");

	int conn_desc = 0;

	char delem = ':';
	char* saveptr;
	int i;
	char bin[2];
	attente_connect:;
	if(Waiting(&soc, &conn_desc, &c_addr, buffer) == -1)
	{
		perror("Err. de recv()");
		exit(-1);
	}

	pthread_mutex_lock(&MutexConnexions);

	for(i = 0; connexions[i] != 0; i+=1);
	{
		connexions[i] = conn_desc;
	}

	printf("Connexion établie %d !\n", connexions[i]);

	pthread_mutex_unlock(&MutexConnexions);

	while(1)
	{

		saveptr = NULL;

		strcpy(buffer, "");


		if(recv(conn_desc, buffer, 50, 0) == -1)
		{
			perror("Err. recv");
			exit(-1);
		}

		strtok_r(buffer, &delem, &saveptr);

		if(strcmp(buffer, "Close") == 0)
			goto exit_server;

		if(strcmp(buffer, "Login") == 0)
		{
			#ifdef DEBUG
			printf("Requête Login!\n");
			#endif
			char login[100][25], password[100][25];
			int i = 0;
			int fd;
			char delem2 = ';';
			char* saveptr2;

			strtok_r(saveptr, &delem2, &saveptr2);

			pthread_mutex_lock(&MutexNewUser);

			fd = open("Login.csv", O_RDONLY);
			lseek(fd, 0, SEEK_SET);

			#ifdef DEBUG
			printf("Recherche: %s et %s\n", saveptr, saveptr2);
			#endif
			for(i = 0; ReadByte(fd, &login[i][0], &password[i][0]); i+=1)
			{

				if(strcmp(&login[i][0], saveptr) == 0)
				{

					//printf("password trouvé: %s, contre password reçu: %s\n", &password[i][0], saveptr2);
					if(strcmp(&password[i][0], saveptr2) == 0)
					{

						//OK
						printf("%s s'est connecté!\n", saveptr);

						send(conn_desc, "Login:OK", 50, 0);
						goto end;
					}

				}
			}
			#ifdef DEBUG
			printf("Pas trouvé !\n");
			#endif
			send(conn_desc, "Login:NOK", 50, 0);
			end:;

			close(fd);

			pthread_mutex_unlock(&MutexNewUser);

		}

		if(strcmp(buffer, "Logout") == 0)
		{
			#ifdef DEBUG
			printf("Requete Logout!\n");
			#endif
			close(conn_desc);
			pthread_mutex_lock(&MutexConnexions);
			connexions[i] = 0;
			pthread_mutex_unlock(&MutexConnexions);
			printf("Une déconnexion a eu lieu !\n");
			goto attente_connect;
		}

		if(strcmp(buffer, "New_User") == 0)
		{
			printf("Requête nouvel utilisateur !\n");

			int i = 0;
			int fd;
			char delem2 = ';';
			char* saveptr2;

			strtok_r(saveptr, &delem2, &saveptr2);

			#ifdef DEBUG
			printf("Nouvel Utilisaeur: %s et %s\n", saveptr, saveptr2);
			#endif
			pthread_mutex_lock(&MutexNewUser);

			fd = open("Login.csv", O_CREAT | O_RDWR, "777");
			lseek(fd, 0, SEEK_END);


			if(WriteByte(fd, saveptr, saveptr2))
			{
				printf("Utilisateur enregistré: %s !\n", saveptr);
			}
			else
			{
				perror("Err. WriteByte()");
			}

			close(fd);
			pthread_mutex_unlock(&MutexNewUser);


		}

		if(strcmp(buffer, "CheckTicket") == 0)
		{
			#ifdef DEBUG
			printf("Requête CheckTicket!\n");
			#endif
			char numTicket[100][25], nb_passagers[100][25];
			int i = 0;
			int fd_tickets;
			char delem2 = ';';
			char* saveptr2;



			strtok_r(saveptr, &delem2, &saveptr2);

			#ifdef DEBUG
			printf("%s\n%s\n", saveptr, saveptr2);
			#endif
			pthread_mutex_lock(&MutexTickets);

			fd_tickets = open("Tickets.csv", O_RDONLY);
			lseek(fd_tickets, 0, SEEK_SET);

			#ifdef DEBUG
			printf("Recherche: %s et %s\n", saveptr, saveptr2);
			#endif
			for(i = 0; ReadByte(fd_tickets, &numTicket[i][0], &nb_passagers[i][0]); i+=1)
			{

				if(strcmp(&numTicket[i][0], saveptr) == 0)
				{
					#ifdef DEBUG
					printf("nb_passagers: %s, saveptr2: %s\n", &nb_passagers[i][0], saveptr2);
					#endif
					if(strcmp(&nb_passagers[i][0], saveptr2) == 0)
					{
						#ifdef DEBUG
						printf("Trouvé!\n");
						#endif

						send(conn_desc, "CheckTicket:OK", 50, 0);
						goto endt;
					}

				}
			}
			#ifdef DEBUG
			printf("Pas trouvé !\n");
			#endif
			send(conn_desc, "CheckTicket:NOK", 50, 0);
			endt:;

			close(fd_tickets);
			pthread_mutex_unlock(&MutexTickets);
		}

		if(strcmp(buffer, "CheckLuggage") == 0)
		{
			#ifdef DEBUG
			printf("Requête CheckLuggage!\n");
			#endif

			char delem2 = ';';
			char* saveptr2;

			strtok_r(saveptr, &delem2, &saveptr2);
			#ifdef DEBUG
			printf("Poids: %skg Valise: %s\n", saveptr, saveptr2);
			#endif
			if(atoi(saveptr) > 20)
			{
				#ifdef DEBUG
				printf("Trop gros mon gars..\n");
				#endif
				send(conn_desc, "CheckLuggage:TOO_BIG", 50, 0);
			}
			else
			{
				send(conn_desc, "CheckLuggage:OK", 50, 0);
			}
			// Réutiliser plus tard les données fournies !
		}

	}

	exit_server:;
	printf("Un thread de demande a été fermé !\n");
	pthread_exit(NULL);
	return NULL;


}

void* Serveur(void* arg)
{

	if(Sockette(&soc, PORT) == -1)
	{
		perror("Err. de bind");
		exit(-1);
	}


	pthread_t pidTraitement[NBCLI];

	for(int i = 0; i < NBCLI; i+=1)
		pthread_create(&pidTraitement[i], NULL, TraitementClient, NULL);

	for(int i = 0; i < NBCLI; i+=1)
		pthread_join(pidTraitement[i], NULL);


	close(soc);

	printf("Le serveur a été fermé !\n");
	pthread_exit(NULL);
	return NULL;
}
