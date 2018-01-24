#!/bin/bash

#javac ./src/filetransmission/tools/Checksum.java ./src/filetransmission/tools/ArgParser.java ./src/filetransmission/net/PhilFTPTransmission.java ./src/filetransmission/net/PhilFTPClient.java ./src/filetransmission/net/PhilFTPServer.java ./src/filetransmission/net/PhilFTPHeader.java ./src/filetransmission/net/PhilFTPPacket.java ./src/filetransmission/client/Client.java ./src/filetransmission/server/Server.java -d ./bin/
#javac ./src/filetransmission/tools/ByteOps.java ./src/filetransmission/client/Client.java -d ./bin/

echo ""
echo "/////////////////////////////"
echo "///// COMPILING PROJECT /////"
echo "/////////////////////////////"
echo ""

COMP_ORDER=(
	./src/filetransmission/tools/ArgParser.java
	./src/filetransmission/tools/ByteOps.java
	./src/filetransmission/tools/Checksum.java
	./src/filetransmission/net/philtcp/packet/PhilTCPHeader.java
	./src/filetransmission/net/philtcp/packet/PhilTCPPacket.java
	./src/filetransmission/net/philtcp/PhilTCPClient.java
	./src/filetransmission/net/philtcp/PhilTCPServer.java
	./src/filetransmission/net/philftp/packet/PhilFTPHeader.java
	./src/filetransmission/net/philftp/packet/PhilFTPPacket.java
	./src/filetransmission/net/philftp/PhilFTPClient.java
	./src/filetransmission/net/philftp/PhilFTPServer.java
#	./src/filetransmission/net/philftp/PhilFTPTransmission.java
	./src/filetransmission/client/Client.java
	./src/filetransmission/server/Server.java
)

rm log
javac `echo "${COMP_ORDER[*]}"` -d ./bin/ &> log
if [ -f log ]; then
	cat log
fi
