package attendance.bean;
//有个属性课程名，用于视图查询，实际插入AC表不插
public class AC {
//平时考勤请记录
public int Sno;//学号
public int Cno;//课程号
public String APname;//班级
public String Cname;//课程号
public String Aname;//名字
public String Acdate;//考勤日期
//数据库中date类型对应java中的java.sql.data
public String Asituation;//出勤状况
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
public String getCname() {
	return Cname;
}
public void setCname(String cname) {
	this.Cname = cname;
}
public String getAname() {
	return Aname;
}
public void setAname(String aname) {
	this.Aname = aname;
}
public String getAcdate() {
	return Acdate;
}
public void setAcdate(String acdate) {
	this.Acdate = acdate;
}
public String getAsituation() {
	return Asituation;
}
public void setAsituation(String asituation) {
	this.Asituation = asituation;
}
public AC() {
	super();
	// TODO Auto-generated constructor stub
}

public AC(int sno, int cno, String aPname, String cname, String aname, String acdate, String asituation) {
	super();
	this.Sno = sno;
	this.Cno = cno;
	this.APname = aPname;
	this.Cname = cname;
	this.Aname = aname;
	this.Acdate = acdate;
	this.Asituation = asituation;
}
@Override
public String toString() {
	return "AC [Sno=" + Sno + ", Cno=" + Cno + ", APname=" + APname + ", Cname=" + Cname + ", Aname=" + Aname
			+ ", Acdate=" + Acdate + ", Asituation=" + Asituation + "]";
}



}
