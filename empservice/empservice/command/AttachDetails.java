package in.vamsoft.empservice.command;

import in.vamsoft.empservice.sql.ConnectionPool;
import in.vamsoft.empservice.sql.SQLHolder;
import in.vamsoft.empservice.utils.Attach;
import in.vamsoft.empservice.utils.CommandUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class AttachDetails implements Command {

    public static Logger logger;
    static {
        logger = Logger.getLogger(CommandUtil.MYLOGGER);
    }

    @Override
    public String execute(HttpServletRequest request,
            HttpServletResponse response) throws SQLException {
        HttpSession session = request.getSession();
        List<Attach> alst = attachDetails(request);
        session.setAttribute(CommandUtil.ATTACHDETAILS, alst);
        List<Attach> plist = alst.subList(0, 10);
        request.setAttribute(CommandUtil.SASUBLIST, plist);
        int size = alst.size();
        int pages = (int) Math.ceil(size / 10.0);
        session.setAttribute(CommandUtil.PAGES, pages);
        if (alst != null) {
            return CommandUtil.SUCCESS;
        } else {
            request.setAttribute(CommandUtil.AD_ERROR,
                    CommandUtil.ADETAIL_ERROR);
            return CommandUtil.FAILURE;
        }
    }

    private List<Attach> attachDetails(HttpServletRequest request) {
        List<Attach> alst = new ArrayList<Attach>();
        Attach atch = null;
        ResultSet rs = null;
        PreparedStatement statement = null;
        int attachId = 0;
        String fileName, path, role = null;
        ServletContext context = request.getServletContext();
        ConnectionPool connectionPool = (ConnectionPool) context
                .getAttribute(CommandUtil.CONNECTIONPOOL);
        Connection connection = (Connection) connectionPool.getConnection();
        try {
            statement = connection
                    .prepareStatement(SQLHolder.VIEWALLATTACHQUERY);
            rs = statement.executeQuery();
            while (rs.next()) {
                atch = new Attach();
                attachId = rs.getInt(CommandUtil.ATTACHID);
                fileName = rs.getString(CommandUtil.FILENAME);
                path = rs.getString(CommandUtil.PATH);
                role = rs.getString(CommandUtil.ROLE);

                atch = new Attach(attachId, fileName, path, role);
                alst.add(atch);
                connectionPool.returnPoolConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                rs.close();

            } catch (SQLException e) {
                logger.debug(e);
            }
        }

        return alst;
    }
}
