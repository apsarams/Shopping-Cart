import java.net.*;
import java.io.*;

// This applet shows you how to open up a socket to an HTTP server
// and post data to a server. It posts information to one of the
// example CGI programs set up by the NCSA.

public class PostSockURL extends Object
{
     public static void main(String args[])
     {
          try {

// Open up a socket to the Web server where this applet came from
               Socket sock = new Socket("hoohoo.ncsa.uiuc.edu", 80);

// Get input and output streams for the socket connection
               DataInputStream inStream = new DataInputStream(
                    sock.getInputStream());
               DataOutputStream outStream = new DataOutputStream(
                    sock.getOutputStream());

// This request is what is sent by the NCSA's example form

               String request = "button=on\r\n";

// Send the POST request to the server
// The request is of the form: POST filename HTTP/1.0

               outStream.writeBytes("POST /cgi-bin/test-cgi/foo "+
                    " HTTP/1.0\r\n");

// Next, send the content type (don't forget the \r\n)
               outStream.writeBytes(
                    "Content-type: application/octet-stream\r\n");

// Send the length of the request
               outStream.writeBytes(
                    "Content-length: "+request.length()+"\r\n");

// Send a \r\n to indicate the end of the header
               outStream.writeBytes("\r\n");

// Now send the information you are posting

               outStream.writeBytes(request);


// Dump the response to System.out

               int ch;

               while ((ch = inStream.read()) >= 0) {
                    System.out.print((char) ch);
               }

// We're done with the streams, so close them
               inStream.close();
               outStream.close();
              
          } catch (Exception e) {
               e.printStackTrace();
          }
     }
}