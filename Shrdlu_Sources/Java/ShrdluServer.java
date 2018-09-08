//////////////////////////////////////////////////////////////////////////
// APPLICATION	ShrdluServer
// VERSION		0.0.1
// DATE			3/13/2000
// AUTHOR		Andy Adrian
// PURPOSE		Runs shrdlu and transmits output over Socket.  Also accepts
// 				input from the Socket.
//////////////////////////////////////////////////////////////////////////
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ShrdluServer/* extends Thread*/ {
/*  DataOutputStream outStream;
  DataInputStream inStream;
  Socket socket;
  ServerSocket serverS;
  int port = 9187;
  
  public ShrdluServer() {
System.out.println("starting up...");
    try {
        // establish socket to listen on
      serverS = new ServerSocket(port);
System.out.println("ServerSocket created...");
    } catch (IOException ioe) {
      ioe.printStackTrace();
      return;
    }
System.out.println("starting thread...");
//    start();
    takeOff();
  }

//  public void run() {
  public void takeOff() {
    try {
System.out.println("trying to accept connections...");
      // accept the socket connection
      socket = serverS.accept();
System.out.println("connection accepted...");
      // establish streams on the socket
      outStream = new DataOutputStream(socket.getOutputStream());
      inStream = new DataInputStream(socket.getInputStream());
      // Process p = Runtime.getRuntime().exec("clisp.exe -M lispinit.mem loader");
      Process p = Runtime.getRuntime().exec("clisp loader");
	  DataInputStream shrdluErr = new DataInputStream(p.getErrorStream());
      DataInputStream shrdluOutput = new DataInputStream(p.getInputStream());
	  DataOutputStream shrdluInput = new DataOutputStream(p.getOutputStream());
System.out.println("Streams created...");
System.out.println("Errors?");
	  outStream.writeUTF(shrdluErr.readUTF());
System.out.println("begin output of shrdlu...");
      outStream.writeUTF(shrdluOutput.readUTF());
      while (true) {
        // read in the string from the client
        String s = inStream.readUTF();
        // send it to shrdlu
		shrdluInput.writeUTF(s);
		//get ouput from shrdlu
		String output = shrdluOutput.readUTF();
		//send through outStream to client
		outStream.writeUTF(output);
	  }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
*/
  public static void main(java.lang.String args[]) {
System.out.println("hello?");
//    ShrdluServer s = new ShrdluServer();
  }
}
