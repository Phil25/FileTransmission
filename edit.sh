#!/bin/bash

#vim -p ./src/filetransmission/client/* ./src/filetransmission/server/* ./src/filetransmission/net/* ./src/filetransmission/tools/*

shopt -s globstar
function findFiles(){
	for i in src/**
	do
		if [ -f "$i" ]; then
			printf "$i "
		fi
	done
}

vim -p `findFiles`
