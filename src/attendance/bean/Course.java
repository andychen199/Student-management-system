package attendance.bean;

public class Course {
public int Cno;//学号
public String Cname;//课程号
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
public Course() {
	super();
	// TODO Auto-generated constructor stub
}
public Course(int cno, String cname) {
	super();
	this.Cno = cno;
	this.Cname = cname;
}
@Override
public String toString() {
	return "Course [Cno=" + Cno + ", Cname=" + Cname + "]";
}

}
