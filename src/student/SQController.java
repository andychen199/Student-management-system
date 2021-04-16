package student;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import admin.studentUI.pwdtrans;
import attendance.bean.Afl;
import attendance.bean.Course;
import attendance.bean.PC;
import attendance.bean.SC;
import attendance.bean.Student;
import attendance.dao.AflDao;
import attendance.dao.CourseDao;
import attendance.dao.PCDao;
import attendance.dao.SCDao;
import attendance.dao.StudentDao;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import teacher.TeachIdentity;

public class SQController {
	@FXML private ComboBox coursecombo;
	@FXML private DatePicker startdate;
	@FXML private DatePicker enddate;
	@FXML private TextArea reason;
	@FXML private Button commitbutton;
	@FXML private Button addbutton;
	@FXML private Button closebutton;
	@FXML private Button tempbutton;
	@FXML private Button reloadbutton;
	@FXML private ListView list;
	@FXML private Label label1;
	@FXML private Label label2;
	@FXML private Label label3;
	@FXML private Label label4;
	@FXML private Label label5;
	@FXML private Label applytime;
	String[] storage = new String[4];
	@FXML
	protected void Reload(ActionEvent event) {
		label1.setVisible(false);
		label2.setVisible(false);
		label3.setVisible(false);
		label4.setVisible(false);
		label5.setVisible(false);
		coursecombo.setVisible(false);
		startdate.setVisible(false);
		enddate.setVisible(false);
		reason.setVisible(false);
		commitbutton.setVisible(false);
		applytime.setVisible(false);
		InitList();
		coursecombo.getSelectionModel().clearSelection();
		storage =null;
		closebutton.setVisible(false);
		tempbutton.setVisible(false);
	}
	@FXML
	protected void Commit(ActionEvent event) {
		AflDao dao = new AflDao();
//		List<Afl> is = new ArrayList<Afl>();
		CourseDao cdao = new CourseDao();
		List<Course> cis = new ArrayList<Course>();
		
		int index = list.getSelectionModel().getSelectedIndex();
		StudentDao studao = new StudentDao();
		List<Student> stuis = new ArrayList<Student>();
		stuis = studao.list1(StudIdentityTrans.getCno());
		
		String APname = stuis.get(0).Pname;
		String Aname = stuis.get(0).Sname;
		java.util.Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String Adate = sdf.format(date);
		if(list.getSelectionModel().getSelectedItem().equals("新建请假")) {
			try {
				cis = cdao.list3(coursecombo.getValue().toString());
				for(Course c:cis) {
					if(dao.add(StudIdentityTrans.getCno(), c.Cno, APname, Aname, Adate, startdate.getValue().toString(), enddate.getValue().toString(), reason.getText())) {
						InitList();
					}
				}
			}catch(Exception e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("提示");
				alert.setHeaderText(null);
				alert.setContentText("请完善请假信息！");
				alert.showAndWait();
			}
			
		}
		else {
			String item = list.getSelectionModel().getSelectedItem().toString();
			Character ifgang = null;
			String coursename = null;
			for(int i=0;i<item.length();i++) {
				ifgang = item.charAt(i);
				if(ifgang==' ') {
					coursename=item.substring(0, i);
					break;
				}
	    	}
			cis = cdao.list3(coursename);
			for(Course c:cis) {
				String newcourse = coursecombo.getValue().toString();
				List<Course> cis1 = new ArrayList<Course>();
				cis1 = cdao.list3(newcourse);
				java.util.Date date1 = new Date();
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				String Adate1 = sdf1.format(date);
				String Adate2 = applytime.getText();
				String ABegindate = startdate.getValue().toString();
				String Aenddate = enddate.getValue().toString();
				String Areason = reason.getText();
				for(Course c1:cis1) {
					if(dao.update1(StudIdentityTrans.getCno(), c.Cno, StudIdentityTrans.getCno(), c1.Cno, APname, Aname, Adate2, Adate1, ABegindate, Aenddate, Areason)) {
						;
						list.getItems().set(list.getSelectionModel().getSelectedIndex(), coursecombo.getValue()+" - "+"待审核");
						commitbutton.setDisable(true);
						commitbutton.setText("已提交");
					}
					else {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("提示");
						alert.setHeaderText(null);
						alert.setContentText("提交失败，请重试");
						alert.showAndWait();
					}

				}
			}
		}
		
		
		
	}
	@FXML
	protected void Close(ActionEvent event) {
		label1.setVisible(false);
		label2.setVisible(false);
		label3.setVisible(false);
		label4.setVisible(false);
		label5.setVisible(false);
		coursecombo.setVisible(false);
		startdate.setVisible(false);
		enddate.setVisible(false);
		reason.setVisible(false);
		applytime.setVisible(false);
		commitbutton.setVisible(false);
		list.getItems().remove("新建请假");
		coursecombo.getSelectionModel().clearSelection();
		storage =null;
		closebutton.setVisible(false);
		tempbutton.setVisible(false);
	}
	@FXML
	protected void TempStorage(ActionEvent event) {
		try {
			storage[0] = coursecombo.getValue().toString();
		}catch(Exception e) {
			
		}
		try {
			storage[1] = startdate.getValue().toString();
			
		}catch(Exception e) {
			
		}
		try {
			storage[2] = enddate.getValue().toString();
		}catch(Exception e) {
			
		}
		try {
			storage[3] = reason.getText();
		}catch(Exception e) {
			
		}
		tempbutton.setText("已暂存");
		tempbutton.setDisable(true);
	}
	@FXML
	protected void New(ActionEvent event) {
		list.getSelectionModel().selectLast();
		try {
			if(list.getSelectionModel().getSelectedItem().equals("新建请假")) {
				//coursecombo.getSelectionModel().clearSelection();
				//System.out.println(storage[0]);
				try {
					coursecombo.setValue(storage[0]);
				}catch(Exception e) {
					coursecombo.getSelectionModel().clearSelection();
				}
				try {
					java.sql.Date date1 = java.sql.Date.valueOf(storage[1]);
					startdate.setValue(date1.toLocalDate());
				}catch(Exception e) {
					startdate.setValue(null);
				}
				try {
					java.sql.Date date2 = java.sql.Date.valueOf(storage[2]);
					enddate.setValue(date2.toLocalDate());
				}catch(Exception e) {
					enddate.setValue(null);
				}
				try {
					reason.setText(storage[3]);
				}catch(Exception e) {
					reason.setText(null);
				}
				coursecombo.setDisable(false);
				startdate.setDisable(false);
				enddate.setDisable(false);
				reason.setEditable(true);
				commitbutton.setDisable(false);
				commitbutton.setText("提交");
				tempbutton.setText("暂存");
				tempbutton.setDisable(false);
				closebutton.setVisible(true);
				tempbutton.setVisible(true);
				applytime.setVisible(true);
				
				
				
			}else {
				list.getItems().addAll("新建请假");
				//System.out.println(storage[0]);
				label1.setVisible(true);
				label2.setVisible(true);
				label3.setVisible(true);
				label4.setVisible(true);
				label5.setVisible(true);
				coursecombo.setVisible(true);
				startdate.setVisible(true);
				enddate.setVisible(true);
				reason.setVisible(true);
				commitbutton.setVisible(true);
				coursecombo.getSelectionModel().clearSelection();
				startdate.setValue(null);
				enddate.setValue(null);
				reason.setText(null);
				startdate.setDisable(false);
				enddate.setDisable(false);
				coursecombo.setDisable(false);
				reason.setEditable(true);
				commitbutton.setDisable(false);
				commitbutton.setText("提交");
				tempbutton.setText("暂存");
				tempbutton.setDisable(false);
				closebutton.setVisible(true);
				tempbutton.setVisible(true);
			}
		}catch(NullPointerException e) {
			list.getItems().addAll("新建请假");
			//System.out.println(storage[0]);
			label1.setVisible(true);
			label2.setVisible(true);
			label3.setVisible(true);
			label4.setVisible(true);
			label5.setVisible(true);
			coursecombo.setVisible(true);
			startdate.setVisible(true);
			enddate.setVisible(true);
			reason.setVisible(true);
			commitbutton.setVisible(true);
			coursecombo.getSelectionModel().clearSelection();
			startdate.setValue(null);
			enddate.setValue(null);
			reason.setText(null);
			startdate.setDisable(false);
			enddate.setDisable(false);
			coursecombo.setDisable(false);
			reason.setEditable(true);
			commitbutton.setDisable(false);
			commitbutton.setText("提交");
			tempbutton.setText("暂存");
			tempbutton.setDisable(false);
			closebutton.setVisible(true);
			tempbutton.setVisible(true);
			list.getSelectionModel().selectLast();
		}
		
		list.getSelectionModel().selectLast();
		java.util.Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String Adate = sdf.format(date);
		System.out.println(Adate);
		applytime.setText(Adate);
	}
	public void onFileListDbclicked() {
		
		int index = list.getSelectionModel().getSelectedIndex();
		label1.setVisible(true);
		label2.setVisible(true);
		label3.setVisible(true);
		label4.setVisible(true);
		label5.setVisible(true);
		coursecombo.setVisible(true);
		startdate.setVisible(true);
		enddate.setVisible(true);
		reason.setVisible(true);
		commitbutton.setVisible(true);
		
		
		
		
		
		if(list.getSelectionModel().getSelectedItem().equals("新建请假")) {
			try {
				coursecombo.setValue(storage[0]);
			}catch(Exception e) {
				coursecombo.getSelectionModel().clearSelection();
			}
			try {
				java.sql.Date date1 = java.sql.Date.valueOf(storage[1]);
				startdate.setValue(date1.toLocalDate());
			}catch(Exception e) {
				startdate.setValue(null);
			}
			try {
				java.sql.Date date2 = java.sql.Date.valueOf(storage[2]);
				enddate.setValue(date2.toLocalDate());
			}catch(Exception e) {
				enddate.setValue(null);
			}
			try {
				reason.setText(storage[3]);
			}catch(Exception e) {
				reason.setText(null);
			}
			coursecombo.setDisable(false);
			startdate.setDisable(false);
			enddate.setDisable(false);
			reason.setEditable(true);
			commitbutton.setDisable(false);
			commitbutton.setText("提交");
			tempbutton.setText("暂存");
			tempbutton.setDisable(false);
			tempbutton.setVisible(true);
			closebutton.setVisible(true);
			applytime.setVisible(true);
			java.util.Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String Adate = sdf.format(date);
			applytime.setText(Adate);
		}else {
			AflDao dao = new AflDao();
			List<Afl> is = new ArrayList<Afl>();
			is = dao.list2(StudIdentityTrans.getCno());
			
			
			java.sql.Date date1 = java.sql.Date.valueOf(is.get(index).ABegindate);
			java.sql.Date date2 = java.sql.Date.valueOf(is.get(index).Aenddate);

			startdate.setValue(date1.toLocalDate());
			enddate.setValue(date2.toLocalDate());
			
			String item = list.getSelectionModel().getSelectedItem().toString();
			Character ifgang = null;
			String coursename = null;
			for(int i=0;i<item.length();i++) {
				ifgang = item.charAt(i);
				if(ifgang==' ') {
					coursename=item.substring(0, i);
					break;
				}
	    	}
			System.out.println(coursename);
			coursecombo.setValue(coursename);
			System.out.println(is.get(index).getAdate());
			applytime.setText(is.get(index).getAdate());
			reason.setText(is.get(index).getAreason());
			String status = list.getSelectionModel().getSelectedItem().toString();
			Character ifgang1 = null;
			String status1 = null;
			for(int i=0;i<status.length();i++) {
				ifgang1 = status.charAt(i);
				if(ifgang1=='-') {
					status1=item.substring(i+2);
					break;
				}
	    		
	    	}
			System.out.println(status1);
			if(status1.equals("待审核")) {
				coursecombo.setDisable(false);
				startdate.setDisable(false);
				enddate.setDisable(false);
				reason.setEditable(true);
				commitbutton.setDisable(false);
				commitbutton.setText("提交");
				
			}
			else if(status1.equals("未通过")) {
				coursecombo.setDisable(false);
				startdate.setDisable(false);
				enddate.setDisable(false);
				reason.setEditable(true);
				commitbutton.setDisable(false);
				commitbutton.setText("再次提交");
			}else if(status1.equals("已批准")) {
				coursecombo.setDisable(false);
				startdate.setDisable(true);
				enddate.setDisable(true);
				reason.setEditable(false);
				commitbutton.setDisable(true);
				commitbutton.setText("已同意");
			}
			label1.setVisible(true);
			label2.setVisible(true);
			label3.setVisible(true);
			label4.setVisible(true);
			label5.setVisible(true);
			tempbutton.setVisible(false);
			closebutton.setVisible(false);
			applytime.setVisible(true);
		}
			
		
		
		
		//ReloadList();
	}
	public void InitUI() {
		applytime.setVisible(false);
		label1.setVisible(false);
		label2.setVisible(false);
		label3.setVisible(false);
		label4.setVisible(false);
		label5.setVisible(false);
		coursecombo.setVisible(false);
		startdate.setVisible(false);
		enddate.setVisible(false);
		reason.setVisible(false);
		closebutton.setVisible(false);
		commitbutton.setVisible(false);
		InitList();
		
		SCDao scdao = new SCDao();
		List<SC> sc = new ArrayList<SC>();
		sc = scdao.list2(StudIdentityTrans.getCno());
		for(SC s:sc) {
			coursecombo.getItems().add(s.Cname);
		}
		
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
		Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
		confirmation.setTitle("提示");
        confirmation.setHeaderText(null);
		confirmation.setContentText("确认删除？");
		
		list.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem deleteItem = new MenuItem();
            deleteItem.textProperty().bind(Bindings.format("删除", cell.itemProperty()));
            StudIdentityTrans.setStatus(0);
            deleteItem.setOnAction(event -> {
                Optional<ButtonType> result = confirmation.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK){
                	list.getSelectionModel().selectLast();
                	
                	list.getItems().remove(cell.getItem());
                	label1.setVisible(false);
            		label2.setVisible(false);
            		label3.setVisible(false);
            		label4.setVisible(false);
            		label5.setVisible(false);
            		coursecombo.setVisible(false);
            		startdate.setVisible(false);
            		enddate.setVisible(false);
            		reason.setVisible(false);
            		commitbutton.setVisible(false);
            		//StudIdentityTrans.setStatus(1);
                	confirmation.hide();
                	
                	
                }
                
            });
            contextMenu.getItems().addAll(deleteItem);
            cell.textProperty().bind(cell.itemProperty());
            
    		
            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else{
                    cell.setContextMenu(contextMenu);
                }
            });
            return cell ;
        });
