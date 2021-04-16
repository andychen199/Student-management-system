package attendance.bean;

import java.util.Date;

public class Xueqi {
	public Date startdate;
	public Date enddate;
	public int Xueqi;
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public int getXueqi() {
		return Xueqi;
	}
	public void setXueqi(int xueqi) {
		Xueqi = xueqi;
	}
	public Xueqi() {
		super();
	}
	public Xueqi(Date startdate, Date enddate, int Xueqi) {
		this.startdate = startdate;
		this.enddate = enddate;
		this.Xueqi = Xueqi;
	}
}
