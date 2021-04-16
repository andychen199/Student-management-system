package attendance.test;
import java.util.ArrayList;
import java.util.List;
import attendance.dao.PCDao;
import attendance.bean.PC;
public class PCTestDao {
	public static void main(String[] args){
		PCDao dao= new PCDao();
	      List<PC> is =new ArrayList<PC>();
	     // dao.add(1,"计算机81",35,"计算机系");//增加测试
	      //int c=dao.getTotal();
	      //System.out.println(c);//getTotal函数测试
	      is =dao.list2();
	      System.out.printf("%s\t%s\t%s\t%s\n","", "班级号","班级名","总人数","系名");
	        for (PC b : is) {
	        	System.out.printf("%d\t%s\t%d\t%s\n",b.Pno,b.Pname,b.Pnum,b.Pcname);	           
	        	System.out.println();
	        }	
	       // dao.delete(3);//删除测试
	      //  dao.update(2, "电信181", 36,"电信系");
	}
}
