package attendance.test;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import attendance.bean.Qimo;
import attendance.bean.Xueqi;
import attendance.dao.QimoDao;
import attendance.dao.XueqiDao;

public class XueqiDaoTest {
	public static void main(String[] args) throws ParseException{
		XueqiDao dao= new XueqiDao();
	    List<Xueqi> is =new ArrayList<Xueqi>();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    //ParsePosition pos = new ParsePosition(0);
	    Date date1 = sdf.parse("2020-01-01");
	    Date date2 = sdf.parse("2020-04-10");
	    //System.out.println(date1);
	    //System.out.println(date2);
	    //dao.add(date1, date2,2);
//	    is = dao.getList();
//	    for(Xueqi x:is) {
//	    	String s = new SimpleDateFormat("yyyy").format(x.startdate);
//	    	System.out.println(s);
//	    	System.out.println(x.enddate);
//	    	System.out.println(x.Xueqi);
//	    }
	    dao.delete("2020-01-01", "2020-04-10");
	}
	
}
