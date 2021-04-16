package attendance.dao;
//任职记录DAO,给管理员看的
import java.sql.*;
import javax.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import attendance.bean.Course;
import attendance.bean.Teach;
import attendance.bean.TeachforUI;;
public class TeachDao {
	public TeachDao(){
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
		//得到目前表Teach总数有多少条
       int total = 0;
       try (Connection c = getConnection(); Statement s = c.createStatement();) {          
           String sql = "select count(*) from Teach"; 
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
	public boolean add(int Tno,int Cno,int  Tschool,int Tsgrade) {
		//任课老师职工号，课程号，学时，学分
        String sql = " insert into Teach(Tno, Cno,Tschool,Tsgrade) values(?,?,?,?) ";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) { 
        	ps.setInt(1,Tno);
        	ps.setInt(2,Cno);
        	ps.setInt(3,Tschool);
        	ps.setInt(4,Tsgrade);
            ps.execute();  
            return true;
            //ResultSet rs = ps.getGeneratedKeys();//如果因为主码自增不插，然后通过这里得到主码赋值给对象对应的属性
        } catch (SQLException e) {  
            e.printStackTrace();
            return false;
        }
    }
	public boolean update(int Tno,int Cno,int Tno1,int Cno1,int  Tschool,int Tsgrade) {
		//任课老师职工号，课程号，学时，学分	
		//因为这个任课表的主码是参照别的表的，可以修改，Tno,Cno是原来的主码，Tno1,Cno1是后来要修改的
         String sql = "update Teach set Tno=?, Cno=? ,Tschool=?, Tsgrade=? where Cno ="+Cno+" and "+"Tno="+Tno; 
	    	 try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql);){	    		
	    		 ps.setInt(1,Tno1); 	    		 
	    		 ps.setInt(2,Cno1);
	    		 ps.setInt(3,Tschool); 	    		 
	    		 ps.setInt(4,Tsgrade);
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
	public void delete(int Tno,int Cno) {
		 //删除函数
		 try (Connection c = getConnection(); Statement s = c.createStatement();) {				  
	            String sql = "delete from Course where Cno ="+Cno+"and"+"Tno="+Tno; 
	            s.execute(sql);	
	            //System.out.println("删除成功");
	      }catch(SQLException e1) {
	            e1.printStackTrace();
	      }catch(Exception e2) {// 捕获其他异常
	    	  e2.printStackTrace();// 打印异常产生的过程信息
	      }
	    }	
	 public List<Teach> list1(int Tno,int Cno) {
	        List<Teach> teachs = new ArrayList<Teach>();
	  //把符合条件的Teach数据查询出来，转换为Teach对象后，放在一个集合中返回
	   //String sql = "select * from hero order by id desc limit ?,? ";		       	         
	        int Tschool=0;//学时
	        int Tsgrade=0;//学分
	        //按即主码查询，单个
	        //System.out.println("请输入要查询任课老师职工号，课程号:");	        	
	        String sql = "select * from Teach where Cno ="+Cno+"and"+"Tno="+Tno; 
		    try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql);
		            while (rs.next()) {
		            	/*定义一个类型为Teach的对象teach ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
		                 * 下面的一样道理*/  
		            	Teach teach=new Teach() ;
		            	Tno= rs.getInt(1);
		            	Cno=rs.getInt(2);
		            	Tschool = rs.getInt("Tschool");	
		            	Tsgrade=rs.getInt(4);
		            	teach.Tno=Tno;
		            	teach.Cno=Cno;
		            	teach.Tschool=Tschool;
		            	teach.Tsgrade=Tsgrade;
		            	teachs.add(teach);
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();}
		     return teachs;//返回系集合，符合条件的系的信息
	    }
	 public List<Teach> list2() {
	        List<Teach> teachs = new ArrayList<Teach>();
	  //把Teach数据查询出来，转换为Teach对象后，放在一个集合中返回
	   //String sql = "select * from hero order by id desc limit ?,? ";	
	        int Tno=0;//任课老师职工号
	        int Cno=0;//课程号
	        int Tschool=0;//学时
	        int Tsgrade=0;//学分
	      //都查询出来
	        //System.out.println("请输入要查询任课老师职工号，课程号:");	        	
	        String sql = "select * from Teach ";
		    try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql);
		            while (rs.next()) {
		            	Teach teach=new Teach() ;
		            	Tno= rs.getInt(1);
		            	Cno=rs.getInt(2);
		            	Tschool = rs.getInt("Tschool");	
		            	Tsgrade=rs.getInt(4);
		            	teach.Tno=Tno;
		            	teach.Cno=Cno;
		            	teach.Tschool=Tschool;
		            	teach.Tsgrade=Tsgrade;
		            	teachs.add(teach);
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();}
		     return teachs;//返回系集合，符合条件的系的信息
	    }
	 public List<Teach> list3(int Tno) {
	        List<Teach> teachs = new ArrayList<Teach>();
	  //把符合条件的Teach数据查询出来，转换为Teach对象后，放在一个集合中返回
	   //String sql = "select * from hero order by id desc limit ?,? ";		       	         
	        int Cno = 0;
	        int Tschool=0;//学时
	        int Tsgrade=0;//学分
	        //按即主码查询，单个
	        //System.out.println("请输入要查询任课老师职工号，课程号:");	        	
	        String sql = "select * from Teach where Tno ="+Tno; 
		    try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql);
		            while (rs.next()) {
		            	/*定义一个类型为Teach的对象teach ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
		                 * 下面的一样道理*/  
		            	Teach teach=new Teach() ;
		            	Tno= rs.getInt(1);
		            	Cno=rs.getInt(2);
		            	Tschool = rs.getInt("Tschool");	
		            	Tsgrade=rs.getInt(4);
		            	teach.Tno=Tno;
		            	teach.Cno=Cno;
		            	teach.Tschool=Tschool;
		            	teach.Tsgrade=Tsgrade;
		            	teachs.add(teach);
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();}
		     return teachs;//返回系集合，符合条件的系的信息
	    }
	 public List<TeachforUI> list3forUI(int Tno) {
	        List<TeachforUI> teachs = new ArrayList<TeachforUI>();
	  //把符合条件的Teach数据查询出来，转换为Teach对象后，放在一个集合中返回
	   //String sql = "select * from hero order by id desc limit ?,? ";		       	         
	        int Cno = 0;
	        String Cname=null;
	        int Tschool=0;//学时
	        int Tsgrade=0;//学分
	        //按即主码查询，单个
	        //System.out.println("请输入要查询任课老师职工号，课程号:");	        	
	        String sql = "select distinct Course.Cno,Cname,Tschool,Tsgrade from Course,Teach where Course.Cno=Teach.Cno and Tno="+Tno; 
		    try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql);
		            
		        	while (rs.next()) {
		            	/*定义一个类型为Teach的对象teach ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
		                 * 下面的一样道理*/  
		            	TeachforUI teach=new TeachforUI();
		            	Cno= rs.getInt(1);
		            	Cname=rs.getString(2);
		            	Tschool = rs.getInt("Tschool");	
		            	Tsgrade=rs.getInt(4);
		            	teach.Cno=Cno;
		            	teach.setCname(Cname);
		            	teach.Tschool=Tschool;
		            	teach.Tsgrade=Tsgrade;
		            	teachs.add(teach);
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();}
		     return teachs;//返回系集合，符合条件的系的信息
	    }
	
}
