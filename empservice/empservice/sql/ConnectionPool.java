package in.vamsoft.empservice.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import in.vamsoft.empservice.utils.CommandUtil;

public class ConnectionPool {

	private static String hostName = Messages.getString(CommandUtil.HOSTNAME); //$NON-NLS-1$
	private static String portNumber = Messages.getString(CommandUtil.PORTNUMBER); //$NON-NLS-1$
	private static String userName = Messages.getString(CommandUtil.SQL_USERNAME); //$NON-NLS-1$
	private static String password = Messages.getString(CommandUtil.PASSWORD); //$NON-NLS-1$
	private static String database = Messages.getString(CommandUtil.DATABASE); //$NON-NLS-1$
	private static String driverName = Messages.getString(CommandUtil.DRIVERNAME); //$NON-NLS-1$
	private static String URLinput = Messages.getString(CommandUtil.URLINPUT); //$NON-NLS-1$
	private static String URL = null;
	private static Connection connection= null;
	int initialConnection=10;
	Map<Connection, String> connectionPool=null;

	public static Logger logger; 
	static{
		logger = Logger.getLogger(CommandUtil.MYLOGGER);	
	}

	public void initializePool() {


		URL = String.format(URLinput,hostName, portNumber, database);

		try {
			//load the driver name
			Class.forName(driverName);
			connection=DriverManager.getConnection(URL,userName,password);

		}
		catch (Exception e) {
			logger.debug(e); 
		}					
		connectionPool=new HashMap<Connection, String>();
		for(int pool=0;pool<initialConnection;pool++){
			connectionPool.put(connection, CommandUtil.AVAILABLE);
		}
	}


	public Connection getConnection() {

		boolean isConnection=true;
		for (Map.Entry<Connection, String> entry : connectionPool.entrySet()) {
			synchronized (entry) {
				if(entry.getValue().equals(CommandUtil.AVAILABLE)){
					entry.setValue(CommandUtil.NOTAVAILABLE);
					return entry.getKey();
				}
				isConnection=false;
			}
		}
		if(!isConnection){
			try {
				Class.forName(driverName);
				connection=DriverManager.getConnection(URL,userName,password);
			} catch (Exception e) {
				logger.debug(e);
			}
			connectionPool.put(connection, CommandUtil.NOTAVAILABLE);
			return connection;
		}
		return null;
	}

	public void returnPoolConnection(Connection connection) {

		for (Entry<Connection, String> entry : connectionPool.entrySet()) {
			synchronized (entry) {
				if(entry.getKey().equals(connection)){
					entry.setValue(CommandUtil.AVAILABLE);
				}
			}
		}

	}

	public void run() {

	}

}
