.SILENT:

APPNAME=CheckIn
COMPILATEUR=gcc

Appli: $(APPNAME)

$(APPNAME):	Client.c Network-Cli.o
		$(COMPILATEUR) Client.c Network-Cli.o -o $(APPNAME)
		echo "Compile $(APPNAME)"


Network-Cli.o:	Network-Cli.c Network-Cli.h
		$(COMPILATEUR) -c Network-Cli.c
		echo "Compile Network-Cli.o"

clobber:	clean
		rm -f ./$(APPNAME)
		echo Projet Nettoyé

clean:	
	rm -f ./*.o ./core
	echo Fichiers obj et core effacés
