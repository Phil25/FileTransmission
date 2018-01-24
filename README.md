# File Transmission — Project Documentation
#### School project to implement a reliable file transfer protocol with throughput control using UDP
#### author: Filip Tomaszewski
#### group: 12c
#### number: s15403

# Contents
* [Overview](#overview-)
* [Running](#running-)
* [PhilTCP Protocol](#philtcp-protocol-)
	* [Packet Structure](#packet-structure-)
	* [Packet Types](#packet-types-)
	* [Specification](#specification-)
* [Features](#features-)
* [Files](#files-)

# Overview [^](#contents)

Overview placeholder

# Running [^](#contents)

Running placeholder

# PhilTCP Protocol [^](#contents)

PhilTCP is used to reliably transfer packets.

## PhilTCP Packet Structure [^](#contents)

byte | bit | name | type | description
---|---|---|---|---
0 | 0 | seq | 15-bit big-endian signed integer | sequence number of the packet
1 | 15 | ack | 1-bit flag value | acknowledgement of correct receival
2 | 16 | checksum | 32-bit big-endian signed integer | very simple checksum obtained by suming all the values
6 | 48 | len | 16-bit big-endian signed integer | length of the `body` byte array
8 | 64 | body | byte array | the byte array containing the file

# PhilFTP Protocol [^](#contents)

PhilFTP is used for transfering files between a client and a server.
It opens a two-way communication where one is used for sending the file from client to server and the other one for relaying commands the other way.
Command types are defined [here](#philftp-packet-types-).

## PhilFTP Packet Structure [^](#contents)

byte | bit | name | type | description
---|---|---|---|---
0 | 0 | type | 8-bit big-endian signed integer | [type](#philftp-packet-types-) of the message
1 | 8 | len | 32-bit flag value | length of the `body` byte array
5 | 40 | body | byte array | the byte array containing the file

## PhilFTP Packet Types [^](#contents)

name | value | description
---|---|---
`TYPE_RECV_PORT` | 0 | tells the receiver of the other port
`TYPE_FILE_NAME` | 1 | specifies that packet carries name of the file sent
`TYPE_SPEED` | 2 | specifies that packet carries the speed the client needs to adjust to
`TYPE_DATA` | 3 | used for transmission of the file's bytes

## Specification [^](#contents)

* Both client and server have two sockets open:
	* client-\>server to send the file
	* server-\>client to relay transfer commands
* After sending a packet the node must receive a response in form of a packet with the same ID and `ack` bit set.

1. Client sends to the server packet of `TYPE_FILE_NAME` type with name of the file as data.
1. Server sends back `TYPE_SPEED` packet.
1. Client sends `TYPE_RECV_PORT` indicating the port its listener is open on.
1. Client sends `TYPE_DATA` every 100ms and waits for the acknowledgement.

# Features [^](#contents)

Features placeholder

# Files [^](#contents)

Files placeholder
