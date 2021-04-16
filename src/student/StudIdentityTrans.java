package student;

public class StudIdentityTrans {
	public static int Cno;
	public static String Cname;
	public static int Status;//判断右键菜单是否展开
	public static int getStatus() {
		return Status;
	}
	public static void setStatus(int status) {
		StudIdentityTrans.Status = status;
	}
	public static int getCno() {
		return Cno;
	}
	public static void setCno(int Cno) {
		StudIdentityTrans.Cno = Cno;
	}
	public static String getCname() {
		return Cname;
	}
	public static void setCname(String Cname) {
		StudIdentityTrans.Cname = Cname;
	}
}
