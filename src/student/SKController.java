package student;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import attendance.bean.Qimo;
import attendance.bean.SC;
import attendance.bean.Xueqi;
import attendance.dao.QimoDao;
import attendance.dao.SCDao;
import attendance.dao.XueqiDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SKController {
	private final String pattern = "yyyy-MM-dd";
	int count1 = 0;
	String[][] storage = null;
    int i = 0;
	@FXML private Label chuqin;
	@FXML private Label qingjia;
	@FXML private Label kuangke;
	@FXML private Label zaotui;
	@FXML private Label chidao;
	@FXML private Button confirm;
	@FXML private ComboBox term;
	@FXML private ComboBox coursename;
	@FXML
	protected void Confirm(ActionEvent event) throws IOException, ParseException {
		try {
			String term1 = term.getSelectionModel().getSelectedItem().toString();
		}catch(Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("提示");
			alert.setHeaderText(null);
			alert.setContentText("未选择学期！");
			
			alert.showAndWait();
			return;
		}
		try {
			String cour = coursename.getSelectionModel().getSelectedItem().toString();
		}catch(Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("提示");
			alert.setHeaderText(null);
			alert.setContentText("未选择课程！");
			
			alert.showAndWait();
			return;
		}
		QimoDao qimodao = new QimoDao();
		XueqiDao xueqi = new XueqiDao();
		List<Qimo> qimois = new ArrayList<Qimo>();
		int index = term.getSelectionModel().getSelectedIndex();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	java.util.Date startd = format.parse(xueqi.getList().get(index).startdate.toString());
    	java.util.Date endd = format.parse(xueqi.getList().get(index).enddate.toString());
    	java.sql.Date startd1 = new java.sql.Date(startd.getTime());
    	java.sql.Date endd1 = new java.sql.Date(endd.getTime());
		qimois = qimodao.list1(StudIdentityTrans.getCno(), startd1, endd1, coursename.getValue().toString());
		int count=0;
		for(Qimo q:qimois) {
			chuqin.setText(String.valueOf(q.Qan));
			qingjia.setText(String.valueOf(q.Qaln));
			kuangke.setText(String.valueOf(q.Qtn));
			zaotui.setText(String.valueOf(q.Qlen));
			chidao.setText(String.valueOf(q.Qlate));
			count=1;
		}
		if(count==0) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("提示");
			alert.setHeaderText(null);
			alert.setContentText("未搜索到考勤记录，请等待老师导入...");
			chuqin.setText("null");
			qingjia.setText("null");
			kuangke.setText("null");
			zaotui.setText("null");
			chidao.setText("null");
			alert.showAndWait();
			return;
		}
	}
	public void InitUI() {
		XueqiDao xueqi = new XueqiDao();
        List<Xueqi> is = new ArrayList<Xueqi>();
        is = xueqi.getList();
        term.getItems().clear();
        for(Xueqi x:is) {
        	count1++;
        }
        storage = new String[count1][2];

        
        for(Xueqi x:is) {
        	int start = 0;
        	int end = 0;
        	String s = new SimpleDateFormat("yyyy").format(x.startdate);
        	String e = new SimpleDateFormat("yyyy").format(x.enddate);
        	start = Integer.valueOf(s);
        	end = Integer.valueOf(e);
        	if(start==end) {
        		start = start - 1;
        	}
        	else if((start+1)==end) {
        		
        	}
        	String count = null;
        	if(x.Xueqi==1) {
        		count = "一";
        	}
        	else if(x.Xueqi==2) {
        		count = "二";
        	}
        	String all = start + " - " + end + "学年" + "第" + count + "学期" + " ( " + x.startdate + " ~ " + x.enddate + " ) ";
        	term.getItems().addAll(all);
        	storage[i][0]=x.startdate.toString();
        	storage[i][1]=x.enddate.toString();
        	i++;
        }
        for(i=0;i<count1;i++) {
        	System.out.println(storage[i][0]);
        	System.out.println(storage[i][1]);
        }
        SCDao dao = new SCDao();
        List<SC> scis = new ArrayList<SC>();
        scis = dao.list2(StudIdentityTrans.getCno());
        for(SC s:scis) {
        	coursename.getItems().add(s.Cname);
        }
        
	}
}
