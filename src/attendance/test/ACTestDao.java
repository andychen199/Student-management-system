package attendance.test;
import java.util.ArrayList;
import java.util.List;
import attendance.dao.ACDao;
import attendance.bean.AC;
public class ACTestDao {
	public static void main(String[] args){
		ACDao dao= new ACDao();
	      List<AC> is =new ArrayList<AC>();
	    // dao.add("计算机181", 1, "2020-02-02");
	     // dao.update1(2, 1, "2020-02-02", "旷课");
	      //is =dao.list4("2020-02-02",1);
//	      is=dao.list2(1, 1);
//	      for(AC b:is){
//	    	  System.out.printf("%d\t%d\t%s\t%s\t%s\t%s\t%s\n",b.Sno,b.Cno,b.Cname,b.APname,b.Aname,b.Acdate,b.Asituation);	           
//	        	System.out.println();
//	    	  }
//	      }
	      //dao.add("计算机181", 1, "2020-04-05");
	      dao.add("计算机181", 1, "2020-04-09");
}
}
