package beans;

import java.io.Serializable;

public class SalaryBeans implements Serializable {


	private int id;
	private int hourlyWage;
	private int overtimeHourlyWage;

	public SalaryBeans() {
	}

	public SalaryBeans(int id, int hourlyWage, int overtimeHourlyWage) {
		this.id = id;
		this.hourlyWage = hourlyWage;
		this.overtimeHourlyWage = overtimeHourlyWage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHourlyWage() {
		return hourlyWage;
	}

	public void setHourlyWage(int hourlyWage) {
		this.hourlyWage = hourlyWage;
	}

	public int getOvertimeHourlyWage() {
		return overtimeHourlyWage;
	}

	public void setOvertimeHourlyWage(int overtimeHourlyWage) {
		this.overtimeHourlyWage = overtimeHourlyWage;
	}


}
