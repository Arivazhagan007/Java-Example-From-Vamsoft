package in.vamsoft.emscrm.utils;

import java.sql.Timestamp;

public class IssueDetails {
	
	private int issueId;
	private int customerId;
	private String issueSubject;
	private String issueDescription;
	private Timestamp dtStamp;
	private String status;
	
	
	public IssueDetails(int issueId, int customerId, String issueSubject,
			String issueDescription, Timestamp dtStamp, String status) {
		super();
		this.issueId = issueId;
		this.customerId = customerId;
		this.issueSubject = issueSubject;
		this.issueDescription = issueDescription;
		this.dtStamp = dtStamp;
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public IssueDetails() {
		
	}
	public int getIssueId() {
		return issueId;
	}
	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getIssueSubject() {
		return issueSubject;
	}
	public void setIssueSubject(String issueSubject) {
		this.issueSubject = issueSubject;
	}
	public String getIssueDescription() {
		return issueDescription;
	}
	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}
	public Timestamp getDtStamp() {
		return dtStamp;
	}
	public void setDtStamp(Timestamp dtStamp) {
		this.dtStamp = dtStamp;
	}
	@Override
	public String toString() {
		return "IssueDetails [issueId=" + issueId + ", customerId="
				+ customerId + ", issueSubject=" + issueSubject
				+ ", issueDescription=" + issueDescription + ", dtStamp="
				+ dtStamp + ", status=" + status + "]";
	}
	
	

}
