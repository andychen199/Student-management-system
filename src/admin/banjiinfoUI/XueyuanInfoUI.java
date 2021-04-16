package admin.banjiinfoUI;

import java.io.IOException;

import admin.studentUI.pwdtrans;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class XueyuanInfoUI {
	public void start(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("XueyuanInfoUI.fxml"));
        Parent root =loader.load();
        XIController Controller = loader.getController();
        Scene scene = new Scene(root, 573, 400);
        stage.setScene(scene);
        stage.setTitle("学院信息管理");
        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("监听到窗口关闭1");
//                FXMLLoader loader1 = new FXMLLoader(ClassInfoUI.class.getResource("ClassInfoUI.fxml"));
//                CIController Controller1 = loader1.getController();
//                Controller1.ReloadCombo();
                
                
            }
        });
        stage.show();
        Controller.InitUI();
    }
}
