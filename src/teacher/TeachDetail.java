package teacher;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TeachDetail {
	public void start(Stage stage) throws IOException {
        stage.setTitle("主界面");
        
      //Parent root = FXMLLoader.load(getClass().getResource("TeachChooseUI.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TeachDetail.fxml"));
        Parent root =loader.load();
        TDController Controller = loader.getController();
        Scene scene = new Scene(root, 441, 501);
        stage.setScene(scene);
        stage.show();
        Controller.InitUI();
        //Controller.InitUI();
    }
}
