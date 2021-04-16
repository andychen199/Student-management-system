package Login;

import java.io.IOException;

import attendance.dao.SuperAdminDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class ChangeAdminpwd {
	public void start(Stage stage) throws IOException {
        stage.setTitle("更改管理员密码");
        Parent root = FXMLLoader.load(getClass().getResource("ChangeAdminpwd.fxml"));
        Scene scene = new Scene(root, 320, 261);
        stage.setScene(scene);
        stage.show();
    }
	@FXML private Button confirmButton;
	@FXML private PasswordField pwdField;
	@FXML
	protected void confirm(ActionEvent event) {
		SuperAdminDao dao = new SuperAdminDao();
        if(dao.changepassword(pwdField.getText().toString())) {
        	Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("提示");
			alert.setHeaderText(null);
			alert.setContentText("修改成功！");
			alert.showAndWait();
			Stage stage = (Stage)confirmButton.getScene().getWindow();
			stage.close();
        }
        else {
        	Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("提示");
			alert.setHeaderText(null);
			alert.setContentText("修改失败");
			alert.showAndWait();
        }
	}
}
