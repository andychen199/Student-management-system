package attendance.test;
import java.util.ArrayList;
import java.util.List;
import attendance.dao.StudentDao;
import attendance.bean.Student;
public class StudentTestDao {
	public static void main(String[] args){
		StudentDao dao= new StudentDao();
//	      List<Student> is =new ArrayList<Student>();
//	      is =dao.list2("计算机181");
//	      for (Student b : is) {
//	        	System.out.printf("%s\t%s\t%s\t%d\t%s\t%d\t%s\t%s\n",b.Pcname,b.Pname,b.Sname,b.Sno,b.Sex,b.Sage,b.Sbirthtime,b.Snative);	           
//	    	  //System.out.printf("%d\t%s\t%s\t%d\t%s\t%s\n",b.Sno,b.Sname,b.Sex,b.Sage,b.Snative,b.Spassword);
//	    	  System.out.println();
//	    	  
//	    	  dao.update2(1, "123980");
//	        }	
	      dao.update1(1, "陈三", "男", 19, "广东广州", 1);
	      
	}
}
