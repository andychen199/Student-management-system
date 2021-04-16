package attendance.dao;
//给管理员用的
import java.sql.*;
import javax.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import attendance.bean.Course;
import attendance.bean.SC;;
public class SCDao {
	public SCDao(){
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
		//得到目前表SC总数有多少条
       int total = 0;
       try (Connection c = getConnection(); Statement s = c.createStatement();) {          
           String sql = "select count(*) from SC"; 
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
	public boolean add(int Sno,int Cno) {
		//这里增加函数课程号（本来主码可以自增不插，但我这里还是打开自增规则插进去），
		//课程号，课程名set identity_insert SC ON 
       String sql = "insert into SC(Sno,Cno) values(?,?) set identity_insert Course OFF";
       try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) { 
       	ps.setInt(1,Sno);
       	ps.setInt(2,Cno);         
           ps.execute();  
           return true;
           //ResultSet rs = ps.getGeneratedKeys();//如果因为主码自增不插，然后通过这里得到主码赋值给对象对应的属性
       } catch (SQLException e) {  
           e.printStackTrace();
           return false;
       }
   }
	public void update(int Sno,int Cno,int Sno1,int Cno1) {
		//课程号，课程名
		//Sno,Cno是原来的；Sno1,Cno1重新要修改的
        String sql = "update Course set Sno=?,Cno=? where Sno ="+Sno+" and "+"Cno="+Cno; 
	    	 try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql);){	    		
	    		 ps.setInt(1, Sno1);
	    		 ps.setInt(1, Cno1);
	    		 ps.execute();
	    	//	 System.out.println("修改成功");	    		 
	    	 }catch (SQLException e1) {
		            e1.printStackTrace();		     
		      }catch (Exception e2) {// 捕获其他异常
		    	  e2.printStackTrace();// 打印异常产生的过程信息
		      }
	    	}
	public boolean delete(int Sno,int Cno) {
		 //删除函数,删掉某个同学的这个记录
		 try (Connection c = getConnection(); Statement s = c.createStatement();) {				  
	            String sql = "delete from SC where Sno ="+Sno+" and "+"Cno="+Cno; 
	            s.execute(sql);	
	            return true;
	            //System.out.println("删除成功");
	      }catch(SQLException e1) {
	            e1.printStackTrace();
	            return false;
	      }catch(Exception e2) {// 捕获其他异常
	    	  e2.printStackTrace();// 打印异常产生的过程信息
	    	  return false;
	      }
	    }	
	 public List<SC> list1(int Cno) {
	        List<SC> scs = new ArrayList<SC>();
	   //查询某个课程的选课记录
	  //把符合条件的SC数据查询出来，转换为SC对象后，放在一个集合中返回
	   //String sql = "select * from hero order by id desc limit ?,? ";		       	         
	        int Sno=0;//学号
	        String Sname=null;//姓名
	        String Cname=null;//课程名	            	
	        String sql = "select * from SC_view where 课程号 ="+Cno;
		    try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql);
		            while (rs.next()) {
		            	/*定义一个类型为Course的对象course ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
		                 * 下面的一样道理*/  
		            	SC sc=new SC() ;
		            	Sno=rs.getInt(1);
		            	Cno=rs.getInt(2); 
		            	Sname=rs.getString("姓名");
		            	Cname = rs.getString("课程名");	
		            	sc.Sno=Sno;
		            	sc.Cno=Cno;
		            	sc.Sname=Sname;
		            	sc.Cname=Cname;
		            	scs.add(sc);
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();}
		     return scs;//返回系集合，符合条件的系的信息
	    }
	 public List<SC> list2(int Sno) {
	        List<SC> scs = new ArrayList<SC>();
	   //查询某个同学的选课记录
	  //把符合条件的SC数据查询出来，转换为SC对象后，放在一个集合中返回
	   //String sql = "select * from hero order by id desc limit ?,? ";		       	         
	        int Cno=0;//学号
	        String Sname=null;//姓名
	        String Cname=null;//课程名	            	
	        String sql = "select * from SC_view where 学号 ="+Sno;
		    try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql);
		            while (rs.next()) {
		            	/*定义一个类型为Course的对象course ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
		                 * 下面的一样道理*/  
		            	SC sc=new SC() ;
		            	Sno=rs.getInt(1);
		            	Cno=rs.getInt(2); 
		            	Sname=rs.getString("姓名");
		            	Cname = rs.getString("课程名");	
		            	sc.Sno=Sno;
		            	sc.Cno=Cno;
		            	sc.Sname=Sname;
		            	sc.Cname=Cname;
		            	scs.add(sc);
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();}
		     return scs;//返回系集合，符合条件的系的信息
	    }
	 public List<SC> list3() {
	        List<SC> scs = new ArrayList<SC>();
	   //查询所以选课记录
	  //把符合条件的SC数据查询出来，转换为SC对象后，放在一个集合中返回
	   //String sql = "select * from hero order by id desc limit ?,? ";		       	         
	        int Sno=0;//学号
	        int Cno=0;//课程号
	        String Sname=null;//姓名
	        String Cname=null;//课程名	            	
	        String sql = "select * from SC_view ";
		    try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql);
		            while (rs.next()) {
		            	/*定义一个类型为Course的对象course ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
		                 * 下面的一样道理*/  
		            	SC sc=new SC() ;
		            	Sno=rs.getInt(1);
		            	Cno=rs.getInt(2); 
		            	Sname=rs.getString("Sname");
		            	Cname = rs.getString("Cname");	
		            	sc.Sno=Sno;
		            	sc.Cno=Cno;
		            	sc.Sname=Sname;
		            	sc.Cname=Cname;
		            	scs.add(sc);
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();}
		     return scs;//返回系集合，符合条件的系的信息
	    }
	 public List<Course> listweixuan(int Cno) {
	        List<Course> courses = new ArrayList<Course>();
	   //查询某个课程的选课记录
	  //把符合条件的SC数据查询出来，转换为SC对象后，放在一个集合中返回
	   //String sql = "select * from hero order by id desc limit ?,? ";		       	         
	        
	        
	        String Cname=null;//课程名	            	
	        String sql = "select * from Course where Cno not in(select 课程号 from SC_view where 学号="+Cno+")";
		    try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql);
		            while (rs.next()) {
		            	/*定义一个类型为Course的对象course ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
		                 * 下面的一样道理*/  
		            	Course course=new Course() ;
		            	Cno=rs.getInt(1);
		            	Cname=rs.getString(2); 
		            	
		            	course.Cno=Cno;
		            	
		            	course.Cname=Cname;
		            	courses.add(course);
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();}
		     return courses;//返回系集合，符合条件的系的信息
	    }
}
