package admin.teachinfoUI;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import admin.studentUI.AfImagePane;
import admin.studentUI.FileItem;
import admin.studentUI.TextFileUtils;
import admin.teacherUI.pwdtrans;
import attendance.bean.PC;
import attendance.bean.Student;
import attendance.bean.Teach;
import attendance.bean.Teacher;
import attendance.bean.TeachforUI;
import attendance.dao.PCDao;
import attendance.dao.StudentDao;
import attendance.dao.TeachDao;
import attendance.dao.TeacherDao;

public class TeachInfoUI{
	int idstart = 0;
    ListView fileList = new ListView();
    TabPane tabPane = new TabPane(); // ???????????????Tab??????
    // ??????static ??????
    final ObservableList<TeachCourse> data = FXCollections.observableArrayList();
    public void start(Stage stage) throws IOException {
    	initList();
//    	tabPane.getSelectionModel().selectedItemProperty().addListener(
//    		    new ChangeListener<Tab>() {
//    		        @Override
//    		        public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
//    		        	
//    		        	System.out.println("Tab Selection changed");
//    		        	String item = tabPane.getSelectionModel().getSelectedItem().toString();
//    		        	try {
//							openFile1(item);
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//    		        }
//    		    }
//    		);
        stage.setTitle("????????????????????????");
        BorderPane border = new BorderPane();
                HBox hbox = addHBox();
        border.setTop(hbox);
        //border.setLeft(addVBox());
        addStackPane(hbox); //??????????????????????????????????????????HBox???
        Label label = new Label("?????????");
        label.setPrefSize(200, 300);
        label.setStyle("-fx-background-color: #ffffff");
        //border.setRight(label);
        border.setLeft(fileList);
        border.setCenter(tabPane);
        //border.setCenter(addGridPane());
        //border.setRight(addFlowPane());
        //Parent root = FXMLLoader.load(getClass().getResource("Student.fxml"));
        Scene scene = new Scene(border, 1327, 547);
        //ListView<String> list = new ListView<>();

        //ObservableList<String> items =FXCollections.observableArrayList (
        //        "Single", "Double", "Suite", "Family App");
        //list.setItems(items);
        
        /**
         * listView?????????????????????
         */
        
        stage.setScene(scene);
        stage.show();
    }
    public static class TeachCourse {

        private final SimpleStringProperty Cono;
        private final SimpleStringProperty CoName;
        private final SimpleStringProperty Coschool;
        private final SimpleStringProperty Cograde;

        private TeachCourse(String Cono1, String CoName1, String Coschool1, String Cograde1) {
            this.Cono = new SimpleStringProperty(Cono1);
            this.CoName = new SimpleStringProperty(CoName1);
            this.Coschool = new SimpleStringProperty(Coschool1);
            this.Cograde = new SimpleStringProperty(Cograde1);
        }

        public String getCono() {
            return Cono.get();
        }

        public void setCono(String Cono1) {
        	Cono.set(Cono1);
        }

        public String getCoName() {
            return CoName.get();
        }

        public void setCoName(String CoName1) {
        	CoName.set(CoName1);
        }

        public String getCoschool() {
            return Coschool.get();
        }

        public void setCoschool(String Coschool1) {
        	Coschool.set(Coschool1);
        }
        public String getCograde() {
            return Cograde.get();
        }

