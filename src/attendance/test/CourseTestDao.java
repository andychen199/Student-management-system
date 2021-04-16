package attendance.test;

import java.util.ArrayList;
import java.util.List;

import attendance.bean.Course;
import attendance.bean.Teach;
import attendance.bean.Teacher;
import attendance.bean.TeachforUI;
import attendance.dao.CourseDao;
import attendance.dao.TeachDao;
import attendance.dao.TeacherDao;

public class CourseTestDao {
	public static void main(String[] args){
//		CourseDao dao= new CourseDao();
//	    List<Course> is =new ArrayList<Course>();
//	    is =dao.list2();
//	    for (Course b : is) {
//	    	System.out.printf("%d\t%s\n",b.Cno,b.Cname);	           
//	    	System.out.println();
//	    }	
	    TeachDao dao= new TeachDao();
	    List<TeachforUI> is =new ArrayList<TeachforUI>();
	    is =dao.list3forUI(1);
	    for (TeachforUI b : is) {
	    	System.out.printf("%d\t%s\n",b.Cno,b.Cname);	           
	    	System.out.println();
	    }	
	}
}
