package attendance.dao;
//管理员DAO
//给管理员用的
import java.sql.*;

//import attendance.bean.Academy;
public class SuperAdminDao {
	public SuperAdminDao(){
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
	
	public boolean changepassword(String pwd) {
		String sql = "update SuperAdmin set pwd=? where id='Admin'";
		try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
			ps.setString(1, pwd);
			ps.execute();
			return true;
		}
		catch (SQLException e) {  
        e.printStackTrace();
        return false;
		}
	}
	public boolean login(String pwd) {
		String sql = "select * from SuperAdmin where id='admin'";
		try (Connection c = getConnection(); Statement s = c.createStatement();) {
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				String sqlpwd = rs.getString(2);
				if(pwd.equals(sqlpwd))
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
}
