package beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class WorkSituationEditBeans implements Serializable {

	private int id;
	private String loginId;
	private Timestamp editDate;
	private String editContent;

	public WorkSituationEditBeans() {
	}

	public WorkSituationEditBeans(int id, String loginId, Timestamp editDate, String editContent) {
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

	public Timestamp getEditDate() {
		return editDate;
	}

	public String getFormatEditDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分ss秒");
		return sdf.format(this.editDate);
	}

	public void setEditDate(Timestamp editDate) {
		this.editDate = editDate;
	}

	public String getEditContent() {
		return editContent;
	}

	public void setEditContent(String editContent) {
		this.editContent = editContent;
	}

}