//		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
//        	@Override
//        	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//        		if(list.getSelectionModel().getSelectedItem().equals("新建请假")) {
//        			try {
//        				coursecombo.setValue(storage[0]);
//        			}catch(Exception e) {
//        				coursecombo.getSelectionModel().clearSelection();
//        			}
//        			try {
//        				java.sql.Date date1 = java.sql.Date.valueOf(storage[1]);
//        				startdate.setValue(date1.toLocalDate());
//        			}catch(Exception e) {
//        				startdate.setValue(null);
//        			}
//        			try {
//        				java.sql.Date date2 = java.sql.Date.valueOf(storage[2]);
//        				enddate.setValue(date2.toLocalDate());
//        			}catch(Exception e) {
//        				enddate.setValue(null);
//        			}
//        			try {
//        				reason.setText(storage[3]);
//        			}catch(Exception e) {
//        				reason.setText(null);
//        			}
//        		}else {
//        			try {
//						
//						onFileListDbclicked();
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//        		}
//        		
//        		
//        	}
//        });
		coursecombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        	@Override
        	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        		commitbutton.setDisable(false);
    			commitbutton.setText("提交");
        		
        		
        	}
        });
		startdate.valueProperty().addListener(new ChangeListener() {
        	@Override
        	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        		commitbutton.setDisable(false);
    			commitbutton.setText("提交");
        	}
        });
		enddate.valueProperty().addListener(new ChangeListener() {
        	@Override
        	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        		commitbutton.setDisable(false);
    			commitbutton.setText("提交");
        	}
        });
		reason.textProperty().addListener(new ChangeListener() {
        	@Override
        	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        		commitbutton.setDisable(false);
    			commitbutton.setText("提交");
        	}
        });
	}
	public void InitList() {
		list.getItems().clear();
		AflDao dao = new AflDao();
		List<Afl> is = new ArrayList<Afl>();
		is = dao.list2(StudIdentityTrans.getCno());
		
		CourseDao cdao = new CourseDao();
		List<Course> cis = new ArrayList<Course>();
		
		for(Afl a:is) {
			cis = cdao.list1(a.Cno);
			for(Course c:cis) {
				list.getItems().addAll(c.Cname+" - "+a.Aresults);
			}
			
		}
	}
}
