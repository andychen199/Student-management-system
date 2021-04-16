package attendance.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import attendance.bean.Student;
//学生Dao，有些操作给管理员做的，有些给学生做的
public class StudentDao {
	public StudentDao(){
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
		//得到目前表Teacher总数有多少条
      int total = 0;
      try (Connection c = getConnection(); Statement s = c.createStatement();) {
        
          String sql = "select count(*) from Student";

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
	public boolean add(int Sno,String Sname,String Sex,int Sage,String Snative,int SPno) {
		//给管理员用的
		//这里增加函数学号（本来主码可以自增不插，但我这里还是打开自增规则插进去）学号，姓名，性别，年龄，籍贯，专业班级号
		//密码在增加函数这里不插，默认，后期再自己改密码
        String sql = "set identity_insert Student ON insert into Student(Sno,Sname,Sex,Sage,Snative,SPno) values(?,?,?,?,?,?) set identity_insert Student OFF";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) { 
        	ps.setInt(1,Sno);
        	ps.setString(2,Sname);
            ps.setString(3,Sex);
            ps.setInt(4,Sage); 
            ps.setString(5,Snative); 
            ps.setInt(6,SPno); 
            ps.execute();  
            return true;
            //ResultSet rs = ps.getGeneratedKeys();//如果因为主码自增不插，然后通过这里得到主码赋值给对象对应的属性
        } catch (SQLException e) {  
            e.printStackTrace();
        }
		return false;
    }
	public boolean delete(int Sno) {
		 //删除函数
		//给管理员用
		 try (Connection c = getConnection(); Statement s = c.createStatement();) {				  
	            String sql = "delete from Student where Sno= " +"'"+Sno+"'";
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
	public void update1(int Sno,String Sname,String Sex,int Sage,String Snative,int SPno) {
		//给管理员用，不更新密码，因为管理员没有权限
		//学号，姓名，性别，年龄，籍贯，专业班级号
		//不更新主码，主码一般不允许更新，一般允许更新主码的数据库设计都是不合理的		
         String sql = "update  Student set Sname=?,Sex=? ,Sage=?,Snative=?,SPno=? where Sno ="+Sno; 
	    	 try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql);){	    		
	    		 ps.setString(1, Sname);
	    		 ps.setString(2, Sex);
	    		 ps.setInt(3, Sage);
	    		 ps.setString(4, Snative);
	    		 ps.setInt(5, SPno);   		 
	    		 ps.execute();
	    	//	 System.out.println("修改成功");	    		 
	    	 }catch (SQLException e1) {
		            e1.printStackTrace();		     
		      }catch (Exception e2) {// 捕获其他异常
		    	  e2.printStackTrace();// 打印异常产生的过程信息
		      }
	    	}
	public boolean update2(int Sno,String Spassword) {
		//给学生用，新密码
		//不更新主码，主码一般不允许更新，一般允许更新主码的数据库设计都是不合理的		
         String sql = "update Student set Spassword=? where Sno ="+Sno; 
	    	 try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql);){	    		
	    		 ps.setString(1, Spassword);	    		 
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
	public List<Student> list1(int Sno) {
        List<Student> students = new ArrayList<Student>();
  //把符合条件的Student数据查询出来，转换为Student对象后，放在一个集合中返回
  //从视图里查询，看不到密码，给学生用，查询某个人或者自己
   //String sql = "select * from hero order by id desc limit ?,? ";	 
        String Pcname=null;
        String Pname=null;
        String Sname=null;
        String Sex=null;
        int Sage=0;
        String Sbirthtime=null;
        String Snative=null;
        //按即主码查询，单个	       	        	
        String sql = "select * from Student_view where 学号="+Sno;
	    try (Connection c = getConnection(); Statement s = c.createStatement();) {
	        	ResultSet rs = s.executeQuery(sql);
	            while (rs.next()) {
	            	/*定义一个类型为Teacher的对象 Teacher ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
	                 * 下面的一样道理*/  
	            	Student student=new Student() ;
	            	Pcname= rs.getString(1);
	            	Pname = rs.getString(2);
	            	Sname=rs.getString(3);
	            	Sno = rs.getInt(4);
	            	Sex=rs.getString(5);
	            	Sage=rs.getInt(6);
	            	Sbirthtime=rs.getString(7);
	            	Snative=rs.getString(8);
	            	student.Pcname=Pcname;
	            	student.Pname=Pname;
	            	student.Sname=Sname;
	            	student.Sno=Sno;
	            	student.Sex=Sex;
	            	student.Sage=Sage;
	            	student.Sbirthtime=Sbirthtime;
	            	student.Snative=Snative;
	            	students.add(student);
	            }
	        }catch (SQLException e) {
	            e.printStackTrace();}
	     return students;//返回系集合，符合条件的系的信息
    }
	public List<Student> list2(String Pname) {
        List<Student> students = new ArrayList<Student>();
  //把符合条件的Student数据查询出来，转换为Student对象后，放在一个集合中返回
  //从视图里查询，看不到密码，给别的用，查询一个班级
   //String sql = "select * from hero order by id desc limit ?,? ";	 
        String Pcname=null;
        String Sname=null;
        int Sno=0;
        String Sex=null;
        int Sage=0;
        String Sbirthtime=null;
        String Snative=null;
        //按即主码查询，单个	       	        	
        String sql = "select * from Student_view where 专业班级="+"'"+Pname+"'";
	    try (Connection c = getConnection(); Statement s = c.createStatement();) {
	        	ResultSet rs = s.executeQuery(sql);
	            while (rs.next()) {
	            	/*定义一个类型为Teacher的对象 Teacher ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
	                 * 下面的一样道理*/  
	            	Student student=new Student() ;
	            	Pcname= rs.getString(1);
	            	Pname = rs.getString(2);
	            	Sname=rs.getString(3);
	            	Sno = rs.getInt(4);
	            	Sex=rs.getString(5);
	            	Sage=rs.getInt(6);
	            	Sbirthtime=rs.getString(7);
	            	Snative=rs.getString(8);
	            	student.Pcname=Pcname;
	            	student.Pname=Pname;
	            	student.Sname=Sname;
	            	student.Sno=Sno;
	            	student.Sex=Sex;
	            	student.Sage=Sage;
	            	student.Sbirthtime=Sbirthtime;
	            	student.Snative=Snative;
	            	students.add(student);
	            }
	        }catch (SQLException e) {
	            e.printStackTrace();}
	     return students;//返回系集合，符合条件的系的信息
    }
	public String getpwd(int Sno) {
        
  //把符合条件的Student数据查询出来，转换为Student对象后，放在一个集合中返回
  //从表查询，给管理员用，看得到密码，查询所有人
   //String sql = "select * from hero order by id desc limit ?,? ";	 
        //int Sno=0;
//        String Sname=null;
//        String Sex=null;
//        int Sage=0;
//        String Snative=null;
//        int SPno=0;
        String Spassword=null;
        //按即主码查询，单个	       	        	
        String sql = "select Spassword from Student where Sno="+Sno;
	    try (Connection c = getConnection(); Statement s = c.createStatement();) {
	        	ResultSet rs = s.executeQuery(sql);
	            while (rs.next()) { 
	            	
	            	
	            	Spassword=rs.getString(1);
	            	
	            }
	        }catch (SQLException e) {
	            e.printStackTrace();}
	     return Spassword;//返回系集合，符合条件的系的信息
    }
	 public List<Student> list3() {
	        List<Student> students = new ArrayList<Student>();
	  //把符合条件的Student数据查询出来，转换为Student对象后，放在一个集合中返回
	  //从表查询，给管理员用，看得到密码，查询所有人
	   //String sql = "select * from hero order by id desc limit ?,? ";	 
	        int Sno=0;
	        String Sname=null;
	        String Sex=null;
	        int Sage=0;
	        String Snative=null;
	        int SPno=0;
	        String Spassword=null;
	        //按即主码查询，单个	       	        	
	        String sql = "select * from Student";
		    try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql);
		            while (rs.next()) { 
		            	Student student=new Student() ;
		            	Sno = rs.getInt(1);	
		            	Sname=rs.getString(2);
		            	Sex = rs.getString(3);
		            	Sage=rs.getInt(4);
		            	Snative=rs.getString(5);
		            	SPno=rs.getInt(6);
		            	Spassword=rs.getString(7);
		            	student.Sno=Sno;
		            	student.Sname=Sname;
		            	student.Sex=Sex;
		            	student.Sage=Sage;
		            	student.Snative=Snative;
		            	student.SPno=SPno;
		            	student.Spassword=Spassword;	            	
		            	students.add(student);
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();}
		     return students;//返回系集合，符合条件的系的信息
	    }
	 public boolean login(int Sno,String pwd) {
		 	int sqlSno = 0;
		 	String sqlpwd = null;
			String sql = "select * from Student where Sno="+Sno+" and "+"Spassword="+"'"+pwd+"'";
			try (Connection c = getConnection(); Statement s = c.createStatement();) {
				ResultSet rs = s.executeQuery(sql);
//				System.out.println(Sno);
//				System.out.println(pwd);
				while (rs.next()) {
					sqlSno = rs.getInt(1);
					sqlpwd = rs.getString(7);
					if(Sno==sqlSno&&pwd.equals(sqlpwd))
					{
						return true;
					}
				
				}
			}
			catch (SQLException e) {  
	        e.printStackTrace();
	        return false;
			}
			return false;
		}
	 public List<Student> list4(int Sno,String Spassword) {
         List<Student> students = new ArrayList<Student>();
   //把符合条件的Student数据查询出来，转换为Student对象后，放在一个集合中返回
   //从表查询，用于登录
    //String sql = "select * from hero order by id desc limit ?,? ";  
         String Sname=null;
         String Sex=null;
         int Sage=0;
         String Snative=null;
         int SPno=0;       
         //按即主码查询，单个                  
         String sql = "select * from Student where Sno="+Sno+" and "+"Spassword="+"'"+Spassword+"'";
      try (Connection c = getConnection(); Statement s = c.createStatement();) {
           ResultSet rs = s.executeQuery(sql);
              while (rs.next()) { 
               Student student=new Student() ;
               Sno = rs.getInt(1); 
               Sname=rs.getString(2);
               Sex = rs.getString(3);
               Sage=rs.getInt(4);
               Snative=rs.getString(5);
               SPno=rs.getInt(6);
               Spassword=rs.getString(7);
               student.Sno=Sno;
               student.Sname=Sname;
               student.Sex=Sex;
               student.Sage=Sage;
               student.Snative=Snative;
               student.SPno=SPno;
               student.Spassword=Spassword;              
               students.add(student);
              }
          }catch (SQLException e) {
              e.printStackTrace();
              return null;}
       return students;
     }
}
