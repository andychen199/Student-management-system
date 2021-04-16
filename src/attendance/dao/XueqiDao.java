package attendance.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import attendance.bean.AC;
import attendance.bean.Xueqi;

public class XueqiDao {
	public XueqiDao(){
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
	public List<Xueqi> getList(){
		List<Xueqi> xueqis = new ArrayList<Xueqi>();
		String sql = "select * from Xueqi";
		Date startdate=null;
		Date enddate = null;
		int intxueqi = 0;
	    try (Connection c = getConnection(); Statement s = c.createStatement();) {
	        	ResultSet rs = s.executeQuery(sql);
	            while (rs.next()) {
	            	/*定义一个类型为Teacher的对象 Teacher ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
	                 * 下面的一样道理*/  
	            	Xueqi xueqi=new Xueqi() ;
	            	startdate= rs.getDate(1);
	            	enddate=rs.getDate(2);	
	            	intxueqi=rs.getInt(3);
	            	
	            	xueqi.startdate=startdate;
	            	xueqi.enddate=enddate;
	            	xueqi.Xueqi=intxueqi;
	            	
	            	xueqis.add(xueqi);
	            }
	        }catch (SQLException e) {
	            e.printStackTrace();}
		return xueqis;
		
		
	}
	public boolean add(Date startdate,Date enddate, int intxueqi) {
		//这里增加函数课程号（本来主码可以自增不插，但我这里还是打开自增规则插进去），
		//课程号，课程名 select * from Xueqi where 
        String sql = "insert into Xueqi(startdate,enddate,xueqi) values(?,?,?)";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) { 
        	
        	java.sql.Date startdate1 = new java.sql.Date(startdate.getTime());
        	java.sql.Date enddate1 = new java.sql.Date(enddate.getTime());
        	ps.setDate(1, startdate1);
        	ps.setDate(2, enddate1);  
        	ps.setInt(3, intxueqi);
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
	public boolean delete(String startdate, String enddate) {
		 //删除函数
		 try (Connection c = getConnection(); Statement s = c.createStatement();) {				  
	            String sql = "delete from Xueqi where startdate =" + "'" + startdate + "'" + " and enddate=" + "'" + enddate + "'";
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
	}
