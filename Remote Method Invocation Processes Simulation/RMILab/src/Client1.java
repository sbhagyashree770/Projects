import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sun.awt.AWTAccessor.WindowAccessor;
/*Author :Bhagyashree Shinde
 * Student ID:1001552353
 * class implementing student process i.e to enter studnet details
 * student name and course name has to be entered
 */
public class Client1 extends JFrame implements ActionListener,WindowListener
//Inherits JFrame for GUI to access JFrame Objects and implements ActionListener and WindowsListener to listen to events on gui and respond

{

	private static final long serialVersionUID = -5624404136485946868L;//The serialization runtime associates with each serializable class a version number, called a serialVersionUID, which is used during deserialization to verify that the sender and receiver of a serialized object have loaded classes for that object that are compatible with respect to serialization.

	String userWord ="";//Global declaration and initialization of variable which stores student name
	String courseSelected="";//Global declaration and initialization of variable which stores course name
	JTextField userInput,courseName;//Declaring a text field object where user can enter details or inout text

	/*Default constructor which creates GUI
	 * for entering student details 
	 */
	public Client1() 
	{
	JFrame jf = new JFrame();//Creating JFrame object for GUI
	JPanel panel = new JPanel();//Creating panel object for adding GUI components to UI
	JLabel jl = new JLabel("Name");//Creating label for textfield which allows you to enter name and label indicates where to enter which details
	jl.setBounds(10, 10, 80, 25);//location and width,height of input area is fixed (x-co-ordinate,y-co-ordinate,width,height)
	JButton jButton = new JButton("Submit");//Creating button for submitting student details
	jButton.setBounds(10, 80, 80, 25);////location and width,height of input area is fixed (x-co-ordinate,y-co-ordinate,width,height)
	userInput = new JTextField("", 20);//Creating textfield of size 20 for enetring student name
	userInput.setBounds(100, 10, 160, 25);//location and width,height of input area is fixed (x-co-ordinate,y-co-ordinate,width,height)
	JLabel j2 = new JLabel("Course");//Creating label for textfield which allows you to enter subject and label indicates where to enter which details
	j2.setBounds(10, 40, 80, 25);//location and width,height of input area is fixed (x-co-ordinate,y-co-ordinate,width,height)
	courseName = new JTextField(20);//Creating textfield of size 20 for enetring course name
	courseName.setBounds(100, 40, 160, 25);//location and width,height of input area is fixed (x-co-ordinate,y-co-ordinate,width,height)
	jButton.addActionListener(this);//Adding event for button click
	jf.setSize(300, 300);//setting size of frame to height=300,width=300
	jf.setVisible(true);//setting visibility true allows user to view UI
	jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);////commands window or UI  to do nothing on closing window
	jf.add(panel);// adding panel to frame
	panel.add(jl);//adding label of student name as "Name" to frame
	panel.add(userInput);//adding textfield for input student name to frame 
	panel.add(j2);//adding label of course name as "Course" to frame
	panel.add(courseName);//adding textfield for input course name to frame
	panel.add(jButton);//adding button to UI for submitting student details
	
	}

	/*Method to take user Input from GUI and store it in 
	 * local variables for processing and transfering it to server(MQS)
	 */
	public void submitAction() {
	userWord = userInput.getText();//reads text field and gets studnet name and stores it in local variable
	courseSelected=courseName.getText();//reads text field and gets course name and stores it in local variable
	//System.out.println(userWord);
	//System.out.println(courseSelected);
	studentDetails(userWord,courseSelected);//call to student details method with student details sent as parameters
	//do something with the variabe userWord here (print it to the console, etc.)
	}
	
	public void studentDetails(String name,String Cname){
		//Scanner sc=new Scanner(System.in);
		try// to handle RMIExceptions
		{
			//System.out.println("in student details");
		Interface i=(Interface)Naming.lookup("rmi://localhost:1099/aaa");//Call inspects the RMI Registry running in the localhost for a binding under the name "aaa".
		//System.out.println("enter numbers");
		//int a=sc.nextInt();
		//int b=sc.nextInt();
		//System.out.println("add is"+i.add(a,b));
		//System.out.println(Cname);

		System.out.println("queue added"+i.display(name,Cname));//checking if studnet details has beensent to MQS and added to queue

		}
		catch(Exception e)//To Catch RMI exceptions
		{}

	}
public static void main(String args[])
{
	Client1 client=new Client1();//Creating class object which runs constructor creating GUI

	//client.studentDetails();
}

@Override
public void actionPerformed(ActionEvent ae) //method for listening button click event and handling it
{
	// TODO Auto-generated method stub
	
//	 System.out.println("in action performed");
	
	 submitAction();//as soon as button is clicked student detals are retrieved and submitted to server using this method
	    
	 
	   
	 
	     }
/*
 * (non-Javadoc)
 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
 *methods of windows listener interface that needs to be 
 * overwritten in order to receive window events and process them
 */
@Override
public void windowActivated(WindowEvent arg0) {
	// TODO Auto-generated method stub
	
}
/*
 * (non-Javadoc)
 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
 *methods of windows listener interface that needs to be 
 * overwritten in order to receive window events and process them
 */
@Override
public void windowClosed(WindowEvent arg0) {
	// TODO Auto-generated method stub
	
}
/*
 * (non-Javadoc)
 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
 *methods of windows listener interface that needs to be 
 * overwritten in order to receive window events and process them
 */
@Override
public void windowClosing(WindowEvent arg0) {
	// TODO Auto-generated method stub
	
}
/*
 * (non-Javadoc)
 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
 *methods of windows listener interface that needs to be 
 * overwritten in order to receive window events and process them
 */
@Override
public void windowDeactivated(WindowEvent arg0) {
	// TODO Auto-generated method stub
	
}
/*
 * (non-Javadoc)
 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
 *methods of windows listener interface that needs to be 
 * overwritten in order to receive window events and process them
 */
@Override
public void windowDeiconified(WindowEvent arg0) {
	// TODO Auto-generated method stub
	
}
/*
 * (non-Javadoc)
 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
 *methods of windows listener interface that needs to be 
 * overwritten in order to receive window events and process them
 */
@Override
public void windowIconified(WindowEvent arg0) {
	// TODO Auto-generated method stub
	
}
/*
 * (non-Javadoc)
 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
 *methods of windows listener interface that needs to be 
 * overwritten in order to receive window events and process them
 */
@Override
public void windowOpened(WindowEvent arg0) {
	// TODO Auto-generated method stub
	
}

}

