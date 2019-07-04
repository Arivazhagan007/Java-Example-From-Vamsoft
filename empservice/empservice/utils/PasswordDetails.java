package in.vamsoft.empservice.utils;

public class PasswordDetails {

    
    private int empId;
    private String pwd;
    public PasswordDetails() {
        
       
    }
    
    public PasswordDetails(int empId, String pwd) {
        super();
        this.empId = empId;
        this.pwd = pwd;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "PasswordDetails [empId=" + empId + ", pwd=" + pwd + "]";
    }
    
    
}
