package attendance.bean;
//任职记录表
public class Teach {
public int Tno;
public int Cno;
public int  Tschool;
public int Tsgrade;
public int getTno() {
	return Tno;
}
public void setTno(int tno) {
	this.Tno = tno;
}
public int getCno() {
	return Cno;
}
public void setCno(int cno) {
	this.Cno = cno;
}
public int getTschool() {
	return Tschool;
}
public void setTschool(short tschool) {
	this.Tschool = tschool;
}
public int getTsgrade() {
	return Tsgrade;
}
public void setTsgrade(short tsgrade) {
	this.Tsgrade = tsgrade;
}
public Teach() {
	super();
	// TODO Auto-generated constructor stub
}
public Teach(int tno, int cno, int tschool, int tsgrade) {
	super();
	this.Tno = tno;
	this.Cno = cno;
	this.Tschool = tschool;
	this.Tsgrade = tsgrade;
}
//@Override
//public String toString() {
//	return "Teach [Tno=" + Tno + ", Cno=" + Cno + ", Tschool=" + Tschool + ", Tsgrade=" + Tsgrade + "]";
//}

}
