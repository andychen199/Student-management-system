package teacher;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TeachChooseUI {
	public void start(Stage stage) throws IOException {
        stage.setTitle("主界面");
        
      //Parent root = FXMLLoader.load(getClass().getResource("TeachChooseUI.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TeachChooseUI.fxml"));
        Parent root =loader.load();
        TCController Controller = loader.getController();
        Scene scene = new Scene(root, 897, 568);
        //scene.getStylesheets().add(TeachChooseUI.class.getResource("login.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
        Controller.getno();
        //Controller.InitUI();
    }
}
