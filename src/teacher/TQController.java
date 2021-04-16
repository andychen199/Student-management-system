package teacher;

import java.util.ArrayList;
import java.util.List;

import admin.studentUI.pwdtrans;
import attendance.bean.Afl;
import attendance.bean.Course;
import attendance.bean.PC;
import attendance.bean.Student;
import attendance.dao.AflDao;
import attendance.dao.CourseDao;
import attendance.dao.PCDao;
import attendance.dao.StudentDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class TQController {
	@FXML private Label id;
	@FXML private Label classname;
	@FXML private Label name;
	@FXML private Label courseid;
	@FXML private Label coursename;
	@FXML private Label applytime;
	@FXML private Label startdate;
	@FXML private Label enddate;
	@FXML private Label label1;
	@FXML private Label label2;
	@FXML private Label label3;
	@FXML private Label label4;
	@FXML private Label label5;
	@FXML private Label label6;
	@FXML private Label label7;
	@FXML private Label label8;
	@FXML private Label label9;
	@FXML private TextArea reason1;
	@FXML private Button accept;
	@FXML private Button inject;
	@FXML private Button reload;
	@FXML private ComboBox ifaccept;
	@FXML private ListView list;
	@FXML
	protected void Accept(ActionEvent event) {
		AflDao dao = new AflDao();
		List<Afl> is = new ArrayList<Afl>();
		dao.update2(Integer.valueOf(id.getText().toString()), Integer.valueOf(courseid.getText().toString()), applytime.getText().toString(), "已批准");
		accept.setDisable(true);
		accept.setText("已批准");
		inject.setDisable(false);
		inject.setText("拒绝");
	}
	@FXML
	protected void Reload(ActionEvent event) {
		list.getItems().clear();
		ReloadList();
	}
	@FXML
	protected void Inject(ActionEvent event) {
		AflDao dao = new AflDao();
		List<Afl> is = new ArrayList<Afl>();
		dao.update2(Integer.valueOf(id.getText().toString()), Integer.valueOf(courseid.getText().toString()), applytime.getText().toString(), "未通过");
		inject.setDisable(true);
		inject.setText("已拒绝");
		accept.setDisable(false);
		accept.setText("批准");
	}
	public void InitUI() {
		id.setVisible(false);
		classname.setVisible(false);
		name.setVisible(false);
		courseid.setVisible(false);
		coursename.setVisible(false);
		applytime.setVisible(false);
		startdate.setVisible(false);
		enddate.setVisible(false);
		reason1.setVisible(false);
		accept.setVisible(false);
		inject.setVisible(false);
		label1.setVisible(false);
		label2.setVisible(false);
		label3.setVisible(false);
		label4.setVisible(false);
		label5.setVisible(false);
		label6.setVisible(false);
		label7.setVisible(false);
		label8.setVisible(false);
		label9.setVisible(false);
		
		ifaccept.getItems().addAll("待审核","已批准","未通过");
		ifaccept.setValue("待审核");
		ifaccept.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        	@Override
        	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        		AflDao dao= new AflDao();
        	    List<Afl> is =new ArrayList<Afl>();
        	    is=dao.list(TeachIdentity.getTno());
        		for(Afl a:is) {
          	      	//classoptions.add(c.Pname);
        			
        			list.getItems().clear();
        			
          	    }
        		String results = ifaccept.getValue().toString();
        		System.out.println(results);
        		
        		is = dao.get(TeachIdentity.getTno(), results);
        		System.out.println(TeachIdentity.getTno());
        		for(Afl a:is) {
        			System.out.println(a.Aname);
        			list.getItems().addAll(a.Aname+"    "+a.Sno);
        			
        			
          	    }
        		id.setVisible(false);
        		classname.setVisible(false);
        		name.setVisible(false);
        		courseid.setVisible(false);
        		coursename.setVisible(false);
        		applytime.setVisible(false);
        		startdate.setVisible(false);
        		enddate.setVisible(false);
        		reason1.setVisible(false);
        		accept.setVisible(false);
        		inject.setVisible(false);
        		label1.setVisible(false);
        		label2.setVisible(false);
        		label3.setVisible(false);
        		label4.setVisible(false);
        		label5.setVisible(false);
        		label6.setVisible(false);
        		label7.setVisible(false);
        		label8.setVisible(false);
        		label9.setVisible(false);
        	}
        });
		list.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event)
			{
				if(event.getClickCount()== 1)
				{
					try {
						
						onFileListDbclicked();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}			
		});
		ReloadList();
	}
	public void onFileListDbclicked() {
		//String item = list.selectionModelProperty().getValue().toString();
		String item = list.getSelectionModel().getSelectedItem().toString();
		int idstart = 0;
		for(int i=0;i<item.length();i++) {
    		if(Character.isDigit(item.charAt(i))) {
    			idstart=Integer.parseInt(item.substring(i));
    			//System.out.println(item.substring(i));
    		}
    	}
		int index = list.getSelectionModel().getSelectedIndex();
		id.setVisible(true);
		classname.setVisible(true);
		name.setVisible(true);
		courseid.setVisible(true);
		coursename.setVisible(true);
		applytime.setVisible(true);
		startdate.setVisible(true);
		enddate.setVisible(true);
		reason1.setVisible(true);
		accept.setVisible(true);
		inject.setVisible(true);
		reason1.setEditable(false);
		AflDao dao = new AflDao();
		List<Afl> is = new ArrayList<Afl>();
		String status = ifaccept.getValue().toString();
		is = dao.get(TeachIdentity.getTno(),status);
		CourseDao dao1 = new CourseDao();
		List<Course> is1 = new ArrayList<Course>();
		id.setText(String.valueOf(is.get(index).Sno));
		classname.setText(is.get(index).APname);
		name.setText(is.get(index).Aname);
		courseid.setText(String.valueOf(is.get(index).Cno));
		is1=dao1.list1(is.get(index).Cno);
		coursename.setText(is1.get(0).getCname());
		applytime.setText(is.get(index).Adate);
		startdate.setText(is.get(index).ABegindate);
		enddate.setText(is.get(index).Aenddate);
		reason1.setText(is.get(index).Areason);
		if(is.get(index).Aresults.equals("待审核")) {
			accept.setDisable(false);
			accept.setText("批准");
			inject.setDisable(false);
			inject.setText("拒绝");
		}
		else if(is.get(index).Aresults.equals("未通过")) {
			accept.setDisable(false);
			inject.setDisable(true);
			inject.setText("已拒绝");
			accept.setText("批准");
		}else if(is.get(index).Aresults.equals("已批准")) {
			accept.setDisable(true);
			inject.setDisable(false);
			inject.setText("拒绝");
			accept.setText("已批准");
		}
		label1.setVisible(true);
		label2.setVisible(true);
		label3.setVisible(true);
		label4.setVisible(true);
		label5.setVisible(true);
		label6.setVisible(true);
		label7.setVisible(true);
		label8.setVisible(true);
		label9.setVisible(true);
		//ReloadList();
	}
	public void ReloadList() {
		list.getItems().clear();
		AflDao dao = new AflDao();
		List<Afl> is = new ArrayList<Afl>();
		
		String status = ifaccept.getValue().toString();
		is = dao.get(TeachIdentity.getTno(), status);
		for(Afl a:is) {
			list.getItems().addAll(a.Aname+"    "+a.Sno);
			
		}
		
	}
}
