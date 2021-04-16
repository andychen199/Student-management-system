package attendance.dao;
//课程DAO，给管理员用的
import java.sql.*;
import javax.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import attendance.bean.Course;
public class CourseDao {
	public CourseDao(){
		 try {
	            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	          // System.out.println("加载驱动成功！");
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	            System.out.println("加载驱动失败！");
	        }
	}
	public Connection getConnection()throws SQLException{
      //抛出异常
		return DriverManager.getConnection("jdbc:sqlserver://sql.cerny.cn:1433;DatabaseName=attendance", "sa", "Myclassdesign330");
		//数据库的名字叫attendance，我的数据库账户是sa,密码是sa
		//这里我建立连接的时候不是用try-catcth来捕捉异常，而是主动抛出，那么我下面调用这个函数时要么继续抛出，要么用try-catcth捕捉，我选择用try-catcth捕捉，到时候主函数就不用处理了
      }
	public int getTotal() {
		//得到目前表Course总数有多少条
        int total = 0;
        try (Connection c = getConnection(); Statement s = c.createStatement();) {          
            String sql = "select count(*) from Course"; 
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);//	这里getInt是因为rs这个结果集这里是返回的结果行数
            }
            // System.out.println("total:" + total); 
        } catch (SQLException e) {  
            e.printStackTrace();
        }
        return total;
    }
	public boolean add(int Cno,String Cname) {
		//这里增加函数课程号（本来主码可以自增不插，但我这里还是打开自增规则插进去），
		//课程号，课程名
        String sql = "set identity_insert Course ON insert into Course(Cno,Cname) values(?,?) set identity_insert Course OFF";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) { 
        	ps.setInt(1,Cno);
        	ps.setString(2,Cname);         
            ps.execute();  
            return true;
            //ResultSet rs = ps.getGeneratedKeys();//如果因为主码自增不插，然后通过这里得到主码赋值给对象对应的属性
        } catch (SQLException e) {  
            e.printStackTrace();
        }
		return false;
    }
	public boolean update(int Cno,String Cname) {
		//课程号，课程名
		//不更新主码，主码一般不允许更新，一般允许更新主码的数据库设计都是不合理的		
         String sql = "update Course set Cname=? where Cno ="+Cno; 
	    	 try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql);){	    		
	    		 ps.setString(1, Cname); 	    		 
	    		 ps.execute();
	    		 return true;
	    	//	 System.out.println("修改成功");	    		 
	    	 }catch (SQLException e1) {
		            e1.printStackTrace();		     
		      }catch (Exception e2) {// 捕获其他异常
		    	  e2.printStackTrace();// 打印异常产生的过程信息
		      }
	    	 return false;
	    	}
	public boolean delete(int Cno) {
		 //删除函数
		 try (Connection c = getConnection(); Statement s = c.createStatement();) {				  
	            String sql = "delete from Course where Cno ="+Cno;
	            s.execute(sql);	
	            return true;
	            //System.out.println("删除成功");
	      }catch(SQLException e1) {
	            e1.printStackTrace();
	      }catch(Exception e2) {// 捕获其他异常
	    	  e2.printStackTrace();// 打印异常产生的过程信息
	      }
		return false;
	    }	
	 public List<Course> list1(int Cno) {
	        List<Course> courses = new ArrayList<Course>();
	  //把符合条件的Course数据查询出来，转换为Course对象后，放在一个集合中返回
	   //String sql = "select * from hero order by id desc limit ?,? ";		       	         
	        String Cname=null;//课程名
	        //按即主码查询，单个
	        //System.out.println("请输入要查询课程号:");	        	
	        String sql = "select * from Course where Cno ="+Cno;
		    try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql);
		            while (rs.next()) {
		            	/*定义一个类型为Course的对象course ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
		                 * 下面的一样道理*/  
		            	Course course=new Course() ;
		            	Cno= rs.getInt(1);
		            	Cname = rs.getString("Cname");	        
		            	course.Cno=Cno;
		            	course.Cname=Cname;
		            	courses.add(course);
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();}
		     return courses;//返回系集合，符合条件的系的信息
	    }
	 public List<Course> list2() {
	        List<Course> courses = new ArrayList<Course>();
	  //把Course数据查询出来，转换为Course对象后，放在一个集合中返回
	   //String sql = "select * from hero order by id desc limit ?,? ";	
	        int Cno=0;//课程号
	        String Cname=null;//课程名 
	      //都查询出来
	        //System.out.println("请输入要查询课程号:");	        	
	        String sql = "select * from Course ";
		    try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql);
		            while (rs.next()) {
		            	/*定义一个类型为Course的对象course ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
		                 * */  
		            	Course course=new Course() ;
		            	Cno= rs.getInt(1);
		            	Cname = rs.getString("Cname");	        
		            	course.Cno=Cno;
		            	course.Cname=Cname;
		            	courses.add(course);
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();}
		     return courses;//返回系集合，符合条件的系的信息
	    }
	 public List<Course> list3(String Cname) {
	        List<Course> courses = new ArrayList<Course>();
	  //把符合条件的Course数据查询出来，转换为Course对象后，放在一个集合中返回
	   //String sql = "select * from hero order by id desc limit ?,? ";		       	         
	        //String Cname=null;//课程名
	        int Cno = 0;
	        //按即主码查询，单个
	        //System.out.println("请输入要查询课程号:");	        	
	        String sql = "select * from Course where Cname =" + "'" + Cname + "'";
		    try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql);
		            while (rs.next()) {
		            	/*定义一个类型为Course的对象course ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
		                 * 下面的一样道理*/  
		            	Course course=new Course() ;
		            	Cno= rs.getInt(1);
		            	Cname = rs.getString("Cname");	        
		            	course.Cno=Cno;
		            	course.Cname=Cname;
		            	courses.add(course);
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();}
		     return courses;//返回系集合，符合条件的系的信息
	    }
}