        public void setCograde(String Cograde1) {
        	Cograde.set(Cograde1);
        }

    }
    private void initList()
	{
		fileList.setPrefWidth(200);
		TeacherDao dao= new TeacherDao();
	    List<Teacher> is =new ArrayList<Teacher>();
	    is =dao.list3();
	    ObservableList<String> items =FXCollections.observableArrayList ();
	    for (Teacher b : is) {
	    	//System.out.printf("%s\t%s\t%s\t%d\t%s\t%d\t%s\t%s\n",b.Pcname,b.Pname,b.Sname,b.Sno,b.Sex,b.Sage,b.Sbirthtime,b.Snative);	           
	    	//System.out.printf("%d\t%s\t%s\t%d\t%s\t%s\n",b.Sno,b.Sname,b.Sex,b.Sage,b.Snative,b.Spassword);
	    	System.out.println();
	    	//String item = String.valueOf(b.Sname);
	    	String item = b.Tname + "    " + b.Tno;
	    	items.add(item);
	    	//System.out.println(item.length());
	    	//Character.isDigit(item.charAt(9));
	    	
	    	
	    	for(int i=0;i<item.length();i++) {
	    		if(Character.isDigit(item.charAt(i))) {
	    			idstart=Integer.parseInt(item.substring(i));
	    			//System.out.println(item.substring(i));
	    		}
	    	}
	    	System.out.println(idstart);
        }
		// ????????????examples??????????????????

		fileList.setItems(items);
		
		// ???????????????????????????????????????
		fileList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event)
			{
				if(event.getClickCount()== 2)
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
	}
    public void addStackPane(HBox hb){
        StackPane stack = new StackPane();
        Rectangle helpIcon = new Rectangle(30.0, 25.0);
        helpIcon.setFill(new LinearGradient(0,0,0,1, true, CycleMethod.NO_CYCLE,
                new Stop[]{
                        new Stop(0,Color.web("#4977A3")),
                        new Stop(0.5, Color.web("#B0C6DA")),
                        new Stop(1,Color.web("#9CB6CF")),}));
        helpIcon.setStroke(Color.web("#D0E6FA"));
        helpIcon.setArcHeight(3.5);
        helpIcon.setArcWidth(3.5);

        Text helpText = new Text("?");
        helpText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        helpText.setFill(Color.WHITE);
        helpText.setStroke(Color.web("#7080A0"));

        stack.getChildren().addAll(helpIcon, helpText);
        stack.setAlignment(Pos.CENTER_RIGHT); //???????????????

        StackPane.setMargin(helpText, new Insets(0, 10, 0, 0)); //????????????????????????
        hb.getChildren().add(stack); // ???StackPane?????????HBox???
        HBox.setHgrow(stack, Priority.ALWAYS); // ???HBox?????????????????????????????????StackPane?????????????????????????????????????????????????????????????????????
    }
    public VBox addVBox(){
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10)); //?????????
        vbox.setSpacing(8); //????????????

        Text title = new Text("Data");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);

        Hyperlink options[] = new Hyperlink[] {
                new Hyperlink("Sales"),
                new Hyperlink("Marketing"),
                new Hyperlink("Distribution"),
                new Hyperlink("Costs")};

        for (int i=0; i<4; i++){
            VBox.setMargin(options[i], new Insets(0, 0, 0, 8)); //??????????????????????????????
            vbox.getChildren().add(options[i]);
        }

        return vbox;
    }
    public TableView addTableView() {
    	
    	TableView table = new TableView();
    	data.clear();
    	table.setEditable(true);
    	TableColumn<TeachCourse, String> firstCol = 
                new TableColumn<>("?????????");
    	firstCol.setCellValueFactory(
                new PropertyValueFactory<>("Cono"));

            firstCol.setCellFactory(TextFieldTableCell.<TeachCourse>forTableColumn());
            
            table.setRowFactory( tv -> {
            	System.out.println("Test");
    	  		TableRow<TeachCourse> row = new TableRow<TeachCourse>();
    	  		String item = fileList.getSelectionModel().getSelectedItem().toString();
    	  		for(int i=0;i<item.length();i++) {
    	    		if(Character.isDigit(item.charAt(i))) {
    	    			idstart=Integer.parseInt(item.substring(i));
    	    			//System.out.println(item.substring(i));
    	    		}
    	    	}TeachCourse tc=null;
    	  		 tc = row.getItem();
    	                    
    	            
    	            return row ;
    	        });
            
//            table.setRowFactory( tv -> {
//            	  		TableRow<TeachCourse> row = new TableRow<TeachCourse>();
//            	            row.setOnMouseClicked(event -> {
//            	                
//								if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
//            	                    TeachCourse tc = row.getItem();
//            	                    System.out.println(tc.getCoName());
//            	                }
//            	            });
//            	            return row ;
//            	        });
            
            TableColumn<TeachCourse, String> secondCol = 
                    new TableColumn<>("?????????");
            secondCol.setCellValueFactory(
                    new PropertyValueFactory<>("CoName"));
            secondCol.setCellFactory(TextFieldTableCell.<TeachCourse>forTableColumn());
            
            TableColumn<TeachCourse, String> thirdCol = 
                    new TableColumn<>("????????????");
            thirdCol.setCellValueFactory(
                    new PropertyValueFactory<>("Coschool"));
            thirdCol.setCellFactory(TextFieldTableCell.<TeachCourse>forTableColumn());

            TableColumn<TeachCourse, String> fourthCol = 
                    new TableColumn<>("??????");
            fourthCol.setCellValueFactory(
                    new PropertyValueFactory<>("Cograde"));
            fourthCol.setCellFactory(TextFieldTableCell.<TeachCourse>forTableColumn());
            
    	
    	firstCol.setPrefWidth(170);
    	secondCol.setPrefWidth(270);
    	thirdCol.setPrefWidth(120);
    	fourthCol.setPrefWidth(120);
    	table.getColumns().addAll(firstCol,secondCol,thirdCol,fourthCol);
    	String item = fileList.getSelectionModel().getSelectedItem().toString();
  		
  		
        for(int i=0;i<item.length();i++) {
    		if(Character.isDigit(item.charAt(i))) {
    			idstart=Integer.parseInt(item.substring(i));
    			pwdtrans.setPno(idstart);
    			//System.out.println(item.substring(i));
    		}
        }
    	firstCol.setOnEditCommit(
                (CellEditEvent<TeachCourse, String> t) -> {
                	
                	String onetemp = firstCol.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getCono();
                	((TeachCourse) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setCono(t.getNewValue());
                	String one = t.getNewValue();
                	//System.out.println("Test");
                	String two = secondCol.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getCoName();
                    //System.out.println(tc.getCoName());
                	String three = thirdCol.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getCoschool();
                	String four = fourthCol.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getCograde();
                    TeachDao dao = new TeachDao();
                    List<Teach>is = new ArrayList<Teach>();
                    int idstart = pwdtrans.getSno();
                    System.out.println(t.getNewValue());
                    if(dao.update(idstart, Integer.valueOf(onetemp) , idstart, Integer.valueOf(t.getNewValue()), Integer.valueOf(three), Integer.valueOf(four))) {
                    	
                    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("??????");
						alert.setHeaderText(null);
						alert.setContentText("????????????");
						alert.showAndWait();
                    }
                    else {
                    	((TeachCourse) t.getTableView().getItems().get(
  	                            t.getTablePosition().getRow())
  	                            ).setCoName(onetemp);
						table.refresh();
                    }
                    
            });
    	
    	
    	secondCol.setOnEditCommit(
                (CellEditEvent<TeachCourse, String> t) -> {
                	
//                	String onetemp = firstCol.getTableView().getItems().get(
//                            t.getTablePosition().getRow()).getCono();
//                	((TeachCourse) t.getTableView().getItems().get(
//                            t.getTablePosition().getRow())
//                            ).setCono(t.getNewValue());
                	String one = firstCol.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getCono();
                	
                	String twotemp = secondCol.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getCoName();
                	//String two = t.getNewValue();
                    //System.out.println(tc.getCoName());
                	String three = thirdCol.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getCoschool();
                	String four = fourthCol.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getCograde();
                    TeachDao dao = new TeachDao();
                    List<Teach>is = new ArrayList<Teach>();
                    int idstart = pwdtrans.getSno();
                    System.out.println(t.getNewValue());
                    //if(dao.update(idstart, Integer.valueOf(twotemp) , idstart, Integer.valueOf(t.getNewValue()), Integer.valueOf(three), Integer.valueOf(four))) {
                    	
                    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("??????");
						alert.setHeaderText(null);
						alert.setContentText("?????????????????????????????????????????????????????????");
						
						alert.showAndWait();
						System.out.println(twotemp);
						((TeachCourse) t.getTableView().getItems().get(
  	                            t.getTablePosition().getRow())
  	                            ).setCoName(twotemp);
						table.refresh();
						
                    
                    
            });
    	
    	
    	thirdCol.setOnEditCommit(
                (CellEditEvent<TeachCourse, String> t) -> {
                	//System.out.println("Test");
                	String one = firstCol.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getCono();
                	String two = secondCol.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getCoName();
                    //System.out.println(tc.getCoName());
                	String threetemp = thirdCol.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getCoschool();
                	String three = t.getNewValue();
                	String four = fourthCol.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getCograde();
                    TeachDao dao = new TeachDao();
                    List<Teach>is = new ArrayList<Teach>();
                    int idstart = pwdtrans.getSno();
                    System.out.println(t.getNewValue());
                    if(dao.update(idstart, Integer.valueOf(one) , idstart, Integer.valueOf(one), Integer.valueOf(three), Integer.valueOf(four))) {
                    	
                    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("??????");
						alert.setHeaderText(null);
						alert.setContentText("????????????");
						alert.showAndWait();
                    }
                    else {
                    	((TeachCourse) t.getTableView().getItems().get(
  	                            t.getTablePosition().getRow())
  	                            ).setCoName(threetemp);
						table.refresh();
                    }
                    
            });
    	
    	
    	fourthCol.setOnEditCommit(
                (CellEditEvent<TeachCourse, String> t) -> {
                	
                	String one = firstCol.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getCono();
                	String two = secondCol.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getCoName();
                    //System.out.println(tc.getCoName());
                	String three = thirdCol.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getCoschool();
                	String fourtemp = fourthCol.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getCograde();
                	String four = t.getNewValue();
                    TeachDao dao = new TeachDao();
                    List<Teach>is = new ArrayList<Teach>();
                    int idstart = pwdtrans.getSno();
                    System.out.println(t.getNewValue());
                    if(dao.update(idstart, Integer.valueOf(one) , idstart, Integer.valueOf(one), Integer.valueOf(three), Integer.valueOf(four))) {
                    	
                    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("??????");
						alert.setHeaderText(null);
						alert.setContentText("????????????");
						alert.showAndWait();
                    }
                    else {
                    	((TeachCourse) t.getTableView().getItems().get(
  	                            t.getTablePosition().getRow())
  	                            ).setCoName(fourtemp);
						table.refresh();
                    }
                    
            });
    	TeachDao dao = new TeachDao();
    	List<TeachforUI> is = new ArrayList<TeachforUI>();
    	
    	is = dao.list3forUI(idstart);
    	for(TeachforUI t:is) {
    		System.out.printf("\n%d\t%s\t%d\t%d\n", t.Cno,t.Cname,t.Tschool,t.Tsgrade);
    		
    		data.add(new TeachCourse(String.valueOf(t.Cno),t.Cname,String.valueOf(t.Tschool),String.valueOf(t.Tsgrade)));
    		//data.add(new TeachCourse("Jacob", "Smith", "jacob.smith@example.com", "Test"));

    		System.out.println();
    	}
    	
    		table.setItems(data);

    	return table;
    }
public void SeletedTabPane(String item) {
    	
//    	TableView table = new TableView();
//    	
//    	table.setEditable(true);
//    	TableColumn firstCol = new TableColumn("?????????");
//    	TableColumn secondCol = new TableColumn("?????????");
//    	TableColumn thirdCol = new TableColumn("????????????");
//    	TableColumn fourthCol = new TableColumn("??????");
//    	firstCol.setPrefWidth(170);
//    	secondCol.setPrefWidth(270);
//    	thirdCol.setPrefWidth(120);
//    	fourthCol.setPrefWidth(120);
//    	table.getColumns().addAll(firstCol,secondCol,thirdCol,fourthCol);
    	
    	TeachDao dao = new TeachDao();
    	List<TeachforUI> is = new ArrayList<TeachforUI>();
    	
    	
    	int idstart = 0;
    	for(int i=0;i<item.length();i++) {
    		if(Character.isDigit(item.charAt(i))) {
    			idstart=Integer.parseInt(item.substring(i));
    			//System.out.println(item.substring(i));
    		}
    	}
    	is = dao.list3forUI(idstart);
    	for(TeachforUI t:is) {
    		System.out.printf("\n%d\t%s\t%d\t%d\n", t.Cno,t.Cname,t.Tschool,t.Tsgrade);
    		data.add(new TeachCourse(String.valueOf(t.Cno),t.Cname,String.valueOf(t.Tschool),String.valueOf(t.Tsgrade)));
    	}
    	
    }

    public HBox addHBox(){
    	HBox hbox = new HBox();
    	hbox.setAlignment(Pos.CENTER);
    	hbox.setPadding(new Insets(15, 12, 15, 12)); //????????????????????????
        hbox.setSpacing(10);//?????????????????????
    	hbox.setStyle("-fx-background-color: #336699"); //?????????
        
        Button buttonCurrent = new Button("????????????");
        buttonCurrent.setPrefSize(100, 20);

        Button buttonAdd = new Button("????????????(????????????)");
        TextField Cno = new TextField();
        TextField Cname = new TextField();
        TextField Tschool = new TextField();
        TextField Tsgrade = new TextField();
        Cno.setPromptText("?????????");
        Cname.setPromptText("?????????");
        Tschool.setPromptText("????????????");
        Tsgrade.setPromptText("??????");
        //buttonAdd.setPrefSize(100, 20);
        buttonAdd.setOnAction((ActionEvent e) -> {
//        	data.add(new TeachforUI(
//        			Integer.parseInt(Cno.getText()),
//                	Cname.getText().toString(),
//                	Integer.parseInt(Tschool.getText()),
//                	Integer.parseInt(Tsgrade.getText())
//                	));
        	TeachDao dao = new TeachDao();
        	if(tabPane.getTabs().isEmpty()) {
				Alert alert1 = new Alert(Alert.AlertType.ERROR);
				alert1.setTitle("??????");
				alert1.setHeaderText(null);
				alert1.setContentText("??????????????????????????????????????????");
				alert1.showAndWait();
			}
        	else {
        		if(dao.add(idstart, Integer.valueOf(Cno.getText()), Integer.valueOf(Tschool.getText()), Integer.valueOf(Tsgrade.getText()))) {
                	data.add(new TeachCourse(String.valueOf(Cno.getText()),Cname.getText().toString(),Tschool.getText(),Tsgrade.getText()));

            		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    				alert.setTitle("??????");
    				alert.setHeaderText(null);
    				alert.setContentText("????????????");
    				alert.showAndWait();
    				Cno.clear();
    				Cname.clear();
    				Tschool.clear();
    				Tsgrade.clear();
            	}
            	else {
            		Alert alert = new Alert(Alert.AlertType.ERROR);
    				alert.setTitle("??????");
    				alert.setHeaderText(null);
    				alert.setContentText("????????????????????????????????????????????????????????????");
    				alert.showAndWait();
    				
            	}
        	}
        	
        });
        hbox.getChildren().addAll(buttonCurrent, buttonAdd,Cno ,Cname, Tschool, Tsgrade);
        buttonCurrent.setOnAction((ActionEvent e) -> {
        	initList();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("??????");
            alert.setHeaderText(null);
            alert.setContentText("????????????");
            alert.showAndWait();
        });
        return hbox;
    }
    private void onFileListDbclicked() throws Exception
	{
    	TeacherDao dao= new TeacherDao();
	    List<Teacher> is =new ArrayList<Teacher>();
	    int id=0;
		int index = fileList.getSelectionModel().getSelectedIndex();
		String name_id = fileList.getSelectionModel().getSelectedItem().toString();
		for(int i=0;i<name_id.length();i++) {
    		if(Character.isDigit(name_id.charAt(i))) {
    			id=Integer.parseInt(name_id.substring(i));
    			//System.out.println(item.substring(i));
    		}
    	}
		//FileItem item = fileList.data().get(index);
		
		is =dao.list3();
		
		for (Teacher b : is) {
        	//System.out.printf("%s\t%s\t%s\t%d\t%s\t%d\t%s\t%s\n",b.Pcname,b.Pname,b.Sname,b.Sno,b.Sex,b.Sage,b.Sbirthtime,b.Snative);	           
    	  //System.out.printf("%d\t%s\t%s\t%d\t%s\t%s\n",b.Sno,b.Sname,b.Sex,b.Sage,b.Snative,b.Spassword);
    	  System.out.println();
    	  //String item = String.valueOf(b.Sname);
    	  String item = b.Tname + "    " + b.Tno;
    	  Tab tab = null;
    	  tab = findTab(item);
    	  if(tab!=null) {
    		  tabPane.getTabs().remove(tab);
    	  }
    	  
    	  System.out.print(b);
    	  if(id==b.Tno) {
    		  
        	  openFile(item);
    	  }
    	  
        }
		
		
	}
    private void openFile1(String item) throws Exception
	{
		// ?????????????????????????????????
		Tab tab = findTab ( item );
		if( tab != null)
		{
			int tabIndex = tabPane.getTabs().indexOf(tab);
			tabPane.getSelectionModel().select(tabIndex);
			SeletedTabPane(item);
		}
	}
	// ????????????
	private void openFile(String item) throws Exception
	{
		// ?????????????????????????????????
		Tab tab = findTab ( item );
		if( tab != null)
		{
			int tabIndex = tabPane.getTabs().indexOf(tab);
			tabPane.getSelectionModel().select(tabIndex);	
			return;
		}

		
		// ????????????????????????
		Node contentView = null;		
		if(true) // text file
		{
			// ??????: ????????????????????????GBK???
			//GridPane grid = addGridPane();
			TableView table = addTableView();
			//String text = TextFileUtils.read(item.file, "GBK");
			//TextArea t = new TextArea();
			//t.setText( text );
			contentView = table;
		}
		
		// ????????????TAB???
		tab = new Tab();
		tab.setText( item );
		
		tab.setContent(contentView);
		tabPane.getTabs().add(tab);		
		int tabIndex = tabPane.getTabs().indexOf(tab);
		tabPane.getSelectionModel().select(tabIndex);
	}
	
	// ?????????????????????????????????
	private Tab findTab (String item)
	{
		ObservableList<Tab> tabs = tabPane.getTabs();
		for(int i=0; i< tabs.size(); i++)
		{
			Tab t = tabs.get(i);
			if( t.getText().equals(item))
			{
				return t;
			}
		}
		return null;
	}

	private void closeFile(String item) throws Exception
	{
		// ?????????????????????????????????
		Tab tab = findTab ( item );
		if( tab != null)
		{
			if(item.equals("????????????")) {
				int tabIndex = tabPane.getTabs().indexOf(tab);
				tabPane.getTabs().remove(tabIndex);
				
			}
			else {
				int tabIndex = tabPane.getTabs().indexOf(tab);
				for(int i=0;i<tabIndex;i++) {
					tabPane.getSelectionModel().select(tabIndex);
					tabPane.getTabs().remove(tabIndex);
					
					
				}
				
				
			}
			return;
		}
	}
}
