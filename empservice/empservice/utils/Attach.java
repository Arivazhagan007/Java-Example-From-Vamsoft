package in.vamsoft.empservice.utils;

public class Attach {
	private int attachId;
	private String fileName;
	private String path;
	private String role;
	public Attach(int attachId2, String fileName2, String path2, String role2) {
		super();
		this.attachId=attachId2;
		this.fileName=fileName2;
		this.path=path2;
		this.role=role2;
	}

	public Attach() {

	}

	public int getAttachId() {
		return attachId;
	}

	public void setAttachId(int attachId) {
		this.attachId = attachId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Attach [attachId=" + attachId + ", fileName=" + fileName
				+ ", path=" + path + ", role=" + role + "]";
	}

}
