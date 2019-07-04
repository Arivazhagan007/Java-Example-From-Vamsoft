package com.training.iface;

import com.training.model.Customer;
import com.training.model.Employee;
import com.training.model.Tour;

public interface Dao {
	
		public int addCustomer(Customer customer);
		public boolean customerLoginValidate(Customer customer);
		public int addEmployee(Employee employee);
		public boolean employeeLoginValidate(Employee employee);
		public int addTour(Tour tour);
}
