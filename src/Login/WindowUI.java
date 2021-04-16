package Login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

import admin.banjiinfoUI.ClassInfoUI;
import admin.classinfoUI.Classmessage;
import admin.studentUI.Stud;
import admin.teacherUI.TeachUI;
import admin.teacherUI.TeachUIold;
import admin.teachinfoUI.TeachInfoUI;
import admin.xueqinfoUI.XueqinfoUI;
import attendance.dao.SuperAdminDao;

public class WindowUI {

    public void start(Stage stage) throws IOException {
        stage.setTitle("主界面");
        Parent root = FXMLLoader.load(getClass().getResource("Windesign.fxml"));
//        GridPane grid = new GridPane();
//        grid.setAlignment(Pos.CENTER);
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setPadding(new Insets(25, 25, 25, 25));
          Scene scene = new Scene(root, 957, 500);
          scene.getStylesheets().add(Main.class.getResource("main.css").toExternalForm());
          //root.getStylesheets().add(getClass().getResource("JMetroLightTheme.css").toExternalForm());
//        Button stubtn = new Button("学生");
//        Button teacherbtn = new Button("教师");
//        Button classbtn = new Button("课程信息");
          //JMetro jmetro = new JMetro(Style.LIGHT);
          //jmetro.setScene(scene);
          stage.setScene(scene);
          stage.show();
    }
    @FXML
    protected void classmessage(ActionEvent event) throws IOException {
        new Classmessage().start(new Stage());
    }
    @FXML
    protected void Teacher(ActionEvent event) throws IOException {
        new TeachUI().start(new Stage());
    }
    @FXML
    protected void Student(ActionEvent event) throws IOException {
        new Stud().start(new Stage());
    }
    @FXML
    protected void changepwd(ActionEvent event) throws IOException {
    	new ChangeAdminpwd().start(new Stage());
        
    }
    @FXML
    protected void TeachUI(ActionEvent event) throws IOException {
    	new TeachInfoUI().start(new Stage());
        
    }
    @FXML
    protected void XueqiUI(ActionEvent event) throws IOException {
    	new XueqinfoUI().start(new Stage());
        
    }
    @FXML
    protected void banjiUI(ActionEvent event) throws IOException {
    	new ClassInfoUI().start(new Stage());
        
    }
    @FXML private Button logout;
    @FXML
    protected void Logout(ActionEvent event) throws IOException {
    	new Main().start(new Stage());
        Stage stage = (Stage)logout.getScene().getWindow();
        stage.hide();
    }
}
