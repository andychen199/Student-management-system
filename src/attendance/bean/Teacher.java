package attendance.bean;

public class Teacher {	
public int Tno;//职工号
public String Tname;//姓名
public String  Tsex;//性别
public int Tage;//教龄
public String Tphone ;//电话
public String Tcname;//系名
public String Tbirthtime;//出生日期
public String Tpassword;//密码
public int getTno() {
	return Tno;
}
public void setTno(int tno) {
	this.Tno = tno;
}
public String getTname() {
	return Tname;
}
public void setTname(String tname) {
	this.Tname = tname;
}
public String getTsex() {
	return Tsex;
}
public void setTsex(String tsex) {
	this.Tsex = tsex;
}
public int getTage() {
	return Tage;
}
public void setTage(short tage) {
	this.Tage = tage;
}
public String getTphone() {
	return Tphone;
}
public void setTphone(String tphone) {
	this.Tphone = tphone;
}
public String getTcname() {
	return Tcname;
}
public void setTcname(String tcname) {
	this.Tcname = tcname;
}
public String getTbirthtime() {
	return Tbirthtime;
}
public void setTbirthtime(String tbirthtime) {
	Tbirthtime = tbirthtime;
}
public String getTpassword() {
	return Tpassword;
}
public void setTpassword(String tpassword) {
	this.Tpassword = tpassword;
}
public Teacher() {
	super();
	// TODO Auto-generated constructor stub
}
public Teacher(int tno, String tname, String tsex, int tage, String tphone, String tcname,String Tpassword, String tpassword) {
	super();
	this.Tno = tno;
	this.Tname = tname;
	this.Tsex = tsex;
	this.Tage = tage;
	this.Tphone = tphone;
	this.Tcname = tcname;
	this.Tpassword=Tpassword;
	this.Tpassword = tpassword;
}
@Override
public String toString() {
	return "Teacher [Tno=" + Tno + ", Tname=" + Tname + ", Tsex=" + Tsex + ", Tage=" + Tage + ", Tphone=" + Tphone
			+ ", Tcname=" + Tcname + ", Tbirthtime=" + Tbirthtime + ", Tpassword=" + Tpassword + "]";
}




}
