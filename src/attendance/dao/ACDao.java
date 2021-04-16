package attendance.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import attendance.bean.AC;
import attendance.bean.Student;
import attendance.dao.StudentDao;
public class ACDao {
	public ACDao(){
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
       
         String sql = "select count(*) from AC";

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
	public boolean add(String APname,int Cno,String Acdate) {
		//给老师用的,录入同个学生考勤情况，不录入出勤状况，默认出勤，后期修改要修改的那些
		//这里增加函数，输入班级,课程号,还有日期，自动插入学生信息，
		//增加函数这里
		StudentDao students=new StudentDao();
		List<Student> is =new ArrayList<Student>();
		is=students.list2(APname);
		for (Student b : is){
			int Sno=b.Sno;
			int Sno1=0;
			String sql = " insert into AC(Sno,Cno,APname,Aname,Acdate) values(?,?,?,?,?) ";
			String sql1="select Sno from SC where Sno="+Sno+" and Cno="+Cno;
			 try (Connection c1 = getConnection(); Statement s = c1.createStatement();) {
		        	ResultSet rs1 = s.executeQuery(sql1);
		            while (rs1.next()) {
		            	/*定义一个类型为Teacher的对象 Teacher ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
		                 * 下面的一样道理*/  
		            	Sno1=rs1.getInt(1);
		            }		            
		        }catch (SQLException e) {
		            e.printStackTrace();}
			 if(Sno==Sno1){
				 String Aname=b.Sname;	            	
					 try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) { 
				        	ps.setInt(1,Sno1);
				        	ps.setInt(2,Cno);
				            ps.setString(3,APname);
				            ps.setString(4,Aname); 
				            ps.setString(5,Acdate); 
				            ps.execute();  
				            
				            //ResultSet rs = ps.getGeneratedKeys();//如果因为主码自增不插，然后通过这里得到主码赋值给对象对应的属性
				        } catch (SQLException e) {  
				            e.printStackTrace();
				            return false;
				        }
	            }else{
	            	continue;
	            }
			 
		}
		return true;
		
    }
	public boolean addold(String APname,int Cno,String Acdate) {
		//给老师用的,录入同个学生考勤情况，不录入出勤状况，默认出勤，后期修改要修改的那些
		//这里增加函数，输入班级,课程号,还有日期，自动插入学生信息，
		//增加函数这里
		StudentDao students=new StudentDao();
		List<Student> is =new ArrayList<Student>();
		is=students.list2(APname);
		for (Student b : is){
			int Sno=b.Sno;
			String Aname=b.Sname;
			 String sql = " insert into AC(Sno,Cno,APname,Aname,Acdate) values(?,?,?,?,?) ";
			 try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) { 
		        	ps.setInt(1,Sno);
		        	ps.setInt(2,Cno);
		            ps.setString(3,APname);
		            ps.setString(4,Aname); 
		            ps.setString(5,Acdate); 
		            ps.execute();  
		            return true;
		            //ResultSet rs = ps.getGeneratedKeys();//如果因为主码自增不插，然后通过这里得到主码赋值给对象对应的属性
		        } catch (SQLException e) {  
		            e.printStackTrace();
		            return false;
		        }
		}
		return false;
    }
	public boolean update(int Sno,int Cno,String Acdate,String Asituation) {
		//给老师用的
		//这里修改函数,修改某个同学某一天某个课程的出勤情况	
			 String sql = "  update AC set Asituation=? where Sno= "+Sno+"and"+" Cno="+Cno+" and"+" Acdate="+"'"+Acdate+"'";
			 try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) { 
		            ps.setString(1,Asituation);
		            ps.execute();  
		            return true;
		            //ResultSet rs = ps.getGeneratedKeys();//如果因为主码自增不插，然后通过这里得到主码赋值给对象对应的属性
		        } catch (SQLException e) {  
		            e.printStackTrace();
		            return false;
		        }
		}
	public boolean delete(int Sno,int Cno,String Acdate) {
		//给老师用的
		//这里删除函数,删除某个同学某一天某个课程的出勤记录	
			 String sql = "  delete from AC  where Sno= "+Sno+"and"+" Cno="+Cno+" and"+" Acdate="+"'"+Acdate+"'";
			 try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) { 
		            ps.execute();  
		            return true;
		            //ResultSet rs = ps.getGeneratedKeys();//如果因为主码自增不插，然后通过这里得到主码赋值给对象对应的属性
		        } catch (SQLException e) {  
		            e.printStackTrace();
		            return false;
		        }
		}
	public List<AC> list1(int Sno) {
        List<AC> acs = new ArrayList<AC>();
  //给学生用的 ,查询学生自己各个科目平时考勤状况     
  //把符合条件的AC数据查询出来，转换为AC对象后，放在一个集合中返回
   //String sql = "select * from hero order by id desc limit ?,? ";	 
        int Cno=0;//课程号
        String APname=null;
        String Cname=null;
        String Aname=null;//名字
        String Acdate=null;//考勤日期
        String Asituation=null;//出勤状况	       	        	
        String sql = "select * from AC_view where 学号="+Sno;
	    try (Connection c = getConnection(); Statement s = c.createStatement();) {
	        	ResultSet rs = s.executeQuery(sql);
	            while (rs.next()) {
	            	/*定义一个类型为Teacher的对象 Teacher ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
	                 * 下面的一样道理*/  
	            	AC ac=new AC() ;
	            	Sno= rs.getInt(1);
	            	Cno = rs.getInt(2);	
	            	APname=rs.getString(3);
	            	Cname = rs.getString(4);
	            	Aname=rs.getString(5);
	            	Acdate = rs.getString(6);
	            	Asituation=rs.getString(7);
	            	ac.Sno=Sno;
	            	ac.Cno=Cno;
	            	ac.Cname=Cname;
	            	ac.APname=APname;
	            	ac.Aname=Aname;
	            	ac.Acdate=Acdate;
	            	ac.Asituation=Asituation;
	            	acs.add(ac);
	            }
	        }catch (SQLException e) {
	            e.printStackTrace();}
	     return acs;//返回系集合，符合条件的系的信息
    }
	
	public List<AC> list2(int Sno,int Cno) {
        List<AC> acs = new ArrayList<AC>();
  //给老师用的 ,查询这个学生这个科目平时考勤状况  （老师只能查对应的任职科目，其他看不了）   
  //把符合条件的AC数据查询出来，转换为AC对象后，放在一个集合中返回
   //String sql = "select * from hero order by id desc limit ?,? ";	
        String APname=null;
        String Cname=null;
        String Aname=null;//名字
        String Acdate=null;//考勤日期
        String Asituation=null;//出勤状况	       	        	
        String sql = "select * from AC_view where 学号="+Sno;
	    try (Connection c = getConnection(); Statement s = c.createStatement();) {
	        	ResultSet rs = s.executeQuery(sql);
	            while (rs.next()) {
	            	/*定义一个类型为Teacher的对象 Teacher ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
	                 * 下面的一样道理*/  
	            	AC ac=new AC() ;
	            	Sno= rs.getInt(1);
	            	Cno = rs.getInt(2);	
	            	APname=rs.getString(3);
	            	Cname = rs.getString(4);
	            	Aname=rs.getString(5);
	            	Acdate = rs.getString(6);
	            	Asituation=rs.getString(7);
	            	ac.Sno=Sno;
	            	ac.Cno=Cno;
	            	ac.Cname=Cname;
	            	ac.APname=APname;
	            	ac.Aname=Aname;
	            	ac.Acdate=Acdate;
	            	ac.Asituation=Asituation;
	            	acs.add(ac);
	            }
	        }catch (SQLException e) {
	            e.printStackTrace();}
	     return acs;//返回系集合，符合条件的系的信息
    }
	public List<AC> list3(String APname) {
        List<AC> acs = new ArrayList<AC>();
  //给老师用的      ，（老师只能查对应的任职科目，其他看不了） 
  //把符合条件的AC数据查询出来，转换为AC对象后，放在一个集合中返回
  //按班级查询,某个课程
   //String sql = "select * from hero order by id desc limit ?,? ";	 
        int Sno=0;//学号
        int Cno = 0;
        String Cname=null;
        String Aname=null;//名字
        String Acdate=null;//考勤日期
        String Asituation=null;//出勤状况	       	        	
        String sql = "select * from AC_view where 班级名="+"'"+APname+"'";
	    try (Connection c = getConnection(); Statement s = c.createStatement();) {
	        	ResultSet rs = s.executeQuery(sql);
	            while (rs.next()) {
	            	/*定义一个类型为Teacher的对象 Teacher ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
	                 * 下面的一样道理*/  
	            	AC ac=new AC() ;
	            	Sno= rs.getInt(1);
	            	Cno = rs.getInt(2);	
	            	APname=rs.getString(3);
	            	Cname = rs.getString(4);
	            	Aname=rs.getString(5);
	            	Acdate = rs.getString(6);
	            	Asituation=rs.getString(7);
	            	ac.Sno=Sno;
	            	ac.Cno=Cno;
	            	ac.Cname=Cname;
	            	ac.APname=APname;
	            	ac.Aname=Aname;
	            	ac.Acdate=Acdate;
	            	ac.Asituation=Asituation;
	            	acs.add(ac);
	            }
	        }catch (SQLException e) {
	            e.printStackTrace();}
	     return acs;//返回系集合，符合条件的系的信息
    }
	public List<AC> list4(String Acdate) {
        List<AC> acs = new ArrayList<AC>();
  //给老师用的，（老师只能查对应的任职科目，其他看不了）       
  //把符合条件的AC数据查询出来，转换为AC对象后，放在一个集合中返回
  //按日期查询,某个科目
   //String sql = "select * from hero order by id desc limit ?,? ";	 
        int Sno=0;//学号
        int Cno = 0;
        String APname=null;//班级
        String Cname=null;//课程名
        String Aname=null;//名字
        String Asituation=null;//出勤状况	       	        	
        String sql = "select * from AC_view where 考勤日期="+"'"+Acdate+"'";
	    try (Connection c = getConnection(); Statement s = c.createStatement();) {
	        	ResultSet rs = s.executeQuery(sql);
	            while (rs.next()) {
	            	/*定义一个类型为Teacher的对象 Teacher ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
	                 * 下面的一样道理*/  
	            	AC ac=new AC() ;
	            	Sno= rs.getInt(1);
	            	Cno = rs.getInt(2);	
	            	APname=rs.getString(3);
	            	Cname = rs.getString(4);
	            	Aname=rs.getString(5);
	            	Acdate = rs.getString(6);
	            	Asituation=rs.getString(7);
	            	ac.Sno=Sno;
	            	ac.Cno=Cno;
	            	ac.Cname=Cname;
	            	ac.APname=APname;
	            	ac.Aname=Aname;
	            	ac.Acdate=Acdate;
	            	ac.Asituation=Asituation;
	            	acs.add(ac);
	            }
	        }catch (SQLException e) {
	            e.printStackTrace();}
	     return acs;//返回系集合，符合条件的系的信息
    }
	public List<AC> list5(int Sno ) {
        List<AC> acs = new ArrayList<AC>();
  //给老师用的  ，（老师只能查对应的任职科目，其他看不了）    
  //把符合条件的AC数据查询出来，转换为AC对象后，放在一个集合中返回
  //按姓名查询
   //String sql = "select * from hero order by id desc limit ?,? ";	 
        int Cno=0;
        String APname=null;
        String Aname = null;
        String Cname=null;
        String Acdate=null;
        String Asituation=null;//出勤状况	       	        	
        String sql = "select * from AC_view where 学号="+Sno;
	    try (Connection c = getConnection(); Statement s = c.createStatement();) {
	        	ResultSet rs = s.executeQuery(sql);
	            while (rs.next()) {
	            	/*定义一个类型为Teacher的对象 Teacher ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
	                 * 下面的一样道理*/  
	            	AC ac=new AC() ;
	            	Sno= rs.getInt(1);
	            	Cno = rs.getInt(2);	
	            	APname=rs.getString(3);
	            	Cname = rs.getString(4);
	            	Aname=rs.getString(5);
	            	Acdate = rs.getString(6);
	            	Asituation=rs.getString(7);
	            	ac.Sno=Sno;
	            	ac.Cno=Cno;
	            	ac.Cname=Cname;
	            	ac.APname=APname;
	            	ac.Aname=Aname;
	            	ac.Acdate=Acdate;
	            	ac.Asituation=Asituation;
	            	acs.add(ac);
	            }
	        }catch (SQLException e) {
	            e.printStackTrace();}
	     return acs;//返回系集合，符合条件的系的信息
    }
	public List<AC> list6(String Cname) {
        List<AC> acs = new ArrayList<AC>();
  //给老师用的  ，（老师只能查对应的任职科目，其他看不了）    
  //把符合条件的AC数据查询出来，转换为AC对象后，放在一个集合中返回
  //按班级查询
   //String sql = "select * from hero order by id desc limit ?,? ";	 
        int Sno=0;//学号
        String APname=null;
        String Aname = null;
        int Cno = 0;
        String Acdate=null;
        String Asituation=null;//出勤状况	       	        	
        String sql = "select * from AC_view where 课程名="+"'"+Cname+"'";
	    try (Connection c = getConnection(); Statement s = c.createStatement();) {
	        	ResultSet rs = s.executeQuery(sql);
	            while (rs.next()) {
	            	/*定义一个类型为Teacher的对象 Teacher ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
	                 * 下面的一样道理*/  
	            	AC ac=new AC() ;
	            	Sno= rs.getInt(1);
	            	Cno = rs.getInt(2);	
	            	APname=rs.getString(3);
	            	Cname = rs.getString(4);
	            	Aname=rs.getString(5);
	            	Acdate = rs.getString(6);
	            	Asituation=rs.getString(7);
	            	ac.Sno=Sno;
	            	ac.Cno=Cno;
	            	ac.Cname=Cname;
	            	ac.APname=APname;
	            	ac.Aname=Aname;
	            	ac.Acdate=Acdate;
	            	ac.Asituation=Asituation;
	            	acs.add(ac);
	            }
	        }catch (SQLException e) {
	            e.printStackTrace();}
	     return acs;//返回系集合，符合条件的系的信息
    }
	public List<AC> list7(String APname, String Cname, String Acdate) {
        List<AC> acs = new ArrayList<AC>();
  //给老师用的  ，（老师只能查对应的任职科目，其他看不了）    
  //把符合条件的AC数据查询出来，转换为AC对象后，放在一个集合中返回
  //按班级查询
   //String sql = "select * from hero order by id desc limit ?,? ";	 
        int Sno=0;//学号
        //String APname=null;
        String Aname = null;
        int Cno = 0;
        //String Acdate=null;
        String Asituation=null;//出勤状况	       	        	
        String sql = "select * from AC_view where 课程名="+"'"+Cname+"'"+" and "+"班级名="+"'"+APname+"'"+" and "+"考勤日期="+"'"+Acdate+"'";
	    try (Connection c = getConnection(); Statement s = c.createStatement();) {
	        	ResultSet rs = s.executeQuery(sql);
	            while (rs.next()) {
	            	/*定义一个类型为Teacher的对象 Teacher ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
	                 * 下面的一样道理*/  
	            	AC ac=new AC() ;
	            	Sno= rs.getInt(1);
	            	Cno = rs.getInt(2);	
	            	APname=rs.getString(3);
	            	Cname = rs.getString(4);
	            	Aname=rs.getString(5);
	            	Acdate = rs.getString(6);
	            	Asituation=rs.getString(7);
	            	ac.Sno=Sno;
	            	ac.Cno=Cno;
	            	ac.Cname=Cname;
	            	ac.APname=APname;
	            	ac.Aname=Aname;
	            	ac.Acdate=Acdate;
	            	ac.Asituation=Asituation;
	            	acs.add(ac);
	            }
	        }catch (SQLException e) {
	            e.printStackTrace();}
	     return acs;//返回系集合，符合条件的系的信息
    }
}
