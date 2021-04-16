package teacher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import admin.studentUI.pwdtrans;
import attendance.bean.Teacher;
import attendance.dao.TeacherDao;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class TDController {
	@FXML private Label idlabel;
	@FXML private Label namelabel;
	@FXML private Label sexlabel;
	@FXML private Label yearlabel;
	@FXML private Label tellabel;
	@FXML private Label xueyuanlabel;
	@FXML private Label birthlabel;
	@FXML private TextField pwdlabel;
	@FXML
	protected void Changepwd(ActionEvent event) throws IOException {
		TeacherDao dao = new TeacherDao();
		List<Teacher> is = new ArrayList<Teacher>();
		is = dao.list3();
		int Tno = TeachIdentity.getTno();
		for(Teacher t:is) {
			if(t.Tno==Tno) {
				if(dao.update2(Tno, pwdlabel.getText())) {
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setTitle("提示");
					alert.setHeaderText(null);
					alert.setContentText("修改成功");
					alert.showAndWait();
					return;
				}
				else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("提示");
					alert.setHeaderText(null);
					alert.setContentText("修改失败");
					alert.showAndWait();
					return;
				}
			}
		}
	}
	public void InitUI() {
		TeacherDao dao = new TeacherDao();
		List<Teacher> is = new ArrayList<Teacher>();
		is = dao.list3();
		int Tno = TeachIdentity.getTno();
		String no = String.valueOf(Tno);
		for(Teacher t:is) {
			if(t.Tno==Tno) {
				idlabel.setText(no);
				namelabel.setText(t.Tname);
				sexlabel.setText(t.Tsex);
				String Tage = String.valueOf(t.Tage);
				yearlabel.setText(Tage);
				tellabel.setText(t.Tpassword);
				xueyuanlabel.setText(t.Tcname);
				birthlabel.setText(t.Tbirthtime);
				pwdlabel.setText(t.Tpassword);
				return;
			}
		}
	}
}
