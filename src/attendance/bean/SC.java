package attendance.bean;
//选课表
//课程名，姓名是为了查询，实际SC表没有
public class SC {
	public int Sno ;//学号
	public int Cno ;//课程号
	public String Sname;//姓名
	public String Cname;//课程名
	public int getSno() {
		return Sno;
	}
	public void setSno(int sno) {
		this.Sno = sno;
	}
	public int getCno() {
		return Cno;
	}
	public void setCno(int cno) {
		this.Cno = cno;
	}
	public String getSname() {
		return Sname;
	}
	public void setSname(String sname) {
		this.Sname = sname;
	}
	public String getCname() {
		return Cname;
	}
	public void setCname(String cname) {
		this.Cname = cname;
	}
	public SC() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SC(int sno, int cno, String sname, String cname) {
		super();
		this.Sno = sno;
		this.Cno = cno;
		this.Sname = sname;
		this.Cname = cname;
	}
	@Override
	public String toString() {
		return "SC [Sno=" + Sno + ", Cno=" + Cno + ", Sname=" + Sname + ", Cname=" + Cname + "]";
	}
	
	
	
}
