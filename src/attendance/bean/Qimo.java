package attendance.bean;

import java.sql.Date;

//期末考勤总记录,给老师用的
//这里的属性系名，班级，姓名，课程名并不是真正的期末考勤表里的属性，而是从期末视图查询出来，插入的是并没有插入期末表
public class Qimo {
public String Pcname;//系名	
public String Pname;//班级
public int Sno;//学号
public String Sname;//姓名
public int Cno;//课程号
public String Cname;//课程名
public Date Qstartdate;//开始学年
public Date Qenddate;//结束学年
public int Qan;//出勤次数
public int Qaln;//请假次数
public int Qtn;//旷课次数
public int Qlen;//早退次数
public int Qlate;//迟到次数
public int getQlate() {
	return Qlate;
}
public void setQlate(int qlate) {
	this.Qlate = qlate;
}

public String getPcname() {
	return Pcname;
}
public void setPcname(String pcname) {
	this.Pcname = pcname;
}
public String getPname() {
	return Pname;
}
public void setPname(String pname) {
	this.Pname = pname;
}
public int getSno() {
	return Sno;
}
public void setSno(int sno) {
	this.Sno = sno;
}
public String getSname() {
	return Sname;
}
public void setSname(String sname) {
	this.Sname = sname;
}
public int getCno() {
	return Cno;
}
public void setCno(int cno) {
	this.Cno = cno;
}
public String getCname() {
	return Cname;
}
public void setCname(String cname) {
	this.Cname = cname;
}

public java.sql.Date getQstartdate() {
	return Qstartdate;
}
public void setQstartdate(Date qstartdate) {
	this.Qstartdate = qstartdate;
}
public Date getQenddate() {
	return Qenddate;
}
public void setQenddate(Date qenddate) {
	this.Qenddate = qenddate;
}
public int getQan() {
	return Qan;
}
public void setQan(int qan) {
	this.Qan = qan;
}
public int getQaln() {
	return Qaln;
}
public void setQaln(int qaln) {
	this.Qaln = qaln;
}
public int getQtn() {
	return Qtn;
}
public void setQtn(int qtn) {
	this.Qtn = qtn;
}
public int getQlen() {
	return Qlen;
}
public void setQlen(int qlen) {
	this.Qlen = qlen;
}
public Qimo() {
	super();
	// TODO Auto-generated constructor stub
}
public Qimo(String pcname, String pname, int sno, String sname, int cno, String cname, Date qstartdate, Date qenddate,
		int qan, int qaln, int qtn, int qlen, int qlate) {
	super();
	this.Pcname = pcname;
	this.Pname = pname;
	this.Sno = sno;
	this.Sname = sname;
	this.Cno = cno;
	this.Cname = cname;
	this.Qstartdate = qstartdate;
	this.Qenddate = qenddate;
	this.Qan = qan;
	this.Qaln = qaln;
	this.Qtn = qtn;
	this.Qlen = qlen;
	this.Qlate = qlate;
	
}
@Override
public String toString() {
	return "Qimo [Pcname=" + Pcname + ", Pname=" + Pname + ", Sno=" + Sno + ", Sname=" + Sname + ", Cno=" + Cno
			+ ", Cname=" + Cname + ", Qstartdate=" + Qstartdate + ", Qenddate=" + Qenddate + ", Qan=" + Qan + ", Qaln="
			+ Qaln + ", Qtn=" + Qtn + ", Qlen=" + Qlen + ", Qlate=" + Qlate + "]";
}





}
