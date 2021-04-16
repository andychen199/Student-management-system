package attendance.dao;
//请假记录表，给学生和老师用的，学生可以增删改查（在未提交请假条前可以修改删除，提交后就不行了，提交后只能查），老师只能改（改审核结果）
import java.sql.*;
import javax.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import attendance.bean.Afl;
public class AflDao {
	public AflDao(){
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
		//得到目前表Afl总数有多少条
       int total = 0;
       try (Connection c = getConnection(); Statement s = c.createStatement();) {
         
           String sql = "select count(*) from Afl";
 
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
	public boolean add(int Sno,int Cno,String APname,String Aname,String Adate,String ABegindate,String Aenddate,String Areason) {
		//(请假申请记录表)学号，课程号,班级，姓名，申请日期(默认当前时间的年月日小时分秒:2006-05-16 10:57:47)，开始时间，结束时间，请假原因
		//学生在申请请假的时候没有填审核结果的权限
        String sql = " insert into Afl( Sno,Cno,APname,Aname,Adate,ABegindate,Aenddate,Areason) values(?,?,?,?,?,?,?,?) ";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) { 
        	ps.setInt(1,Sno);
        	ps.setInt(2,Cno);
            ps.setString(3,APname);
            ps.setString(4,Aname); 
            ps.setString(5,Adate); 
            ps.setString(6,ABegindate); 
            ps.setString(7,Aenddate);
            ps.setString(8,Areason);
            ps.execute();  
            return true;
            //ResultSet rs = ps.getGeneratedKeys();//如果因为主码自增不插，然后通过这里得到主码赋值给对象对应的属性
        } catch (SQLException e) {  
            e.printStackTrace();
            return false;
        }
    }
	public boolean update1(int Sno,int Cno,int Sno1,int Cno1,String APname,String Aname,String Adate,String Adate1,String ABegindate,String Aenddate,String Areason) {
		//给学生用的,因为这个请假记录表的主码是参照别的表的，可以修改，Sno,Cno,Adate1是原来的主码，Sno1,Cno1,Adate1,是后来要修改的
		//(请假申请记录表)学号，课程号,班级，姓名，申请日期(默认当前时间的年月日小时分秒:2006-05-16 10:57:47)，开始时间，结束时间，请假原因
	   //学生在未提交时修改请假的时候没有修改审核结果的权限	
         String sql = "update Afl set Sno=?,Cno=? ,APname=?,Aname=?,Adate=?,ABegindate=?,Aenddate=?,Areason=? where Sno ="+Sno+" and "+"Cno="+Cno+" and "+"Adate="+"'"+Adate+"'"; 
         try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql);) { 
         	ps.setInt(1,Sno1);
         	ps.setInt(2,Cno1);
             ps.setString(3,APname);
             ps.setString(4,Aname); 
             ps.setString(5,Adate); 
             ps.setString(6,ABegindate); 
             ps.setString(7,Aenddate);
             ps.setString(8,Areason);
             ps.execute();  
             return true;
             //ResultSet rs = ps.getGeneratedKeys();//如果因为主码自增不插，然后通过这里得到主码赋值给对象对应的属性
	    	//	 System.out.println("修改成功");	    		 
	    	 }catch (SQLException e1) {
		            e1.printStackTrace();
		            return false;
		      }catch (Exception e2) {// 捕获其他异常
		    	  e2.printStackTrace();// 打印异常产生的过程信息
		    	  return false;
		      }
	    	}
	public void update2(int Sno,int Cno,String Adate,String Aresults) {
		//给老师用的,老师只能修改审核结果
         String sql = "update Afl set Aresults=? where Sno ="+Sno+"and "+"Cno="+Cno+" and "+"Adate="+"'"+Adate+"'"; 
         try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql);) { 
             ps.setString(1,Aresults);
             ps.execute();  
             //ResultSet rs = ps.getGeneratedKeys();//如果因为主码自增不插，然后通过这里得到主码赋值给对象对应的属性
	    	//	 System.out.println("修改成功");	    		 
	    	 }catch (SQLException e1) {
		            e1.printStackTrace();		     
		      }catch (Exception e2) {// 捕获其他异常
		    	  e2.printStackTrace();// 打印异常产生的过程信息
		      }
	    	}
	public void delete(int Sno,int Cno,String Adate) {
		 //删除函数
		 try (Connection c = getConnection(); Statement s = c.createStatement();) {				  
	            String sql = "delete from Afl where Sno ="+Sno+"and"+"Cno="+Cno+"and"+"Adate="+"'"+Adate+"'"; 
	            s.execute(sql);	
	            //System.out.println("删除成功");
	      }catch(SQLException e1) {
	            e1.printStackTrace();
	      }catch(Exception e2) {// 捕获其他异常
	    	  e2.printStackTrace();// 打印异常产生的过程信息
	      }
	    }	
	 public List<Afl> list1(int Sno,int Cno,String Adate) {
	        List<Afl> afls = new ArrayList<Afl>();
	  //把符合条件的Afl数据查询出来，转换为Afl对象后，放在一个集合中返回select * from Afl where Sno=4 and Adate='2020-04-14' and Cno in(select Cno from Teach where Tno=1)
	   //String sql = "select * from hero order by id desc limit ?,? ";		       	         
	        String APname=null;
	        String Aname=null;
	        String ABegindate=null;
	        String Aenddate=null;
	        String Areason=null;
	        String Aresults=null;
	        //按即主码查询，单个        	
	        String sql = "select * from Afl where Sno ="+Sno+"and"+"Cno="+Cno+"and"+"Adate="+"'"+Adate+"'"; 
		    try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql);
		            while (rs.next()) {
		            	/*定义一个类型为Admin的对象 admin ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
		                 * 下面的一样道理*/  
		            	Afl afl=new Afl() ;
		            	Sno=rs.getInt(1);
		            	Cno= rs.getInt(2);           	
		            	APname= rs.getString(3);
		            	Aname = rs.getString("Aname");
		            	Adate = rs.getString(5);
		            	ABegindate=rs.getString(6);
		            	Aenddate=rs.getString(7);
		            	Areason=rs.getString(8);
		            	Aresults=rs.getString(9);
		            	afl.Sno=Sno;
		            	afl.Cno=Cno;
		            	afl.APname =APname;
		            	afl.Aname=Aname;
		            	afl.Adate=Adate;
		            	afl.ABegindate=ABegindate;
		            	afl.Aenddate=Aenddate;
		            	afl.Areason=Areason;
		            	afl.Aresults=Aresults;
		            	afls.add(afl);
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();}
		     return afls;//返回系集合，符合条件的系的信息
	    }
	 public List<Afl> list2(int Sno) {
	        List<Afl> afls = new ArrayList<Afl>();
	  //把符合条件的Afl数据查询出来，转换为Afl对象后，放在一个集合中返回select * from Afl where Sno=4 and Adate='2020-04-14' and Cno in(select Cno from Teach where Tno=1)
	   //String sql = "select * from hero order by id desc limit ?,? ";		       	         
	        String APname=null;
	        String Aname=null;
	        String ABegindate=null;
	        String Aenddate=null;
	        String Areason=null;
	        String Aresults=null;
	        int Cno = 0;
	        String Adate = null;
	        //按即主码查询，单个        	
	        String sql = "select * from Afl where Sno ="+Sno; 
		    try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql);
		            while (rs.next()) {
		            	/*定义一个类型为Admin的对象 admin ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
		                 * 下面的一样道理*/  
		            	Afl afl=new Afl() ;
		            	Sno=rs.getInt(1);
		            	Cno= rs.getInt(2);           	
		            	APname= rs.getString(3);
		            	Aname = rs.getString("Aname");
		            	Adate = rs.getString(5);
		            	ABegindate=rs.getString(6);
		            	Aenddate=rs.getString(7);
		            	Areason=rs.getString(8);
		            	Aresults=rs.getString(9);
		            	afl.Sno=Sno;
		            	afl.Cno=Cno;
		            	afl.APname =APname;
		            	afl.Aname=Aname;
		            	afl.Adate=Adate;
		            	afl.ABegindate=ABegindate;
		            	afl.Aenddate=Aenddate;
		            	afl.Areason=Areason;
		            	afl.Aresults=Aresults;
		            	afls.add(afl);
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();}
		     return afls;//返回系集合，符合条件的系的信息
	    }
	 public List<Afl> list(int Tno) {
	        List<Afl> afls = new ArrayList<Afl>();
	        int Sno=0;
	        int Cno=0;
	        String APname=null;
	        String Aname=null;
	        String Adate=null;
	        String ABegindate=null;
	        String Aenddate=null;
	        String Areason=null;
	        String Aresults=null;
	        //按即主码查询，单个        	
	        String sql = "select * from Afl where Cno in(select Cno from Teach where Tno="+Tno+")";
		    try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql);
		            while (rs.next()) {
		            	/*定义一个类型为Admin的对象 admin ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
		                 * 下面的一样道理*/  
		            	Afl afl=new Afl() ;
		            	Sno=rs.getInt(1);
		            	Cno= rs.getInt(2);           	
		            	APname= rs.getString(3);
		            	Aname = rs.getString("Aname");
		            	Adate = rs.getString(5);
		            	ABegindate=rs.getString(6);
		            	Aenddate=rs.getString(7);
		            	Areason=rs.getString(8);
		            	Aresults=rs.getString(9);
		            	afl.Sno=Sno;
		            	afl.Cno=Cno;
		            	afl.APname =APname;
		            	afl.Aname=Aname;
		            	afl.Adate=Adate;
		            	afl.ABegindate=ABegindate;
		            	afl.Aenddate=Aenddate;
		            	afl.Areason=Areason;
		            	afl.Aresults=Aresults;
		            	afls.add(afl);
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();}
		     return afls;//返回系集合，符合条件的系的信息
	    }
	 public List<Afl> get(int Tno,String Aresults) {
	        List<Afl> afls = new ArrayList<Afl>();
	        int Sno=0;
	        int Cno=0;
	        String APname=null;
	        String Aname=null;
	        String Adate=null;
	        String ABegindate=null;
	        String Aenddate=null;
	        String Areason=null;
	        //String Aresults=null;
	        //按即主码查询，单个        	
	        String sql = "select * from Afl where Aresults='"+Aresults+"' and Cno in(select Cno from Teach where Tno="+Tno+")";
		    try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql);
		            while (rs.next()) {
		            	/*定义一个类型为Admin的对象 admin ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
		                 * 下面的一样道理*/  
		            	Afl afl=new Afl() ;
		            	Sno=rs.getInt(1);
		            	Cno= rs.getInt(2);           	
		            	APname= rs.getString(3);
		            	Aname = rs.getString("Aname");
		            	Adate = rs.getString(5);
		            	ABegindate=rs.getString(6);
		            	Aenddate=rs.getString(7);
		            	Areason=rs.getString(8);
		            	Aresults=rs.getString(9);
		            	afl.Sno=Sno;
		            	afl.Cno=Cno;
		            	afl.APname =APname;
		            	afl.Aname=Aname;
		            	afl.Adate=Adate;
		            	afl.ABegindate=ABegindate;
		            	afl.Aenddate=Aenddate;
		            	afl.Areason=Areason;
		            	afl.Aresults=Aresults;
		            	afls.add(afl);
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();}
		     return afls;//返回系集合，符合条件的系的信息
	    }
}
