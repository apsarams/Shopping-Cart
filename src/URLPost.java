import java.net.*;
import java.io.*;

public class URLPost extends Object
{
    public static void main(String args[])
    {
        try {
            URL destURL = new URL(
                "http://hoohoo.ncsa.uiuc.edu/cgi-bin/test-cgi/foo");

// The following request data mimics what the NCSA example CGI
// form for this CGI program would send.

            String request = "button=on\r\n";   
            URLConnection urlConn = destURL.openConnection();

            urlConn.setDoOutput(true);    // we need to write
            urlConn.setDoInput(true);    // just to be safe...
            urlConn.setUseCaches(false);    // get info fresh from server

// Tell the server what kind of data you are sending - in this case,
// just a stream of bytes.

            urlConn.setRequestProperty("Content-type",
                "application/octet-stream");

// Must tell the server the size of the data you are sending. This also
// tells the URLConnection class that you are doing a POST instead
// of a GET.

            urlConn.setRequestProperty("Content-length", ""+request.length());

// Open an output stream so you can send the info you are posting

            DataOutputStream outStream = new DataOutputStream(
                urlConn.getOutputStream());

// Write out the actual request data

            outStream.writeBytes(request);
            outStream.close();

// Now that you have sent the data, open up an input stream and get
// the response back from the server

            DataInputStream inStream = new DataInputStream(
                urlConn.getInputStream());

            int ch;

// Dump the contents of the request to System.out

            while ((ch = inStream.read()) >= 0) {
                System.out.print((char) ch);
            }

            inStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}