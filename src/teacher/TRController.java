package teacher;

import java.time.format.DateTimeFormatter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import admin.studentUI.pwdtrans;
import admin.teachinfoUI.TeachInfoUI.TeachCourse;
import attendance.bean.AC;
import attendance.bean.Course;
import attendance.bean.PC;
import attendance.bean.SC;
import attendance.bean.Student;
import attendance.bean.Teach;
import attendance.bean.TeachforUI;
import attendance.dao.ACDao;
import attendance.dao.CourseDao;
import attendance.dao.PCDao;
import attendance.dao.SCDao;
import attendance.dao.StudentDao;
import attendance.dao.TeachDao;
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
import jdk.nashorn.internal.runtime.options.Options;

public class TRController {
	@FXML private ComboBox classcombo;
	@FXML private ComboBox coursecombo;
	@FXML private ComboBox namecombo;
	@FXML private DatePicker datecombo;
	@FXML private TableView tablecombo;
	@FXML private Button savebutton;
	@FXML private Button loadbutton;
	@FXML private ComboBox findchoose;
	@FXML private RadioButton insertcheck;
	@FXML private RadioButton findcheck;
	@FXML private TableColumn<TeachRoutine, String> classid;
	@FXML private TableColumn<TeachRoutine, String> id;
	@FXML private TableColumn<TeachRoutine, String> name;
	@FXML private TableColumn<TeachRoutine, String> courseid;
	@FXML private TableColumn<TeachRoutine, String> coursename;
	@FXML private TableColumn<TeachRoutine, String> date;
	@FXML private TableColumn<TeachRoutine, String> situation;
	@FXML private ToggleGroup group;
	@FXML private Label totallabel;
	@FXML private Button deleteall;
    final ObservableList<TeachRoutine> data = FXCollections.observableArrayList();
    int max = 0;
    private final String pattern = "yyyy-MM-dd";
	@FXML
	protected void save(ActionEvent event) {
//		PCDao pcdao= new PCDao();
//	    List<PC> pcis =new ArrayList<PC>();
//	    pcis=pcdao.list2();
//	    for(PC c:pcis) {
//	    	System.out.println(c.Pname);
//  	      	//classcombo.getItems().addAll(c.Pname);
//	    	classcombo.getItems().addAll(pcis);
//  	    }
//	    TeachRoutine t1 = (TeachRoutine) tablecombo.getSelectionModel().selectedItemProperty().getBean();
//		System.out.println(t1.getTcname());
		ACDao dao = new ACDao();
		//????????????????????????????????????????????????????????????
		if(TeachRoutineTrans.getClassname()==null&&TeachRoutineTrans.getCourseid()==0&&TeachRoutineTrans.getCoursename()==null&&TeachRoutineTrans.getDate()==null&&TeachRoutineTrans.getId()==0&&TeachRoutineTrans.getName()==null&&TeachRoutineTrans.getSituation()==null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			
			alert.setTitle("??????");
			alert.setHeaderText(null);
			alert.setContentText("???????????????????????????????????????");
			
			alert.showAndWait();
			return;
		}
		else {
			if(dao.delete(TeachRoutineTrans.getId(), TeachRoutineTrans.getCourseid(), TeachRoutineTrans.getDate())) {
//				LoadTableView();
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				
				alert.setTitle("??????");
				alert.setHeaderText(null);
				alert.setContentText("????????????");
				if(max!=0) {
					max = max - 1;
				}
				else if(max==0) {
					savebutton.setDisable(true);
					deleteall.setDisable(true);
				}


				
				tablecombo.getItems().clear();
		    	try {
		    		if(group.getSelectedToggle().getUserData().toString().equals("??????")) {
		    			
		    			//situation.setCellFactory(TextFieldTableCell.<TeachRoutine>forTableColumn());
		    			
		    		    
		    			
		    			System.out.println("?????????????????????");
		    			if(findchoose.getValue().equals("??????")) {
		    				System.out.println("????????????");
		    				ACDao acdao = new ACDao();
		    				List<AC> ac = new ArrayList<AC>();
//		    				CourseDao cdao = new CourseDao();
//		    				List<Course> course = new ArrayList<Course>();
//		    				course = cdao.list2();
//		    				int Cno = 0;
//		    				for(Course c:course) {
//		    					if(c.Cname.equals(coursecombo.getValue().toString())) {
//		    						Cno = c.Cno;
//		    						System.out.println(Cno);
//		    					}
//		    				}
		    				ac = acdao.list3(classcombo.getValue().toString());
		    				max = 0;
		    				for(AC a:ac) {
		    					System.out.printf("%d\t%d\t%s\t%s\t%s\t%s\n", a.Cno, a.Sno, a.Aname, a.APname,a.Acdate,a.Asituation);
		    					data.add(new TeachRoutine(a.APname,String.valueOf(a.Sno), a.Aname, String.valueOf(a.Cno), a.Cname, a.Acdate, a.Asituation));
		    					max++;
		    				}
		    				if(max!=0) {
		    					savebutton.setDisable(false);
		    					deleteall.setDisable(false);
		    				}
		    				else if(max==0) {
		    					savebutton.setDisable(true);
		    					deleteall.setDisable(true);
		    				}
		    				
		    				totallabel.setText("????????????"+max+"???");
		    				tablecombo.setItems(data);
		    				tablecombo.refresh();
		    			}
		    			else if(findchoose.getValue().equals("?????????")) {
		    				System.out.println("???????????????");
		    				
		    				TeachDao tdao = new TeachDao();
		    			    List<TeachforUI> tis =new ArrayList<TeachforUI>();
		    			    tis = tdao.list3forUI(TeachIdentity.getTno());
		    			    max = 0;
		    			    for(TeachforUI t:tis) {
		    		    		System.out.println(t.Cno);
		    		        	//coursecombo.getItems().addAll(t.Cname);
		    		        	ACDao acdao = new ACDao();
		    					List<AC> ac = new ArrayList<AC>();
		    					ac = acdao.list6(t.Cname);
		    					String match = coursecombo.getValue().toString();
		    					for(AC a:ac) {
		    						if(a.Cname.equals(match)) {
		    							data.add(new TeachRoutine(a.APname,String.valueOf(a.Sno), a.Aname, String.valueOf(a.Cno), a.Cname, a.Acdate, a.Asituation));
		    							max++;
		    						}
		    					}
		    		    	}
		    			    if(max!=0) {
		    					savebutton.setDisable(false);
		    					deleteall.setDisable(false);
		    				}
		    			    else if(max==0) {
		    					savebutton.setDisable(true);
		    					deleteall.setDisable(true);
		    				}
		    			    totallabel.setText("????????????"+max+"???");
		    			    //System.out.println(data.get(max-1).getTname());
		    				tablecombo.setItems(data);
		    				tablecombo.refresh();
		    			}
		    			else if(findchoose.getValue().equals("??????")) {
		    				System.out.println("????????????");
		    				int idstart = 0;
		    				String item = namecombo.getValue().toString();
		    				for(int i=0;i<item.length();i++) {
		    		    		if(Character.isDigit(item.charAt(i))) {
		    		    			idstart=Integer.parseInt(item.substring(i));
		    		    			//System.out.println(item.substring(i));
		    		    		}
		    		    	}
		    				ACDao acdao = new ACDao();
		    				List<AC> ac = new ArrayList<AC>();
		    				ac = acdao.list5(idstart);
		    				max = 0;
		    				for(AC a:ac) {
		    					data.add(new TeachRoutine(a.APname,String.valueOf(a.Sno), a.Aname, String.valueOf(a.Cno), a.Cname, a.Acdate, a.Asituation));
		    						//break;	
		    					max++;
		    				}
		    				if(max!=0) {
		    					savebutton.setDisable(false);
		    					deleteall.setDisable(false);
		    				}
		    				else if(max==0) {
		    					savebutton.setDisable(true);
		    					deleteall.setDisable(true);
		    				}
		    				totallabel.setText("????????????"+max+"???");
		    				tablecombo.setItems(data);
		    				tablecombo.refresh();
		    			}
		    			else if(findchoose.getValue().equals("??????")) {
		    				System.out.println("????????????");
		    				int idstart = 0;
		    				
		    				TeachDao tdao = new TeachDao();
		    			    List<TeachforUI> tis =new ArrayList<TeachforUI>();
		    			    tis = tdao.list3forUI(TeachIdentity.getTno());
		    			    max = 0;
		    			    for(TeachforUI t:tis) {
		    		    		//System.out.println(t.Cno);
		    		        	//coursecombo.getItems().addAll(t.Cname);
		    		        	ACDao acdao = new ACDao();
		    					List<AC> ac = new ArrayList<AC>();
		    					ac = acdao.list4(datecombo.getValue().toString());
		    					//String match = coursecombo.getValue().toString();
		    					for(AC a:ac) {
		    						if(t.Cno==a.Cno) {
		    							data.add(new TeachRoutine(a.APname,String.valueOf(a.Sno), a.Aname, String.valueOf(a.Cno), a.Cname, a.Acdate, a.Asituation));
		    							max++;
		    						}
		    					}
		    		    	}
		    			    if(max!=0) {
		    					savebutton.setDisable(false);
		    					deleteall.setDisable(false);
		    				}
		    			    else if(max==0) {
		    					savebutton.setDisable(true);
		    					deleteall.setDisable(true);
		    				}
		    			    totallabel.setText("????????????"+max+"???");
		    				tablecombo.setItems(data);
		    				tablecombo.refresh();
		    			}
		    		}
		    		else if(group.getSelectedToggle().getUserData().toString().equals("??????")) {
		    			ACDao acdao = new ACDao();
		    			List<AC> ac = new ArrayList<AC>();
		    			
		    			
		    			//try()
		    			//int idstart = 0;
		    			//String item = namecombo.getValue().toString();
//		    			for(int i=0;i<item.length();i++) {
//		    	    		if(Character.isDigit(item.charAt(i))) {
//		    	    			idstart=Integer.parseInt(item.substring(i));
//		    	    			//System.out.println(item.substring(i));
//		    	    		}
//		    	    	}
		    			
		    				String classname = classcombo.getValue().toString();
		    				String date = datecombo.getValue().toString();
		    				String coursename = coursecombo.getValue().toString();
		    				CourseDao coursedao = new CourseDao();
		    				List<Course> cis = new ArrayList<Course>();
		    				cis = coursedao.list3(coursename);
		    				int courseid = 0;
		    				for(Course c:cis) {
		    					courseid = c.getCno();
		    				}
		    				System.out.println(courseid);
//		    				if(acdao.add(classname, courseid, date)) {
//		    					
//		    				}else {
//		    					Alert alert = new Alert(Alert.AlertType.ERROR);
//		    					alert.setTitle("??????");
//		    					alert.setHeaderText(null);
//		    					alert.setContentText("???????????????????????????????????????????????????????????????");
//		    					alert.showAndWait();
//		    					
//		    				}
		    				ac = acdao.list7(classname,coursename,date);
		    				max = 0;
		    				for(AC a:ac) {
		    					data.add(new TeachRoutine(a.APname,String.valueOf(a.Sno), a.Aname, String.valueOf(a.Cno), a.Cname, a.Acdate, a.Asituation));
		    						//break;	
		    					max++;
		    				}
		    				if(max!=0) {
		    					savebutton.setDisable(false);
		    					deleteall.setDisable(false);
		    				}
		    				else if(max==0) {
		    					savebutton.setDisable(true);
		    					deleteall.setDisable(true);
		    				}
		    				totallabel.setText("????????????"+max+"???");
		    				tablecombo.setItems(data);
		    				tablecombo.refresh();
		    		}
		    	}catch(Exception e){
		    		//System.out.println(e);
		    		e.printStackTrace();
		    		totallabel.setText("");
					savebutton.setDisable(true);
					deleteall.setDisable(true);
		    		Alert alert1 = new Alert(Alert.AlertType.ERROR);
					alert1.setTitle("??????");
					alert1.setHeaderText(null);
					alert1.setContentText("??????????????????");
					alert1.showAndWait();
		    	}
				
				
				
				
				
				
				
				alert.showAndWait();
				TeachRoutineTrans.setClassname(null);
				TeachRoutineTrans.setCourseid(0);
				TeachRoutineTrans.setCoursename(null);
				TeachRoutineTrans.setDate(null);
				TeachRoutineTrans.setId(0);
				TeachRoutineTrans.setName(null);
				TeachRoutineTrans.setSituation(null);
			}
			else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				
				alert.setTitle("??????");
				alert.setHeaderText(null);
				alert.setContentText("???????????????");
				
				alert.showAndWait();
			}
		}
		
		
	}
	@FXML
	protected void FindCheckSelected(ActionEvent event) {
		System.out.print("Hello");
	}
	@FXML
	protected void DeleteAll(ActionEvent event) {
		//System.out.print("Hello");
		if(max==0) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("??????");
			alert.setHeaderText(null);
			alert.setContentText("???????????????");
			alert.showAndWait();
		}
		else {
			ACDao dao = new ACDao();
			for(int i=0;i<max;i++) {
				dao.delete(Integer.valueOf(data.get(i).getTid()), Integer.valueOf(data.get(i).getTcid()), data.get(i).getTdate());
			}
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("??????");
			alert.setHeaderText(null);
			alert.setContentText("???????????????");
			TeachRoutineTrans.setClassname(null);
			TeachRoutineTrans.setCourseid(0);
			TeachRoutineTrans.setCoursename(null);
			TeachRoutineTrans.setDate(null);
			TeachRoutineTrans.setId(0);
			TeachRoutineTrans.setName(null);
			TeachRoutineTrans.setSituation(null);
			max=0;
			savebutton.setDisable(true);
			deleteall.setDisable(true);
			alert.showAndWait();
			
		}
	}
	@FXML
	protected void Load(ActionEvent event) {
		tablecombo.getItems().clear();
		ObservableList<String> options = FXCollections.observableArrayList("??????", "??????", "??????", "??????", "??????");
	    situation.setCellFactory(tc -> {
	    	ComboBox<String>combo = new ComboBox<String>();
	    	combo.setItems(options);
	    	combo.setEditable(false);
	    	TableCell<TeachRoutine, String> cell = new TableCell<TeachRoutine, String>(){
	    		protected void updateItem(String chuzhi,boolean empty) {
	    			super.updateItem(chuzhi, empty);
	    			if(empty) {
	    				setGraphic(null);
	    			}
	    			else {
	    				combo.setValue(chuzhi);
	    				setGraphic(combo);
	    			}
	    		}
	    	};
	    	combo.setOnAction(e -> {
	    		int rank = cell.getIndex();
	    		String value = combo.getValue();
	    		
	    		//System.out.println(onetemp);
	    		String two = classid.getTableView().getItems().get(
                        rank).getTid();
	    		String three = classid.getTableView().getItems().get(
                        rank).getTname();
	    		String four = classid.getTableView().getItems().get(
                        rank).getTcid();
	    		String six = classid.getTableView().getItems().get(
                        rank).getTdate();
	    		String seventemp = classid.getTableView().getItems().get(
                        rank).getTroutine();
	    		String seven = value;
            	
            	ACDao dao = new ACDao();
	    		if(dao.update(Integer.valueOf(two), Integer.valueOf(four), six, seven)) {
	    			System.out.println(three + "???????????????????????????");
	    		}
	    		else {
	    			Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("??????");
					alert.setHeaderText(null);
					alert.setContentText("????????????");
					alert.showAndWait();
	    		}
	    		if(value.equals(options.get(0))){
	    			System.out.println("???????????????");
	    			
	    		}
	    		else if(value.equals(options.get(1))) {
	    			
	    		}
	    		else if(value.equals(options.get(2))) {
	    			
	    		}
	    		else if(value.equals(options.get(3))) {
	    			
	    		}
	    		else if(value.equals(options.get(4))) {
	
	    		}
	    	});
	    	return cell;
	    });
	    LoadTableView();

		
	}
	public static class TeachRoutine {

        private final SimpleStringProperty Tclassid;
        private final SimpleStringProperty Tid;
        private final SimpleStringProperty Tname;
        private final SimpleStringProperty Tcid;
        private final SimpleStringProperty Tcname;
        private final SimpleStringProperty Tdate;
        private final SimpleStringProperty Troutine;

        private TeachRoutine(String Tclassid1, String Tid1, String Tname1, String Tcid1, String Tcname1, String Tdate1, String Troutine1) {
            this.Tclassid = new SimpleStringProperty(Tclassid1);
            this.Tid = new SimpleStringProperty(Tid1);
            this.Tname = new SimpleStringProperty(Tname1);
            this.Tcid = new SimpleStringProperty(Tcid1);
            this.Tcname = new SimpleStringProperty(Tcname1);
            this.Tdate = new SimpleStringProperty(Tdate1);
            this.Troutine = new SimpleStringProperty(Troutine1);
        }

        public String getTclassid() {
            return Tclassid.get();
        }

        public void setTClassid(String TClassid1) {
        	Tclassid.set(TClassid1);
        }

        public String getTid() {
            return Tid.get();
        }

        public void setTid(String Tid1) {
        	Tid.set(Tid1);
        }

        public String getTname() {
            return Tname.get();
        }

        public void setTname(String Tname1) {
        	Tname.set(Tname1);
        }
        
        public String getTcid() {
            return Tcid.get();
        }

        public void setTcid(String Tcid1) {
        	Tcname.set(Tcid1);
        }
        
        public String getTcname() {
            return Tcname.get();
        }

        public void setTcname(String Tcname1) {
        	Tcname.set(Tcname1);
        }

		public String getTdate() {
			return Tdate.get();
		}

		public String getTroutine() {
			return Troutine.get();
		}

		
    }
    public void InitUI() {
    	TeachRoutineTrans.setClassname(null);
		TeachRoutineTrans.setCourseid(0);
		TeachRoutineTrans.setCoursename(null);
		TeachRoutineTrans.setDate(null);
		TeachRoutineTrans.setId(0);
		TeachRoutineTrans.setName(null);
		TeachRoutineTrans.setSituation(null);
		deleteall.setDisable(true);
		savebutton.setDisable(true);
    	//??????????????????yyyy-mm-dd
    	StringConverter converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                DateTimeFormatter.ofPattern(pattern);
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };    
        //?????????????????????????????????????????????
        final Callback<DatePicker, DateCell> dayCellFactory = 
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                               
                                if (item.isAfter(LocalDate.now().plusDays(0))) {
                                        setDisable(true);
                                        setStyle("-fx-background-color: #ffc0cb;");
                                }   
                        }
                    };
                }
            };
        datecombo.setDayCellFactory(dayCellFactory);
        datecombo.setConverter(converter);
        
        //?????????????????????
    	PCDao pcdao= new PCDao();
	    List<PC> pcis =new ArrayList<PC>();
	    pcis=pcdao.list2();
	    for(PC c:pcis) {
	    	System.out.println(c.Pname);
  	      	classcombo.getItems().addAll(c.Pname);
	    	//classcombo.getItems().addAll(pcis);
  	    }
	    CourseDao dao = new CourseDao();
	    List<Course> is =new ArrayList<Course>();
	    TeachDao tdao = new TeachDao();
	    List<TeachforUI> tis =new ArrayList<TeachforUI>();
	    tis = tdao.list3forUI(TeachIdentity.getTno());
	    for(TeachforUI t:tis) {
    		System.out.println(t.Cno);
        	coursecombo.getItems().addAll(t.Cname);
    	}
	    
	    classcombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        	@Override
        	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        		StudentDao stdao= new StudentDao();
        	    List<Student> stis =new ArrayList<Student>();
        	    stis=stdao.list3();
        		namecombo.getItems().clear();

        		//????????????????????????????????????
        		if(group.getSelectedToggle().getUserData().toString().equals("??????")) {
        			if(findchoose.getValue().toString().equals("??????")) {
            			stis=stdao.list3();
            		}
            		else if(findchoose.getValue().toString().equals("??????")){
            			stis=stdao.list2(classcombo.getValue().toString());
            		}
        		}
        		else if(group.getSelectedToggle().getUserData().toString().equals("??????")) {
        			stis=stdao.list2(classcombo.getValue().toString());
        		}
        		
        		
