package com.training.factory;

import java.io.*;
import java.util.*;

import com.training.iface.Command;

public class Factoroy {

	
	public static Command getAccess(String a){
		
		Properties properties = new Properties();
		Command command = null;
		try {
			properties.load(new FileReader("C:/Training/TravelOperatorApplication/src/Command.properties"));
			
			String className= properties.getProperty(a);
			System.out.println(className);
			
			Class class1 = Class.forName(className);
			if(class1!=null){
				command = (Command) class1.newInstance();
			}
			
			System.out.println(command);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return command; 
	}
	
	
}
