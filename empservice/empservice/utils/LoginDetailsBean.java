package in.vamsoft.empservice.utils;

import java.util.Date;

public class LoginDetailsBean {
    
    private int empId;
    private String empName;
    private Date loginTime;
    private Date logoutTime;
    private String ipAddress;
    private String userAgent;
    public LoginDetailsBean(int empId, String empName, Date loginTime,
            Date logoutTime, String ipAddress, String userAgent) {
        super();
        this.empId = empId;
        this.empName = empName;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
    }
    public LoginDetailsBean() {
        
    }
    public int getEmpId() {
        return empId;
    }
    public void setEmpId(int empId) {
        this.empId = empId;
    }
    public String getEmpName() {
        return empName;
    }
    public void setEmpName(String empName) {
        this.empName = empName;
    }
    public Date getLoginTime() {
        return loginTime;
    }
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
    public Date getLogoutTime() {
        return logoutTime;
    }
    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }
    public String getIpAddress() {
        return ipAddress;
    }
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    public String getUserAgent() {
        return userAgent;
    }
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    
    @Override
    public String toString() {
        return "LoginDetailsBean [empId=" + empId + ", empName=" + empName
                + ", loginTime=" + loginTime + ", logoutTime=" + logoutTime
                + ", ipAddress=" + ipAddress + ", userAgent=" + userAgent + "]";
    }
    
    
}
