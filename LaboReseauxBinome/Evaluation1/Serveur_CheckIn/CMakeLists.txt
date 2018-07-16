cmake_minimum_required(VERSION 3.8)
project(Serveur_CheckIn)
project(Librairies)

set(CMAKE_CXX_STANDARD 11)

set(LIBRARY_SOURCE_FILES
		../Librairies/SocketUtilities/HostInfo.cpp
		../Librairies/SocketUtilities/HostInfo.h
		../Librairies/SocketUtilities/Socket.cpp
		../Librairies/SocketUtilities/Socket.h
		../Librairies/SocketUtilities/ClientSocket.h
		../Librairies/SocketUtilities/ClientSocket.cpp
		../Librairies/SocketUtilities/ServerSocket.cpp
		../Librairies/SocketUtilities/ServerSocket.h
		../Librairies/CSV/TicketCsvHandler.cpp
		../Librairies/CSV/TicketCsvHandler.h
		../Librairies/CSV/LoginCsvHandler.cpp
		../Librairies/CSV/LoginCsvHandler.h
		../Librairies/CSV/LuggageDatabase.h
		../Librairies/CSV/LuggageDatabase.cpp
		../Librairies/CSV/LuggageCsvHandler.h
		../Librairies/CSV/LuggageCsvHandler.cpp
		../Librairies/CSV/CsvHandler.cpp
		../Librairies/CSV/CsvHandler.h
		../Librairies/Exceptions/CsvException.h
		../Librairies/Exceptions/CsvException.cpp
		../Librairies/Exceptions/PropertiesException.h
		../Librairies/Exceptions/PropertiesException.cpp
		../Librairies/Exceptions/SocketException.cpp
		../Librairies/Exceptions/SocketException.h
		../Librairies/Exceptions/ErrnoException.cpp
		../Librairies/Exceptions/ErrnoException.h
		../Librairies/Exceptions/HostException.cpp
		../Librairies/Exceptions/HostException.h
		../Librairies/Exceptions/CIMPException.cpp
		../Librairies/Exceptions/CIMPException.h
		../Librairies/Properties/Properties.h
		../Librairies/Properties/Properties.cpp
		../Librairies/CIMP/CIMP.h
		../Librairies/CIMP/CIMP.cpp)
set(SERVER_SOURCE_FILES
		Launcher.cpp
		Serveur_CheckIn.h
		Serveur_CheckIn.cpp
		ServerException.h
		ServerException.cpp
		Serveur_CheckInThread.h
		Serveur_CheckInThread.cpp
		)

add_library(Librairies ${LIBRARY_SOURCE_FILES})
add_executable(Serveur_CheckIn ${LIBRARY_SOURCE_FILES} ${SERVER_SOURCE_FILES})