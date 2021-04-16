package attendance.bean;

public class Academy {
public String  Acname;//系名
public String  Acchairman;//系主任
public String  Acaddress;//系的地址
public String getAcname() {
	return Acname;
}
public void setAcname(String acname) {
	this.Acname = acname;
}
public String getAcchairman() {
	return Acchairman;
}
public void setAcchairman(String acchairman) {
	this.Acchairman = acchairman;
}
public String getAcaddress() {
	return Acaddress;
}
public void setAcaddress(String acaddress) {
	this.Acaddress = acaddress;
}
public Academy(){
	//super();写与不写都一样每个子类的构造函数都会默认调用父类的无参构造函数, super()写与不写一样
}
public Academy(String  Acname,String  Acchairman,String  Acaddress){
	this.Acname=Acname;
	this.Acchairman=Acchairman;
	this.Acaddress=Acaddress;
}
@Override
public String toString() {
	return "Academy [Acname=" + Acname + ", Acchairman=" + Acchairman + ", Acaddress=" + Acaddress
			+ ", getAcname()=" + getAcname() + ", getAcchairman()=" + getAcchairman() + ", getAcaddress()="
			+ getAcaddress() +  "]";
}

}
