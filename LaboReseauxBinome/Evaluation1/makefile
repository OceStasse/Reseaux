.SILENT:
.PHONY: clean, clobber

COMP=compiledOut
LIB_EXC=Librairies/Exceptions
LIB_CIMP=Librairies/CIMP
LIB_CSV=Librairies/CSV
LIB_PROP=Librairies/Properties
LIB_SOCK=Librairies/SocketUtilities
LIB_TEST=Librairies/Tests
SERV=Serveur_CheckIn
APPL=Application_CheckIn

CC = g++ -m64 -std=c++11 -Wall -pthread

OBJS=$(COMP)/CsvException.o $(COMP)/ErrnoException.o $(COMP)/PropertiesException.o $(COMP)/SocketException.o $(COMP)/HostException.o $(COMP)/CIMPException.o $(COMP)/CIMP.o $(COMP)/LuggageDatabase.o $(COMP)/LuggageCsvHandler.o $(COMP)/TicketCsvHandler.o $(COMP)/LoginCsvHandler.o $(COMP)/CsvHandler.o $(COMP)/Properties.o $(COMP)/HostInfo.o $(COMP)/Socket.o $(COMP)/ClientSocket.o $(COMP)/ServerSocket.o  $(COMP)/ServerException.o $(COMP)/Serveur_CheckInThread.o $(COMP)/Serveur_CheckIn.o $(COMP)/QuitApp.o $(COMP)/ClientException.o $(COMP)/Application_CheckIn.o
PROGRAMS=CsvExceptionTest CsvHandlerTest ErrnoExceptionTest HostInfoTest LoginCsvHandlerTest PropertiesTest test launcherServeur_CheckIn launcherApplication_CheckIn
ALL: $(PROGRAMS)


launcherApplication_CheckIn:	$(APPL)/launcher.cpp $(OBJS)
		echo Creating launcherApplication_CheckIn executable ...
		$(CC) -o launcherApplication_CheckIn $(APPL)/launcher.cpp $(OBJS)

$(COMP)/Application_CheckIn.o:	$(APPL)/Application_CheckIn.h $(APPL)/Application_CheckIn.cpp $(OBJS)
		echo Creating Application_CheckIn.o ...
		$(CC) -c $(APPL)/Application_CheckIn.cpp
		mv Application_CheckIn.o $(COMP)

$(COMP)/ClientException.o:	$(APPL)/ClientException.h $(APPL)/ClientException.cpp
		echo Creating ClientException.o ...
		$(CC) -c $(APPL)/ClientException.cpp
		mv ClientException.o $(COMP)

$(COMP)/QuitApp.o:	$(APPL)/QuitApp.h $(APPL)/QuitApp.cpp
		echo Creating QuitApp.o ...
		$(CC) -c $(APPL)/QuitApp.cpp
		mv QuitApp.o $(COMP)

launcherServeur_CheckIn:	$(SERV)/launcher.cpp $(OBJS)
		echo Creating launcherServeur_CheckIn executable ...
		$(CC) -o launcherServeur_CheckIn $(SERV)/launcher.cpp $(OBJS)

$(COMP)/Serveur_CheckInThread.o:	$(SERV)/Serveur_CheckInThread.h $(SERV)/Serveur_CheckInThread.cpp $(COMP)/Socket.o $(COMP)/Serveur_CheckIn.o $(COMP)/LoginCsvHandler.o
		echo Creating Serveur_CheckInThread.o ...
		$(CC) -c $(SERV)/Serveur_CheckInThread.cpp
		mv Serveur_CheckInThread.o $(COMP)

$(COMP)/Serveur_CheckIn.o:	$(SERV)/Serveur_CheckIn.h $(SERV)/Serveur_CheckIn.cpp $(COMP)/Serveur_CheckInThread.o $(COMP)/ServerException.o $(COMP)/HostInfo.o $(COMP)/ServerSocket.o $(COMP)/CIMP.o $(COMP)/LoginCsvHandler.o $(COMP)/Properties.o
		echo Creating Serveur_CheckIn.o ...
		$(CC) -c $(SERV)/Serveur_CheckIn.cpp
		mv Serveur_CheckIn.o $(COMP)

