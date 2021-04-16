package attendance.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import attendance.bean.Qimo;

public class QimoDao {
	public QimoDao(){
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
      
        String sql = "select count(*) from Qimo";

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
	public boolean add(int Sno,int Cno,Date Qstartdate,Date Qenddate) {
		//给老师用的,录入某个学生期末考勤情况，这里设置以个人为单位而不是班级，若是以班级单位，某个班级插入后假设有新同学来，那她期末考勤记录就插不了了
		//这里增加函数，输入学号,课程号,学年自动插入学生期末考勤信息，
		//增加函数这里
		///StudentDao students=new StudentDao();
		//List<Student> is =new ArrayList<Student>();
		//is=students.list2(Pname);//这个班级的同学都查询出来
	     //for (Student b : is){
			int Qan=0;
			int Qaln=0;
			int Qtn=0;
			int Qlen=0;
			int Qlate=0;
			String sql1 = "select count(*) from AC where Asituation='出勤' and Sno="+Sno+" and "+"Cno="+Cno+" and Acdate>'"+Qstartdate.toString()+"' and Acdate<'"+Qenddate.toString()+"'";
			String sql2 = "select count(*) from AC where Asituation='请假' and Sno="+Sno+" and "+"Cno="+Cno+" and Acdate>'"+Qstartdate.toString()+"' and Acdate<'"+Qenddate.toString()+"'";
			String sql3 = "select count(*) from AC where Asituation='旷课' and Sno="+Sno+" and "+"Cno="+Cno+" and Acdate>'"+Qstartdate.toString()+"' and Acdate<'"+Qenddate.toString()+"'";
			String sql4 = "select count(*) from AC where Asituation='早退' and Sno="+Sno+" and "+"Cno="+Cno+" and Acdate>'"+Qstartdate.toString()+"' and Acdate<'"+Qenddate.toString()+"'";	
			String sql5 = "select count(*) from AC where Asituation='迟到' and Sno="+Sno+" and "+"Cno="+Cno+" and Acdate>'"+Qstartdate.toString()+"' and Acdate<'"+Qenddate.toString()+"'";	
			String sql6 = " insert into Qimo(Sno,Cno,Qstartdate,Qenddate,Qan,Qaln,Qtn,Qlen,Qlate) values(?,?,?,?,?,?,?,?,?) ";
			 try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql6,Statement.RETURN_GENERATED_KEYS);Statement s = c.createStatement();) { 
				    ResultSet rs1 = s.executeQuery(sql1);
				    /*java获取结果集，if(rs!=null)。和while(rs.next())差别 
                      com.microsoft.sqlserver.jdbc.SQLServerException: 结果集没有当前行。  
                                                        原因:结果集rs的位置初始时位于第一条记录的前面，即为0。
                                                          这是ResultSet指向的问题。ResultSet取值后。指针默认指向index为-1的前一个元素,
                                                           即ResultSet中第一个元素的前面，这时指针默认指向是不存在元素的，因此出现错误。必须调用.next()函数才干，对ResultSet进行遍历。*/
				    while (rs1.next()){
				    	Qan=rs1.getInt(1);
				    }
				    ResultSet rs2 = s.executeQuery(sql2);
				    while (rs2.next()){
				    	Qaln=rs2.getInt(1);
				    }
				    ResultSet rs3 = s.executeQuery(sql3);
				    while (rs3.next()){
				    	Qtn=rs3.getInt(1);
				    }    
				    ResultSet rs4 = s.executeQuery(sql4);
				    while (rs4.next()){
				    	Qlen=rs4.getInt(1);
				    }
				    ResultSet rs5 = s.executeQuery(sql5);
				    while (rs5.next()){
				    	Qlate=rs5.getInt(1);
				    }
				    ps.setInt(1,Sno);
		        	ps.setInt(2,Cno);
		            ps.setDate(3,Qstartdate);
		            ps.setDate(4,Qenddate);		            
		            ps.setInt(5,Qan); 
		            ps.setInt(6,Qaln); 
		            ps.setInt(7,Qtn); 
		            ps.setInt(8,Qlen);
		            ps.setInt(9,Qlate); 
		            ps.execute();  
		            return true;
		            //ResultSet rs = ps.getGeneratedKeys();//如果因为主码自增不插，然后通过这里得到主码赋值给对象对应的属性
		        } catch (SQLException e) {  
		            try {
		            	List<Qimo> is = new ArrayList<Qimo>();
		            	is = list5(Qstartdate, Qenddate, Cno);
		            	for(Qimo q:is) {
		            		update(q.Sno, q.Cno, q.Qstartdate, q.Qenddate, q.Qan, q.Qaln, q.Qtn, q.Qlen, q.Qlate);
		            	}
		            	return true;
		            	
		            }catch(Exception e1) {
		            	e1.printStackTrace();
		            	return false;
		            }
		        }
			
			
		}	
	public void update(int Sno,int Cno,Date Qstartdate,Date Qenddate,int Qan,int Qaln,int Qtn,int Qlen,int Qlate) {
		//给老师用的
		//这里修改函数,修改某个同学某个课程的期末出勤情况	
			 String sql = "  update Qimo set Qan=?,Qaln=?,Qtn=?,Qlen=?,Qlate=? where Sno= "+Sno+"and"+" Cno="+Cno+" and"+" Qstartdate='"+Qstartdate+"' and "+"Qenddate='"+Qenddate+"'";
			 try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) { 
		            ps.setInt(1,Qan);
		            ps.setInt(2,Qaln);
		            ps.setInt(3,Qtn);
		            ps.setInt(4,Qlen);
		            ps.setInt(5,Qlate);
		            ps.execute();  
		            //ResultSet rs = ps.getGeneratedKeys();//如果因为主码自增不插，然后通过这里得到主码赋值给对象对应的属性
		        } catch (SQLException e) {  
		            e.printStackTrace();
		        }
		}
	public void delete(int Sno,int Cno,Date Qstartdate,Date Qenddate) {
		//给老师用的
		//这里删除函数,删除某个同学某个课程的期末出勤情况	
			 String sql = "  delete Qimo  where Sno= "+Sno+"and"+" Cno="+Cno+" and"+" Qstartdate='"+Qstartdate+"' and "+"Qenddate='"+Qenddate+"'";
			 try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) { 
		            ps.execute();  
		            //ResultSet rs = ps.getGeneratedKeys();//如果因为主码自增不插，然后通过这里得到主码赋值给对象对应的属性
		        } catch (SQLException e) {  
		            e.printStackTrace();
		        }
		}
	public List<Qimo> list1(int Sno, Date Qstartdate, Date Qenddate, String Cname) {
        List<Qimo> qimos = new ArrayList<Qimo>();
  //给学生 ,查询学生自己各个科目期末考勤状况     
  //把符合条件的AC数据查询出来，转换为AC对象后，放在一个集合中返回
   //String sql = "select * from hero order by id desc limit ?,? ";	 
        String Pcname=null;//系名	
        String Pname=null;//班级
        String Sname=null;//姓名
        int Cno=0;//课程号
        //String Cname=null;//课程名
        //Date Qstartdate=new java.sql.Date(System.currentTimeMillis());//开始学年
        //Date Qenddate=new java.sql.Date(System.currentTimeMillis());//结束学年
        int Qan=0;//出勤次数
        int Qaln=0;//请假次数
        int Qtn=0;//旷课次数
        int Qlen=0;//早退次数   
        int Qlate=0;//迟到次数   
        String sql = "select * from Qimo_view where 学号="+Sno+" and 开始学年='"+Qstartdate+"' and 结束学年='"+Qenddate+"' and 课程名='"+Cname+"'";
	    try (Connection c = getConnection(); Statement s = c.createStatement();) {
	        	ResultSet rs = s.executeQuery(sql);
	            while (rs.next()) { 
	            	Qimo qimo=new Qimo() ;
	            	Pcname=rs.getString(1);//系名	
	                Pname=rs.getString(2);//班级
	                Sno=rs.getInt(3);
	                Sname=rs.getString(4);//姓名
	                Cno=rs.getInt(5);//课程号
	                Cname=rs.getString(6);//课程名
	                Qstartdate=rs.getDate(7);
	                Qenddate=rs.getDate(8);		
	                Qan=rs.getInt(9);//出勤次数
	                Qaln=rs.getInt(10);//请假次数
	                Qtn=rs.getInt(11);//旷课次数
	               Qlen=rs.getInt(12);//早退次数      
	               Qlate=rs.getInt(13);
	               qimo.Pcname=Pcname;
	               qimo.Pname=Pname;
	               qimo.Sno= Sno;
	               qimo.Sname=Sname;
	               qimo.Cno=Cno;
	               qimo.Cname=Cname;
	               qimo.Qstartdate=Qstartdate;
	               qimo.Qenddate=Qenddate;
	               qimo.Qan=Qan;
	               qimo.Qaln=Qaln;
	               qimo. Qtn= Qtn;
	               qimo.Qlen=Qlen;
	               qimo.Qlate=Qlate;
	            	qimos.add(qimo);
	            }
	        }catch (SQLException e) {
	            e.printStackTrace();}
	     return qimos;//返回系集合，符合条件的系的信息
    }
	public List<Qimo> list2(int Sno,int Cno) {
        List<Qimo> qimos = new ArrayList<Qimo>();
  //给老师 ,查询某个学生自己某个科目期末考勤状况    （老师只能查对应的任职科目，其他看不了） 
  //把符合条件的AC数据查询出来，转换为AC对象后，放在一个集合中返回
   //String sql = "select * from hero order by id desc limit ?,? ";	 
        String Pcname=null;//系名	
        String Pname=null;//班级
        String Sname=null;//姓名
        String Cname=null;//课程名
        Date Qstartdate=new java.sql.Date(System.currentTimeMillis());//开始学年
        Date Qenddate=new java.sql.Date(System.currentTimeMillis());//结束学年
        int Qan=0;//出勤次数
        int Qaln=0;//请假次数
        int Qtn=0;//旷课次数
        int Qlen=0;//早退次数  
        int Qlate=0;//迟到次数   
        String sql = "select * from Qimo_view where 学号="+Sno+" and "+"课程号="+Cno;
	    try (Connection c = getConnection(); Statement s = c.createStatement();) {
	        	ResultSet rs = s.executeQuery(sql);
	            while (rs.next()) {
	            	/*定义一个类型为Teacher的对象 Teacher ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
	                 * 下面的一样道理*/  
	            	Qimo qimo=new Qimo() ;
	            	Pcname=rs.getString(1);//系名	
	                Pname=rs.getString(2);//班级
	                Sno=rs.getInt(3);
	                Sname=rs.getString(4);//姓名
	                Cno=rs.getInt(5);//课程号
	                Cname=rs.getString(6);//课程名
	                Qstartdate=rs.getDate(7);
	                Qenddate=rs.getDate(8);		
	                Qan=rs.getInt(10);//出勤次数
	                Qaln=rs.getInt(11);//请假次数
	                Qtn=rs.getInt(12);//旷课次数
	               Qlen=rs.getInt(13);//早退次数      
	               Qlate=rs.getInt(14);
	               qimo.Pcname=Pcname;
	               qimo.Pname=Pname;
	               qimo.Sno= Sno;
	               qimo.Sname=Sname;
	               qimo.Cno=Cno;
	               qimo.Cname=Cname;
	               qimo.Qstartdate=Qstartdate;
		            qimo.Qenddate=Qenddate;
	               qimo.Qan=Qan;
	               qimo.Qaln=Qaln;
	               qimo. Qtn= Qtn;
	               qimo.Qlen=Qlen;
	               qimo.Qlate=Qlate;
	            	qimos.add(qimo);
	            }
	        }catch (SQLException e) {
	            e.printStackTrace();}
	     return qimos;//返回系集合，符合条件的系的信息
    }
	public List<Qimo> list3(String Pname,int Cno) {
        List<Qimo> qimos = new ArrayList<Qimo>();
  //老师用的 ,按班级查询，查询某个班某个科目期末考勤状况 （老师只能查对应的任职科目，其他看不了）    
  //把符合条件的AC数据查询出来，转换为AC对象后，放在一个集合中返回
   //String sql = "select * from hero order by id desc limit ?,? ";	 
        String Pcname=null;//系名	
        int Sno=0;//学号
        String Sname=null;//姓名
        String Cname=null;//课程名
        Date Qstartdate=new java.sql.Date(System.currentTimeMillis());//开始学年
        Date Qenddate=new java.sql.Date(System.currentTimeMillis());//结束学年
        int Qan=0;//出勤次数
        int Qaln=0;//请假次数
        int Qtn=0;//旷课次数
        int Qlen=0;//早退次数       
        int Qlate=0;//迟到次数 
        String sql = "select * from Qimo_view where 班级="+"'"+Pname+"'"+" and "+"课程号="+Cno;
	    try (Connection c = getConnection(); Statement s = c.createStatement();) {
	        	ResultSet rs = s.executeQuery(sql);
	            while (rs.next()) {
	            	/*定义一个类型为Teacher的对象 Teacher ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
	                 * 下面的一样道理*/  
	            	Qimo qimo=new Qimo() ;
	            	Pcname=rs.getString(1);//系名	
	                Pname=rs.getString(2);//班级
	                Sno=rs.getInt(3);
	                Sname=rs.getString(4);//姓名
	                Cno=rs.getInt(5);//课程号
	                Cname=rs.getString(6);//课程名
	                Qstartdate=rs.getDate(7);
	                Qenddate=rs.getDate(8);		
	                Qan=rs.getInt(10);//出勤次数
	                Qaln=rs.getInt(11);//请假次数
	                Qtn=rs.getInt(12);//旷课次数
	               Qlen=rs.getInt(13);//早退次数      
	               Qlate=rs.getInt(14);
	               qimo.Pcname=Pcname;
	               qimo.Pname=Pname;
	               qimo.Sno= Sno;
	               qimo.Sname=Sname;
	               qimo.Cno=Cno;
	               qimo.Cname=Cname;
	               qimo.Qstartdate=Qstartdate;
		            qimo.Qenddate=Qenddate;
	               qimo.Qan=Qan;
	               qimo.Qaln=Qaln;
	               qimo. Qtn= Qtn;
	               qimo.Qlen=Qlen;
	               qimo.Qlate=Qlate;
	            	qimos.add(qimo);
	            }
	        }catch (SQLException e) {
	            e.printStackTrace();}
	     return qimos;//返回系集合，符合条件的系的信息
    }
	public List<Qimo> list4(String Sname,int Cno) {
        List<Qimo> qimos = new ArrayList<Qimo>();
  //老师用的 ,按姓名查询，查询相同姓名同学某个科目期末考勤状况     
  //把符合条件的AC数据查询出来，转换为AC对象后，放在一个集合中返回
   //String sql = "select * from hero order by id desc limit ?,? ";	 
        String Pcname=null;//系名	
        String Pname=null;//班级
        int Sno=0;//学号
        String Cname=null;//课程名
        Date Qstartdate=new java.sql.Date(System.currentTimeMillis());//开始学年
        Date Qenddate=new java.sql.Date(System.currentTimeMillis());//结束学年
        int Qan=0;//出勤次数
        int Qaln=0;//请假次数
        int Qtn=0;//旷课次数
        int Qlen=0;//早退次数       
        int Qlate=0;//迟到次数
        String sql = "select * from Qimo_view where 姓名="+"'"+Sname+"'"+" and "+"课程号="+Cno;
	    try (Connection c = getConnection(); Statement s = c.createStatement();) {
	        	ResultSet rs = s.executeQuery(sql);
	            while (rs.next()) {
	            	/*定义一个类型为Teacher的对象 Teacher ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
	                 * 下面的一样道理*/  
	            	Qimo qimo=new Qimo() ;
	            	Pcname=rs.getString(1);//系名	
	                Pname=rs.getString(2);//班级
	                Sno=rs.getInt(3);
	                Sname=rs.getString(4);//姓名
	                Cno=rs.getInt(5);//课程号
	                Cname=rs.getString(6);//课程名
	                Qstartdate=rs.getDate(7);
	                Qenddate=rs.getDate(8);		
	                Qan=rs.getInt(10);//出勤次数
	                Qaln=rs.getInt(11);//请假次数
	                Qtn=rs.getInt(12);//旷课次数
	               Qlen=rs.getInt(13);//早退次数      
	               Qlate=rs.getInt(14);
	               qimo.Pcname=Pcname;
	               qimo.Pname=Pname;
	               qimo.Sno= Sno;
	               qimo.Sname=Sname;
	               qimo.Cno=Cno;
	               qimo.Cname=Cname;
	               qimo.Qstartdate=Qstartdate;
		            qimo.Qenddate=Qenddate;
	               qimo.Qan=Qan;
	               qimo.Qaln=Qaln;
	               qimo. Qtn= Qtn;
	               qimo.Qlen=Qlen;
	               qimo.Qlate=Qlate;
	            	qimos.add(qimo);
	            }
	        }catch (SQLException e) {
	            e.printStackTrace();}
	     return qimos;//返回系集合，符合条件的系的信息
    }
	public List<Qimo> list5(Date Qstartdate,Date Qenddate,int Cno) {
        List<Qimo> qimos = new ArrayList<Qimo>();
  //老师用的 ,按学年查询，查询相同学年某个科目期末考勤状况 （老师只能查对应的任职科目，其他看不了）           
  //把符合条件的AC数据查询出来，转换为AC对象后，放在一个集合中返回
   //String sql = "select * from hero order by id desc limit ?,? ";	 
        String Pcname=null;//系名	
        String Pname=null;//班级
        int Sno=0;//学号
        String Sname=null;//姓名
        String Cname=null;//课程名
        int Qan=0;//出勤次数
        int Qaln=0;//请假次数
        int Qtn=0;//旷课次数
        int Qlen=0;//早退次数   
        int Qlate=0;//迟到次数
        String sql = "select * from Qimo_view where 开始学年='"+Qstartdate+"' and 结束学年='"+Qenddate+"' and "+"课程号="+Cno;
	    try (Connection c = getConnection(); Statement s = c.createStatement();) {
	        	ResultSet rs = s.executeQuery(sql);
	            while (rs.next()) {
	            	/*定义一个类型为Teacher的对象 Teacher ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
	                 * 下面的一样道理*/  
	            	Qimo qimo=new Qimo() ;
	            	Pcname=rs.getString(1);//系名	
	                Pname=rs.getString(2);//班级
	                Sno=rs.getInt(3);
	                Sname=rs.getString(4);//姓名
	                Cno=rs.getInt(5);//课程号
	                Cname=rs.getString(6);//课程名
	                Qstartdate=rs.getDate(7);
	                Qenddate=rs.getDate(8);		
	                Qan=rs.getInt(9);//出勤次数
	                Qaln=rs.getInt(10);//请假次数
	                Qtn=rs.getInt(11);//旷课次数
	               Qlen=rs.getInt(12);//早退次数      
	               Qlate=rs.getInt(13);
	               qimo.Pcname=Pcname;
	               qimo.Pname=Pname;
	               qimo.Sno= Sno;
	               qimo.Sname=Sname;
	               qimo.Cno=Cno;
	               qimo.Cname=Cname;
	               qimo.Qstartdate=Qstartdate;
		            qimo.Qenddate=Qenddate;
	               qimo.Qan=Qan;
	               qimo.Qaln=Qaln;
	               qimo. Qtn= Qtn;
	               qimo.Qlen=Qlen;
	               qimo.Qlate=Qlate;
	            	qimos.add(qimo);
	            }
	        }catch (SQLException e) {
	            e.printStackTrace();}
	     return qimos;//返回系集合，符合条件的系的信息
    }
	public List<Qimo> list6(int Tno) {
        List<Qimo> qimos = new ArrayList<Qimo>();
  //老师用的 ,按老师教工号查询，查询老师任职的课程所有时期记录           
  //把符合条件的AC数据查询出来，转换为AC对象后，放在一个集合中返回
   //String sql = "select * from hero order by id desc limit ?,? ";	 
        String Pcname=null;//系名	
        String Pname=null;//班级
        int Sno=0;//学号
        String Sname=null;//姓名
        String Cname=null;//课程名
        int Cno = 0;
        Date Qstartdate = null;
        Date Qenddate = null;
        int Qan=0;//出勤次数
        int Qaln=0;//请假次数
        int Qtn=0;//旷课次数
        int Qlen=0;//早退次数   
        int Qlate=0;//迟到次数
        String sql = "select * from Qimo_view where 课程号 in(select Cno from Teach where Tno="+Tno+")";
	    try (Connection c = getConnection(); Statement s = c.createStatement();) {
	        	ResultSet rs = s.executeQuery(sql);
	            while (rs.next()) {
	            	/*定义一个类型为Teacher的对象 Teacher ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
	                 * 下面的一样道理*/  
	            	Qimo qimo=new Qimo() ;
	            	Pcname=rs.getString(1);//系名	
	                Pname=rs.getString(2);//班级
	                Sno=rs.getInt(3);
	                Sname=rs.getString(4);//姓名
	                Cno=rs.getInt(5);//课程号
	                Cname=rs.getString(6);//课程名
	                Qstartdate=rs.getDate(7);
	                Qenddate=rs.getDate(8);		
	                Qan=rs.getInt(9);//出勤次数
	                Qaln=rs.getInt(10);//请假次数
	                Qtn=rs.getInt(11);//旷课次数
	               Qlen=rs.getInt(12);//早退次数      
	               Qlate=rs.getInt(13);
	               qimo.Pcname=Pcname;
	               qimo.Pname=Pname;
	               qimo.Sno= Sno;
	               qimo.Sname=Sname;
	               qimo.Cno=Cno;
	               qimo.Cname=Cname;
	               qimo.Qstartdate=Qstartdate;
		            qimo.Qenddate=Qenddate;
	               qimo.Qan=Qan;
	               qimo.Qaln=Qaln;
	               qimo. Qtn= Qtn;
	               qimo.Qlen=Qlen;
	               qimo.Qlate=Qlate;
	            	qimos.add(qimo);
	            }
	        }catch (SQLException e) {
	            e.printStackTrace();}
	     return qimos;//返回系集合，符合条件的系的信息
    }
	public List<Qimo> list7(Date Qstartdate,Date Qenddate, int Tno) {
        List<Qimo> qimos = new ArrayList<Qimo>();
  //老师用的 ,按老师教工号查询，查询老师任职的课程所有时期记录           
  //把符合条件的AC数据查询出来，转换为AC对象后，放在一个集合中返回
   //String sql = "select * from hero order by id desc limit ?,? ";	 
        String Pcname=null;//系名	
        String Pname=null;//班级
        int Sno=0;//学号
        String Sname=null;//姓名
        String Cname=null;//课程名
        int Cno = 0;
        
        int Qan=0;//出勤次数
        int Qaln=0;//请假次数
        int Qtn=0;//旷课次数
        int Qlen=0;//早退次数   
        int Qlate=0;//迟到次数
        String sql = "select * from Qimo_view where 开始学年='" + Qstartdate + "' and 结束学年='"+ Qenddate +"' and 课程号 in(select Cno from Teach where Tno="+Tno+")";
	    try (Connection c = getConnection(); Statement s = c.createStatement();) {
	        	ResultSet rs = s.executeQuery(sql);
	            while (rs.next()) {
	            	/*定义一个类型为Teacher的对象 Teacher ，在while里查询出来的属性赋值给它，接着把对象该给集合，然后返回集合
	                 * 下面的一样道理*/  
	            	Qimo qimo=new Qimo() ;
	            	Pcname=rs.getString(1);//系名	
	                Pname=rs.getString(2);//班级
	                Sno=rs.getInt(3);
	                Sname=rs.getString(4);//姓名
	                Cno=rs.getInt(5);//课程号
	                Cname=rs.getString(6);//课程名
	                Qstartdate=rs.getDate(7);
	                Qenddate=rs.getDate(8);		
	                Qan=rs.getInt(9);//出勤次数
	                Qaln=rs.getInt(10);//请假次数
	                Qtn=rs.getInt(11);//旷课次数
	               Qlen=rs.getInt(12);//早退次数      
	               Qlate=rs.getInt(13);
	               qimo.Pcname=Pcname;
	               qimo.Pname=Pname;
	               qimo.Sno= Sno;
	               qimo.Sname=Sname;
	               qimo.Cno=Cno;
	               qimo.Cname=Cname;
	               qimo.Qstartdate=Qstartdate;
		            qimo.Qenddate=Qenddate;
	               qimo.Qan=Qan;
	               qimo.Qaln=Qaln;
	               qimo. Qtn= Qtn;
	               qimo.Qlen=Qlen;
	               qimo.Qlate=Qlate;
	            	qimos.add(qimo);
	            }
	        }catch (SQLException e) {
	            e.printStackTrace();}
	     return qimos;//返回系集合，符合条件的系的信息
    }
}
