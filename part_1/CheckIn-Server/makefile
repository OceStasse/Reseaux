.SILENT:

DEBUG=

Appli: Serveur

Serveur:	Serveur.c Libcsv.o Network.o
	gcc Serveur.c -pthread Libcsv.o Network.o $(DEBUG) -o Serveur
	echo "Compile Serveur"

Libcsv.o:	Libcsv.c
		gcc -c Libcsv.c $(DEBUG)
		echo "Compile Libcsv.o"

Network.o:	Network.c
		gcc -c Network.c $(DEBUG)
		echo "Compile Network.o"
		
clobber:	clean
		rm -f ./Serveur
		echo Projet nettoyé

clean:	
	rm -f ./*.o ./core
	echo Fichiers obj et core effacés
