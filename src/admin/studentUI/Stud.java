package admin.studentUI;

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
import admin.studentUI.pwdtrans;
import attendance.bean.PC;
import attendance.bean.Student;
import attendance.dao.PCDao;
import attendance.dao.StudentDao;

public class Stud{
	int idstart = 0;
    ListView fileList = new ListView();
    TabPane tabPane = new TabPane(); // 中间放一个Tab容器
    // 必须static 类型
    public void start(Stage stage) throws IOException {
    	initList();
        stage.setTitle("学生信息管理");
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
		StudentDao dao= new StudentDao();
	    List<Student> is =new ArrayList<Student>();
	    is =dao.list3();
	    ObservableList<String> items =FXCollections.observableArrayList ();
	    for (Student b : is) {
	    	System.out.printf("%s\t%s\t%s\t%d\t%s\t%d\t%s\t%s\n",b.Pcname,b.Pname,b.Sname,b.Sno,b.Sex,b.Sage,b.Sbirthtime,b.Snative);	           
	    	//System.out.printf("%d\t%s\t%s\t%d\t%s\t%s\n",b.Sno,b.Sname,b.Sex,b.Sage,b.Snative,b.Spassword);
	    	System.out.println();
	    	//String item = String.valueOf(b.Sname);
	    	String item = b.Sname + "    " + b.Sno;
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
						alert.setContentText("修改失败");
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
        grid.setPadding(new Insets(35, 35, 35, 35)); //节点之间的间距
        //grid.setStyle("-fx-background-color: #336699"); //背景色
        pwdtrans.setCount(0);
        Label idlabel = new Label("学号");
        grid.add(idlabel, 0, 0);
        
        Label idTextField = new Label();
        grid.add(idTextField, 1, 0);
        Label namelabel = new Label("姓名");
        grid.add(namelabel, 0, 1);
        TextField nameBox = new TextField();
        grid.add(nameBox, 1, 1);
        Label yearlabel = new Label("年龄");
        grid.add(yearlabel, 0, 2);
        
        int[] year = new int[99];
        ObservableList<Integer> options = 
        	    FXCollections.observableArrayList();
        final ComboBox yearcombo = new ComboBox(options);
        for(int i=0;i<99;i++) {
        	year[i]=i+1;
        	yearcombo.getItems().addAll(year[i]);
        }
        grid.add(yearcombo, 1, 2);
        //final ComboBox yearcombo = new ComboBox();
        ObservableList<String> sex = 
        	    FXCollections.observableArrayList("男","女");
        final ComboBox sexcombo = new ComboBox(sex);
        grid.add(sexcombo, 1, 3);
        Label sexlabel = new Label("性别");
        grid.add(sexlabel, 0, 3);
        Label arealabel = new Label("籍贯");
        grid.add(arealabel, 0, 4);
        TextField areaTextField = new TextField();
        grid.add(areaTextField, 1, 4);
        
        PCDao pcdao= new PCDao();
	    List<PC> pcis =new ArrayList<PC>();
	    pcis=pcdao.list2();
        Label xueyuanlabel = new Label("所在系");
        grid.add(xueyuanlabel, 0, 5);
        ComboBox xueyuancombo = new ComboBox();
        for(PC c:pcis) {
  	      	//classoptions.add(c.Pname);
  	      	xueyuancombo.getItems().addAll(c.Pcname);
  	    }
        grid.add(xueyuancombo, 1, 5);
        Label SPnolabel = new Label("专业班级");
        grid.add(SPnolabel, 0, 6);
        ObservableList<String> classoptions = 
        	    FXCollections.observableArrayList();
        ComboBox classcombo = new ComboBox(classoptions);
        
        
//  	    for(PC c:pcis) {
//  	      	//classoptions.add(c.Pname);
//  	    	
//  	    		classcombo.getItems().addAll(c.Pname);
//    			
//  	      	
//  	    }
        
        grid.add(classcombo, 1, 6);
        xueyuancombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        	@Override
        	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        		PCDao pcdao= new PCDao();
        	    List<PC> pcis =new ArrayList<PC>();
        	    pcis=pcdao.list2();
        		for(PC c:pcis) {
          	      	//classoptions.add(c.Pname);
        			
        			classcombo.getItems().removeAll(c.Pname);
        			
          	    }
        		String xueyuan = xueyuancombo.getValue().toString();
        		pcis=pcdao.list3(xueyuan);
        		int count1 = pwdtrans.getCount();
        		if(fileList.getSelectionModel().getSelectedItem().equals("新建学生")&&count1==0) {
        			pcis=pcdao.list3("计算机系");
        		}
        		
        		
        		for(PC c:pcis) {
          	      	//classoptions.add(c.Pname);
        			int count = pwdtrans.getCount();
        			if(count==0)break;
        			classcombo.getItems().addAll(c.Pname);
        			System.out.println(count);
        			
          	    }
        	}
        });
        //TextField SPnoTextField = new TextField();
        //grid.add(SPnoTextField, 1, 5);
        Label Passwordlabel = new Label("学生端密码");
        grid.add(Passwordlabel, 0, 7);
        Label Passwordslabel = new Label("default");
        grid.add(Passwordslabel, 1, 7);
        Button buttonchangepassword = new Button("修改密码");
        grid.add(buttonchangepassword, 2, 7);
        
        Button buttonsave = new Button("保存");
        grid.add(buttonsave, 0, 8);
        
