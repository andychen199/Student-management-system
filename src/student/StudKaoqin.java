package student;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import teacher.TCController;

public class StudKaoqin {
	public void start(Stage stage) throws IOException {
        stage.setTitle("主界面");
        
      //Parent root = FXMLLoader.load(getClass().getResource("TeachChooseUI.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudKaoqin.fxml"));
        Parent root =loader.load();
        SKController Controller = loader.getController();
        Scene scene = new Scene(root, 1004, 569);
        stage.setScene(scene);
        stage.show();
        //Controller.getno();
        Controller.InitUI();
    }
}
