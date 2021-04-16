package student;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StudentChooseUI {
	public void start(Stage stage) throws IOException {
        stage.setTitle("主界面");
        
      //Parent root = FXMLLoader.load(getClass().getResource("TeachChooseUI.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentChooseUI.fxml"));
        Parent root =loader.load();
        SCController Controller = loader.getController();
        Scene scene = new Scene(root, 732, 609);
        stage.setScene(scene);
        stage.show();
        //Controller.getno();
        Controller.InitUI();
    }
}
