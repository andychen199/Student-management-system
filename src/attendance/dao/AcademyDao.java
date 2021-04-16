package attendance.dao;
//系Dao
//给管理员用1的
import java.sql.*;
import javax.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import attendance.bean.Academy;
public class AcademyDao {
	public AcademyDao(){
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
		//得到目前表Academy总数有多少条
        int total = 0;
        try (Connection c = getConnection(); Statement s = c.createStatement();) {
          
            String sql = "select count(*) from Academy";
  
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
	 public boolean add(String Acname,String Acchairman,String Acaddress) {
		  //增加函数,Acname系名（主码）,Acchairman系主任,Acaddress系的地址
	        String sql = "insert into Academy(Acname,Acchairman,Acaddress) values(?,?,?)";
	        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {
	  
	            ps.setString(1, Acname);
	            ps.setString(2, Acchairman);
	            ps.setString(3, Acaddress); 
	            ps.execute();
	            return true;
	        } catch (SQLException e) {
	  
	            e.printStackTrace();
	            return false;
	        }
	    }
	 public boolean update(String Acname,String Acchairman,String Acaddress) {
		//不更新主码，主码一般不允许更新，一般允许更新主码的数据库设计都是不合理的						    	
	    	 String sql3 = "update Academy set Acchairman= ?, Acaddress=? where Acname ="+"'"+Acname+"'"; 
	    	 try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql3);){	    		
	    		 ps.setString(1, Acchairman);
	    		 ps.setString(2, Acaddress);
	    		 ps.execute();
	    		 return true;
	    	
	    	// System.out.println("修改成功");	    		 
	    	 }catch (SQLException e1) {
		            e1.printStackTrace();	
		            return false;
		     }catch (Exception e2) {// 捕获其他异常
		    	  e2.printStackTrace();// 打印异常产生的过程信息
		    	  return false;
		     }    	 
	     }	 	  	    
	 public boolean delete(String Acname) {
		 //删除函数
			 try (Connection c = getConnection(); Statement s = c.createStatement();) {				  
		            String sql3 = "delete from Academy where Acname= " +"'"+Acname+"'";
		            s.execute(sql3);	
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
	
  public List<Academy> list1(String Acname) {
	        List<Academy> academys = new ArrayList<Academy>();
	  //把符合条件的Academy数据查询出来，转换为Academy对象后，放在一个集合中返回
	   //String sql = "select * from hero order by id desc limit ?,? ";	
	        String Acchairman=null;
	        String Acaddress=null;
	        	//按系名即主码查询，单个
	        	//System.out.println("请输入要查询的系的名字");	        	
	        	String sql1 = "select * from Academy where Acname="+"'"+Acname+"'";
		        try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql1);
		            while (rs.next()) {
		            /*定义一个类型为Academy的对象 academy ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
		             * 下面的一样道理*/
		            	Academy academy = new Academy();
		                Acname = rs.getString(1);
		                Acchairman = rs.getString("Acchairman");
		                Acaddress = rs.getString(3);
		                academy.Acname = Acname;
		                academy.Acchairman = Acchairman;
		                academy.Acaddress = Acaddress;
		                academys.add(academy);
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();}
		        return academys;//返回系集合，符合条件的系的信息
	    }
  public List<Academy> list2() {
	//都查询出来
      List<Academy> academys = new ArrayList<Academy>();
      String Acname=null;
      String Acchairman=null;
      String Acaddress=null;	  
	  String sql2 = "select * from Academy ";
	  try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql2);
		            while (rs.next()) {
		            	Academy academy = new Academy();
		                Acname = rs.getString(1);
		                Acchairman = rs.getString("Acchairman");
		                Acaddress = rs.getString(3);
		                academy.Acname = Acname;
		                academy.Acchairman = Acchairman;
		                academy.Acaddress = Acaddress;
		                academys.add(academy);
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();
		        }
	  return academys;//返回系集合，里面都是各个系的信息
	        }	    
  }

