package in.vamsoft.emscrm.command;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface Command {
	String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException;

}
