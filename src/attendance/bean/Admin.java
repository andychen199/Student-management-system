package attendance.bean;

public class Admin {
public int Ano;//管理号
public String Aname;//姓名
public String Asex;//性别
public String Azhichen;//职称
public String Aphone;//电话
public int Aage;//年龄
public String Acname;//任职系名
public String Apassword;//登录密码
public int getAno() {
	return Ano;
}
public void setAno(int ano) {
	this.Ano = ano;
}
public String getAname() {
	return Aname;
}
public void setAname(String aname) {
	this.Aname = aname;
}
public String getAsex() {
	return Asex;
}
public void setAsex(String asex) {
	this.Asex = asex;
}
public String getAzhichen() {
	return Azhichen;
}
public void setAzhichen(String azhichen) {
	this.Azhichen = azhichen;
}
public String getAphone() {
	return Aphone;
}
public void setAphone(String aphone) {
	this.Aphone = aphone;
}
public int getAage() {
	return Aage;
}
public void setAage(int aage) {
	this.Aage = aage;
}
public String getAcname() {
	return Acname;
}
public void setAcname(String acname) {
	this.Acname = acname;
}
public String getApassword() {
	return Apassword;
}
public void setApassword(String apassword) {
	this.Apassword = apassword;
}
public Admin() {
	super();
	// TODO Auto-generated constructor stub
}
public Admin(int ano, String aname, String asex, String azhichen, String aphone, int aage, String acname,String apassword){	
	this.Ano = ano;
	this.Aname = aname;
	this.Asex = asex;
	this.Azhichen = azhichen;
	this.Aphone = aphone;
	this.Aage = aage;
	this.Acname = acname;
	this.Apassword = apassword;
}
@Override
public String toString() {
	return "Admin [Ano=" + Ano + ", Aname=" + Aname + ", Asex=" + Asex + ", Azhichen=" + Azhichen + ", Aphone=" + Aphone
			+ ", Aage=" + Aage + ", Acname=" + Acname + ", Apassword=" + Apassword + "]";
}

}
