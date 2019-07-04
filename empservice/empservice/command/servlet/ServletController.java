package in.vamsoft.empservice.command.servlet;

import in.vamsoft.empservice.command.Command;
import in.vamsoft.empservice.sql.ConnectionPool;
import in.vamsoft.empservice.utils.CommandUtil;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
@MultipartConfig  
public class ServletController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Properties properties=null;

	public ServletController() {
		super();
	}

	public static Logger logger; 
	static{

		logger = Logger.getLogger(CommandUtil.MYLOGGER);	
	}	

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		properties= new Properties();
		ServletContext context = getServletContext();

		ConnectionPool connectionPool=new ConnectionPool();
		context.setAttribute(CommandUtil.CONNECTIONPOOL, connectionPool);
		connectionPool.initializePool();

		logger.debug(context);
		String fileSource = context.getInitParameter(CommandUtil.CONTROLLERSRC);

		String fileDestination=context.getInitParameter(CommandUtil.CONTROLLERDES);

		InputStream source = context.getResourceAsStream(fileSource);
		InputStream destination=context.getResourceAsStream(fileDestination);
		try {
			properties.load(source);
			properties.load(destination);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				source.close();
				destination.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		perform(request,response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		perform(request, response);
	}

	protected void perform(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		response.setContentType(CommandUtil.TEXTHTML);  

		String pathURL=request.getRequestURI();
		logger.debug(pathURL);

		String value=pathURL.substring(request.getContextPath().length());
		logger.debug(value);

		String[] arr = pathURL.split(CommandUtil.SLASH);
		String key=arr[arr.length-1];
		String value1=properties.getProperty(key);
		logger.debug(value1);

		try {
			Class class1=Class.forName(value1);
			Command command=(Command) class1.newInstance();
			String key1;
			key1 = command.execute(request, response);
			logger.debug(key1);
			String value2=properties.getProperty(key+key1);
			logger.debug(value2);
			request.getRequestDispatcher(value2).forward(request, response);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (InstantiationException e) {
			e.printStackTrace();

		} catch (IllegalAccessException e) {
			e.printStackTrace();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}


