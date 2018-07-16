cmake_minimum_required(VERSION 3.8)

set(CMAKE_CXX_STANDARD 11)

project(Librairies)
project(test_CsvException)
project(test_CsvHandler)
project(test_LoginCsvHandler)
project(test_Properties)
project(test_Project)

set(CIMP_SOURCE_FILES
		CIMP/CIMP.h
		CIMP/CIMP.cpp)
set(CSV_SOURCE_FILES
		CSV/CsvHandler.h
		CSV/CsvHandler.cpp
		CSV/LoginCsvHandler.cpp
		CSV/LoginCsvHandler.h
		CSV/TicketCsvHandler.h
		CSV/TicketCsvHandler.cpp
		CSV/LuggageDatabase.h
		CSV/LuggageDatabase.cpp
		CSV/LuggageCsvHandler.cpp
		CSV/LuggageCsvHandler.h)
set(EXCEPTION_SOURCE_FILES
		Exceptions/CsvException.h
		Exceptions/CsvException.cpp
		Exceptions/PropertiesException.h
		Exceptions/PropertiesException.cpp
		Exceptions/SocketException.cpp
		Exceptions/SocketException.h
		Exceptions/ErrnoException.cpp
		Exceptions/ErrnoException.h
		Exceptions/HostException.cpp
		Exceptions/HostException.h
		Exceptions/CIMPException.h
		Exceptions/CIMPException.cpp)
set(PROPERTIES_SOURCE_FILES
		Properties/Properties.h
		Properties/Properties.cpp)
set(SOCKET_SOURCE_FILES
		SocketUtilities/HostInfo.cpp
		SocketUtilities/HostInfo.h
		SocketUtilities/Socket.cpp
		SocketUtilities/Socket.h
		SocketUtilities/ClientSocket.h
		SocketUtilities/ClientSocket.cpp
		SocketUtilities/ServerSocket.cpp
		SocketUtilities/ServerSocket.h)


add_library(Librairies ${CIMP_SOURCE_FILES} ${CSV_SOURCE_FILES} ${EXCEPTION_SOURCE_FILES} ${PROPERTIES_SOURCE_FILES} ${SOCKET_SOURCE_FILES})

add_executable(test_CsvException
		Tests/CsvExceptionTest.cpp
		${EXCEPTION_SOURCE_FILES})
add_executable(test_CsvHandler
		Tests/CsvHandlerTest.cpp
		${EXCEPTION_SOURCE_FILES} ${CSV_SOURCE_FILES})
add_executable(test_LoginCsvHandler
		Tests/LoginCsvHandlerTest.cpp
		${EXCEPTION_SOURCE_FILES} ${CSV_SOURCE_FILES})
add_executable(test_Properties
		Tests/PropertiesTest.cpp
		${EXCEPTION_SOURCE_FILES} ${PROPERTIES_SOURCE_FILES})
add_executable(test_errnoException
		Tests/ErrnoExceptionTest.cpp
		${EXCEPTION_SOURCE_FILES})
add_executable(test_hostInfo
		Tests/HostInfoTest.cpp
		${EXCEPTION_SOURCE_FILES} ${SOCKET_SOURCE_FILES})

add_executable(test_Project
		Tests/test.cpp)