import java.rmi.Naming;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
/*Author :Bhagyashree Shinde
 * Student ID:1001552353
 * class implementing Notification process 
 * i.e process which notifies  advisor decision to student request
 * retrieves advisor response from MQS using remote method invocation
 */
public class Notification {

	String message;//local variable to store advisor response retrieved from MQS
	/**
	 * @param args
	 */
	/*Method to notify advisor response to student request
	 * after retrieving from MQS
	 */
	public void notifyStudent(){
		try //to handle RMIExceptions
		{
			Interface i=(Interface)Naming.lookup("rmi://localhost:1099/aaa");//Call inspects the RMI Registry running in the localhost for a binding under the name "aaa".
			Queue queueA = i.returnResponseNotification();//storing in local queue advisor response retrieved from MQS by queue
			if(queueA!=null)// if advisor has processed student request
			{
				//System.out.println();
			
				Iterator iterator = queueA.iterator();//creating iterator object to iterate over a queue
				//String message;
				while(iterator.hasNext())//iterator iterates until it has next element in queue
				{
					
					message= (String)iterator.next();//storing advisor response in local varaiable
				
				}
//				String[] parts=element.split("-",-3);
//				//System.out.println(parts[0]);
//				name=parts[0];
//				System.out.println(name);
//				//System.out.println(parts[1]);
//				courseName=parts[1];
//				System.out.println(courseName);
//				message=parts[2];
//				if(message==null || message==""){
//					JFrame parent = new JFrame();
//
//				    JOptionPane.showMessageDialog(parent,"No message found");
////				    
//					Thread.sleep(7000);
//					notifyStudent();
//				}
//				else{
				//System.out.println(message);
				JFrame parent = new JFrame();///ceating JFrame object for creating UI for notifying advisor response

			    JOptionPane.showMessageDialog(parent,message);//displaying advisor response in message dialog UI

//			    JOptionPane.showInternalMessageDialog(parent, "Printing complete");

				i.msgRetrieved(1);//letting server know that the advisor response has been notified and processed completely
				
				//}
			}
			else
			{
				JFrame parent = new JFrame();//ceating JFrame object for creating UI for displaying advisor response

			    JOptionPane.showMessageDialog(parent,"No message found");// If no response found notify thst no message found from advisor
//			    
				Thread.sleep(7000);//pausing for 7 secs
				notifyStudent();// again checking for advisor response
			}
		}
		catch(Exception e)// catch RMIExceptions
		{
			e.printStackTrace();//debugging
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Notification notification= new Notification();//creaing an object of class
		notification.notifyStudent();//call to method which notifies advisor response
		
	}

}