//        Button buttonCurrent = new Button("读取数据");
//        buttonCurrent.setPrefSize(100, 20);
//        Button buttonProjected = new Button("Projected");
//        buttonProjected.setPrefSize(100, 20);
        //grid.getChildren().addAll(buttonCurrent, buttonProjected);
        buttonchangepassword.setOnAction((ActionEvent e) -> {
        	Platform.runLater(() -> {//创建修改密码界面窗口
       
        	try {
        		
				//new changepassword().start(new Stage());
//				stage.setTitle("修改密码");
		        //Parent root = FXMLLoader.load(getClass().getResource("changepassword.fxml"));
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("changepassword.fxml"));
		        Parent root1 =loader.load();
		       // pwdget controller = loader.getController();
		        Scene scene1 = new Scene(root1, 419, 210);
		        Stage stage1 = new Stage();
		        //controller.initpwd();
		          stage1.setScene(scene1);
		          stage1.setTitle("修改密码");
		          changeController controller = loader.getController();
		          controller.Initpwd();
		          String index1 = fileList.getSelectionModel().getSelectedItem().toString();
		          pwdtrans.setName(index1);
		          stage1.setOnHiding(new EventHandler<WindowEvent>() {
		              @Override
		              public void handle(WindowEvent event) {
		                  System.out.println("监听到窗口关闭1");
		                  
		                  
		                  Passwordslabel.setText(pwdtrans.getpwd());
		              }
		          });
		          stage1.show();
		          
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}//关闭登陆窗口
       
                
        });
        });
        
        StudentDao dao= new StudentDao();
	    List<Student> is =new ArrayList<Student>();
	    PCDao classdao= new PCDao();
	    List<PC> classis =new ArrayList<PC>();
		int index = fileList.getSelectionModel().getSelectedIndex();
		TextField idnewTextField = new TextField();
		//TextField pwdnewTextField = new TextField();
		idnewTextField.setVisible(false);
		//pwdnewTextField.setVisible(false);
		if(fileList.getSelectionModel().getSelectedItem().equals("新建学生")) {
			System.out.println("新建了一个学生啦~");
			//Passwordslabel.setVisible(false);
			buttonchangepassword.setDisable(true);
			idTextField.setVisible(false);
			idnewTextField.setVisible(true);
			//pwdnewTextField.setVisible(true);
	        grid.add(idnewTextField, 1, 0);
	        classis = classdao.list2();
      	  
      	  for (PC c : classis) {
      		  
      		  //xueyuancombo.setValue(c.Pcname);
      		  //String xueyuan = c.Pcname;
//      		  List<PC> classis1 = classdao.list3(c.Pcname);
//      		  for (PC c1 : classis1) {
      		  classcombo.getItems().addAll(c.Pname);
      		  
      		  pwdtrans.setCount(1);
      		  }
//      	  }
	        //grid.add(pwdnewTextField, 1, 7);
		}
		else {
			is =dao.list3();
			for (Student b : is) {
	        	System.out.printf("%s\t%s\t%s\t%d\t%s\t%d\t%s\t%s\n",b.Pcname,b.Pname,b.Sname,b.Sno,b.Sex,b.Sage,b.Sbirthtime,b.Snative);	           
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
	    	  if(b.Sno==id1) {
	    		  String id = String.valueOf(b.Sno);
	    		  idTextField.setVisible(true);
	    		  buttonchangepassword.setDisable(false);
	    		  //Passwordslabel.setVisible(true);
	        	  idTextField.setText(id);
	        	  nameBox.setText(b.Sname);
	        	  yearcombo.setValue(b.Sage);
	        	  sexcombo.setValue(b.Sex);
	        	  areaTextField.setText(b.Snative);
	        	  classis = classdao.list1(b.SPno);
	        	  pwdtrans.setSpno(b.SPno);
	        	  for (PC c : classis) {
	        		  
	        		  xueyuancombo.setValue(c.Pcname);
	        		  String xueyuan = c.Pcname;
	        		  List<PC> classis1 = classdao.list3(c.Pcname);
	        		  for (PC c1 : classis1) {
	        		  classcombo.getItems().addAll(c1.Pname);
	        		  classcombo.setValue(c.Pname);
	        		  pwdtrans.setCount(1);
	        		  }
	        	  }
	        	  //String SPno = String.valueOf(b.SPno);
	        	  
	        	  
	        	  Passwordslabel.setText(b.Spassword);
	        	  pwdtrans.setPno(b.Sno);
	        	  pwdtrans.setpwd(b.Spassword);
	    		  break;
	    	  }
	        }
		}
		//FileItem item = fileList.data().get(index);
		
		buttonsave.setOnAction((ActionEvent e) -> {
			
    	    int yearint = Integer.parseInt(yearcombo.getValue().toString());
			if(fileList.getSelectionModel().getSelectedItem().equals("新建学生")) {
//        		StudentDao dao= new StudentDao();
//        	    List<Student> is =new ArrayList<Student>();
        	    //dao.add(idnewTextField., Sname, Sex, Sage, Snative, SPno);
				int idnewint = Integer.parseInt(idnewTextField.getText());
        		Tab newtab = findTab("新建学生");
        		
        		newtab.setText(nameBox.getText().toString() + "    " + idnewint);
        		
        		int SPno = classdao.findPno(classcombo.getValue().toString());
        		System.out.printf("%d\t%s\t%s\t%d\t%s\t%d\n", idnewint, nameBox.getText().toString(), sexcombo.getValue().toString(), yearint, areaTextField.getText().toString(), SPno);
        		if(dao.add(idnewint, nameBox.getText().toString(), sexcombo.getValue().toString(), yearint, areaTextField.getText().toString(), SPno)) {
        			Alert alert = new Alert(Alert.AlertType.INFORMATION);
					initList();
					Passwordslabel.setText("123456");
					alert.setTitle("提示");
					alert.setHeaderText(null);
					alert.setContentText("保存成功");
					//pwdtrans.setpwd(pwdField.getText());
					alert.showAndWait();
        		}
				
        	}
			else {
				int idint = Integer.parseInt(idTextField.getText());
				int SPno = classdao.findPno(classcombo.getValue().toString());
	    	    //dao.update1(idint, nameBox.getText(), sexcombo.getValue().toString(), yearint, areaTextField.getText(), pwdtrans.getSpno());
	    	    dao.update1(idint, nameBox.getText().toString(), sexcombo.getValue().toString(), yearint, areaTextField.getText().toString(), SPno);
	    	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    	    alert.setTitle("提示");
				alert.setHeaderText(null);
				alert.setContentText("保存成功");
				//pwdtrans.setpwd(pwdField.getText());
				alert.showAndWait();
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
        	fileList.getItems().add("新建学生");
        	fileList.getSelectionModel().selectLast();
        	
        	try {
				openFile("新建学生");
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
    	StudentDao dao= new StudentDao();
	    List<Student> is =new ArrayList<Student>();
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
		if(fileList.getSelectionModel().getSelectedItem().equals("新建学生")) {
			openFile("新建学生");
		}
		for (Student b : is) {
        	System.out.printf("%s\t%s\t%s\t%d\t%s\t%d\t%s\t%s\n",b.Pcname,b.Pname,b.Sname,b.Sno,b.Sex,b.Sage,b.Sbirthtime,b.Snative);	           
    	  //System.out.printf("%d\t%s\t%s\t%d\t%s\t%s\n",b.Sno,b.Sname,b.Sex,b.Sage,b.Snative,b.Spassword);
    	  System.out.println();
    	  //String item = String.valueOf(b.Sname);
    	  String item = b.Sname + "    " + b.Sno;
    	  System.out.print(b);
    	  if(id==b.Sno) {
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
			if(item.equals("新建学生")) {
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
			if(item.equals("新建学生")) {
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
