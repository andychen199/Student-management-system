package admin.classinfoUI;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
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
import admin.classinfoUI.pwdtrans;
import attendance.bean.Course;
import attendance.bean.PC;
import attendance.bean.Student;
import attendance.bean.Teacher;
import attendance.dao.CourseDao;
import attendance.dao.PCDao;
import attendance.dao.StudentDao;
import attendance.dao.TeacherDao;

public class Classmessage{
	int idstart = 0;
    ListView fileList = new ListView();
    TabPane tabPane = new TabPane(); // 中间放一个Tab容器
    // 必须static 类型
    public void start(Stage stage) throws IOException {
    	initList();
        stage.setTitle("课程信息管理");
        BorderPane border = new BorderPane();
                HBox hbox = addHBox();
        border.setTop(hbox);
        //border.setLeft(addVBox());
        addStackPane(hbox); //添加一个堆栈面板到上方区域的HBox中
        Label label = new Label("色彩块");
        label.setPrefSize(200, 300);
        label.setStyle("-fx-background-color: #ffffff");
        //border.setRight(label);
        border.setLeft(fileList);
        border.setCenter(tabPane);
        //border.setCenter(addGridPane());
        //border.setRight(addFlowPane());
        //Parent root = FXMLLoader.load(getClass().getResource("Student.fxml"));
        Scene scene = new Scene(border, 884, 573);
        //ListView<String> list = new ListView<>();

        //ObservableList<String> items =FXCollections.observableArrayList (
        //        "Single", "Double", "Suite", "Family App");
        //list.setItems(items);
        
        /**
         * listView的事件处理添加
         */
        
        stage.setScene(scene);
        stage.show();
    }

    private void initList()
	{
		fileList.setPrefWidth(200);
		CourseDao dao= new CourseDao();
	    List<Course> is =new ArrayList<Course>();
	    is =dao.list2();
	    ObservableList<String> items =FXCollections.observableArrayList ();
	    for (Course b : is) {
	    	//System.out.printf("%s\t%s\t%s\t%d\t%s\t%d\t%s\t%s\n",b.Pcname,b.Pname,b.Sname,b.Sno,b.Sex,b.Sage,b.Sbirthtime,b.Snative);	           
	    	//System.out.printf("%d\t%s\t%s\t%d\t%s\t%s\n",b.Sno,b.Sname,b.Sex,b.Sage,b.Snative,b.Spassword);
	    	System.out.println();
	    	//String item = String.valueOf(b.Sname);
	    	String item = b.Cname + "    " + b.Cno;
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
		// 左侧加载examples目录下的文件
		
//		File dir = new File("examples"); 
		fileList.setItems(items);
		Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
		confirmation.setTitle("提示");
        confirmation.setHeaderText(null);
        confirmation.setContentText("确认删除？");
		fileList.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem deleteItem = new MenuItem();
            deleteItem.textProperty().bind(Bindings.format("删除 \"%s\"", cell.itemProperty()));
            deleteItem.setOnAction(event -> {
                Optional<ButtonType> result = confirmation.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK){
                	String item = fileList.getSelectionModel().getSelectedItem().toString();
                	int id=0;
                	for(int i=0;i<item.length();i++) {
        	    		if(Character.isDigit(item.charAt(i))) {
        	    			id=Integer.parseInt(item.substring(i));
        	    			//System.out.println(item.substring(i));
        	    		}
        	    	}
                	if(dao.delete(id)) {
                		try {
                    		
    						closeFile(cell.getItem());
    					} catch (Exception e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
                    	pwdtrans.setListname(cell.getItem());
                    	//System.out.println(cell.getItem());
                    	fileList.getItems().remove(cell.getItem());
                    	//System.out.println(cell.getItem());
                    	pwdtrans.setListname(cell.getItem());
                    	confirmation.hide();
                	}
                	else {
                		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setTitle("提示");
						alert.setHeaderText(null);
						alert.setContentText("删除失败");
						alert.showAndWait();
                	}
                	
                }
                
            });
            contextMenu.getItems().addAll(deleteItem);
            cell.textProperty().bind(cell.itemProperty());
            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });
            return cell ;
        });
