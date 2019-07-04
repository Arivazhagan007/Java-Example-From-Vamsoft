package in.vamsoft.empservice.command;

import in.vamsoft.empservice.sql.ConnectionPool;
import in.vamsoft.empservice.sql.SQLHolder;
import in.vamsoft.empservice.utils.CommandUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class LoginDetails implements Command{

    public static Logger logger;
    static{
        logger = Logger.getLogger(CommandUtil.MYLOGGER);    
    }

    @Override
    public String execute(HttpServletRequest request,
            HttpServletResponse response) throws SQLException {

        logger.debug(CommandUtil.LOGIN_DETAIL_START);

        ServletContext context=request.getServletContext();
        HttpSession session=request.getSession();

        int empId=Integer.parseInt(request.getParameter
                (CommandUtil.HEMPID));
        String role = request.getParameter(CommandUtil.HROLE);
        String empName = (String) session.getAttribute(
                CommandUtil.SEMP_NAME);

        SimpleDateFormat dateFormat = new SimpleDateFormat
                (CommandUtil.LOGIN_DETAILS_DATE_FORMAT);
        Date date = new Date();

        String filePath = CommandUtil.REPORT_FILE_PATH+empName
                +dateFormat.format(date)+CommandUtil.PDF;
        
        Document document = new Document();


        File file = new File(filePath);
        FileOutputStream fileOutputStream;

        try {

            fileOutputStream = new FileOutputStream(file);
            PdfWriter.getInstance(document, fileOutputStream);

        } catch (DocumentException | FileNotFoundException e1) {

            e1.printStackTrace();
        }  

        document.open();
        PdfPTable pdfPTable = new PdfPTable(6);

        PdfPTable pTable = addContent(pdfPTable,empId,context);

        try {
            document.add(pTable);

            if(document!=null){
                if(role.equals(CommandUtil.RADMIN)){                
                    request.setAttribute(CommandUtil.LD_SUCCESS, 
                            CommandUtil.LD_SUCCESS_MSG+filePath);
                    document.close();
                    return CommandUtil.ADMINSUCCESS;

                }else {
                    request.setAttribute(CommandUtil.LD_SUCCESS, 
                            CommandUtil.LD_SUCCESS_MSG+filePath);
                    document.close();
                    return CommandUtil.USERSUCCESS;
                }

            }else {

                if(role.equals(CommandUtil.RADMIN)){                
                    request.setAttribute(CommandUtil.LD_FAILURE, 
                            CommandUtil.LD_FAILURE_MSG);
                    document.close();
                    return CommandUtil.ADMINFAILURE;

                }else {
                    request.setAttribute(CommandUtil.LD_SUCCESS, 
                            CommandUtil.LD_SUCCESS_MSG);
                    document.close();
                    return CommandUtil.USERFAILURE;
                }

            }
        } catch (DocumentException e1) {

            e1.printStackTrace();
        }

        document.close();
        return CommandUtil.FAILURE;
    }

    private PdfPTable addContent(PdfPTable pdfPTable, int empId, 
            ServletContext context) {

        PdfPCell table_cell=null;


        ConnectionPool connectionPool=(ConnectionPool)
                context.getAttribute(CommandUtil.CONNECTIONPOOL);
        Connection connection=(Connection)connectionPool.getConnection();
        ResultSet resultSet=null;
        PreparedStatement statement=null;

        int beanEmpId=0;
        String empName,ipAddress,userAgent=null;
        Date loginTime=null;
        Date logoutTime=null;

        try {
            statement = connection.prepareStatement
                    (SQLHolder.VIEWLOGINBYIDQRY);
            statement.setInt(1, empId);
            resultSet= statement.executeQuery();

            while (resultSet.next()) {   

                beanEmpId = resultSet.getInt(CommandUtil.EMPID); 
                table_cell=new PdfPCell(new Phrase(
                        String.valueOf(beanEmpId)));
                pdfPTable.addCell(table_cell);

                empName = resultSet.getString(CommandUtil.EMPNAME);  
                table_cell=new PdfPCell(new Phrase(empName));  
                pdfPTable.addCell(table_cell);

                ipAddress=resultSet.getString(CommandUtil.IPADDRESS);  
                table_cell=new PdfPCell(new Phrase(ipAddress)); 
                pdfPTable.addCell(table_cell);

                loginTime=resultSet.getTimestamp(CommandUtil.LOGINTIME);
                table_cell=new PdfPCell(new Phrase(
                        String.valueOf(loginTime)));
                pdfPTable.addCell(table_cell);

                logoutTime=resultSet.getTimestamp(CommandUtil.LOGOUTTIME);
                table_cell=new PdfPCell(new Phrase(
                        String.valueOf(logoutTime)));
                pdfPTable.addCell(table_cell);

                userAgent=resultSet.getString(CommandUtil.USER_AGENT);  
                table_cell=new PdfPCell(new Phrase(userAgent));  
                pdfPTable.addCell(table_cell);

            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return pdfPTable;
    }



}



