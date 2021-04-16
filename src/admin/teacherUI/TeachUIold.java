package admin.teacherUI;

import javafx.application.Platform;
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
import java.util.ResourceBundle;


import admin.teacherUI.pwdtrans;
import attendance.bean.PC;
import attendance.bean.Teacher;
import attendance.dao.PCDao;
import attendance.dao.TeacherDao;
import attendance.dao.TeacherDao;

public class TeachUIold{
    ListView fileList = new ListView();
    TabPane tabPane = new TabPane(); // 中间放一个Tab容器
    // 必须static 类型
    public void start(Stage stage) throws IOException {
    	initList();
        stage.setTitle("教师信息管理");
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
        //Parent root = FXMLLoader.load(getClass().getResource("Teacher.fxml"));
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
		TeacherDao dao= new TeacherDao();
	    List<Teacher> is =new ArrayList<Teacher>();
	    is =dao.list3();
	    //List<Teacher> is =new ArrayList<Teacher>();
	    ObservableList<String> items =FXCollections.observableArrayList ();
	    for (Teacher b : is) {
	    	System.out.printf("%s\t%s\t%s\t%d\t%s\t%d\t%s\n",b.Tcname,b.Tname,b.Tname,b.Tno,b.Tsex,b.Tage,b.Tbirthtime);	           
	    	//System.out.printf("%d\t%s\t%s\t%d\t%s\t%s\n",b.Tno,b.Tname,b.Sex,b.Sage,b.Snative,b.Tpassword);
	    	System.out.println();
	    	String item = String.valueOf(b.Tname);
	    	items.add(item);
        }
		// 左侧加载examples目录下的文件
		
//		File dir = new File("examples"); 
		fileList.setItems(items);
		
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
        Label idlabel = new Label("教工号");
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
        Label birthlabel = new Label("生日");
        grid.add(birthlabel, 0, 4);
        TextField birthTextField = new TextField();
        grid.add(birthTextField, 1, 4);
        
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
        Label tellabel = new Label("电话");
        grid.add(tellabel, 0, 6);
        TextField telTextField = new TextField();
        grid.add(telTextField, 1, 6);
//        Label SPnolabel = new Label("专业班级");
//        grid.add(SPnolabel, 0, 6);
//        ObservableList<String> classoptions = 
//        	    FXCollections.observableArrayList();
//        ComboBox classcombo = new ComboBox(classoptions);
//
//        grid.add(classcombo, 1, 6);
//        xueyuancombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
//        	@Override
//        	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//        		PCDao pcdao= new PCDao();
//        	    List<PC> pcis =new ArrayList<PC>();
//        	    pcis=pcdao.list2();
//        		for(PC c:pcis) {
//          	      	//classoptions.add(c.Pname);
//        			
//        			classcombo.getItems().removeAll(c.Pname);
//        			
//          	    }
//        		String xueyuan = xueyuancombo.getValue().toString();
//        		pcis=pcdao.list3(xueyuan);
//        		
//        		for(PC c:pcis) {
//          	      	//classoptions.add(c.Pname);
//        			int count = pwdtrans.getCount();
//        			if(count==0)break;
//        			classcombo.getItems().addAll(c.Pname);
//        			System.out.println(count);
//        			
//          	    }
//        	}
//        });
        //TextField SPnoTextField = new TextField();
        //grid.add(SPnoTextField, 1, 5);
        Label Passwordlabel = new Label("教师端密码");
        grid.add(Passwordlabel, 0, 7);
        Label Passwordslabel = new Label("default");
        grid.add(Passwordslabel, 1, 7);
        Button buttonchangepassword = new Button("修改密码");
        grid.add(buttonchangepassword, 2, 7);
        
        Button buttonsave = new Button("保存");
        grid.add(buttonsave, 0, 8);
        buttonsave.setOnAction((ActionEvent e) -> {
        	TeacherDao dao= new TeacherDao();
    	    List<Teacher> is =new ArrayList<Teacher>();
    	    int idint = Integer.parseInt(idTextField.getText());
    	    int yearint = Integer.parseInt(yearcombo.getValue().toString());
    	    //dao.update1(idint, nameBox.getText(), sexcombo.getValue().toString(), yearint, birthTextField.getText(), pwdtrans.getSpno());
    	    dao.update1(idint, nameBox.getText().toString(), sexcombo.getValue().toString(), yearint, telTextField.getText().toString(), xueyuancombo.getValue().toString(), birthTextField.getText().toString());
    	    //dao.update1(idint, nameBox.getText().toString(), sexcombo.getValue().toString(), yearint, birthTextField.getText().toString(), pwdtrans.getSpno());
        });
        
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
        Reload:
        {
        TeacherDao dao= new TeacherDao();
	    List<Teacher> is =new ArrayList<Teacher>();
	    PCDao classdao= new PCDao();
	    List<PC> classis =new ArrayList<PC>();
		int index = fileList.getSelectionModel().getSelectedIndex();
		//FileItem item = fileList.data().get(index);
		is =dao.list3();
		for (Teacher b : is) {
        	//System.out.printf("%s\t%s\t%s\t%d\t%s\t%d\t%s\t%s\n",b.Tcname,b.Tname,b.Tname,b.Tno,b.Tsex,b.Tage,b.Tbirthtime);	           
    	  //System.out.printf("%d\t%s\t%s\t%d\t%s\t%s\n",b.Tno,b.Tname,b.Sex,b.Sage,b.Snative,b.Tpassword);
    	  System.out.println();
    	  
    	  if(b.Tno==index+1) {
    		  String id = String.valueOf(b.Tno);
        	  idTextField.setText(id);
        	  nameBox.setText(b.Tname);
        	  yearcombo.setValue(b.Tage);
        	  sexcombo.setValue(b.Tsex);
        	  birthTextField.setText(b.Tbirthtime);
        	  xueyuancombo.setValue(b.Tcname);
        	  telTextField.setText(b.Tphone);
//        	  classis = classdao.list1(b.SPno);
//        	  pwdtrans.setSpno(b.SPno);
//        	  for (PC c : classis) {//学生管理页面中添加Combo选项相关代码
//        		  
//        		  xueyuancombo.setValue(c.Tcname);
//        		  String xueyuan = c.Tcname;
//        		  List<PC> classis1 = classdao.list3(c.Tcname);
//        		  for (PC c1 : classis1) {
//        		  classcombo.getItems().addAll(c1.Pname);
//        		  classcombo.setValue(c.Pname);
//        		  pwdtrans.setCount(1);
//        		  }
//        	  }
        	  //String SPno = String.valueOf(b.SPno);
        	  
        	  
        	  Passwordslabel.setText(b.Tpassword);
        	  pwdtrans.setPno(b.Tno);
        	  pwdtrans.setpwd(b.Tpassword);
    		  break;
    	  }
        }
        return grid;
        }
    }

