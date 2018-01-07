# File Transmission — Project Documentation
#### School project to implement a reliable file transfer protocol with throughput control using UDP
#### author: Filip Tomaszewski
#### group: 12c
#### number: s15403

# Contents
* [Overview](#overview-)
* [Running](#running-)
* [PhilFTP Protocol](#philftp-protocol-)
	* [Packet Structure](#packet-structure-)
	* [Packet Types](#packet-types-)
	* [Specification](#specification-)
* [Features](#features-)
* [Files](#files-)

# Overview [^](#contents)

Overview placeholder

# Running [^](#contents)

Running placeholder

# PhilFTP Protocol [^](#contents)

## Packet Structure [^](#contents)

byte | bit | name | type | description
---|---|---|---|---
0 | 0 | checksum | 32-bit big-endian signed integer | very simple checksum optained by suming all the values
4 | 32 | id | 32-bit big-endian signed integer | ID to aid ordered delivery of packets
8 | 64 | type | 7-bit big-endian signed integer | [type of the packet](#packet-types-) used
8 | 71 | ack | 1-bit boolean | Acknowledgement bit for confirming packet delivery
9 | 72 | len | 32-bit big-endian signed integer | length of the `body` byte array
13 | 104 | body | byte array | the byte array containing the file

## Packet Types [^](#contents)

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
