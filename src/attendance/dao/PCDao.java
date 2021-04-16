package attendance.dao;
//班级DAO
//给管理员用的
import java.sql.*;
import javax.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import attendance.bean.PC;
public class PCDao {
	public PCDao(){
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
		//得到目前表PC总数有多少条
        int total = 0;
        try (Connection c = getConnection(); Statement s = c.createStatement();) {
          
            String sql = "select count(*) from PC";
  
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
	public boolean add(int Pno,String Pname,int Pnum,String Pcname) {
		//这里增加函数班级号（本来主码可以自增不插，但我这里还是打开自增规则插进去），
		//班级号，班级名，总人数，系名
        String sql = "set identity_insert PC ON insert into PC(Pno,Pname,Pnum,Pcname) values(?,?,?,?) set identity_insert PC OFF";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) { 
        	ps.setInt(1,Pno);
        	ps.setString(2,Pname);
            ps.setInt(3,Pnum);
            ps.setString(4,Pcname);            
            ps.execute();  
            return true;
            
            //ResultSet rs = ps.getGeneratedKeys();//如果因为主码自增不插，然后通过这里得到主码赋值给对象对应的属性
        } catch (SQLException e) {  
            //e.printStackTrace();
        	System.out.println(e);
            return false;
        }
    }
	public boolean update(int Pno,String Pname,int Pnum,String Pcname) {
		//班级号，班级名，总人数，系名
		//不更新主码，主码一般不允许更新，一般允许更新主码的数据库设计都是不合理的		
         String sql = "update PC set Pname=?,Pnum=? ,Pcname=? where Pno ="+Pno; 
	    	 try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql);){	    		
	    		 ps.setString(1, Pname);
	    		 ps.setInt(2, Pnum);
	    		 ps.setString(3, Pcname); 		 	    		 
	    		 ps.execute();
	    		 return true;
	    	//	 System.out.println("修改成功");	    		 
	    	 }catch (SQLException e1) {
		            e1.printStackTrace();	
		            return false;
		      }catch (Exception e2) {// 捕获其他异常
		    	  e2.printStackTrace();// 打印异常产生的过程信息
		    	  return false;
		      }
	    	}
	 public boolean delete(int Pno) {
		 //删除函数
		 try (Connection c = getConnection(); Statement s = c.createStatement();) {				  
	            String sql = "delete from PC where Pno ="+Pno;
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
	 public int findPno(String Pname) {
	        
	  //把符合条件的PC数据查询出来，转换为PC对象后，放在一个集合中返回
	   //String sql = "select * from hero order by id desc limit ?,? ";		       	         
	        //String Pname=null;//班级名
	        int Pno=0;
	        //String Pcname=null;//系名
	        //按即主码查询，单个
	        //System.out.println("请输入要查询班级号:");	        	
	        String sql = "select * from PC where Pname ="+"'"+Pname+"'";
		    try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql);
		            while (rs.next()) {
		            	/*定义一个类型为PC的对象pc ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
		                 * 下面的一样道理*/  
		            	
		            	Pno= rs.getInt(1);
		            			        
		            	
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();}
		     return Pno;//返回系集合，符合条件的系的信息
	    }
	 public List<PC> list1(int Pno) {
	        List<PC> pcs = new ArrayList<PC>();
	  //把符合条件的PC数据查询出来，转换为PC对象后，放在一个集合中返回
	   //String sql = "select * from hero order by id desc limit ?,? ";		       	         
	        String Pname=null;//班级名
	        int Pnum=0;//总人数
	        String Pcname=null;//系名
	        //按即主码查询，单个
	        //System.out.println("请输入要查询班级号:");	        	
	        String sql = "select * from PC where Pno ="+Pno;
		    try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql);
		            while (rs.next()) {
		            	/*定义一个类型为PC的对象pc ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
		                 * 下面的一样道理*/  
		            	PC pc=new PC() ;
		            	Pno= rs.getInt(1);
		            	Pname = rs.getString("Pname");
		            	Pnum = rs.getInt(3);
		            	Pcname=rs.getString(4);		        
		            	pc.Pno=Pno;
		            	pc.Pname=Pname;
		            	pc.Pnum=Pnum;
		            	pc.Pcname=Pcname; 
		            	pcs.add(pc);
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();}
		     return pcs;//返回系集合，符合条件的系的信息
	    }
	 public List<PC> list2() {
		 //都查询出来
		 List<PC> pcs = new ArrayList<PC>();
	       int Pno=0;//班级号
	       String Pname=null;//班级名
	        int Pnum=0;//总人数
	        String Pcname=null;//系名     	
	       String sql = "select * from PC ";
		    try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql);
		            while (rs.next()) {
		            	PC pc=new PC() ;
		            	Pno= rs.getInt(1);
		            	Pname = rs.getString("Pname");
		            	Pnum = rs.getInt(3);
		            	Pcname=rs.getString(4);		        
		            	pc.Pno=Pno;
		            	pc.Pname=Pname;
		            	pc.Pnum=Pnum;
		            	pc.Pcname=Pcname; 
		            	pcs.add(pc);
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();}
		  return pcs;//返回系集合，里面都是各个系的信息
		        }	 
	 public List<PC> list3(String Pcname) {
	        List<PC> pcs = new ArrayList<PC>();
	  //把符合条件的PC数据查询出来，转换为PC对象后，放在一个集合中返回
	   //String sql = "select * from hero order by id desc limit ?,? ";		       	         
	        String Pname=null;//班级名
	        int Pnum=0;//总人数
	        int Pno=0;//班级号
	        //按即主码查询，单个
	        //System.out.println("请输入要查询班级号:");	        	
	        String sql = "select * from PC where Pcname ="+"'"+Pcname+"'";
		    try (Connection c = getConnection(); Statement s = c.createStatement();) {
		        	ResultSet rs = s.executeQuery(sql);
		            while (rs.next()) {
		            	/*定义一个类型为PC的对象pc ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
		                 * 下面的一样道理*/  
		            	PC pc=new PC() ;
		            	Pno= rs.getInt(1);
		            	Pname = rs.getString("Pname");
		            	Pnum = rs.getInt(3);
		            	Pcname=rs.getString(4);		        
		            	pc.Pno=Pno;
		            	pc.Pname=Pname;
		            	pc.Pnum=Pnum;
		            	pc.Pcname=Pcname; 
		            	pcs.add(pc);
		            }
		        }catch (SQLException e) {
		            e.printStackTrace();}
		     return pcs;//返回系集合，符合条件的系的信息
	    }
}
