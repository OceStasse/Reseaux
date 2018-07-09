#!/usr/bin/python3
#-*- coding: utf-8 -*-

import socket
import sys

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

server_address = ('127.0.0.1', 65000)
print('Connexion à {} au port {}'.format(*server_address))
sock.connect(server_address)

#message = b'New_User:Hello;World'
message = b'Login:Hello;World'
#message = b'Close'

print('Envoi {!r}'.format(message))
sock.sendall(message)

sock.close()

