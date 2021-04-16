package attendance.dao;
//教师DAO，里面有些函数是给管理员用，有些给教师用
import java.sql.*;
import javax.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import attendance.bean.Admin;
import attendance.bean.Teacher;
public class TeacherDao {
	public TeacherDao(){
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
         
           String sql = "select count(*) from Teacher";
 
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
	public boolean add(int Tno,String Tname,String  Tsex,int Tage,String Tphone,String Tcname,String Tbirthtime) {
		//给管理员用的
		//这里增加函数职工号（本来主码可以自增不插，但我这里还是打开自增规则插进去），职工号，姓名，性别，教龄,电话,系名
		//密码在增加函数这里不插，默认，后期再自己改密码
        String sql = "set identity_insert Teacher ON insert into Teacher(Tno,Tname,Tsex,Tage,Tphone,Tcname,Tbirthtime) values(?,?,?,?,?,?,?) set identity_insert Teacher OFF";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) { 
        	ps.setInt(1,Tno);
        	ps.setString(2,Tname);
            ps.setString(3,Tsex);
            ps.setInt(4,Tage); 
            ps.setString(5,Tphone); 
            ps.setString(6,Tcname); 
            ps.setString(7,Tbirthtime);
            ps.execute();  
            return true;
            //ResultSet rs = ps.getGeneratedKeys();//如果因为主码自增不插，然后通过这里得到主码赋值给对象对应的属性
        } catch (SQLException e) {  
            e.printStackTrace();
        }
		return false;
    }
	public void update1(int Tno,String Tname,String  Tsex,int Tage,String Tphone,String Tcname,String Tbirthtime) {
		//给管理员用，不更新密码，因为管理员没有权限
		//姓名，性别，职称，电话，工龄,系名,密码（初始为123456）
		//不更新主码，主码一般不允许更新，一般允许更新主码的数据库设计都是不合理的		
         String sql = "update Teacher set Tname=?,Tsex=? ,Tage=?,Tphone=?,Tcname=?,Tbirthtime=? where Tno ="+Tno; 
	    	 try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql);){	    		
	    		 ps.setString(1, Tname);
	    		 ps.setString(2, Tsex);
	    		 ps.setInt(3, Tage);
	    		 ps.setString(4, Tphone);
	    		 ps.setString(5, Tcname);
	    		 ps.setString(6, Tbirthtime);
	    		// ps.setString(6, Tpassword);	    		 
	    		 ps.execute();
	    	//	 System.out.println("修改成功");	    		 
	    	 }catch (SQLException e1) {
		            e1.printStackTrace();		     
		      }catch (Exception e2) {// 捕获其他异常
		    	  e2.printStackTrace();// 打印异常产生的过程信息
		      }
	    	}
	public boolean update2(int Tno,String Tpassword) {
		//给老师用，新密码
		//不更新主码，主码一般不允许更新，一般允许更新主码的数据库设计都是不合理的		
         String sql = "update Teacher set Tpassword=? where Tno ="+Tno; 
	    	 try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql);){	    		
	    		 ps.setString(1, Tpassword);	    		 
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
	public boolean delete(int Tno) {
		 //删除函数
		//给管理员用
		 try (Connection c = getConnection(); Statement s = c.createStatement();) {				  
	            String sql = "delete from Teacher where Tno= " +Tno;
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
	 public List<Teacher> list1(int Tno) {
	        List<Teacher> teachers = new ArrayList<Teacher>();
	  //把符合条件的Teacher数据查询出来，转换为Teacher对象后，放在一个集合中返回
	  //从视图里查询，看不到密码，给老师用，查询某个人
	   //String sql = "select * from hero order by id desc limit ?,? ";	        
	        String Tcname=null;//
	        String Tname=null;//
	        String Tsex=null;//
	        int Tage=0;//
	        String Tbirthtime=null;//
	        String Tphone=null;//
	        //按即主码查询，单个	       	        	
	        String sql = "select * from Teacher_view where 职工号="+Tno;
		    try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql);
		            while (rs.next()) {
		            	/*定义一个类型为Teacher的对象 Teacher ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
		                 * 下面的一样道理*/  
		            	Teacher teacher=new Teacher() ;
		            	Tcname= rs.getString(1);
		            	Tno = rs.getInt(2);
		            	Tname=rs.getString(3);
		            	Tsex = rs.getString(4);
		            	Tage=rs.getInt(5);
		            	Tbirthtime=rs.getString(6);
		            	Tphone=rs.getString(7);
		            	teacher.Tcname=Tcname;
		            	teacher.Tno=Tno;
		            	teacher.Tname=Tname;
		            	teacher.Tsex=Tsex;
		            	teacher.Tage=Tage;
		            	teacher.Tbirthtime=Tbirthtime;
		            	teacher.Tphone=Tphone;
		            	teachers.add(teacher);
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();}
		     return teachers;//返回系集合，符合条件的系的信息
	    }
	 public List<Teacher> list2() {
	        List<Teacher> teachers = new ArrayList<Teacher>();
	  //把符合条件的Teacher数据查询出来，转换为Teacher对象后，放在一个集合中返回
	  //从视图里查询，看不到密码，给其他老师用，查询所有人
	   //String sql = "select * from hero order by id desc limit ?,? ";	 
	        String Tcname=null;//
	        int Tno=0;
	        String Tname=null;//
	        String Tsex=null;//
	        int Tage=0;//
	        String Tbirthtime=null;//
	        String Tphone=null;//
	        //按即主码查询，单个	       	        	
	        String sql = "select * from Teacher_view ";
		    try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql);
		            while (rs.next()) { 
		            	Teacher teacher=new Teacher() ;
		            	Tcname= rs.getString(1);
		            	Tno = rs.getInt(2);
		            	Tname=rs.getString(3);
		            	Tsex = rs.getString(4);
		            	Tage=rs.getInt(5);
		            	Tbirthtime=rs.getString(6);
		            	Tphone=rs.getString(7);
		            	teacher.Tcname=Tcname;
		            	teacher.Tno=Tno;
		            	teacher.Tname=Tname;
		            	teacher.Tsex=Tsex;
		            	teacher.Tage=Tage;
		            	teacher.Tbirthtime=Tbirthtime;
		            	teacher.Tphone=Tphone;
		            	teachers.add(teacher);
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();}
		     return teachers;//返回系集合，符合条件的系的信息
	    }
	 public List<Teacher> list3() {
	        List<Teacher> teachers = new ArrayList<Teacher>();
	  //把符合条件的Teacher数据查询出来，转换为Teacher对象后，放在一个集合中返回
	  //从表查询，给管理员用，看得到密码，查询所有人
	   //String sql = "select * from hero order by id desc limit ?,? ";	 
	        String Tcname=null;//
	        int Tno=0;
	        String Tname=null;//
	        String Tsex=null;//
	        int Tage=0;//
	        String Tbirthtime=null;//
	        String Tphone=null;//
	        String Tpassword=null;
	        //按即主码查询，单个	       	        	
	        String sql = "select * from Teacher";
		    try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql);
		            while (rs.next()) { 
		            	Teacher teacher=new Teacher() ;
		            	Tno = rs.getInt(1);	
		            	Tname=rs.getString(2);
		            	Tsex = rs.getString(3);
		            	Tage=rs.getInt(4);
		            	Tphone=rs.getString(5);
		            	Tcname=rs.getString(6);
		            	Tbirthtime=rs.getString(7);
		            	Tpassword=rs.getString(8);
		            	teacher.Tno=Tno;
		            	teacher.Tname=Tname;
		            	teacher.Tsex=Tsex;
		            	teacher.Tage=Tage;
		            	teacher.Tphone=Tphone;
		            	teacher.Tcname=Tcname;
		            	teacher.Tbirthtime=Tbirthtime;
		            	teacher.Tpassword=Tpassword;		            	
		            	teachers.add(teacher);
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();}
		     return teachers;//返回系集合，符合条件的系的信息
	    }
	 public boolean login(int Tno,String pwd) {
		 	int sqlTno = 0;
		 	String sqlpwd = null;
			String sql = "select * from Teacher where Tno="+Tno+" and "+"Tpassword="+"'"+pwd+"'";
			try (Connection c = getConnection(); Statement s = c.createStatement();) {
				ResultSet rs = s.executeQuery(sql);
//				System.out.println(Sno);
//				System.out.println(pwd);
				while (rs.next()) {
					sqlTno = rs.getInt(1);
					sqlpwd = rs.getString(8);
					if(Tno==sqlTno&&pwd.equals(sqlpwd))
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
	 public List<Teacher> list4(int Tno, String Tpassword) {
         List<Teacher> teachers = new ArrayList<Teacher>();
   //把符合条件的Teacher数据查询出来，转换为Teacher对象后，放在一个集合中返回
   //从表查询，用于登录
    //String sql = "select * from hero order by id desc limit ?,? ";  
         String Tcname=null;//
         String Tname=null;//
         String Tsex=null;//
         int Tage=0;//
         String Tbirthtime=null;//
         String Tphone=null;//
        
         //按即主码查询，单个                  
         String sql = "select * from Teacher where Tno="+Tno+" and "+"Tpassword="+"'"+Tpassword+"'";
      try (Connection c = getConnection(); Statement s = c.createStatement();) {
           ResultSet rs = s.executeQuery(sql);
              while (rs.next()) { 
               Teacher teacher=new Teacher() ;
               Tno = rs.getInt(1); 
               Tname=rs.getString(2);
               Tsex = rs.getString(3);
               Tage=rs.getInt(4);
               Tphone=rs.getString(5);
               Tcname=rs.getString(6);
               Tbirthtime=rs.getString(7);
               Tpassword=rs.getString(8);
               teacher.Tno=Tno;
               teacher.Tname=Tname;
               teacher.Tsex=Tsex;
               teacher.Tage=Tage;
               teacher.Tphone=Tphone;
               teacher.Tcname=Tcname;
               teacher.Tbirthtime=Tbirthtime;
               teacher.Tpassword=Tpassword;               
               teachers.add(teacher);
              }
          }catch (SQLException e) {
              e.printStackTrace();}
       return teachers;
     }
}