//		File[] files = dir.listFiles();
//		for(File f : files)
//		{
//			FileItem item = new FileItem(f);
//			fileList.data().add( item );
//			System.out.println(item);
//		}
		
		// 当双击左侧时，右侧显示内容
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
        stack.setAlignment(Pos.CENTER_RIGHT); //右对齐节点

        StackPane.setMargin(helpText, new Insets(0, 10, 0, 0)); //设置问号居中显示
        hb.getChildren().add(stack); // 将StackPane添加到HBox中
        HBox.setHgrow(stack, Priority.ALWAYS); // 将HBox水平多余的所有空间都给StackPane，这样前面设置的右对齐就能保证问号按钮在最右边
    }
    public VBox addVBox(){
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10)); //内边距
        vbox.setSpacing(8); //节点间距

        Text title = new Text("Data");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);

        Hyperlink options[] = new Hyperlink[] {
                new Hyperlink("Sales"),
                new Hyperlink("Marketing"),
                new Hyperlink("Distribution"),
                new Hyperlink("Costs")};

        for (int i=0; i<4; i++){
            VBox.setMargin(options[i], new Insets(0, 0, 0, 8)); //为每个节点设置外边距
            vbox.getChildren().add(options[i]);
        }

        return vbox;
    }

    public GridPane addGridPane(){
    	GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setPadding(new Insets(50, 50, 50, 50)); //节点之间的间距
        //grid.setStyle("-fx-background-color: #336699"); //背景色
        pwdtrans.setCount(0);
        Label classlabel = new Label("课程号");
        grid.add(classlabel, 0, 0);
        
        TextField classTextField = new TextField();
        grid.add(classTextField, 1, 0);
        Label classnamelabel = new Label("课程名");
        grid.add(classnamelabel, 0, 1);
        TextField classnameTextField = new TextField();
        grid.add(classnameTextField, 1, 1);
    
        
        Button buttonsave = new Button("保存");
        grid.add(buttonsave, 0, 2);
        buttonsave.setOnAction((ActionEvent e) -> {
        	CourseDao dao= new CourseDao();
    	    List<Course> is =new ArrayList<Course>();
    	    int idint = Integer.parseInt(classlabel.getText());
    	    //int yearint = Integer.parseInt(yearcombo.getValue().toString());
    	    //dao.update1(idint, nameBox.getText(), sexcombo.getValue().toString(), yearint, birthTextField.getText(), pwdtrans.getSpno());
    	    dao.update(idint, classnameTextField.getText());
        });
        
        CourseDao dao= new CourseDao();
	    List<Course> is =new ArrayList<Course>();
	    
		int index = fileList.getSelectionModel().getSelectedIndex();
		TextField classnewTextField = new TextField();
		
		if(fileList.getSelectionModel().getSelectedItem().equals("新建课程")) {
			System.out.println("新建了一个课程啦~");
			

		}
		else {
			is =dao.list2();
			for (Course b : is) {
	        	//System.out.printf("%s\t%s\t%s\t%d\t%s\t%d\t%s\t%s\n",b.Pcname,b.Pname,b.Sname,b.Sno,b.Sex,b.Sage,b.Sbirthtime,b.Snative);	           
	        	//System.out.printf("%d\t%s\t%s\t%d\t%s\t%s\n",b.Sno,b.Sname,b.Sex,b.Sage,b.Snative,b.Spassword);
	        	System.out.println();
	        	int id1=0;
	  			String name_id = fileList.getSelectionModel().getSelectedItem().toString();
	  			for(int i=0;i<name_id.length();i++) {
	  				if(Character.isDigit(name_id.charAt(i))) {
	  					id1=Integer.parseInt(name_id.substring(i));
	  					//System.out.println(item.substring(i));
	  				}
	  			}
	    	  if(b.Cno==id1) {
	    		  String id = String.valueOf(b.Cno);
	        	  classTextField.setText(id);
	        	  classnameTextField.setText(b.Cname);
	    		  break;
	    	  }
	        }
		}
		//FileItem item = fileList.data().get(index);
		
		buttonsave.setOnAction((ActionEvent e) -> {
			
    	    int classidint = Integer.parseInt(classTextField.getText().toString());
			if(fileList.getSelectionModel().getSelectedItem().equals("新建课程")) {
//        		StudentDao dao= new StudentDao();
//        	    List<Student> is =new ArrayList<Student>();
        	    //dao.add(idnewTextField., Sname, Sex, Sage, Snative, SPno);
				//int idnewint = Integer.parseInt(idnewTextField.getText());
        		Tab newtab = findTab("新建课程");
        		
        		newtab.setText(classnameTextField.getText().toString() + "    " + classidint);
        		
        		//int SPno = classdao.findPno(classcombo.getValue().toString());
        		//System.out.printf("%d\t%s\t%s\t%d\t%s\t%d\n", idnewint, nameBox.getText().toString(), sexcombo.getValue().toString(), yearint, areaTextField.getText().toString(), SPno);
        		//dao.add(idnewint, nameBox.getText().toString(), sexcombo.getValue().toString(), yearint, telTextField.getText().toString(), xueyuancombo.getValue().toString(), birthTextField.getText().toString());
        		//dao.add(classidint, classnameTextField.getText().toString());
        		if(dao.add(classidint, classnameTextField.getText().toString())) {
        			Alert alert = new Alert(Alert.AlertType.INFORMATION);
					initList();
					//Passwordslabel.setText("123456");
					alert.setTitle("提示");
					alert.setHeaderText(null);
					alert.setContentText("保存成功");
					//pwdtrans.setpwd(pwdField.getText());
					alert.showAndWait();
        		}
				
        	}
			else {
				if(dao.update(classidint, classnameTextField.getText().toString())) {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
		    	    alert.setTitle("提示");
					alert.setHeaderText(null);
					alert.setContentText("保存成功");
					alert.showAndWait();
				}
				else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
		    	    alert.setTitle("提示");
					alert.setHeaderText(null);
					alert.setContentText("保存失败");
					alert.showAndWait();
				}
			}
        });
        return grid;
    }

    public HBox addHBox(){
    	HBox hbox = new HBox();
    	hbox.setAlignment(Pos.CENTER);
    	hbox.setPadding(new Insets(15, 12, 15, 12)); //节点到边缘的距离
        hbox.setSpacing(10);//节点之间的间距
    	hbox.setStyle("-fx-background-color: #336699"); //背景色
        
        Button buttonCurrent = new Button("刷新数据");
        buttonCurrent.setPrefSize(100, 20);

        Button buttonAdd = new Button("新增");
        buttonAdd.setPrefSize(100, 20);
        buttonAdd.setOnAction((ActionEvent e) -> {
        	fileList.getItems().add("新建课程");
        	fileList.getSelectionModel().selectLast();
        	
        	try {
				openFile("新建课程");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("提示");
//            alert.setHeaderText(null);
//            alert.setContentText("读取成功");
//            alert.showAndWait();
        });
        hbox.getChildren().addAll(buttonCurrent, buttonAdd);
        buttonCurrent.setOnAction((ActionEvent e) -> {
        	initList();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText(null);
            alert.setContentText("读取成功");
            alert.showAndWait();
        });
        return hbox;
    }
    private void onFileListDbclicked() throws Exception
	{
    	CourseDao dao= new CourseDao();
	    List<Course> is =new ArrayList<Course>();
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
		
		is =dao.list2();
		if(fileList.getSelectionModel().getSelectedItem().equals("新建课程")) {
			openFile("新建课程");
		}
		for (Course b : is) {
        	//System.out.printf("%s\t%s\t%s\t%d\t%s\t%d\t%s\t%s\n",b.Pcname,b.Pname,b.Sname,b.Sno,b.Sex,b.Sage,b.Sbirthtime,b.Snative);	           
    	  //System.out.printf("%d\t%s\t%s\t%d\t%s\t%s\n",b.Sno,b.Sname,b.Sex,b.Sage,b.Snative,b.Spassword);
    	  System.out.println();
    	  //String item = String.valueOf(b.Sname);
    	  String item = b.Cname + "    " + b.Cno;
    	  System.out.print(b);
    	  if(id==b.Cno) {
        	  openFile(item);
    	  }
    	  
        }
		
		
	}

	// 打开文件
	private void openFile(String item) throws Exception
	{
		// 查看选项卡是否已经打开
		Tab tab = findTab ( item );
		if( tab != null)
		{
			if(item.equals("新建课程")) {
				int tabIndex = tabPane.getTabs().indexOf(tab);
				tabPane.getSelectionModel().select(tabIndex);
			}
			else {
				int tabIndex = tabPane.getTabs().indexOf(tab);
				tabPane.getSelectionModel().select(tabIndex);
			}
			
			return;
		}
		
		// 打开一个新选项卡
		Node contentView = null;		
		if(true) // text file
		{
			// 注意: 这里演示的文件是GBK的
			GridPane grid = addGridPane();
			
			//String text = TextFileUtils.read(item.file, "GBK");
			//TextArea t = new TextArea();
			//t.setText( text );
			contentView = grid;
		}
		
		// 添加一个TAB页
		tab = new Tab();
		tab.setText( item );
		
		tab.setContent(contentView);
		tabPane.getTabs().add(tab);		
		int tabIndex = tabPane.getTabs().indexOf(tab);
		tabPane.getSelectionModel().select(tabIndex);
	}
	
	// 查找选项卡是否已经打开
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
		// 查看选项卡是否已经打开
		Tab tab = findTab ( item );
		if( tab != null)
		{
			if(item.equals("新建课程")) {
				int tabIndex = tabPane.getTabs().indexOf(tab);
				tabPane.getTabs().remove(tabIndex);
			}
			else {
				int tabIndex = tabPane.getTabs().indexOf(tab);
				tabPane.getSelectionModel().select(tabIndex);
				tabPane.getTabs().remove(tabIndex);
			}
			return;
		}
	}
}
