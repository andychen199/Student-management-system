package attendance.test;
import java.util.ArrayList;
import java.util.List;
import attendance.dao.TeacherDao;
import attendance.bean.Teacher;
public class TeacherTestDao {
	public static void main(String[] args){
		TeacherDao dao= new TeacherDao();
	      List<Teacher> is =new ArrayList<Teacher>();
	      is =dao.list2();
	      for (Teacher b : is) {
	        	System.out.printf("%s\t%d\t%s\t%s\t%s\t%s\t%s\n",b.Tcname,b.Tno,b.Tname,b.Tsex,b.Tage,b.Tbirthtime,b.Tphone);	           
	        	System.out.println();
	        }	
	}
}
