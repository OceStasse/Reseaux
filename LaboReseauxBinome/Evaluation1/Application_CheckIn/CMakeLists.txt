cmake_minimum_required(VERSION 3.8)
project(Application_CheckIn)
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
		../Librairies/Properties/Properties.h
		../Librairies/Properties/Properties.cpp
		../Librairies/CIMP/CIMP.h
		../Librairies/CIMP/CIMP.cpp
		../Librairies/Exceptions/CIMPException.h
		../Librairies/Exceptions/CIMPException.cpp
		../Librairies/Exceptions/CsvException.h
		../Librairies/Exceptions/CsvException.cpp
		../Librairies/Exceptions/PropertiesException.h
		../Librairies/Exceptions/PropertiesException.cpp
		../Librairies/Exceptions/SocketException.cpp
		../Librairies/Exceptions/SocketException.h
		../Librairies/Exceptions/ErrnoException.cpp
		../Librairies/Exceptions/ErrnoException.h
		../Librairies/Exceptions/HostException.cpp
		../Librairies/Exceptions/HostException.h)
set(SOURCE_FILES
		Launcher.cpp
		Application_CheckIn.h
		Application_CheckIn.cpp
		ClientException.h
		ClientException.cpp
		QuitApp.cpp
		QuitApp.h)

add_library(Librairies ${LIBRARY_SOURCE_FILES})
add_executable(Application_CheckIn ${SOURCE_FILES} ${LIBRARY_SOURCE_FILES})