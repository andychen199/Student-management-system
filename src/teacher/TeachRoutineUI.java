package teacher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import admin.classinfoUI.Classmessage;
import attendance.bean.PC;
import attendance.dao.PCDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class TeachRoutineUI {
	
	public void start(Stage stage) throws IOException {
        stage.setTitle("主界面");
        
      //Parent root = FXMLLoader.load(getClass().getResource("TeachChooseUI.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TeachRoutineUI.fxml"));
        Parent root =loader.load();
        TRController Controller = loader.getController();
        Scene scene = new Scene(root, 1158, 586);
        stage.setScene(scene);
        stage.show();
        Controller.InitUI();
    }
	
//	@FXML private
//    protected void classmessage(ActionEvent event) throws IOException {
//        new Classmessage().start(new Stage());
//    }
	
}
