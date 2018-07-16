cmake_minimum_required(VERSION 3.8)
project(Application_CIAChat)

set(CMAKE_CXX_STANDARD 11)

set(LIBRARY_SOURCE_FILES
		../../Evaluation1/Librairies/SocketUtilities/HostInfo.cpp
		../../Evaluation1/Librairies/SocketUtilities/HostInfo.h
		../../Evaluation1/Librairies/SocketUtilities/Socket.cpp
		../../Evaluation1/Librairies/SocketUtilities/Socket.h
		../../Evaluation1/Librairies/SocketUtilities/ClientSocket.h
		../../Evaluation1/Librairies/SocketUtilities/ClientSocket.cpp
		../../Evaluation1/Librairies/Properties/Properties.h
		../../Evaluation1/Librairies/Properties/Properties.cpp
		../../Evaluation1/Librairies/Exceptions/PropertiesException.cpp
		../../Evaluation1/Librairies/Exceptions/PropertiesException.h
		../../Evaluation1/Librairies/Exceptions/SocketException.cpp
		../../Evaluation1/Librairies/Exceptions/SocketException.h
		../../Evaluation1/Librairies/Exceptions/HostException.cpp
		../../Evaluation1/Librairies/Exceptions/HostException.h
		../../Evaluation1/Librairies/Exceptions/ErrnoException.cpp
		../../Evaluation1/Librairies/Exceptions/ErrnoException.h
		)

set(SOURCE_FILES
		Launcher.cpp
		Application_CIAChat_IN.cpp
		Application_CIAChat_IN.h
		IACOP.cpp
		IACOP.h
		IACOPException.cpp
		IACOPException.cpp
		QuitApp.cpp
		QuitApp.h
		ClientException.cpp
		ClientException.h
		Message.cpp
		Message.h
		MulticastSocket.cpp
		MulticastSocket.h
		ThreadReception.cpp
		ThreadReception.h)

add_library(Librairies ${LIBRARY_SOURCE_FILES})
add_executable(Application_CIAChat ${SOURCE_FILES} ${LIBRARY_SOURCE_FILES})