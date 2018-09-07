import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.vecmath.*;

public class Shrdlu extends Frame implements ActionListener {

  public Button Send, Clear, Exit;
  public TextArea history;
  public TextField current;
  private BlocksWorld BWPanel;

  //initializes sockets and streams, and reads initial Strings from server.  
  private BufferedReader shrdluErr, shrdluOutput;
  private PrintStream shrdluInput;
  private String ReadyTest = "blah";
    
/*
*  class constructor - defines/initializes all objects used in class 
*/

  public Shrdlu() {
    addWindowListener(new WindowAdapter() {
									public void windowClosing(WindowEvent event) {
									  try {
										shrdluErr.close();
										shrdluOutput.close();
										shrdluInput.close();
									  } catch (IOException e) { e.printStackTrace(); }
									  System.exit(0);
									}
								});

    Send = new Button("Send");
	Send.addActionListener(this);
	Clear = new Button("Clear");
	Clear.addActionListener(this);
	Exit = new Button("Exit");
	Exit.addActionListener(this);
	
    Panel SPanel = new Panel();
	Panel CPanel = new Panel();
	BWPanel = new BlocksWorld();
	Panel BackPanel = new Panel();
		
	history = new TextArea(null, 10, 50, TextArea.SCROLLBARS_BOTH);
	history.setEditable(false);
	current = new TextField(null, 50);
  
    CPanel.setLayout(new BorderLayout());
    CPanel.add("North", history);
	CPanel.add("South", current);
	SPanel.add(Send);
	SPanel.add(Clear);
	SPanel.add(Exit);

	BackPanel.setLayout(new BorderLayout());
	BackPanel.add("South", SPanel);
    BackPanel.add("Center", CPanel);
	add("South", BackPanel);
	add(BWPanel);
	
	StartMe();
  }
 
  private void StartMe() {
  
    try {
      Process p = Runtime.getRuntime().exec("clisp.exe -M lispinit.mem loader");
	  shrdluErr = new BufferedReader(new InputStreamReader(p.getErrorStream()));
      shrdluOutput = new BufferedReader(new InputStreamReader(p.getInputStream()));
	  shrdluInput = new PrintStream(p.getOutputStream());
      for (int i=0; i<=6; i++) {
	    String temp = shrdluOutput.readLine();
		ReadyTest = temp;
//System.out.println(temp);
		history.append(temp + "\n");
	  }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private void ParseCommand(String Command){
  		  float LocX, LocY, LocZ;
		  String strLocX = "", strLocY = "", strLocZ = "";
		  
		  char[] com = new char[Command.length()];
		  Command.getChars(0, Command.length(), com, 0); 
  		  switch (com[1]) {
		    case 'M': 
				 int dir = 0;
				 for (int i = 17; i<Command.length()-2; i++) {
					 switch(dir) {
						case 0:
						  if(com[i] == com[7]) {
						   dir++;
						  } else {
						   strLocX += com[i];
						  }
						break;
						case 1:
						  if(com[i] == com[7]) {
							dir++;
						  } else {
							strLocY += com[i];
						  }
						break;
						case 2:
						  if(com[i] == com[7]) {
							dir++;
						  } else {
							strLocZ += com[i];
						  }
						break;
						default:  
						    System.out.println("shouldn't be here... Shrdlu:ParseCommand():M");
						    System.out.println("--> the current character is " + com[i]);
					 }
				 }
						  
				 LocX = Integer.decode(strLocX).floatValue();
				 LocY = Integer.decode(strLocY).floatValue();
				 LocZ = Integer.decode(strLocZ).floatValue();
						 						  
				  BWPanel.MoveTo(new Point3f(LocX, LocY, LocZ));
 		    break;
			case 'L': 
//System.out.println("--> UnGrasp");
				BWPanel.UnGrasp();
			break;
			case 'G': 
			    String blockName = null;
//System.out.println("--> Grasp");
				if(com[18] == com[0]) {
				  blockName = Command.substring(16, 18);
				} else {
				  blockName = Command.substring(16, 19);
				}
		 	  	 BWPanel.Grasp(blockName);
		    break;
			default:  System.out.println("-->Should not be here. Shrdlu:ParseCommand:default");
					  	  System.out.println(com);
 		    break;
		  }
  }
  
  public void actionPerformed(ActionEvent evt) {
  	Object source = evt.getSource();
	String CurText, Command, CurReturn;
	if(source == Send) {
	 try {
        // read in the string from the TextField
        // send it to shrdlu
	   String command = current.getText();
	   current.setText(null);
  	   shrdluInput.println(command);
	   shrdluInput.flush();
//System.out.println(command);
	   history.append(command + "\n");
		
		//get ouput from shrdlu
	   CurReturn = "startme...";
     
	   while(! CurReturn.equals(ReadyTest) && command!=null) {
		 CurReturn = shrdluOutput.readLine();
//System.out.println(CurReturn);
		 if (CurReturn.length() != 0) {
		   char[] ret = new char[CurReturn.length()];
		   CurReturn.getChars(0, CurReturn.length(), ret, 0);		
		    if(ret[0] == '~') {
			     ParseCommand(CurReturn);
		    } else {
		      history.append(CurReturn + "\n");
		    }
         } else history.append("\n");
	    }  
	 } catch( IOException e) {
		System.out.println("An I/O exception has occurred.  Shrdlu:actionPerformed");
		e.printStackTrace(System.out);
	 }
	}
	if(source == Clear) {
 	  current.setText(null);
	}
	if(source == Exit) {
	    try {
			shrdluErr.close();
			shrdluOutput.close();
			shrdluInput.close();
	    } catch (IOException e) { e.printStackTrace(); }
		System.exit(0);
	}
}

/**
 * main entrypoint - starts the part when it is run as an application
 * @param args java.lang.String[]
 */

 public static void main(java.lang.String[] args) {

	try {
	
		Frame frame = new Shrdlu();
		frame.setTitle("Shrdlu");
		frame.setSize(500, 800);
		frame.setVisible(true);
		frame.pack();
		frame.setVisible(true);
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of Shrdlu");
		exception.printStackTrace(System.out);
	}
 }
}