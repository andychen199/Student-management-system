package student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import admin.studentUI.pwdtrans;
import attendance.bean.Student;
import attendance.dao.StudentDao;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SDController {
	@FXML private Label idlabel;
	@FXML private Label namelabel;
	@FXML private Label sexlabel;
	@FXML private Label yearlabel;
	@FXML private Label classlabel;
	@FXML private Label nativelabel;
	@FXML private Label xueyuanlabel;
	@FXML private Label birthlabel;
	@FXML private TextField pwdlabel;
	@FXML
	protected void Changepwd(ActionEvent event) throws IOException {
		StudentDao dao = new StudentDao();
		List<Student> is = new ArrayList<Student>();
		is = dao.list3();
		int Sno = StudIdentityTrans.getCno();
		for(Student s:is) {
			if(s.Sno==Sno) {
				if(dao.update2(Sno, pwdlabel.getText())) {
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
		StudentDao dao = new StudentDao();
		List<Student> is = new ArrayList<Student>();
		is = dao.list1(StudIdentityTrans.getCno());
		
		for(Student s:is) {
				System.out.println(s.Pname);
				System.out.println(s.Pcname);
				System.out.println(s.Sbirthtime);
				System.out.println(s.Snative);
				idlabel.setText(String.valueOf(StudIdentityTrans.getCno()));
				namelabel.setText(s.Sname);
				sexlabel.setText(s.Sex);
				String Sage = String.valueOf(s.Sage);
				yearlabel.setText(Sage);
				classlabel.setText(s.Pname);
				xueyuanlabel.setText(s.Pcname);
				birthlabel.setText(s.Sbirthtime);
				nativelabel.setText(s.Snative);
				
				
			
		}
		System.out.println("密码是："+dao.getpwd(StudIdentityTrans.getCno()));
		pwdlabel.setText(dao.getpwd(StudIdentityTrans.getCno()));
		
		
	}
}
