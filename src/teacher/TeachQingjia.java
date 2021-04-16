package teacher;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TeachQingjia {
	public void start(Stage stage) throws IOException {
        stage.setTitle("主界面");
        
      //Parent root = FXMLLoader.load(getClass().getResource("TeachChooseUI.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TeachQingjia.fxml"));
        Parent root =loader.load();
        TQController Controller = loader.getController();
        Scene scene = new Scene(root, 719, 652);
        stage.setScene(scene);
        stage.show();
        //Controller.getno();
        Controller.InitUI();
    }
}
