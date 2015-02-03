#Client example. 
#Sending a message and reading its echo from the server

import socket 
import sys

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

sock.connect(('localhost', 9999))
 
try: 
	#Sending a stupid ping message
	message = 'This is a ping\n'
	print 'sending a message'
	sock.sendall(message)
	print 'message sent !'

	#reveiving the echo
	data = sock.recv(16)
	print 'received: "%s"' % data

finally: 
	print >> sys.stderr, 'closing socket'
	sock.close()  