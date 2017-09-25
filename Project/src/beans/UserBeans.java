package beans;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class UserBeans implements Serializable {
	private int id;
	private String loginId;
	private String name;
	private Date birthDate;
	private String position;
	private String password;
	private Timestamp createDate;
	private Timestamp updateDate;

	public UserBeans() {

	}

	public UserBeans(int id, String loginId, String name, String position, Date birthDate, String password, Timestamp createDate, Timestamp updateDate) {
		this.id = id;
		this.loginId = loginId;
		this.name = name;
		this.position = position;
		this.birthDate = birthDate;
		this.password = password;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public String getFormatBirthDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(birthDate);
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}


	public Timestamp getCreateDate() {
		return createDate;
	}

	public String getFormatCreateDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日　HH時mm分ss秒");
		return sdf.format(createDate);
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public String getFormatUpdateDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日　HH時mm分ss秒");
		return sdf.format(updateDate);
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}
