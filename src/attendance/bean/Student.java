package attendance.bean;
public class Student {
//这里属性有系名，专业班级名，还有出生日期，但是在增删改并没有实际跟学生表挂钩，这里只是为了查询时从视图查询
public String Pcname;//系名
public String Pname;//专业班级名
public int Sno;//学号
public String Sname;//姓名
public String Sex;//性别
public int Sage;//年龄
public String Sbirthtime;//出生日期
public String Snative;//籍贯
public int SPno ;//专业班级号
public String Spassword;//密码
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
public String getSex() {
	return Sex;
}
public void setSex(String sex) {
	this.Sex = sex;
}
public int getSage() {
	return Sage;
}
public void setSage(int sage) {
	this.Sage = sage;
}
public String getSbirthtime() {
	return Sbirthtime;
}
public void setSbirthtime(String sbirthtime) {
	this.Sbirthtime = sbirthtime;
}
public String getSnative() {
	return Snative;
}
public void setSnative(String snative) {
	this.Snative = snative;
}
public int getSPno() {
	return SPno;
}
public void setSPno(int sPno) {
	this.SPno = sPno;
}
public String getSpassword() {
	return Spassword;
}
public void setSpassword(String spassword) {
	this.Spassword = spassword;
}
public Student() {
	super();
	// TODO Auto-generated constructor stub
}
public Student(String pcname, String pname, int sno, String sname, String sex, int sage, String sbirthtime,	String snative, int sPno, String spassword) {
	super();
	this.Pcname = pcname;
	this.Pname = pname;
	this.Sno = sno;
	this.Sname = sname;
	this.Sex = sex;
	this.Sage = sage;
	this.Sbirthtime = sbirthtime;
	this.Snative = snative;
	this.SPno = sPno;
	this.Spassword = spassword;
}
@Override
public String toString() {
	return "Student [Pcname=" + Pcname + ", Pname=" + Pname + ", Sno=" + Sno + ", Sname=" + Sname + ", Sex=" + Sex
			+ ", Sage=" + Sage + ", Sbirthtime=" + Sbirthtime + ", Snative=" + Snative + ", SPno=" + SPno
			+ ", Spassword=" + Spassword + "]";
}


}
