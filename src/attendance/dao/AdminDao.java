package attendance.dao;
//管理员DAO
//给管理员用的
import java.sql.*;
import javax.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//import attendance.bean.Academy;
import attendance.bean.Admin;
public class AdminDao {
	public AdminDao(){
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
		//得到目前表Admin总数有多少条
        int total = 0;
        try (Connection c = getConnection(); Statement s = c.createStatement();) {
          
            String sql = "select count(*) from Admin";
  
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
	public void add(int Ano,String Aname,String Asex,String Azhichen,String Aphone,int Aage,String Acname) {
		//这里增加函数管理号（本来主码可以自增不插，但我这里还是打开自增规则插进去），姓名，性别，职称，电话，工龄,系名,密码（初始为123456）
		//密码在增加函数这里不插，默认，后期再自己改密码
        String sql = "set identity_insert Admin ON insert into Admin(Ano,Aname,Asex,Azhichen,Aphone,Aage,Acname) values(?,?,?,?,?,?,?) set identity_insert Admin OFF";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) { 
        	ps.setInt(1,Ano);
        	ps.setString(2,Aname);
            ps.setString(3,Asex);
            ps.setString(4,Azhichen); 
            ps.setString(5,Aphone); 
            ps.setInt(6,Aage); 
            ps.setString(7,Acname);            
            ps.execute();  
            //ResultSet rs = ps.getGeneratedKeys();//如果因为主码自增不插，然后通过这里得到主码赋值给对象对应的属性
        } catch (SQLException e) {  
            e.printStackTrace();
        }
    }
	public void update(int Ano,String Aname,String Asex,String Azhichen,String Aphone,int Aage,String Acname,String Apassword) {
		//姓名，性别，职称，电话，工龄,系名,密码（初始为123456）
		//不更新主码，主码一般不允许更新，一般允许更新主码的数据库设计都是不合理的		
         String sql = "update Admin set Aname=?,Asex=? ,Azhichen=?,Aphone=?,Aage=?,Acname=?,Apassword=? where Ano ="+Ano; 
	    	 try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql);){	    		
	    		 ps.setString(1, Aname);
	    		 ps.setString(2, Asex);
	    		 ps.setString(3, Azhichen);
	    		 ps.setString(4, Aphone);
	    		 ps.setInt(5, Aage);
	    		 ps.setString(6, Acname);
	    		 ps.setString(7, Apassword);	    		 
	    		 ps.execute();
	    	//	 System.out.println("修改成功");	    		 
	    	 }catch (SQLException e1) {
		            e1.printStackTrace();		     
		      }catch (Exception e2) {// 捕获其他异常
		    	  e2.printStackTrace();// 打印异常产生的过程信息
		      }
	    	}
	 public void delete(int Ano) {
		 //删除函数
		 try (Connection c = getConnection(); Statement s = c.createStatement();) {				  
	            String sql = "delete from Admin where Ano= "+Ano;
	            s.execute(sql);	
	            //System.out.println("删除成功");
	      }catch(SQLException e1) {
	            e1.printStackTrace();
	      }catch(Exception e2) {// 捕获其他异常
	    	  e2.printStackTrace();// 打印异常产生的过程信息
	      }
	    }	
	 public List<Admin> list1(int Ano) {
	        List<Admin> admins = new ArrayList<Admin>();
	  //把符合条件的Admin数据查询出来，转换为Admin对象后，放在一个集合中返回
	   //String sql = "select * from hero order by id desc limit ?,? ";		       	         
	        String Aname=null;//姓名
	        String Asex=null;//性别
	        String Azhichen=null;//职称
	        String Aphone=null;//电话
	        int Aage=0;;//年龄
	        String Acname=null;//任职系名
	        String Apassword=null;//登录密码
	        //按即主码查询，单个
	        //System.out.println("请输入要查询的管理员号:");	        	
	        String sql = "select * from Admin where Ano="+Ano;
		    try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql);
		            while (rs.next()) {
		            	/*定义一个类型为Admin的对象 admin ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
		                 * 下面的一样道理*/  
		            	Admin admin=new Admin() ;
		            	Ano= rs.getInt(1);
		            	Aname = rs.getString("Aname");
		            	Asex = rs.getString(3);
		            	Azhichen=rs.getString(4);
		                Aphone=rs.getString(5);
		    	        Aage=rs.getInt(6);
		    	        Acname=rs.getString(7);
		    	        Apassword=rs.getString(8);
		            	admin.Ano=Ano;
		            	admin.Aname=Aname;
		            	admin.Asex=Asex;
		            	admin.Azhichen=Azhichen;
		            	admin.Aphone=Aphone;
		            	admin.Aage=Aage;
		            	admin.Acname=Acname;
		            	admin.Apassword=Apassword;
		            	admins.add(admin);
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();}
		     return admins;//返回系集合，符合条件的系的信息
	    }
    public List<Admin> list2() {
	 //都查询出来
       List<Admin> admins = new ArrayList<Admin>();
       int Ano=0;//职工号
       String Aname=null;//姓名
       String Asex=null;//性别
       String Azhichen=null;//职称
       String Aphone=null;//电话
       int Aage=0;;//年龄
       String Acname=null;//任职系名
       String Apassword=null;//登录密码
       //按即主码查询，单个
       //System.out.println("请输入要查询的管理员号:");	        	
       String sql = "select * from Admin ";
	    try (Connection c = getConnection(); Statement s = c.createStatement();) {
	        	ResultSet rs = s.executeQuery(sql);
	            while (rs.next()) {
	            	Admin admin=new Admin() ;
	            	Ano= rs.getInt(1);
	            	Aname = rs.getString("Aname");
	            	Asex = rs.getString(3);
	            	Azhichen=rs.getString(4);
	                Aphone=rs.getString(5);
	    	        Aage=rs.getInt(6);
	    	        Acname=rs.getString(7);
	    	        Apassword=rs.getString(8);
	            	admin.Ano=Ano;
	            	admin.Aname=Aname;
	            	admin.Asex=Asex;
	            	admin.Azhichen=Azhichen;
	            	admin.Aphone=Aphone;
	            	admin.Aage=Aage;
	            	admin.Acname=Acname;
	            	admin.Apassword=Apassword;
	            	admins.add(admin);
	            }
	        }catch (SQLException e) {
	            e.printStackTrace();}
	  return admins;//返回系集合，里面都是各个系的信息
	        }	
    public List<Admin> list3(int Ano, String Apassword) {
        List<Admin> admins = new ArrayList<Admin>();
  //把符合条件的Admin数据查询出来，转换为Admin对象后，放在一个集合中返回
        //用于登录
   //String sql = "select * from hero order by id desc limit ?,? ";                   
        String Aname=null;//姓名
        String Asex=null;//性别
        String Azhichen=null;//职称
        String Aphone=null;//电话
        int Aage=0;;//年龄
        String Acname=null;//任职系名
       
        //按即主码查询，单个
        //System.out.println("请输入要查询的管理员号:");          
        String sql = "select * from Admin where Ano="+Ano+" and "+"Apassword="+"'"+Apassword+"'";
     try (Connection c = getConnection(); Statement s = c.createStatement();) {
          ResultSet rs = s.executeQuery(sql);
             while (rs.next()) {
              /*定义一个类型为Admin的对象 admin ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
                  * 下面的一样道理*/  
              Admin admin=new Admin() ;
              Ano= rs.getInt(1);
              Aname = rs.getString("Aname");
              Asex = rs.getString(3);
              Azhichen=rs.getString(4);
                 Aphone=rs.getString(5);
              Aage=rs.getInt(6);
              Acname=rs.getString(7);
              Apassword=rs.getString(8);
              admin.Ano=Ano;
              admin.Aname=Aname;
              admin.Asex=Asex;
              admin.Azhichen=Azhichen;
              admin.Aphone=Aphone;
              admin.Aage=Aage;
              admin.Acname=Acname;
              admin.Apassword=Apassword;
              admins.add(admin);
             }
         }catch (SQLException e) {
             e.printStackTrace();}
      return admins;//返回系集合，符合条件的系的信息
    }
	}


