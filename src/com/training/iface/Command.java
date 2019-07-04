package com.training.iface;

import javax.servlet.http.HttpServletRequest;

import com.training.daos.DaoImpl;

public interface Command {
	
	public String execute(HttpServletRequest request, DaoImpl dao) throws javax.servlet.ServletException, java.io.IOException; 

}