//        		int count1 = pwdtrans.getCount();
//        		if(fileList.getSelectionModel().getSelectedItem().equals("????????????")&&count1==0) {
//        			pcis=pcdao.list3("????????????");
//        		}
        		
        		
        		for(Student s:stis) {
          	      	//classoptions.add(c.Pname);
//        			int count = pwdtrans.getCount();
//        			if(count==0)break;
        			String longname = s.Sname + "    " + s.Sno;
        			namecombo.getItems().addAll(longname);
        			//System.out.println(count);
        			
          	    }
        	}
        });
	    StudentDao stdao = new StudentDao();
	    List<Student> stis =new ArrayList<Student>(); 
	    stis = stdao.list3();
	    for(Student s:stis) {
	    	String longname = s.Sname + "    " + s.Sno;
	    	namecombo.getItems().addAll(longname);
	    }
	    
	    findchoose.getItems().addAll("??????", "??????", "?????????", "??????");
		//System.out.print(group.getSelectedToggle().getUserData().toString());
	    //final ToggleGroup group = new ToggleGroup();
		findcheck.setToggleGroup(group);
		findcheck.setUserData("??????");
		insertcheck.setUserData("??????");
		insertcheck.setToggleGroup(group);
		findcheck.setSelected(true);
		findchoose.setVisible(true);
		
		classcombo.setDisable(true);
		coursecombo.setDisable(true);
		datecombo.setDisable(true);
		namecombo.setDisable(true);
