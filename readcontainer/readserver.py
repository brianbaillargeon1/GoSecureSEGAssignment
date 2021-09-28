#!/usr/bin/env python3

print("Importing dependencies")

import redis
from http.server import BaseHTTPRequestHandler
from http.server import HTTPServer
import json

print("Setting up PeopleHandler")

class PeopleHandler(BaseHTTPRequestHandler):
	def getPeopleAsHtml(self):
		html = "<html><body>"
		r = redis.StrictRedis(host="personContainer", decode_responses=True)
		people = r.smembers('person')
		if not people:
			html = "There are no person entries."
		else:
			for personJson in people:
				person = json.loads(personJson)
				html += "<ul>"
				html += "<li>Name: " + person["name"] + "</li>"
				html += "<li>Address: " + person["address"] + "</li>"
				html += "<li>Phone Number: " + person["phoneNumber"] + "</li>"
				html += "<li>Email: " + person["email"] + "</li>"
				html += "</ul>"
		html += "</body>\n</html>"
		return html

	def do_GET(self):
		if self.path == '/people':
			print("Received request for /people")
			self.send_response(200)
			self.send_header("Content-type","text/html")
			self.end_headers()
			self.wfile.write(self.getPeopleAsHtml().encode('utf-8'))
		else:
			print("Unknown path: " + self.path)
			self.send_error(404, "Not Found")

print("Setting up the HTTP Server")
httpserver = HTTPServer(("0.0.0.0",8080), PeopleHandler)

print("HttpServer created; about to begin serving")
httpserver.serve_forever()