$(COMP)/ServerException.o:	$(SERV)/ServerException.h $(SERV)/ServerException.cpp
		echo Creating ServerException.o ...
		$(CC) -c $(SERV)/ServerException.cpp
		mv ServerException.o $(COMP)



test:	$(LIB_TEST)/test.cpp
		echo Creating test executable ...
		$(CC) -o test $(LIB_TEST)/test.cpp

PropertiesTest:	$(LIB_TEST)/PropertiesTest.cpp $(COMP)/Properties.o $(COMP)/PropertiesException.o
		echo Creating PropertiesTest executable ...
		$(CC) -o PropertiesTest $(LIB_TEST)/PropertiesTest.cpp $(COMP)/Properties.o $(COMP)/PropertiesException.o

LoginCsvHandlerTest:	$(LIB_TEST)/LoginCsvHandlerTest.cpp $(COMP)/LoginCsvHandler.o $(COMP)/CsvHandler.o $(COMP)/CsvException.o
		echo Creating LoginCsvHandlerTest executable ...
		$(CC) -o LoginCsvHandlerTest $(LIB_TEST)/LoginCsvHandlerTest.cpp $(COMP)/LoginCsvHandler.o $(COMP)/CsvHandler.o $(COMP)/CsvException.o

HostInfoTest:	$(LIB_TEST)/HostInfoTest.cpp $(COMP)/HostInfo.o $(COMP)/HostException.o
		echo Creating HostInfoTest executable ...
		$(CC) -o HostInfoTest $(LIB_TEST)/HostInfoTest.cpp $(COMP)/HostInfo.o $(COMP)/HostException.o

ErrnoExceptionTest:	$(LIB_TEST)/ErrnoExceptionTest.cpp $(COMP)/ErrnoException.o
		echo Creating ErrnoExceptionTest executable ...
		$(CC) -o ErrnoExceptionTest $(LIB_TEST)/ErrnoExceptionTest.cpp $(COMP)/ErrnoException.o

CsvHandlerTest:	$(LIB_TEST)/CsvHandlerTest.cpp $(COMP)/CsvHandler.o $(COMP)/CsvException.o
		echo Creating CsvHandlerTest executable ...
		$(CC) -o CsvHandlerTest $(LIB_TEST)/CsvHandlerTest.cpp $(COMP)/CsvHandler.o $(COMP)/CsvException.o

CsvExceptionTest:	$(LIB_TEST)/CsvExceptionTest.cpp $(COMP)/CsvException.o
		echo Creating CsvExceptionTest executable ...
		$(CC) -o CsvExceptionTest $(LIB_TEST)/CsvExceptionTest.cpp $(COMP)/CsvException.o

$(COMP)/ServerSocket.o:	$(LIB_SOCK)/ServerSocket.h $(LIB_SOCK)/ServerSocket.cpp $(COMP)/Socket.o
		echo Creating ServerSocket.o ...
		$(CC) -c $(LIB_SOCK)/ServerSocket.cpp
		mv ServerSocket.o $(COMP)

$(COMP)/ClientSocket.o:	$(LIB_SOCK)/ClientSocket.h $(LIB_SOCK)/ClientSocket.cpp $(COMP)/Socket.o
		echo Creating ClientSocket.o ...
		$(CC) -c $(LIB_SOCK)/ClientSocket.cpp
		mv ClientSocket.o $(COMP)

$(COMP)/Socket.o:	$(LIB_SOCK)/Socket.h $(LIB_SOCK)/Socket.cpp $(COMP)/ErrnoException.o $(COMP)/SocketException.o $(COMP)/HostInfo.o
		echo Creating Socket.o ...
		$(CC) -c $(LIB_SOCK)/Socket.cpp
		mv Socket.o $(COMP)

$(COMP)/HostInfo.o:	$(LIB_SOCK)/HostInfo.h $(LIB_SOCK)/HostInfo.cpp $(COMP)/HostException.o
		echo Creating HostInfo.o ...
		$(CC) -c $(LIB_SOCK)/HostInfo.cpp
		mv HostInfo.o $(COMP)

$(COMP)/Properties.o:	$(LIB_PROP)/Properties.h $(LIB_PROP)/Properties.cpp $(COMP)/PropertiesException.o
		echo Creating Properties.o ...
		$(CC) -c $(LIB_PROP)/Properties.cpp
		mv Properties.o $(COMP)

