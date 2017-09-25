package beans;

import java.io.Serializable;
import java.sql.Date;

public class WorkSituationEditBeans implements Serializable {

	private int id;
	private String loginId;
	private Date editDate;
	private String editContent;

	public WorkSituationEditBeans() {
	}

	public WorkSituationEditBeans(int id, String loginId, Date editDate, String editContent) {
		this.id = id;
		this.loginId = loginId;
		this.editDate = editDate;
		this.editContent = editContent;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	public String getEditContent() {
		return editContent;
	}

	public void setEditContent(String editContent) {
		this.editContent = editContent;
	}

}
