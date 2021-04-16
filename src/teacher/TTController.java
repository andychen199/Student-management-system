package teacher;

import java.time.format.DateTimeFormatter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import admin.studentUI.pwdtrans;
import admin.teachinfoUI.TeachInfoUI.TeachCourse;
import attendance.bean.AC;
import attendance.bean.Course;
import attendance.bean.PC;
import attendance.bean.Qimo;
import attendance.bean.SC;
import attendance.bean.Student;
import attendance.bean.Teach;
import attendance.bean.TeachforUI;
import attendance.bean.Xueqi;
import attendance.dao.ACDao;
import attendance.dao.CourseDao;
import attendance.dao.PCDao;
import attendance.dao.QimoDao;
import attendance.dao.SCDao;
import attendance.dao.StudentDao;
import attendance.dao.TeachDao;
import attendance.dao.XueqiDao;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import teacher.TRController.TeachRoutine;

public class TTController {
	@FXML private ComboBox xueqicombo;
	@FXML private ComboBox namecombo;
	@FXML private ComboBox classcombo;
	@FXML private ComboBox coursenamecombo;
	@FXML private Button addbutton;
	@FXML private Button loadbutton;
	@FXML private Button addallbutton;
	@FXML private TableView table;
	@FXML private TableColumn classname;
	@FXML private TableColumn id;
	@FXML private TableColumn name;
	@FXML private TableColumn term;
	@FXML private TableColumn courseid;
	@FXML private TableColumn coursename;
	@FXML private TableColumn Qan;
	@FXML private TableColumn Qaln;
	@FXML private TableColumn Qtn;
	@FXML private TableColumn Qlen;
	@FXML private TableColumn Qlate;
	final ObservableList<TeachTotal> data = FXCollections.observableArrayList();
	int count1 = 0;
	String[][] storage = null;
    int i = 0;
    public static class TeachTotal {

        private final SimpleStringProperty classname;
        private final SimpleStringProperty id;
        private final SimpleStringProperty name;
        private final SimpleStringProperty xueqi;
        private final SimpleStringProperty courseid;
        private final SimpleStringProperty coursename;
        private final SimpleStringProperty Qan;
        private final SimpleStringProperty Qaln;
        private final SimpleStringProperty Qtn;
        private final SimpleStringProperty Qlen;
        private final SimpleStringProperty Qlate;

        private TeachTotal(String classname1, String id1, String name1, String xueqi1, String courseid1, String coursename1, String Qan1, String Qaln1, String Qtn1, String Qlen1, String Qlate1) {
            this.classname = new SimpleStringProperty(classname1);
            this.id = new SimpleStringProperty(id1);
            this.name = new SimpleStringProperty(name1);
            this.xueqi = new SimpleStringProperty(xueqi1);
            this.courseid = new SimpleStringProperty(courseid1);
            this.coursename = new SimpleStringProperty(coursename1);
            this.Qan = new SimpleStringProperty(Qan1);
            this.Qaln = new SimpleStringProperty(Qaln1);
            this.Qtn = new SimpleStringProperty(Qtn1);
            this.Qlen = new SimpleStringProperty(Qlen1);
            this.Qlate = new SimpleStringProperty(Qlate1);
        }
        
		public String getClassname() {
			return classname.get();
		}
		
		public void setclassname(String classname1) {
			classname.set(classname1);
        }
		
		public String getId() {
			return id.get();
		}

		public void setid(String id1) {
			id.set(id1);
        }
		
		public String getName() {
			return name.get();
		}

		public void setname(String xueqi1) {
			xueqi.set(xueqi1);
        }
		
		public String getXueqi() {
			return xueqi.get();
		}

		public void setxueqi(String name1) {
			name.set(name1);
        }
		
		public String getCourseid() {
			return courseid.get();
		}

		public void setcourseid(String courseid1) {
			courseid.set(courseid1);
        }
		
		public String getCoursename() {
			return coursename.get();
		}

		public void setcoursename(String coursename1) {
			coursename.set(coursename1);
        }
		
		public String getQan() {
			return Qan.get();
		}

		public void setQan(String Qan1) {
			Qan.set(Qan1);
        }
		
		public String getQaln() {
			return Qaln.get();
		}

		public void setQaln(String Qaln1) {
			Qaln.set(Qaln1);
        }
		
		public String getQtn() {
			return Qtn.get();
		}

		public void setQtn(String Qtn1) {
			Qtn.set(Qtn1);
        }
		
		public String getQlen() {
			return Qlen.get();
		}

		public void setQlen(String Qlen1) {
			Qlen.set(Qlen1);
        }
		
		public String getQlate() {
			return Qlate.get();
		}
		
