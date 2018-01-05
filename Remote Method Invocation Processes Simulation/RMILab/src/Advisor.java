import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
/*Author :Bhagyashree Shinde
 * Student ID:1001552353
 * class implementing advisor process i.e to process student request by 
 *approving or disapproving student request.
 *Retrieves student details from MQS using remote method invocation
 */
public class Advisor {
	String name,courseName;//declaring local varaibles for storing student details after retieving from server
	
	/**
	 * @param args
	 */
	/*
	 * Method depending of random probability to  approve or disapproves the 
	 * student request and sends it's response to MQS
	 * processes student request retrieved from MQS and sends response to MQS
	 */
	public void updateQueue(){
		String element = "";//Creating and intialising local variable to store studnet details

		try // to handle RMIExceptions
		{
		Interface i=(Interface)Naming.lookup("rmi://localhost:1099/aaa");//Call inspects the RMI Registry running in the localhost for a binding under the name "aaa".
		//System.out.println("enter numbers");
//		int a=sc.nextInt();
	//	int b=sc.nextInt();
		//System.out.println("add is"+i.add(a,b));
		Queue queueA = i.returnQueue();//storing in local queue student details retrieved from MQS by queue
		
		if(queueA==null)//if queue object retrieved from MQS is null,it indicates , student details are not found yet or entered
		{
//			System.out.println("NO message Found");
			JFrame parent = new JFrame();//ceating JFrame object for creating UI for displaying message

		    JOptionPane.showMessageDialog(parent,"NO message Found");//displaying message in message dialog UI

			Thread.sleep(3000);//pause till 3secs
			updateQueue();//again look for student details
		}
		else//if details of student are found
		{
			
		//System.out.println("queue added"+i.returnQueue());
		Iterator iterator = queueA.iterator();//creating iterator object to iterate over a queue
		while(iterator.hasNext())//iterator iterates until it has next element in queue
		{
			
		  element+= (String) iterator.next()+"-";//stores all student details in string
		
		}
		//System.out.println(element);
		String[] parts=element.split("-",-2);//splitting string to separate student details which were concatenated into one string and storing it in an array
		//System.out.println(parts[0]);
		name=parts[0];//first part of string is name
		//System.out.println(name);
		//System.out.println(parts[1]);
		courseName=parts[1];//second is course name
		//System.out.println(courseName);
		
		Random rand = new Random(); // used to generate random value/probability
		int value = rand.nextInt(50); //generates randomly numbers between 1-50
		String messageN="Subject "+courseName+" has not been approved for stduent "+name;//storing advisor response of not approving

		String messageY="Subject "+courseName+" has been approved for stduent "+name;//storing advisor response of approving

		if(value%2==0)//if randomly generated integer divide by 2 gives remainder 0 
		{
					 i.responseFromAdvisor(messageY);//then pass approval message to MQS
		 JFrame parent = new JFrame();//ceating JFrame object for creating UI for displaying message


		    JOptionPane.showMessageDialog(parent,messageY);//displaying message in message dialog UI

//		    JOptionPane.showInternalMessageDialog(parent, "Printing complete");

		}
		else // not approved
		{
			 i.responseFromAdvisor(messageN);//then pass disapproval message to MQS

			 JFrame parent = new JFrame();//ceating JFrame object for creating UI for displaying message

			    JOptionPane.showMessageDialog(parent,messageN);//displaying message in message dialog UI

//			    JOptionPane.showInternalMessageDialog(parent, "Printing complete");

		}
		}
		
		
		}
		catch(Exception e)//to catch RMIExceptions
		{
			e.printStackTrace();// to debug error
		}
		//return null;
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Advisor advisor= new Advisor();//creating object of class
        advisor.updateQueue();//call to method which processes student request retrieved from MQS and sends response to MQS
	}

}
