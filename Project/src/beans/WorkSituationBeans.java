package beans;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;


public class WorkSituationBeans implements Serializable {
    private int id;
    private String loginId;
    private Date createDate;
    private String workSitu;
    private Time workStart;
    private Time workEnd;
    private Time breakTime;
    private Time workTime;
    private Time overtime;


    public WorkSituationBeans() {
    }

    public WorkSituationBeans(int id, String loginId, Date createDate, String workSitu, Time workStart, Time workEnd,  Time breakTime, Time workTime, Time overtime) {
        this.id = id;
        this.loginId = loginId;
        this.createDate = createDate;
        this.workSitu = workSitu;
        this.workStart = workStart;
        this.workEnd = workEnd;
        this.breakTime = breakTime;
        this.workTime = workTime;
        this.overtime = overtime;
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

	public Date getCreateDate() {
		return createDate;
	}

	public String getFormatCreateDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		return sdf.format(this.createDate);
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getWorkSitu() {
		return workSitu;
	}

	public void setWorkSitu(String workSitu) {
		this.workSitu = workSitu;
	}

	public Time getWorkStart() {
		return workStart;
	}

	public void setWorkStart(Time workStart) {
		this.workStart = workStart;
	}

	public Time getWorkEnd() {
		return workEnd;
	}

	public void setWorkEnd(Time workEnd) {
		this.workEnd = workEnd;
	}

	public Time getBreakTime() {
		return breakTime;
	}

	public void setBreakTime(Time breakTime) {
		this.breakTime = breakTime;
	}

	public Time getWorkTime() {
		return workTime;
	}

	public void setWorkTime(Time workTime) {
		this.workTime = workTime;
	}

	public Time getOvertime() {
		return overtime;
	}

	public void setOvertime(Time overtime) {
		this.overtime = overtime;
	}


}


