package admin.xueqinfoUI;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import teacher.TRController;

public class XueqinfoUI {
	public void start(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("XueqinfoUI.fxml"));
        Parent root =loader.load();
        XQController Controller = loader.getController();
        Scene scene = new Scene(root, 784, 482);
        stage.setScene(scene);
        stage.setTitle("学期信息管理");
        stage.show();
        Controller.InitUI();
    }
}
