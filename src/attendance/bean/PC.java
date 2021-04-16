package attendance.bean;

public class PC {
public int Pno;//班级号
public String Pname;//班级名
public int Pnum;//班级总人数
public String Pcname;//系名
public int getPno() {
	return Pno;
}
public void setPno(int pno) {
	this.Pno = pno;
}
public String getPname() {
	return Pname;
}
public void setPname(String pname) {
	this.Pname = pname;
}
public int getPnum() {
	return Pnum;
}
public void setPnum(int pnum) {
	this.Pnum = pnum;
}
public String getPcname() {
	return Pcname;
}
public void setPcname(String pcname) {
	this.Pcname = pcname;
}
public PC() {
	super();
	// TODO Auto-generated constructor stub
}
public PC(int pno, String pname, int pnum, String pcname) {
	super();
	this.Pno = pno;
	this.Pname = pname;
	this.Pnum = pnum;
	this.Pcname = pcname;
}
@Override
public String toString() {
	return "PC [Pno=" + Pno + ", Pname=" + Pname + ", Pnum=" + Pnum + ", Pcname=" + Pcname + "]";
}

}
