#include <fcntl.h> 
#include <stdio.h> 
#include <string.h>
#include <unistd.h> 
#include "Libcsv.h"


int ReadByte(int fd, char* l, char* p)
{
	char buff;
	int end;
	
	for(int i = 0; buff != ';' && buff != ','; i+=1)
	{
		end = read(fd, &buff, 1);
		
			
		if(end == 0)
		{
			goto end;
		}
		
		*(l+i) = buff;
		
		if(*(l+i) == ';' || *(l+i) == ',')
			*(l+i) = '\0';
			
	}
	
	buff = 1;
	int i = 0;
	for(i = 0; buff != '\n'; i+=1)
	{
		end = read(fd, &buff, 1);
		if(end == 0)
		{
			fprintf(stderr,"goto\n");
			goto end;
		}
		*(p+i) = buff;
	}
	*(p+i-1) = '\0';
	
	
	return 1;
	
	end:;
	
	return 0;
}

int WriteByte(int fd, char* l, char* p)
{
	char buff;
	int end = 1;
	char *p1, *p2;
	
	p1 = (char*)malloc(sizeof(l));
	p2 = (char*)malloc(sizeof(p));
	strcpy(p1, l);
	strcpy(p2, p);
	if(write(fd, p1, sizeof(p1)) == 0)
		return 0;
	
	
	buff = ';';
	
	if(write(fd, &buff, 1) == 0)
		return 0;
	
	if(write(fd, p2, sizeof(p2)) == 0)
		return 0;
	
	buff = '\n';
	
	if(write(fd, &buff, 1) == 0)
		return 0;
	
	return 1;
	
}



