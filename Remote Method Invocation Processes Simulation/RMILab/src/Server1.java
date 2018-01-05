import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.*;
import java.rmi.server.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.*;
/*Author :Bhagyashree Shinde
 * Student ID:1001552353
 * class implementing Message queuing server 
 *Stores all messages and student details
 *Extending UnicastReamoteObject ensures that your subclass will be exported once constructed.
 *Implements ActioListener to listen to UI actions  
 */
class Server1 extends UnicastRemoteObject implements Interface,ActionListener
{
	Queue queueA = new LinkedList();//queue object used for queuing student details and messages
	   File newTextFile;//globally declaring file object
	//Queue queueB = new PriorityQueue();
	//private static final int COLS = 10;
	//private JPanel mainPanel = new JPanel(); // this is what I'll add to contentPane
	//private JTextField field1 = new JTextField(COLS);
	//private JTextField field2 = new JTextField(COLS);
	//private JPasswordField passwordField = new JPasswordField(COLS);
	private JTextArea serverSideLogs= new JTextArea(); //Here UI element i.e textarea variable is declared which decides the area for text in UI
	private JFrame serverWindowSize;//To create framed window for UI.

	//private JComponent[] allComponents = { new JLabel("Field 1:"), field1,
	  //    new JLabel("Field 2:"), field2, new JLabel("Password:"), passwordField };
	
/*
 * Default constructor that creates GUI
 */
public Server1()throws RemoteException 
{
	serverWindowSize = new JFrame("Server monitor");//Setting the name for frame or window 
	serverWindowSize.setSize(680,750);//setting size of window
	serverWindowSize.getContentPane().setLayout(null);//setting layout 
	//serverWindowSize.getContentPane().setBackground(new Color(205,205,195));//setting background color to frame/window.
	//serverWindowSize.getContentPane().setForeground(Color.white);
	serverWindowSize.setResizable(false);//Window set to non-resizeable
	serverWindowSize.setLocationRelativeTo(null);//Window is non-relative to location

	serverSideLogs = new JTextArea(30,30);//Textarea size set to length=30,breadth=30
	serverSideLogs.setEditable(false);//Textarea is set as non-editable
	JScrollPane scrollserverWindowSize = new JScrollPane(serverSideLogs);//ScrollPane is added to text area
	scrollserverWindowSize.setBounds(10, 10, 652, 600);//Boundaries of window are set
	serverWindowSize.add(scrollserverWindowSize);//adding scrollPane to window/frame.

	JButton exitButton = new JButton("EXIT");//exit button is defined with name on button as "EXIT"


	exitButton.setBounds(275, 625, 150, 50);//(x,y,width,height) to specify the position and size of a GUI component as layout is set to null
	serverWindowSize.getContentPane().add(exitButton);//exit button is added to frame/window i.e to UI
	exitButton.addActionListener(new ActionListener() {//actioListener is added to handle the button actions such as button click.
			public void actionPerformed(ActionEvent e)//method defines action to be performed on clicking
			{
				System.exit(0);//Action taken will be exiting application and closing connection with server i.e shutting down system normally as status code=0
			}
	});
	serverWindowSize.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	serverWindowSize.setVisible(true);//setting visibility to true allows user to view serverWindow
	serverSideLogs.append("Server is now running ..\n");//let's us now that socket is created successfully and server is in running state by appending/adding this message to log screen
	
	checkFileForUserInput();//call to method which checks if any request is pending to be processed
}
/*
 * Method checks a text file which stores student details which are not processed 
 * or the advisor response which has not been notified .
 * If it finds pending request, loads the request from file
 * Or if server crashes before processing of request then , it retrieves data from file
 * and starts from where it left off.
 */
public void checkFileForUserInput(){
	BufferedReader br = null;//declaration and intialisation of BufferedReader object to read file which has pending requests
	FileReader fr = null;//declaration and intialisation of FileReader object to read file which has pending requests
	try //To handle IOExceptions
	{
		fr = new FileReader("D:/thetextfile.txt");//object associated to file
		br = new BufferedReader(fr);//passing FileReader object to BufferedReader object to wrap it with BufferedReader

		String sCurrentLine;//declaring local variable to store contents read from file

		while ((sCurrentLine = br.readLine()) != null)//while we reach end of file 
		{
		//	System.out.println(sCurrentLine);
			queueA.add(sCurrentLine);//adds contents fo file to queue
		}

	} catch (IOException e) //catch IOException
	{

		e.printStackTrace();//debug

	} finally 
	{

		try // Handles IOException 
		{

			if (br != null)
				br.close();//close bufferedReader object

			if (fr != null)
				fr.close();//close file object

		}	
		
	catch(Exception e) //Catch IOException
	{
		e.printStackTrace();//debug
	}
	}
}
/*
 * (non-Javadoc)
 * @see Interface#add(int, int)
 * for testing client process
 */
public int add(int a,int b)throws RemoteException

{

return (a+b);
}
/*
 * (non-Javadoc)
 * @see Interface#display(java.lang.String, java.lang.String)
 * Adds studnet details received form server to queue
 */
@Override
public Queue display(String a,String b) throws RemoteException {
	// TODO Auto-generated method stub
	
	//System.out.println(a);
	queueA.add(a);//adding student details to queue sent student process
	//System.out.println(a);
	queueA.add(b);//adding student details to queue sent student process
	serverSideLogs.append("Student Process Runing\n");//appending messages to server UI
	serverSideLogs.append("Student details:\n");//displaying student details on UI received from Student process
	serverSideLogs.append("Student Name:"+a+"\n");//displaying studnet name on UI
	serverSideLogs.append("Course Name:"+b+"\n");//displaying student course name on UI
	BufferedWriter bw = null;//BufferedWriter object creation and intialisation 
	FileWriter fw = null;//FileWriter object creation and intialisation

	try //To handle IOException 
	{

//		String content = "This is the content to write into file\n";

		fw = new FileWriter("D:/thetextfile.txt");// associating object to file
		bw = new BufferedWriter(fw);//warping file object by bufferedWriter object
		bw.write(a);//writing student details in file for backup
		bw.newLine();//writing data on different line
		bw.write(b);//writing student details in file for backup

		//System.out.println("Done");

	} catch (IOException e)//Catch IOException 
	{

		e.printStackTrace();//debug

	} finally {

		try //To handle IOException 
		{

			if (bw != null)
				bw.close();//close BufferedWriter Object

			if (fw != null)
				fw.close();//close FileWriter Object

		} catch (IOException ex)//Catch IOException  
		{

			ex.printStackTrace();//debug

		}
	}

	return queueA;
}

public static void main(String args[])
{
try //To handle RMIExceptions
{
Server1 s=new Server1();//creating server object
Naming.rebind("rmi://localhost:1099/aaa",s);//Call inspects the RMI Registry running in the localhost for a binding under the name "aaa".
System.out.println("hello");//check if server is running without any error

}

catch(Exception e) //To catch RMIExceptions
{
	System.out.println(e);//debug
}

}

/*
 * (non-Javadoc)
 * @see Interface#returnQueue()
 * Returns queue to Advisor process having studnet details stored in it received 
 * form student process
 */
@Override
public Queue returnQueue() throws RemoteException {
	// TODO Auto-generated method stub
	serverSideLogs.append("Advisor process running\n");//Indicates advisor process is running
	if(queueA.size()==0)//If no student details received from student process
	{
		serverSideLogs.append("No student details found\n");//displaying message on UI
		return null;
		
	}
	serverSideLogs.append("Message retrieved by Advisor process\n");//If student details are found and retrieved by advisor the display message on UI 

		return queueA;
}


@Override
public void responseFromAdvisor(String message) throws RemoteException {
	// TODO Auto-generated method stub
    queueA.clear();//removing student details after being retrieved by advisor
    //System.out.println(queueA);
	queueA.add(message);//adding advisor response to queue
	
	serverSideLogs.append("Advisor Response:"+message+"\n\n");//displaying on UI
	 try //To Handle File IOExceptione 
	 {
        // String str = "SomeMoreTextIsHere";
          newTextFile = new File("D:/thetextfile.txt");//associating file object to a existing file

         FileWriter fw = new FileWriter(newTextFile);//associating fileWriter object to write in file
         Iterator iterator = queueA.iterator();//creating iterator object to iterate over a queue
 		while(iterator.hasNext())//iterator iterates until it has next element in queue
 		{
 			
 		 String element= (String) iterator.next();//stores advisor response to file if server goes down before notifying response
 		     fw.write(element);//writing in file
// 		     fw.write(",");
// 		     fw.write("\n");
 		     fw.write(System.getProperty( "line.separator" ));//writing on new line
 		}
    
         fw.close();//closing file

     } catch (IOException iox) //Catch IOException 
     {
         //do stuff with exception
         iox.printStackTrace();//debug
     }

//System.out.println(queueA);	
}

/*
 * (non-Javadoc)
 * @see Interface#returnResponseNotification()
 * Method returns advisor response sent by advisor process after evaluating the 
 * student process to notification process which in turn notifies advisor response or decision
 */
@Override
public Queue returnResponseNotification() throws RemoteException {
	// TODO Auto-generated method stub\
	serverSideLogs.append("Notification process running ...\n");//Indicating notification process is running by displaying message in UI
	if(queueA.size()!=2 && queueA.size()==1) //if queue has student details then return queue
	{
	return queueA;
	}
	else //if queue has other data stored in it besides advisor response return null
		{
		return null;
	}
}

/*
 * (non-Javadoc)
 * @see Interface#msgRetrieved(int)
 * Method indicates that advisor response has been successfully retrieved by notification process
 * and it is ok to clear the message stored in file as backup 
 * and also to empty the queue as student request has been processed
 */
@Override
public void msgRetrieved(int msgRetrievalStatus) throws RemoteException {
	// TODO Auto-generated method stub
	 try //To handle File IOExceptions
	 {
	if(msgRetrievalStatus==1) //status received from notification process 1=yes retrieved successfully ,0=failure
	{
		serverSideLogs.append("Message retrieved by Notification process\n");//displaying logs in UI
		
		serverSideLogs.append("Message notified by Notification process\n");//displaying logs in UI
		
		 queueA.clear();//clearing queue after message retrieval
        	new FileWriter("D:/thetextfile.txt").close();//clearing data stored in file 
//            String str = "SomeMoreTextIsHere";
//            File newTextFile = new File("D:/thetextfile.txt");
//
//            FileWriter fw = new FileWriter(newTextFile);
//            Iterator iterator = queueA.iterator();
//    		while(iterator.hasNext()){
//    			
//    		 String element= (String) iterator.next();
//    		     fw.write(element);
////    		     fw.write(",");
////    		     fw.write("\n");
//    		     fw.write(System.getProperty( "line.separator" ));
        	
        }
          //  fw.close();

        } 
	catch (Exception iox) //Catch File IOExceptions
	{
            //do stuff with exception
            iox.printStackTrace();//debug
        }
	
}
/*
 * (non-Javadoc)
 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
 */
@Override
public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
	
}



}