//		group.getSelectedToggle().selectedProperty().addListener()
		findchoose.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        	@Override
        	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        		if(findchoose.getValue().equals("??????")) {
        			classcombo.setDisable(false);
        			namecombo.setDisable(true);
        			coursecombo.setDisable(true);
        			datecombo.setDisable(true);
        			classcombo.getSelectionModel().clearSelection();
        			namecombo.getSelectionModel().clearSelection();
        			coursecombo.getSelectionModel().clearSelection();
        			datecombo.setValue(null);
        			classcombo.setPromptText("??????");
        			namecombo.setPromptText("?????????");
        			coursecombo.setPromptText("?????????");
        			datecombo.setPromptText("?????????");
        		}
        		else if(findchoose.getValue().equals("??????")) {
        			classcombo.setDisable(true);
        			namecombo.setDisable(false);
        			coursecombo.setDisable(true);
        			datecombo.setDisable(true);
        			classcombo.getSelectionModel().clearSelection();
        			namecombo.getSelectionModel().clearSelection();
        			coursecombo.getSelectionModel().clearSelection();
        			datecombo.setValue(null);
        			classcombo.setPromptText("?????????");
        			namecombo.setPromptText("??????");
        			coursecombo.setPromptText("?????????");
        			datecombo.setPromptText("?????????");
        		}
        		else if(findchoose.getValue().equals("?????????")) {
        			classcombo.setDisable(true);
        			namecombo.setDisable(true);
        			coursecombo.setDisable(false);
        			datecombo.setDisable(true);
        			classcombo.getSelectionModel().clearSelection();
        			namecombo.getSelectionModel().clearSelection();
        			coursecombo.getSelectionModel().clearSelection();
        			datecombo.setValue(null);
        			classcombo.setPromptText("?????????");
        			namecombo.setPromptText("?????????");
        			coursecombo.setPromptText("?????????");
        			datecombo.setPromptText("?????????");
        		}
        		else if(findchoose.getValue().equals("??????")) {
        			classcombo.setDisable(true);
        			namecombo.setDisable(true);
        			coursecombo.setDisable(true);
        			datecombo.setDisable(false);
        			classcombo.getSelectionModel().clearSelection();
        			namecombo.getSelectionModel().clearSelection();
        			coursecombo.getSelectionModel().clearSelection();
        			datecombo.setValue(null);
        			classcombo.setPromptText("?????????");
        			namecombo.setPromptText("?????????");
        			coursecombo.setPromptText("?????????");
        			datecombo.setPromptText("??????(yyyy-mm-dd)");
        		}
        		else {
        			System.out.println("?????????????????????~");
        		}
        	}
        });
	    group.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ov, Toggle old_Toggle,
                Toggle new_Toggle) -> {
                    if (group.getSelectedToggle() != null) {
                    	if(group.getSelectedToggle().getUserData().toString().equals("??????")) {
                    		namecombo.getItems().clear();
                    		loadbutton.setText("??????");
                    		tablecombo.getItems().clear();
                    		findchoose.getSelectionModel().clearSelection();
                    		classcombo.getSelectionModel().clearSelection();
                    		namecombo.getSelectionModel().clearSelection();
                			coursecombo.getSelectionModel().clearSelection();
                			datecombo.setValue(null);
                    		findchoose.setVisible(true);
                    		namecombo.setDisable(true);
                    		classcombo.setDisable(true);
                			coursecombo.setDisable(true);
                			datecombo.setDisable(true);
                			StudentDao stdao1= new StudentDao();
                    	    List<Student> stis1 =new ArrayList<Student>();
                    	    stis1=stdao.list3();
                    	    for(Student s:stis1) {
                    	    	String longname = s.Sname + "    " + s.Sno;
                    	    	namecombo.getItems().addAll(longname);
                    	    }
                    	}
                    	else if(group.getSelectedToggle().getUserData().toString().equals("??????")) {
                    		loadbutton.setText("??????");
                    		tablecombo.getItems().clear();
                    		findchoose.setVisible(false);
                    		classcombo.setDisable(false);
                    		namecombo.setDisable(true);
                			coursecombo.setDisable(false);
                			datecombo.setDisable(false);
                			classcombo.getSelectionModel().clearSelection();
                			namecombo.getSelectionModel().clearSelection();
                			coursecombo.getSelectionModel().clearSelection();
                			datecombo.setValue(null);
                			classcombo.setPromptText("??????");
                			namecombo.setPromptText("??????");
                			coursecombo.setPromptText("?????????");
                			datecombo.setPromptText("??????");
                    	}
//                        final Image image = new Image(
//                            getClass().getResourceAsStream(
//                            group.getSelectedToggle().getUserData().toString() +
//                            ".jpg"));
//                    icon.setImage(image);
                }
            });
	    
	    
	    		
	    
	    
    	classid.setCellValueFactory(
                new PropertyValueFactory<>("Tclassid"));
    	classid.setCellFactory(TextFieldTableCell.<TeachRoutine>forTableColumn());
    	
        id.setCellValueFactory(
                new PropertyValueFactory<>("Tid"));
        id.setCellFactory(TextFieldTableCell.<TeachRoutine>forTableColumn());
        
        name.setCellValueFactory(
                new PropertyValueFactory<>("Tname"));
        name.setCellFactory(TextFieldTableCell.<TeachRoutine>forTableColumn());

        courseid.setCellValueFactory(
                new PropertyValueFactory<>("Tcid"));
        courseid.setCellFactory(TextFieldTableCell.<TeachRoutine>forTableColumn());
        
        coursename.setCellValueFactory(
                new PropertyValueFactory<>("Tcname"));
        coursename.setCellFactory(TextFieldTableCell.<TeachRoutine>forTableColumn());
        
        date.setCellValueFactory(
                new PropertyValueFactory<>("Tdate"));
        date.setCellFactory(TextFieldTableCell.<TeachRoutine>forTableColumn());
        
        situation.setCellValueFactory(
                new PropertyValueFactory<>("Troutine"));
        //situation.setCellFactory(TextFieldTableCell.<TeachRoutine>forTableColumn());
		classid.setStyle("-fx-alignment: CENTER;");
		id.setStyle("-fx-alignment: CENTER;");
		name.setStyle("-fx-alignment: CENTER;");
		courseid.setStyle("-fx-alignment: CENTER;");
		coursename.setStyle("-fx-alignment: CENTER;");
		date.setStyle("-fx-alignment: CENTER;");
		situation.setStyle("-fx-alignment: CENTER;");
		//tablecombo.setEditable(true);
        classid.setOnEditCommit(
                (CellEditEvent<TeachRoutine, String> t) -> {
                	
                	String onetemp = classid.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getTclassid();
                	((TeachRoutine) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setTClassid(t.getNewValue());
                	String one = t.getNewValue();
                	//System.out.println("Test");
                	String two = id.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getTid();
                    //System.out.println(tc.getCoName());
                	String three = name.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getTname();
                	String four = courseid.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getTcid();
                	String five = coursename.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getTcname();
                	String six = date.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getTdate();
                	String seven = situation.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getTroutine();
                    ACDao acdao = new ACDao();
                    List<AC> ac = new ArrayList<AC>();
                    acdao.update(Integer.valueOf(two), Integer.valueOf(four), six, seven);
                    int idstart = pwdtrans.getSno();
                    System.out.println(t.getNewValue());
                    if(acdao.update(Integer.valueOf(two), Integer.valueOf(four), six, seven)) {
                    	System.out.println("????????????");
                    	
                    }
                    else {
                    	Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("??????");
						alert.setHeaderText(null);
						alert.setContentText("????????????");
						alert.showAndWait();
                    	((TeachRoutine) t.getTableView().getItems().get(
  	                            t.getTablePosition().getRow())
  	                            ).setTcname(onetemp);
						tablecombo.refresh();
                    }
                    
            });
        tablecombo.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
		    if (newValue != null) {
		        System.out.println("Selected Person: "
		            + ((TeachRoutine) newValue).getTcname() + " | "
		            + ((TeachRoutine) newValue).getTname() 
		        );
		        TeachRoutineTrans.setClassname(((TeachRoutine) newValue).getTcname());
		        TeachRoutineTrans.setId(Integer.valueOf(((TeachRoutine) newValue).getTid()));
		        TeachRoutineTrans.setName(((TeachRoutine) newValue).getTname());
		        TeachRoutineTrans.setCourseid(Integer.valueOf(((TeachRoutine) newValue).getTcid()));
		        TeachRoutineTrans.setCoursename(((TeachRoutine) newValue).getTcname());
		        TeachRoutineTrans.setDate(((TeachRoutine) newValue).getTdate());
		        TeachRoutineTrans.setSituation(((TeachRoutine) newValue).getTroutine());
		        System.out.printf("%s\t%s\t%s\t%s\t%s\t%s\t%s\n", ((TeachRoutine) newValue).getTcname(), ((TeachRoutine) newValue).getTid(), ((TeachRoutine) newValue).getTname(), ((TeachRoutine) newValue).getTcid(), ((TeachRoutine) newValue).getTcname(), ((TeachRoutine) newValue).getTdate(), ((TeachRoutine) newValue).getTroutine());
		   }
		});
        
        
    }
    public void LoadTableView() {
    	tablecombo.getItems().clear();
    	try {
    		if(group.getSelectedToggle().getUserData().toString().equals("??????")) {
    			
    			//situation.setCellFactory(TextFieldTableCell.<TeachRoutine>forTableColumn());
    			
    		    
    			
    			System.out.println("?????????????????????");
    			if(findchoose.getValue().equals("??????")) {
    				System.out.println("????????????");
    				ACDao acdao = new ACDao();
    				List<AC> ac = new ArrayList<AC>();
//    				CourseDao cdao = new CourseDao();
//    				List<Course> course = new ArrayList<Course>();
//    				course = cdao.list2();
//    				int Cno = 0;
//    				for(Course c:course) {
//    					if(c.Cname.equals(coursecombo.getValue().toString())) {
//    						Cno = c.Cno;
//    						System.out.println(Cno);
//    					}
//    				}
    				ac = acdao.list3(classcombo.getValue().toString());
    				max = 0;
    				for(AC a:ac) {
    					System.out.printf("%d\t%d\t%s\t%s\t%s\t%s\n", a.Cno, a.Sno, a.Aname, a.APname,a.Acdate,a.Asituation);
    					data.add(new TeachRoutine(a.APname,String.valueOf(a.Sno), a.Aname, String.valueOf(a.Cno), a.Cname, a.Acdate, a.Asituation));
    					max++;
    				}
    				if(max!=0) {
    					savebutton.setDisable(false);
    					deleteall.setDisable(false);
    				}
    				else if(max==0) {
    					savebutton.setDisable(true);
    					deleteall.setDisable(true);
    				}
    				
    				totallabel.setText("????????????"+max+"???");
    				tablecombo.setItems(data);
    				tablecombo.refresh();
    			}
    			else if(findchoose.getValue().equals("?????????")) {
    				System.out.println("???????????????");
    				
    				TeachDao tdao = new TeachDao();
    			    List<TeachforUI> tis =new ArrayList<TeachforUI>();
    			    tis = tdao.list3forUI(TeachIdentity.getTno());
    			    max = 0;
    			    for(TeachforUI t:tis) {
    		    		System.out.println(t.Cno);
    		        	//coursecombo.getItems().addAll(t.Cname);
    		        	ACDao acdao = new ACDao();
    					List<AC> ac = new ArrayList<AC>();
    					ac = acdao.list6(t.Cname);
    					String match = coursecombo.getValue().toString();
    					for(AC a:ac) {
    						if(a.Cname.equals(match)) {
    							data.add(new TeachRoutine(a.APname,String.valueOf(a.Sno), a.Aname, String.valueOf(a.Cno), a.Cname, a.Acdate, a.Asituation));
    							max++;
    						}
    					}
    		    	}
    			    if(max!=0) {
    					savebutton.setDisable(false);
    					deleteall.setDisable(false);
    				}
    			    else if(max==0) {
    					savebutton.setDisable(true);
    					deleteall.setDisable(true);
    				}
    			    totallabel.setText("????????????"+max+"???");
    			    //System.out.println(data.get(max-1).getTname());
    				tablecombo.setItems(data);
    				tablecombo.refresh();
    			}
    			else if(findchoose.getValue().equals("??????")) {
    				System.out.println("????????????");
    				int idstart = 0;
    				String item = namecombo.getValue().toString();
    				for(int i=0;i<item.length();i++) {
    		    		if(Character.isDigit(item.charAt(i))) {
    		    			idstart=Integer.parseInt(item.substring(i));
    		    			//System.out.println(item.substring(i));
    		    		}
    		    	}
    				ACDao acdao = new ACDao();
    				List<AC> ac = new ArrayList<AC>();
    				ac = acdao.list5(idstart);
    				max = 0;
    				for(AC a:ac) {
    					data.add(new TeachRoutine(a.APname,String.valueOf(a.Sno), a.Aname, String.valueOf(a.Cno), a.Cname, a.Acdate, a.Asituation));
    						//break;	
    					max++;
    				}
    				if(max!=0) {
    					savebutton.setDisable(false);
    					deleteall.setDisable(false);
    				}
    				else if(max==0) {
    					savebutton.setDisable(true);
    					deleteall.setDisable(true);
    				}
    				totallabel.setText("????????????"+max+"???");
    				tablecombo.setItems(data);
    				tablecombo.refresh();
    			}
    			else if(findchoose.getValue().equals("??????")) {
    				System.out.println("????????????");
    				int idstart = 0;
    				
    				TeachDao tdao = new TeachDao();
    			    List<TeachforUI> tis =new ArrayList<TeachforUI>();
    			    tis = tdao.list3forUI(TeachIdentity.getTno());
    			    max = 0;
    			    for(TeachforUI t:tis) {
    		    		//System.out.println(t.Cno);
    		        	//coursecombo.getItems().addAll(t.Cname);
    		        	ACDao acdao = new ACDao();
    					List<AC> ac = new ArrayList<AC>();
    					ac = acdao.list4(datecombo.getValue().toString());
    					//String match = coursecombo.getValue().toString();
    					for(AC a:ac) {
    						if(t.Cno==a.Cno) {
    							data.add(new TeachRoutine(a.APname,String.valueOf(a.Sno), a.Aname, String.valueOf(a.Cno), a.Cname, a.Acdate, a.Asituation));
    							max++;
    						}
    					}
    		    	}
    			    if(max!=0) {
    					savebutton.setDisable(false);
    					deleteall.setDisable(false);
    				}
    			    else if(max==0) {
    					savebutton.setDisable(true);
    					deleteall.setDisable(true);
    				}
    			    totallabel.setText("????????????"+max+"???");
    				tablecombo.setItems(data);
    				tablecombo.refresh();
    			}
    		}
    		else if(group.getSelectedToggle().getUserData().toString().equals("??????")) {
    			ACDao acdao = new ACDao();
    			List<AC> ac = new ArrayList<AC>();
    			
    			
    			//try()
    			//int idstart = 0;
    			//String item = namecombo.getValue().toString();
//    			for(int i=0;i<item.length();i++) {
//    	    		if(Character.isDigit(item.charAt(i))) {
//    	    			idstart=Integer.parseInt(item.substring(i));
//    	    			//System.out.println(item.substring(i));
//    	    		}
//    	    	}
    			
    				String classname = classcombo.getValue().toString();
    				String date = datecombo.getValue().toString();
    				String coursename = coursecombo.getValue().toString();
    				CourseDao coursedao = new CourseDao();
    				List<Course> cis = new ArrayList<Course>();
    				cis = coursedao.list3(coursename);
    				int courseid = 0;
    				for(Course c:cis) {
    					courseid = c.getCno();
    				}
    				System.out.println(courseid);
    				if(acdao.add(classname, courseid, date)) {
    					
    				}else {
    					Alert alert = new Alert(Alert.AlertType.ERROR);
    					alert.setTitle("??????");
    					alert.setHeaderText(null);
    					alert.setContentText("???????????????????????????????????????????????????????????????");
    					alert.showAndWait();
    					
    				}
    				ac = acdao.list7(classname,coursename,date);
    				max = 0;
    				for(AC a:ac) {
    					data.add(new TeachRoutine(a.APname,String.valueOf(a.Sno), a.Aname, String.valueOf(a.Cno), a.Cname, a.Acdate, a.Asituation));
    						//break;	
    					max++;
    				}
    				if(max!=0) {
    					savebutton.setDisable(false);
    					deleteall.setDisable(false);
    				}
    				else if(max==0) {
    					savebutton.setDisable(true);
    					deleteall.setDisable(true);
    				}
    				totallabel.setText("????????????"+max+"???");
    				tablecombo.setItems(data);
    				tablecombo.refresh();
    		}
    	}catch(Exception e){
    		//System.out.println(e);
    		e.printStackTrace();
    		totallabel.setText("");
			savebutton.setDisable(true);
			deleteall.setDisable(true);
    		Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("??????");
			alert.setHeaderText(null);
			alert.setContentText("??????????????????");
			alert.showAndWait();
    	}
    	
    }
}


