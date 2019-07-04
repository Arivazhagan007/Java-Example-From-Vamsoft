package in.vamsoft.empservice.sql;

public interface SQLHolder {

	String LOGINQUERY="select * from empdetails where emailid=? and password=? and status='active'";
	String VIEWBYIDQUERY="select * from empdetails where empid=?";
	String VIEWBYNAMEQUERY="select * from empdetails where empname=?";
	String CHANGEPWDQUERY="update empdetails set password=? where empid=?";
	String UPDATEEMPQUERY="update empdetails set phno=?,designation=?,role=?,dol=? where empid=?";
	String DISABLEEMPQUERY="update empdetails set status='inactive' where empid=?";
	String ENABLEEMPQUERY="update empdetails set status='active' where empid=?";
	String VIEWALLEMPQUERY="select * from empdetails";
	String INSERTEMPLOYEEQUERY="insert into empdetails(empname,emailid,password,phno,designation,role,doj,dol,status) values(?,?,?,?,?,?,?,?,?)";
	String RETRIVELASTINSERTEMPQUERY="select * from empdetails where empid=(select max(empid) as empid from empdetails)";
	String ROLEBYNAMEQUERY="select * from role where rolename=?";
	String INSERTUSER_ROLEQUERY="insert into user_role(empid,roleid)values(?,?)";
	String NEWROLEQUERY="insert into role(rolename,descr)values(?,?)";
	String VIEWALLROLEQUERY="select * from role";
	String ATTACHQUERY="insert into attachment(filename,path,role) values(?,?,?)";
	String RETRIVELASTINSERTATACHQUERY="select * from attachment where attachid=(select max(attachid) as attachid from attachment)";
	String VIEWROLEBYNAME="select * from role where rolename=?";
	String NEWATTACHROLEQUERY="insert into attach_role(attachid,roleid)values(?,?)";
	String VIEWALLATTACHQUERY="select * from attachment";
	String VIEWATTACHBYIDQUERY="select * from attachment where attachid=?";
	String VIEWATTACHBYROLEQUERY="select * from attachment where role=?";
    String DISABLEEMPBYQUERY = "update empdetails set status='inactive' where emailid=?";
    String INSERTLOGINQUERY = "insert into loginhistory (empid,empname,logintime,ipaddress,useragent)values(?,?,?,?,?)";
    String UPDATELOGOUTTIME = "update loginhistory set logouttime=? where empid=? and empname=? and logintime=? and ipaddress=? and useragent=?";
    String VIEWLOGINBYIDQRY = "select * from loginhistory where empid=? and logintime > current_date - 5";
    String INSERTPASSWORDQUERY = "insert into passwordhistory(empid,password) values(?,?)";
    String CHECKPWDHISTORYQRY = "select * from passwordhistory where empid=? order by empid desc limit 10";
}
