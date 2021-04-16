package attendance.test;
import java.util.ArrayList;
import java.util.List;
import attendance.dao.QimoDao;
import attendance.bean.Qimo;
public class QimoTestDao {
	public static void main(String[] args){
	QimoDao dao= new QimoDao();
    List<Qimo> is =new ArrayList<Qimo>();
    //dao.add(1, 1, "2020");
  // dao.add("计算机181", 1, "2020-02-02");
   // dao.update1(2, 1, "2020-02-02", "旷课");
    //is =dao.list4("2020-02-02",1);
   /* for(Qimo b:is){
  	  System.out.printf("%d\t%d\t%s\t%s\t%s\t%s\t%s\n",b.Sno,b.Cno,b.Cname,b.APname,b.Aname,b.Acdate,b.Asituation);	           
      	System.out.println();
  	  }*/
    //dao.add(1, 2, "2020");
    //dao.add(Sno, Cno, Qstartdate, Qenddate);
    //is =dao.list1(1);
    for(Qimo b:is){
    	  //System.out.printf("%s\t%s\t%d\t%s\t%d\t%s\t%s\t%d\t%d\t%d\t%d\n",b.Pcname,b.Pname,b.Sno,b.Sname,b.Cno,b.Cname,b.Qtimeyear,b.Qan,b.Qaln,b.Qtn,b.Qlen);	           
        	System.out.println();
    	  }
    }
}
