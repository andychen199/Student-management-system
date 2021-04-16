package attendance.test;
import java.util.ArrayList;
import java.util.List;
import attendance.dao.AdminDao;
import attendance.bean.Admin;
public class AdminTestDao {
	public static void main(String[] args){
		AdminDao dao= new AdminDao();
	      List<Admin> is =new ArrayList<Admin>();
	        //System.out.println("数据库中总共有" + is.size() + " 条数据");
	        //System.out.println("新加一条数据");
	      
	      // dao.add(1,"史婷婷","女","秘书长","13145201314", 19, "计算机系");//增加测试
	      // int c=dao.getTotal();
	      //System.out.println(c);//getTotal函数测试
	       is =dao.list2();
	      // System.out.println("数据库中总共有" + is.size() + " 条数据");
	       System.out.printf("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n","", "职工号","姓名","性别","职称","电话","工龄","系名","密码");
	        for (Admin b : is) {
	        	System.out.printf("\t%d\t%s\t%s\t%s\t%s\t%d\t%s\t%s\n",b.Ano,b.Aname,b.Asex,b.Azhichen,b.Aphone,b.Aage,b.Acname,b.Apassword);	           
	        	System.out.println();
	        }	       	        
	      //dao.update(1,"史婷婷","女","秘书长","13145201314", 19, "计算机系","13546");//更新测试
	     //dao.delete(3);//删除测试
	        
	      
	 }
}
