package attendance.test;

import java.util.ArrayList;
import java.util.List;

import attendance.bean.AC;
import attendance.bean.Afl;
import attendance.dao.ACDao;
import attendance.dao.AflDao;

public class AflDaoTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AflDao dao= new AflDao();
	    List<Afl> is =new ArrayList<Afl>();
	    dao.add(4, 1, "计算机181", "陈世恩", "2020-04-14", "2020-04-15", "2020-04-16", "身体不适，需要请假");
	}

}
