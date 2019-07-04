package in.vamsoft.emscrm.sql;


public interface SQLHolder {


	String SEARCHQRY = "select customer_id,customer_name,emailid,phone_no,address," +
			"dtstamp from customer_master where customer_name=? or emailid = ?" +
			" or phone_no=?";

	String showall = "select a.issue_id,a.customer_id," +
			"a.issue_subject, a.issue_description,a.dtstamp," +
			"b.customer_id,b.ticket_id,b.event_date,b.event_time,b.location," +
			"b.number_of_ticket," +
			"b.category,b.price from issue_details a ," +
			"ticket_details b where a.customer_id =? and b.customer_id=?";


	String GETTICKETQRY = "select ticket_id,customer_id,event_name,event_date,event_time,location," +
			"number_of_ticket,category,price,total_price,dtstamp from ticket_details where " +
			"customer_id = ? and event_date>CURDATE();";

	String GETISSUEQRY = "select issue_id,customer_id,issue_subject,issue_description," +
			"dtstamp,issue_status from issue_details where customer_id = ? and" +
			" issue_status='pending'";

	String DISABLEEMPBYQUERY = "update empdetails set status='inactive' where emailid=?";

	String INSERTLOGINQUERY = "insert into loginhistory (empid,empname,logintime," +
			"ipaddress,useragent)values(?,?,?,?,?)";

	String LOGINQUERY="select * from empdetails where emailid=? and password=? and" +
			" status='active'";

	String UPDATELOGOUTTIME = "update loginhistory set logouttime=? where empid=? and" +
			" empname=? and logintime=? and ipaddress=? and useragent=?";

	String GETISSUEHISTORYQRY = "select issue_id,customer_id,issue_subject," +
			"issue_description," +
			"dtstamp,issue_status from issue_details where customer_id = ?";

	String GETSOLVEDISSUEQRY = "select issue_id,customer_id,issue_subject," +
			"issue_description," +
			"dtstamp,issue_status from issue_details where customer_id = ? and issue_status='solved'";

	String GETPASTTICKETQRY = "select ticket_id,customer_id,event_name,event_date,event_time,location," +
			"number_of_ticket,category,price,total_price,dtstamp from ticket_details " +
			"where customer_id = ? and event_date<CURDATE()";

	String GETALLTICKETQRY = "select ticket_id,customer_id,event_name,event_date,event_time,location," +
			"number_of_ticket,category,price,total_price,dtstamp from ticket_details " +
			"where customer_id = ?";

	String INSERTEMPLOYEEQUERY = "insert into empdetails(empname,emailid,password,phno,designation,role,doj,status)" +
			" values(?,?,?,?,?,?,?,?)";

	String INSERTCUSTOMERQUERY = "insert into customer_master(customer_name,email_id,phone_no,address) values(?,?,?,?)";

	String RETRIVELASTINSERTCUSTQUERY = "select * from customer_master where customer_id=(select max(customer_id)" +
			" as customer_id from customer_master)";

	String INSERTTICKETQUERY = "insert into ticket_details(customer_id,event_name,event_date,event_time,location," +
			"number_of_ticket,category,price,total_price) values(?,?,?,?,?,?,?,?,?)";

	String GETEVENTDETAILS = "select event_id,event_name,event_date,event_time,location,category,price,organised_by " +
			"from events " +
			"where event_date>CURDATE()";

	String GETONEEVENTDETAIL = "select event_id,event_name,event_date,event_time,location,category,price,organised_by " +
			"from events " +
			"where event_id=?";

	String CUSTOMERLOGINQUERY = "select * from customer_master where emailid=? and password=? and" +
			" status='active'";

	String INSERTCUSTOMERLOGINQUERY = "insert into customerloginhistory (customer_id,customer_name,logintime," +
			"ipaddress,useragent)values(?,?,?,?,?)";

	String DISABLECUSTBYQUERY = "update customer_master set status='inactive' where emailid=?";

	String UPDATECUSTYQUERY = "update customer_master set first_name=?,last_name=?,alt_phone_no=?,alt_email_id=?,pincode=?,state=?,country=?,address=? where emailid=?";

	String VIEWBYIDQUERY = "select * from customer_master where customer_id=?";
	

	String CHANGECUSTPWDQUERY = "update customer_master set password=? where customer_id=?";

	String CHANGECUSTPWDBYEMAILQUERY =" update customer_master set password=? where emailid=?";

	String VIEWBYEMAILQUERY = "select * from customer_master where emailid=?";

	String INSERTCUSTOMERSIGNUPQUERY = "insert into customer_master(customer_name,emailid,alt_email_id,password,phone_no,"
			+ "alt_phone_no,first_name,last_name,security_question,security_answer,dob,pincode,state,country,address)" +
			" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	String VIEWBYEMPIDQUERY = "select * from empdetails where empid=?";

	String UPDATEEMPQUERY = "update empdetails set emailid=?,phno=?,designation=?,role=? where empname=?";

	String CHANGEEMPPWDQUERY = "update empdetails set password=? where empid=?";

	String INSERTEMPTICKETQUERY = "insert into emp_ticket_details(empid,event_name,event_date,event_time,location," +
			"number_of_ticket,category,price,total_price) values(?,?,?,?,?,?,?,?,?)";

	String GETEVENTDETAILSBYDATE = "select * from events where event_date=?";

	String ADDISSUEQUERY = "insert into issue_details(customer_id,issue_subject,issue_description, issue_Status) values(?,?,?,?)";
}

