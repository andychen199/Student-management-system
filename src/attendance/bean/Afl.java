package attendance.bean;

public class Afl {
//请假申请记录（申请成功的保存没成自动从数据库删除）
public int Sno;//学号
public int Cno;//课程号
public String APname;//班级名
public String Aname;//姓名
public String Adate;//申请日期
public String ABegindate;//开始请假日期
public String Aenddate;//请假结束日期
public String Areason;//请假原因
public String Aresults;//请假结果，默认待审核
//数据库中Datetime类型对应java中的java util.data
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
public String getAPname() {
	return APname;
}
public void setAPname(String aPname) {
	this.APname = aPname;
}
public String getAname() {
	return Aname;
}
public void setAname(String aname) {
	this.Aname = aname;
}
public String getAdate() {
	return Adate;
}
public void setAdate(String adate) {
	this.Adate = adate;
}
public String getABegindat() {
	return ABegindate;
}
public void setABegindat(String aBegindat) {
	this.ABegindate = aBegindat;
}
public String getAenddate() {
	return Aenddate;
}
public void setAenddate(String aenddate) {
	this.Aenddate = aenddate;
}
public String getAreason() {
	return Areason;
}
public void setAreason(String areason) {
	this.Areason = areason;
}
public String getAresults() {
	return Aresults;
}
public void setAresults(String aresults) {
	this.Aresults = aresults;
}
public Afl() {
	super();
	// TODO Auto-generated constructor stub
}
public Afl(int sno, int cno, String aPname, String aname, String adate, String aBegindate, String aenddate, String areason,
		String aresults) {
	super();
	this.Sno = sno;
	this.Cno = cno;
	this.APname = aPname;
	this.Aname = aname;
	this.Adate = adate;
	this.ABegindate = aBegindate;
	this.Aenddate = aenddate;
	this.Areason = areason;
	this.Aresults = aresults;
}
@Override
public String toString() {
	return "Afl [Sno=" + Sno + ", Cno=" + Cno + ", APname=" + APname + ", Aname=" + Aname + ", Adate=" + Adate
			+ ", ABegindate=" + ABegindate + ", Aenddate=" + Aenddate + ", Areason=" + Areason + ", Aresults=" + Aresults
			+ "]";
}

}
