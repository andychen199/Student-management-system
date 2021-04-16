package attendance.bean;

public class TeachforUI {
	public int Cno;
	public String Cname;
	public int  Tschool;
	public int Tsgrade;
	public int getCno() {
		return Cno;
	}
	public void setCno(int cno) {
		Cno = cno;
	}
	public String getCname() {
		return Cname;
	}
	public void setCname(String cname) {
		Cname = cname;
	}
	public int getTschool() {
		return Tschool;
	}
	public void setTschool(int tschool) {
		Tschool = tschool;
	}
	public int getTsgrade() {
		return Tsgrade;
	}
	public void setTsgrade(int tsgrade) {
		Tsgrade = tsgrade;
	}
	public TeachforUI() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TeachforUI(int cno, String cname, int tschool, int tsgrade) {
		super();
		this.Cno = cno;
		this.Cname = cname;
		this.Tschool = tschool;
		this.Tsgrade = tsgrade;
	}
}
