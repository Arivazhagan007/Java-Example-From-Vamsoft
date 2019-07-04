package in.vamsoft.empservice.command;

import in.vamsoft.empservice.sql.ConnectionPool;
import in.vamsoft.empservice.sql.SQLHolder;
import in.vamsoft.empservice.utils.Attach;
import in.vamsoft.empservice.utils.CommandUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class DownloadFile implements Command{

	public static Logger logger;
	static{
		logger = Logger.getLogger(CommandUtil.MYLOGGER);	
	}


	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
		HttpSession session=request.getSession();
		Date date=new Date();
		SimpleDateFormat ft=new SimpleDateFormat(CommandUtil.DATEFORMAT);
		String s=ft.format(date);
		session.setAttribute(CommandUtil.DATE, s);

		int attachid = Integer.parseInt(
				request.getParameter(CommandUtil.HATTACHID));
		String role=request.getParameter(CommandUtil.HROLE);
		Attach atch=viewAttachById(attachid,request);
		String fName=null;
		String fPath=null;
		if(atch!=null){
			fName=atch.getFileName();
			fPath=atch.getPath();
		}
		PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		} 

		String fileName = fName;   
		String filePath = fPath;
		response.setContentType(CommandUtil.CONTYPE);   
		response.setHeader(CommandUtil.CONDES,CommandUtil.ATTACHFILENAME + fileName + CommandUtil.DSLASH);   
		FileInputStream fileInputStream=null;
		try {
			fileInputStream =
					new FileInputStream(filePath + fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		int inputFile;   
		try {
			while ((inputFile=fileInputStream.read()) != -1) {  
				out.write(inputFile); 

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if((inputFile=fileInputStream.read())== -1){
				if(role.equals(CommandUtil.RADMIN)){
					request.setAttribute(CommandUtil.D_ERROR, CommandUtil.FDOWN_ERROR);
					return CommandUtil.ADMINFAILURE;
				}
				else {
					request.setAttribute(CommandUtil.D_ERROR, CommandUtil.FDOWN_ERROR);
					return CommandUtil.USERFAILURE;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return CommandUtil.FAILURE;
	}


	private Attach viewAttachById(int attachid, HttpServletRequest request) {
		Attach atch=null;
		ResultSet rs=null;
		PreparedStatement statement=null;

		ServletContext context=request.getServletContext();
		ConnectionPool connectionPool=(ConnectionPool)
				context.getAttribute(CommandUtil.CONNECTIONPOOL);
		Connection connection=(Connection)connectionPool.getConnection();

		try {
			statement=connection.prepareStatement(SQLHolder.VIEWATTACHBYIDQUERY);
			statement.setInt(1, attachid);
			rs=statement.executeQuery();
			while(rs.next())
			{
				atch=new Attach();
				atch.setAttachId(rs.getInt(CommandUtil.ATTACHID));
				atch.setFileName(rs.getString(CommandUtil.FILENAME));
				atch.setPath(rs.getString(CommandUtil.PATH));
				atch.setRole(rs.getString(CommandUtil.ROLE));
				connectionPool.returnPoolConnection(connection);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				statement.close();
				rs.close();

			} catch (SQLException e) {
				logger.debug(e);
			}
		}
		return atch;
	}
}