    public HBox addHBox(){
    	HBox hbox = new HBox();
    	hbox.setAlignment(Pos.CENTER);
    	hbox.setPadding(new Insets(15, 12, 15, 12)); //节点到边缘的距离
        hbox.setSpacing(10);//节点之间的间距
    	hbox.setStyle("-fx-background-color: #336699"); //背景色
        
        Button buttonCurrent = new Button("刷新数据");
        buttonCurrent.setPrefSize(100, 20);

        Button buttonProjected = new Button("Projected");
        buttonProjected.setPrefSize(100, 20);
        hbox.getChildren().addAll(buttonCurrent, buttonProjected);
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
    	TeacherDao dao= new TeacherDao();
	    List<Teacher> is =new ArrayList<Teacher>();
	    
		int index = fileList.getSelectionModel().getSelectedIndex();
		//FileItem item = fileList.data().get(index);
		is =dao.list1(index+1);
		
		for (Teacher b : is) {
        	//System.out.printf("%s\t%s\t%s\t%d\t%s\t%d\t%s\t%s\n",b.Tcname,b.Tname,b.Tname,b.Tno,b.Tsex,b.Tage,b.Tbirthtime);	           
    	  //System.out.printf("%d\t%s\t%s\t%d\t%s\t%s\n",b.Tno,b.Tname,b.Sex,b.Sage,b.Snative,b.Tpassword);
    	  System.out.println();
    	  String item = String.valueOf(b.Tname);
    	  System.out.print(b);
    	  if(fileList.getSelectionModel().getSelectedItem()==b.Tname) {
        	  openFile(item);
    	  }
    	  openFile(item);
        }
		
		
	}

	// 打开文件
	private void openFile(String item) throws Exception
	{
		// 查看选项卡是否已经打开
		Tab tab = findTab ( item );
		if( tab != null)
		{
			int tabIndex = tabPane.getTabs().indexOf(tab);
			tabPane.getSelectionModel().select(tabIndex);
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

}
