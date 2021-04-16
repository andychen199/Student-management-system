package admin.banjiinfoUI;

import java.io.IOException;

import admin.xueqinfoUI.XQController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClassInfoUI {
	public void start(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ClassInfoUI.fxml"));
        Parent root =loader.load();
        CIController Controller = loader.getController();
        Scene scene = new Scene(root, 733, 508);
        stage.setScene(scene);
        stage.setTitle("班级信息管理");
        stage.show();
        Controller.InitUI();
    }
}
