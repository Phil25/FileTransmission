# File Transmission — Project Documentation
#### School project to implement a reliable file transfer protocol with throughput control using UDP
#### author: Filip Tomaszewski
#### group: 12c
#### number: s15403

# Contents
* [Overview](#overview-)
* [Running](#running-)
* [PhilTCP Protocol](#philtcp-protocol-)
	* [Packet Structure](#philtcp-packet-structure-)
* [PhilFTP Protocol](#philftp-protocol-)
	* [Packet Structure](#philftp-packet-structure-)
	* [Packet Types](#philftp-packet-types-)
* [Features](#features-)
* [Files](#files-)

# Overview [^](#contents)

This project contains two applications: Client and Server.

Client sends the specified file to Server specified in Client's arguments. It stops after the file has been successfully sent. Its arguments are `-server`, `-port` and `-file`.

Server application blocks the thread and is constantly awaiting connections, even after the file has been received. Its arguments are `-port` and `-speed` (unimplemented).

# Running [^](#contents)

### Server

From project root: `server.[sh/bat] -port <listen port> -speed <speed in KB/s>`

Example: `./server.sh -port 4000 -speed 1`

If script not working: `java -cp bin filetransmission.server.Server <args>`

### Client

From project root: `client.[sh/bat] -server <address> -port <port of server> -file <filename>`

Example: `./client.sh -server localhost -port 4000 -file project3.pdf`

If script not working: `java -cp bin filetransmission.client.Client <args>`

File is given relative to the script's location.

# PhilTCP Protocol [^](#contents)

PhilTCP is used to reliably transfer packets, providing classes `PhilTCPClient` and `PhilTCPServer` to achieve this.

The data is split into chunks of 1016 (maximum body size), given a header of 8 bytes and sent to the specified address using UDP after being saved in an ArrayList.

No window functionality is implemented, instead, the acknowledgement is awaited after each sent packet with a fixed time span of 64ms.

A packet is resent after the following conditions:

1. Nothing has arrived on time (64ms).
1. Arrived packet's sequence number doesn't match the last sent packet's one.
1. Arrived packet's ack flag is false.
1. Arrived packet's checksum is invalid.

## PhilTCP Packet Structure [^](#contents)

byte | bit | name | type | description
---|---|---|---|---
0 | 0 | seq | 15-bit big-endian signed integer | sequence number of the packet
1 | 15 | ack | 1-bit flag value | acknowledgement of correct receival
2 | 16 | checksum | 32-bit big-endian signed integer | very simple checksum obtained by some random algorithm I made up
6 | 48 | len | 16-bit big-endian signed integer | length of the `body` byte array
8 | 64 | body | byte array | the byte array containing the file

# PhilFTP Protocol [^](#contents)

PhilFTP is used for transfering files between a client and a server.

It **IS SUPPOSED TO** (unimplemented) open a two-way communication, using `PhilTCPClient` and `PhilTCPServer`, where one is used for sending the file from client to server and the other one for relaying commands the other way.
Command types are defined [here](#philftp-packet-types-).

Instead, it opens a one-way communication, using only `PhilTCPServer` for `Server` and `PhilTCPClient` for `Client`, for sending the name of the file initially and then the file itself.

## PhilFTP Packet Structure [^](#contents)

Unused, PhilFTP protocol not fully implemented.

byte | bit | name | type | description
---|---|---|---|---
0 | 0 | type | 8-bit big-endian signed integer | [type](#philftp-packet-types-) of the message
1 | 8 | len | 32-bit big-endian signed integer | length of the `body` byte array
5 | 40 | body | byte array | the byte array containing the file

## PhilFTP Packet Types [^](#contents)

Unused, PhilFTP protocol not fully implemented.

name | value | description
---|---|---
`TYPE_RECV_PORT` | 0 | tells the receiver of the other port
`TYPE_FILE_NAME` | 1 | specifies that packet carries name of the file sent
`TYPE_SPEED` | 2 | specifies that packet carries the speed the client needs to adjust to
`TYPE_DATA` | 3 | used for transmission of the file's bytes

# Features [^](#contents)

What has or hasn't been implemented, plus potential bugs.

### PhilTCP

* (+) Using UDP protocol.
* (+) Assures correct ordering of packets by sending them one at a time.
* (+) Retransmites in case of packet loss.
* (+) Checksum verification after each packet transmission.
* (-) Waits for a response packet after each one sent instead of implementing a window feature.
* (-) No handshake/connection form implemented.
* (?) Uses own algorithm instead of MD5 for checksums.

### PhilFTP

* (+) Uses `PhilTCP` to send the file.
* (+) First packet is the name of the file, after that, the file itself is sent.
* (+) Files are put in a subfolder "incoming/".
* (-) No MD5 file recheck after the transmission.
* (-) No implementation of throughput control nor display.
* (-) Only one file can be sent per running of both Client and Server.

# Files [^](#contents)

## client.Client

The Client application, using `PhilFTPClient` to send the data of the file specified by the arguments given by the user.

## server.Server

The Server application, using `PhilFTPServer` to receive data and building the file.

## tools.ArgParser

Abstracts reading and processing of arguments passed with a hyphen.

## tools.ByteOps

Abstracts bitwise calculations.

## tools.Checksum

Abstracts calculations and checking of the checksum value.

## net.philtcp.packet.*

Provides the structure and byte array-packet translations for PhilTCP packets.

## net.philtcp.PhilTCPClient

The client used for transmitting PhilTCP packets.

## net.philtcp.PhilTCPServer

The server used for receiving PhilTCP packets.

## net.philftp.packet.*

Provides the structure and byte array-packet translations for PhilFTP packets. (unused, PhilFTP protocol not fully implemented)

## net.philftp.PhilFTPClient

The client used for deconstructing and transmitting files.

## net.philftp.PhilFTPServer

The server used for receiving receiving and constructing files.
