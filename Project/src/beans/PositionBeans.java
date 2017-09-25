package beans;

import java.io.Serializable;

public class PositionBeans implements Serializable {

	private int id;
	private String position;

	public PositionBeans() {
	}

	public PositionBeans(int id, String position) {
		this.id = id;
		this.position = position;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}


}

