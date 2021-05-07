from http.server import BaseHTTPRequestHandler, HTTPServer
import logging
import subprocess
import os

#gcloud builds submit --tag gcr.io/cs590-308319/gcf-test:v2

runningThread = 2
#os.system('java -classpath Server SampleClient')
class S(BaseHTTPRequestHandler):
	def _set_response(self):
		self.send_response(200)
		self.send_header('Content-type', 'text/html')
		self.end_headers()
	def do_GET(self):
		logging.info("GET request,\nPath: %s\nHeaders:\n%s\n", str(self.path), str(self.headers))
		self._set_response()
		self.wfile.write("GET request for {}".format(self.path).encode('utf-8'))
	def do_POST(self):	
		content_length = int(self.headers['Content-Length']) # <--- Gets the size of data
		post_data = self.rfile.read(content_length) # <--- Gets the data itself
		logging.info("POST request,\nPath: %s\nHeaders:\n%s\n\nBody:\n%s\n",str(self.path), str(self.headers), post_data.decode('utf-8'))
		data = post_data.decode('utf-8').strip()
		stringArray = data.split("\n")
		command = stringArray[0]
		data = stringArray[1]

		if command == "StartThread":
			f = open("input.txt", "w")
			f.write(data)
			f.close()
			

			#proc = subprocess.Popen(["C:/Users/nivl0/.jdks/adopt-openjdk-14.0.2/bin/java", "-classpath", "Server", "Main"], stdout=subprocess.PIPE, shell=True)
			#os.system('java -classpath Server Main')
			#proc = subprocess.Popen(["java", "-classpath", "Server", "Main"], stdout=subprocess.PIPE, shell=True)
			proc = subprocess.Popen(['java -classpath Server Main'], stdout=subprocess.PIPE, shell=True)
			
			(out, err) = proc.communicate()
			out = out.decode()
			out = out.strip()
			logging.info(out)
		elif command == "Message":
			
		#os.system('C:/Users/nivl0/.jdks/adopt-openjdk-14.0.2/bin/java -classpath Server Main')
		self._set_response()
		self.wfile.write(out.encode('utf-8'))

def run(server_class=HTTPServer, handler_class=S, port=8080):
	logging.basicConfig(level=logging.INFO)
	server_address = ('', port)
	httpd = server_class(server_address, handler_class)
	logging.info('Starting httpd...\n')
	print(httpd)
	try:
		httpd.serve_forever()
	except KeyboardInterrupt as e:
		print(e)
		pass
	httpd.server_close()
	logging.info('Stopping httpd...\n')

if __name__ == '__main__':
	from sys import argv
	if len(argv) == 2:
		run(port=int(argv[1]))
	else:
		run()