$(COMP)/LuggageCsvHandler.o:	$(LIB_CSV)/LuggageCsvHandler.h $(LIB_CSV)/LuggageCsvHandler.cpp $(COMP)/CsvHandler.o $(COMP)/LuggageDatabase.o
		echo Creating LuggageCsvHandler.o ...
		$(CC) -c $(LIB_CSV)/LuggageCsvHandler.cpp
		mv LuggageCsvHandler.o $(COMP)

$(COMP)/LuggageDatabase.o:	$(LIB_CSV)/LuggageDatabase.h $(LIB_CSV)/LuggageDatabase.cpp
		echo Creating LuggageDatabase.o ...
		$(CC) -c $(LIB_CSV)/LuggageDatabase.cpp
		mv LuggageDatabase.o $(COMP)

$(COMP)/TicketCsvHandler.o:	$(LIB_CSV)/TicketCsvHandler.h $(LIB_CSV)/TicketCsvHandler.cpp $(COMP)/CsvHandler.o $(COMP)/CIMP.o
		echo Creating TicketCsvHandler.o ...
		$(CC) -c $(LIB_CSV)/TicketCsvHandler.cpp
		mv TicketCsvHandler.o $(COMP)

$(COMP)/LoginCsvHandler.o:	$(LIB_CSV)/LoginCsvHandler.h $(LIB_CSV)/LoginCsvHandler.cpp $(COMP)/CsvHandler.o
		echo Creating LoginCsvHandler.o ...
		$(CC) -c $(LIB_CSV)/LoginCsvHandler.cpp
		mv LoginCsvHandler.o $(COMP)

$(COMP)/CsvHandler.o:	$(LIB_CSV)/CsvHandler.h $(LIB_CSV)/CsvHandler.cpp $(COMP)/CsvException.o
		echo Creating CsvHandler.o ...
		$(CC) -c $(LIB_CSV)/CsvHandler.cpp
		mv CsvHandler.o $(COMP)

$(COMP)/CIMP.o:	$(LIB_CIMP)/CIMP.h $(LIB_CIMP)/CIMP.cpp $(COMP)/CIMPException.o
		echo Creating CIMP.o ...
		$(CC) -c $(LIB_CIMP)/CIMP.cpp
		mv CIMP.o $(COMP)

$(COMP)/CIMPException.o:	$(LIB_EXC)/CIMPException.cpp $(LIB_EXC)/CIMPException.h
		echo Creating CIMPException.o ...
		$(CC) -c $(LIB_EXC)/CIMPException.cpp
		mv CIMPException.o $(COMP)

$(COMP)/HostException.o:	$(LIB_EXC)/HostException.cpp $(LIB_EXC)/HostException.h
		echo Creating HostException.o ...
		$(CC) -c $(LIB_EXC)/HostException.cpp
		mv HostException.o $(COMP)

$(COMP)/SocketException.o:	$(LIB_EXC)/SocketException.cpp $(LIB_EXC)/SocketException.h
		echo Creating SocketException.o ...
		$(CC) -c $(LIB_EXC)/SocketException.cpp
		mv SocketException.o $(COMP)

$(COMP)/PropertiesException.o:	$(LIB_EXC)/PropertiesException.cpp $(LIB_EXC)/PropertiesException.h
		echo Creating PropertiesException.o ...
		$(CC) -c $(LIB_EXC)/PropertiesException.cpp
		mv PropertiesException.o $(COMP)

$(COMP)/ErrnoException.o:	$(LIB_EXC)/ErrnoException.cpp $(LIB_EXC)/ErrnoException.h
		echo Creating ErrnoException.o ...
		$(CC) -c $(LIB_EXC)/ErrnoException.cpp
		mv ErrnoException.o $(COMP)

$(COMP)/CsvException.o:	$(LIB_EXC)/CsvException.cpp $(LIB_EXC)/CsvException.h
		echo Creating CsvException.o ...
		$(CC) -c $(LIB_EXC)/CsvException.cpp
		mv CsvException.o $(COMP)


clean:
	@rm -f $(COMP)/*.o core

clobber:	clean
	@rm -f tags $(PROGRAMS)
