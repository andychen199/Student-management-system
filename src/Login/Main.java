package Login;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import student.StudIdentityTrans;
import student.StudentChooseUI;
import teacher.TeachChooseUI;
import teacher.TeachIdentity;
import teacher.TeachRoutineUI;
import teacher.TeachTotalUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import admin.studentUI.pwdtrans;
import attendance.bean.PC;
import attendance.bean.Student;
import attendance.dao.PCDao;
import attendance.dao.StudentDao;
import attendance.dao.SuperAdminDao;
import attendance.dao.TeacherDao;

public class Main extends Application {

    protected int loginid;


	@Override
    public void start(Stage primaryStage) {
		Reload12:{
        primaryStage.setTitle("学生考勤管理系统");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        //grid.getStyleClass().add("background");
        Text scenetitle = new Text("仲恺农业工程学院欢迎您");
        scenetitle.setFont(Font.font("微软雅黑", FontWeight.LIGHT, 32));
        grid.add(scenetitle, 0, 0, 2, 1);

        ComboBox choose = new ComboBox();
        choose.getItems().addAll("学生", "教师", "管理员");
        grid.add(choose, 1, 1);
        
        
        Label identity = new Label("身份");
        grid.add(identity, 0, 1);
        //创建Label对象，放到第0列，第1行
        Label userName = new Label("学号   ");
        grid.add(userName, 0, 2);

        //创建文本输入框，放到第1列，第1行
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 2);
        choose.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        	@Override
        	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        		if(choose.getValue().toString().equals("管理员")) {
        			userName.setText("学号   ");
        			userTextField.setText("admin");
        			userTextField.setDisable(true);
        		}
        		else if(choose.getValue().toString().equals("学生")) {
        			userName.setText("学号   ");
        			userTextField.setText("");
        			userTextField.setDisable(false);
        		}
        		else if(choose.getValue().toString().equals("教师")) {
        			userName.setText("教工号");
        			userTextField.setText("");
        			userTextField.setDisable(false);
        		}
        	}
        });
        Label pw = new Label("密码");
        grid.add(pw, 0, 3);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 3);
        //grid.setGridLinesVisible(true);
        Button btn = new Button("Sign in");
//        HBox hbBtn = new HBox(10);
//        hbBtn.setAlignment(Pos.BOTTOM_CENTER);
//        hbBtn.getChildren().add(btn);//将按钮控件作为子节点
        grid.add(btn, 1, 4);//将HBox pane放到grid中的第1列，第4行

        btn.setOnAction(new EventHandler<ActionEvent>() {//注册事件handler
            @Override
            public void handle(ActionEvent e) {
            	try {
            		if(choose.getValue().toString().equals("管理员")) {
                		SuperAdminDao su = new SuperAdminDao();
                		String pwd = pwBox.getText().toString();
                		if(su.login(pwd)) {
                			Platform.runLater(() -> {//创建主界面窗口
                                try {
                                    new WindowUI().start(new Stage());//关闭登陆窗口
                                    Stage stage = (Stage) btn.getScene().getWindow();
                            		stage.hide();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                primaryStage.hide();
                			});
                		}
    	            	else {
    	            	Alert alert = new Alert(Alert.AlertType.INFORMATION);
    	    			alert.setTitle("提示");
    	    			alert.setHeaderText(null);
    	    			alert.setContentText("密码错误");
    	    			alert.showAndWait();
                		}
                	}
                	else if(choose.getValue().toString().equals("学生")) {
            			StudentDao stu = new StudentDao();
            			int loginid = Integer.parseInt(userTextField.getText().toString());
    	        		if(stu.login(loginid, pwBox.getText().toString())) {
    	        			try {
    	        				StudIdentityTrans.setCno(loginid);
    	        	    		new StudentChooseUI().start(new Stage());
    	        	    		Stage stage = (Stage) btn.getScene().getWindow();
                        		stage.hide();
    						} catch (IOException e1) {
    							// TODO Auto-generated catch block
    							e1.printStackTrace();
    						}
    	        		}
    	        		else {
            			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    	    			alert.setTitle("提示");
    	    			alert.setHeaderText(null);
    	    			alert.setContentText("密码错误！！");
    	    			alert.showAndWait();
    	        		}
            		}
                	else if(choose.getValue().toString().equals("教师")) {
                		TeacherDao teacher = new TeacherDao();
            			int loginid = Integer.parseInt(userTextField.getText().toString());
    	        		if(teacher.login(loginid, pwBox.getText().toString())) {
    	        			TeachIdentity.setTno(loginid);
    	        	    	try {
    	        	    		new TeachChooseUI().start(new Stage());
    	        	    		Stage stage = (Stage) btn.getScene().getWindow();
                        		stage.hide();
    						} catch (IOException e1) {
    							// TODO Auto-generated catch block
    							e1.printStackTrace();
    						}
    	        		}
    	        		else {
            			Alert alert = new Alert(Alert.AlertType.INFORMATION);
    	    			alert.setTitle("提示");
    	    			alert.setHeaderText(null);
    	    			alert.setContentText("密码错误！！");
    	    			alert.showAndWait();
    	        		}
                	}
            	}catch(Exception e1) {
            		e1.printStackTrace();
            		Alert alert = new Alert(Alert.AlertType.ERROR);
	    			alert.setTitle("提示");
	    			alert.setHeaderText(null);
	    			alert.setContentText("不能为空！");
	    			alert.showAndWait();
            	}
            	
            }
        });
        Scene scene = new Scene(grid, 1225, 615);
        scene.getStylesheets().add(Main.class.getResource("JMetroLightTheme.css").toExternalForm());

        //JMetro jmetro = new JMetro(Style.LIGHT);
        //jmetro.setScene(scene);
        primaryStage.setScene(scene);
        //scene.getStylesheets().add(getClass().getResource("JMetroLightTheme.css").toExternalForm());

        primaryStage.show();
        
	}
    }


    public static void main(String[] args) {
        launch(args);
    }
}
