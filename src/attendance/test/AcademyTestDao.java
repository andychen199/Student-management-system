package attendance.test;
import java.util.ArrayList;
import java.util.List;
import attendance.dao.AcademyDao;
import attendance.bean.Academy;
public class AcademyTestDao {
	public static void main(String[] args){
		 AcademyDao dao= new AcademyDao();
	      List<Academy> is =new ArrayList<Academy>();
	        //System.out.println("数据库中总共有" + is.size() + " 条数据");
	        //System.out.println("新加一条数据");
	     //   Academy a=new Academy("计算机系","刘双印","仲恺");
	       // dao.add("网络工程系","周军","北楼");//增加测试
	       // int c=dao.getTotal();
	       // System.out.println(c);//getTotal函数测试
	       is =dao.list1("计算机系");
	      // System.out.println("数据库中总共有" + is.size() + " 条数据");
	       System.out.printf("%s\t%s\t%s\n", "系名", "系主任","系地址");
	        for (Academy b : is) {
	        	System.out.printf("%s\t%s\t%s\n",b.Acname,b.Acchairman,b.Acaddress );	           
	        }	       	        
	      // dao.update(3);//更新测试
	      // dao.delete("网络工程系","周军","北楼");//删除测试
	        
	      
	 }
}