		public void setQlate(String Qlate1) {
			Qlate.set(Qlate1);
        }
    }
    @FXML
    protected void Load(ActionEvent event) throws ParseException {
    	table.getItems().clear();
    	int max = 0;
    	QimoDao dao = new QimoDao();
		
		List<Qimo> is = new ArrayList<Qimo>();
		int index = xueqicombo.getSelectionModel().getSelectedIndex();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	java.util.Date startd = format.parse(storage[index][0]);
    	java.util.Date endd = format.parse(storage[index][1]);
    	java.sql.Date startd1 = new java.sql.Date(startd.getTime());
    	java.sql.Date endd1 = new java.sql.Date(endd.getTime());
		is = dao.list7(startd1,endd1,TeachIdentity.getTno());
		
		for(Qimo q:is) {
			data.add(new TeachTotal(q.Pname, String.valueOf(q.Sno), q.Sname, q.Qstartdate.toString()+" ~ "+q.Qenddate.toString(), String.valueOf(q.Cno), q.Cname, String.valueOf(q.Qan), String.valueOf(q.Qaln), String.valueOf(q.Qtn), String.valueOf(q.Qlen), String.valueOf(q.Qlate)));
			max++;
		}
		table.setItems(data);
		table.refresh();
    }
    @FXML
    protected void AddAll(ActionEvent event) {
        
        try {
        	xueqicombo.getSelectionModel().getSelectedIndex();
        	coursenamecombo.getValue().toString();
        	StudentDao dao1 = new StudentDao();
            List<Student> is1 = new ArrayList<Student>();
            is1 = dao1.list2(classcombo.getValue().toString());
            for(Student s:is1) {
            	String longname = s.Sname + "    " + s.Sno;
            	SaveData(longname);
            }
        }catch(Exception e) {
        	Alert alert = new Alert(Alert.AlertType.ERROR);
			
			alert.setTitle("提示");
			alert.setHeaderText(null);
			alert.setContentText("请选择所要添加的班级/学期/课程");
			
			alert.showAndWait();
        }
    }
	@FXML
    protected void Add(ActionEvent event) {
        try {
        	SaveData(namecombo.getValue().toString());
        }catch(Exception e) {
        	Alert alert = new Alert(Alert.AlertType.ERROR);
			
			alert.setTitle("提示");
			alert.setHeaderText(null);
			alert.setContentText("请选择所要添加的班级/学期/课程/学生");
			
			alert.showAndWait();
        }
		
    }
	public void SaveData(String item) {
		try{
        	int num = xueqicombo.getSelectionModel().getSelectedIndex();
        	
        	String start = storage[num][0];
        	String end = storage[num][1];
        	int idstart = 0;
        	int Cno = 0;
        	//String item = namecombo.getValue().toString();
        	for(int i=0;i<item.length();i++) {
	    		if(Character.isDigit(item.charAt(i))) {
	    			idstart=Integer.parseInt(item.substring(i));
	    			//System.out.println(item.substring(i));
	    		}
	    	}
        	String kcname = coursenamecombo.getValue().toString();
        	CourseDao dao = new CourseDao();
        	List<Course> is = new ArrayList<Course>();
        	is = dao.list2();
        	for(Course c:is) {
        		if(c.Cname.equals(kcname)) {
        			Cno=c.Cno;
        		}
        	}
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        	java.util.Date startd = format.parse(start);
        	java.util.Date endd = format.parse(end);
        	java.sql.Date startd1 = new java.sql.Date(startd.getTime());
        	java.sql.Date endd1 = new java.sql.Date(endd.getTime());
        	QimoDao qimodao = new QimoDao();
        	System.out.println(idstart);
        	System.out.println(Cno);
        	System.out.println(startd1);
        	System.out.println(endd1);
        	qimodao.add(idstart, Cno, startd1, endd1);
        }catch(Exception e) {
        	e.printStackTrace();
        	Alert alert = new Alert(Alert.AlertType.ERROR);
			
			alert.setTitle("提示");
			alert.setHeaderText(null);
			alert.setContentText("已添加该学生考勤！无法重复添加！");
			
			alert.showAndWait();
        }
	}
	public void InitUI() {
		InitComboBox();
		InitNameComboBox();
		classcombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        	@Override
        	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        		StudentDao stdao= new StudentDao();
        	    List<Student> stis =new ArrayList<Student>();
        	    stis=stdao.list3();
        		namecombo.getItems().clear();
       		
        		stis=stdao.list2(classcombo.getValue().toString());
       		
        		for(Student s:stis) {
        			String longname = s.Sname + "    " + s.Sno;
        			namecombo.getItems().addAll(longname);
        			//System.out.println(count);
        			
          	    }
        	}
        });
		classname.setCellValueFactory(
                new PropertyValueFactory<>("classname"));
    	classname.setCellFactory(TextFieldTableCell.<TeachTotal>forTableColumn());
    	
    	id.setCellValueFactory(
                new PropertyValueFactory<>("id"));
    	id.setCellFactory(TextFieldTableCell.<TeachTotal>forTableColumn());
    	
    	name.setCellValueFactory(
                new PropertyValueFactory<>("name"));
    	name.setCellFactory(TextFieldTableCell.<TeachTotal>forTableColumn());
    	
    	term.setCellValueFactory(
                new PropertyValueFactory<>("xueqi"));
    	term.setCellFactory(TextFieldTableCell.<TeachTotal>forTableColumn());
    	
    	courseid.setCellValueFactory(
                new PropertyValueFactory<>("courseid"));
    	courseid.setCellFactory(TextFieldTableCell.<TeachTotal>forTableColumn());
    	
    	coursename.setCellValueFactory(
                new PropertyValueFactory<>("coursename"));
    	coursename.setCellFactory(TextFieldTableCell.<TeachTotal>forTableColumn());
    	
    	Qan.setCellValueFactory(
                new PropertyValueFactory<>("Qan"));
    	Qan.setCellFactory(TextFieldTableCell.<TeachTotal>forTableColumn());
    	
    	Qaln.setCellValueFactory(
                new PropertyValueFactory<>("Qaln"));
    	Qaln.setCellFactory(TextFieldTableCell.<TeachTotal>forTableColumn());
    	
    	Qtn.setCellValueFactory(
                new PropertyValueFactory<>("Qtn"));
    	Qtn.setCellFactory(TextFieldTableCell.<TeachTotal>forTableColumn());
    	
    	Qlen.setCellValueFactory(
                new PropertyValueFactory<>("Qlen"));
    	Qlen.setCellFactory(TextFieldTableCell.<TeachTotal>forTableColumn());
    	
    	Qlate.setCellValueFactory(
                new PropertyValueFactory<>("Qlate"));
    	Qlate.setCellFactory(TextFieldTableCell.<TeachTotal>forTableColumn());
    	classname.setStyle("-fx-alignment: CENTER;");
    	id.setStyle("-fx-alignment: CENTER;");
    	name.setStyle("-fx-alignment: CENTER;");
    	term.setStyle("-fx-alignment: CENTER;");
    	courseid.setStyle("-fx-alignment: CENTER;");
    	coursename.setStyle("-fx-alignment: CENTER;");
    	Qan.setStyle("-fx-alignment: CENTER;");
    	Qaln.setStyle("-fx-alignment: CENTER;");
    	Qtn.setStyle("-fx-alignment: CENTER;");
    	Qlen.setStyle("-fx-alignment: CENTER;");
    	Qlate.setStyle("-fx-alignment: CENTER;");
    	LoadTableView();
	}
	public void LoadTableView() {
		int max = 0;
		QimoDao dao = new QimoDao();
		
		List<Qimo> is = new ArrayList<Qimo>();
		is = dao.list6(TeachIdentity.getTno());
		
		for(Qimo q:is) {
			data.add(new TeachTotal(q.Pname, String.valueOf(q.Sno), q.Sname, q.Qstartdate.toString()+" ~ "+q.Qenddate.toString(), String.valueOf(q.Cno), q.Cname, String.valueOf(q.Qan), String.valueOf(q.Qaln), String.valueOf(q.Qtn), String.valueOf(q.Qlen), String.valueOf(q.Qlate)));
			max++;
		}
		table.setItems(data);
		table.refresh();
	}
	public void InitComboBox() {
		XueqiDao xueqi = new XueqiDao();
        List<Xueqi> is = new ArrayList<Xueqi>();
        is = xueqi.getList();
        xueqicombo.getItems().clear();
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
        	xueqicombo.getItems().addAll(all);
        	storage[i][0]=x.startdate.toString();
        	storage[i][1]=x.enddate.toString();
        	i++;
        }
        for(i=0;i<count1;i++) {
        	System.out.println(storage[i][0]);
        	System.out.println(storage[i][1]);
        }
        TeachDao tdao = new TeachDao();
	    List<TeachforUI> tis =new ArrayList<TeachforUI>();
	    tis = tdao.list3forUI(TeachIdentity.getTno());
	    for(TeachforUI t:tis) {
    		System.out.println(t.Cno);
        	coursenamecombo.getItems().addAll(t.Cname);
    	}
	}
	public void InitNameComboBox() {
		classcombo.getItems().clear();
		PCDao pcdao= new PCDao();
	    List<PC> pcis =new ArrayList<PC>();
	    pcis=pcdao.list2();
	    for(PC c:pcis) {
	    	System.out.println(c.Pname);
  	      	classcombo.getItems().addAll(c.Pname);
	    	//classcombo.getItems().addAll(pcis);
  	    }

	}
}
