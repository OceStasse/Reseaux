#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include "Network-Cli.h"


//#include "Client.conf"
#include "Network-Cli.h"

int PORT = 0;
int SIZEBUF = 0;
int soc;

int main()
{
    {
        FILE* config;
        config = fopen("Client.conf", "r+");
        fseek(config, 5, SEEK_SET);
        char port[6], sizebuf[6];
        fgets(port, 6, config);
        PORT = atoi(port);
        fseek(config, 9, SEEK_CUR);
        fgets(sizebuf, 3, config);
        SIZEBUF = atoi(sizebuf);
        fclose(config);
        
    }
    
    char buffer[SIZEBUF];
    char id[20];
    char password[20];
    int accessOK = 0;
    char bin;
    
    logout:;
    if(Sockette(&soc, PORT) == -1)
    {
        perror("Err. Sockette");
        exit(-1);
    }
   
    do
    {
        
        system("clear");
        printf("***** CheckIn Application - Login *****\n\n");
        printf("Login:\n");
        scanf("%s", id);
        printf("Password:\n");
        scanf("%s", password);
        
        strcpy(buffer, "Login:");
        strcat(buffer, id);
        strcat(buffer, ";");
        strcat(buffer, password);
        
        
        if(Sending(&soc, buffer, SIZEBUF) == -1)
        {
            perror("Err. Sending");
            exit(-1);
        }
        
        if(Receiving(&soc, buffer) == -1)
        {
            perror("Err. Receiving");
            exit(-1);
        }
        
        
        if(strcmp(buffer, "Logout") == 0)
        {
            printf("Serveur down !\n");
            close(soc);
            exit(-1);
        }
        
        if(strcmp(buffer, "Login:OK") == 0)
        {
            accessOK = 1;
            printf("Accès Autorisé !\n");
            sleep(2);
        }
        else
        {
            printf("Login ou Password Erroné !\n");
            sleep(2);
        }
        
    }
    while(accessOK != 1);
    
    
    
    char choix;
    int end = 0;
    char numTicket[20];
    char nb_passagers[3];
    char poids_bagage[3];
    char Valise[2];
    
    do
    {
        strcpy(buffer, "");
        system("clear");
        printf("***** CheckIn Application - Menu Principal *****\n\n");
        printf("1. Vérifier un Billet\n");
        printf("2. Déconnexion\n");
        printf("3. Quitter\n");
        fgets(&choix, 2, stdin);
        
        switch(choix)
        {
            case '1': system("clear");
                    printf("***** CheckIn Application - Vérification des Billets *****\n\n");
                    printf("Numéro de Ticket:\n");
                    scanf("%s", numTicket);
                    printf("Nombre de passagers:\n");
                    scanf("%s", nb_passagers);
                    strcpy(buffer, "CheckTicket:");
                    strcat(buffer, numTicket);
                    strcat(buffer, ";");
                    strcat(buffer, nb_passagers);
                    if(Sending(&soc, buffer, SIZEBUF) == -1)
                    {
                        perror("Err. Sending");
                        exit(-1);
                    }
                    
                    if(Receiving(&soc, buffer) == -1)
                    {
                        perror("Err. Receiving");
                        exit(-1);
                    }
                    
                    if(strcmp(buffer, "Logout") == 0)
                    {
                        printf("Serveur down !\n");
                        close(soc);
                        exit(-1);
                    }
			    
                    if(strcmp(buffer, "CheckTicket:OK") == 0)
                    {
                        printf("Ticket Valide !\n");
                        sleep(2);
                        
                        for(int i = 0; i < atoi(nb_passagers); i+=1)
                        {
                            retry:;
                            system("clear");
                            printf("***** CheckIn Application - Vérification des bagages *****\n\n");
                            printf("Poids du bagage n°%d (en kg):\n", i+1);
                            scanf("%s", poids_bagage);
                            do
                            {
                                printf("Valise en possession (O ou N):\n");
                                scanf("%s", Valise);
                            }
                            while(strcmp(Valise, "O") != 0 && strcmp(Valise, "N") != 0);
                            strcpy(buffer, "CheckLuggage:");
                            strcat(buffer, poids_bagage);
                            strcat(buffer, ";");
                            strcat(buffer, Valise);
                            if(Sending(&soc, buffer, SIZEBUF) == -1)
                            {
                                perror("Err. Sending");
                                exit(-1);
                            }
                            
                            if(Receiving(&soc, buffer) == -1)
                            {
                                perror("Err. Receiving");
                                exit(-1);
                            }
                            
                            if(strcmp(buffer, "Logout") == 0)
                            {
                                printf("Serveur down !\n");
                                close(soc);
                                exit(-1);
                            }
                            
                            if(strcmp(buffer, "CheckLuggage:OK") == 0)
                            {
                                printf("Bagage de la personne %d: OK\n", i+1);
                            }
                            else if(strcmp(buffer, "CheckLuggage:TOO_BIG") == 0)
                            {
                                printf("Bagage de la personne %d: Trop lourd\n", i+1);
                            }
                            else
                            {
                                printf("Bagage de la personne %d: Err. lors de la réception\n", i+1);
                                sleep(2);
                                goto retry;
                            }
                            sleep(2);
                        }
                    }
                    else
                    {
                        printf("Ticket Invalide !\n");
                        sleep(2);
                    }
                    break;
            case '3': end = 1;
            case '2': printf("Logout...\n");
                      strcpy(buffer, "Logout");
                      if(Sending(&soc, buffer, SIZEBUF) == -1)
                      {
                          perror("Err. Sending");
                          exit(-1);
                      }
                      printf("soc = %d\n", soc);
                      if(end != 1)
                        goto logout;
                      accessOK = 0;
                      break;
            default: printf("Choix Erroné: %c\n", choix);
        }
        
    }
    while(end != 1);
    
    close(soc);
    
    return 1;

}
