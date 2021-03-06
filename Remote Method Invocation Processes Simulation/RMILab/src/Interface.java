import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Queue;


public interface Interface extends Remote{
	public int add(int a,int b)throws RemoteException;

	public Queue display(String name,String courseName)throws RemoteException;
	public Queue returnQueue()throws RemoteException;
	public void responseFromAdvisor(String msg)throws RemoteException;
	public Queue returnResponseNotification()throws RemoteException;

	public void msgRetrieved(int msgRetrievalStatus) throws RemoteException;
	}


