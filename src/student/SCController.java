package student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Login.Main;
import attendance.dao.StudentDao;
import attendance.bean.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import teacher.TeachRoutineUI;

public class SCController {
	@FXML private Button kaoqinbutton;
	@FXML private Button qingjiabutton;
	@FXML private Button xuankebutton;
	@FXML private Button logout;
	@FXML private Button changepwd;
	@FXML private Label namelabel;
	@FXML
	protected void Qingjia(ActionEvent event) throws IOException {
		new StudQingjia().start(new Stage());
	}
	
	@FXML
	protected void Kaoqin(ActionEvent event) throws IOException {
		new StudKaoqin().start(new Stage());
	}
	@FXML
	protected void Xuanke(ActionEvent event) throws IOException {
		new StudXuanke().start(new Stage());
	}
	@FXML
	protected void XuankeAdded(ActionEvent event) throws IOException {
		new StudAdded().start(new Stage());
	}
	@FXML
	protected void Changepwd(ActionEvent event) throws IOException {
		new StuDetail().start(new Stage());
	}
	@FXML
	protected void Logout(ActionEvent event) throws IOException {
		new Main().start(new Stage());
		Stage stage = (Stage) logout.getScene().getWindow();
		stage.hide();
	}
	public void InitUI() {
		StudentDao dao = new StudentDao();
		List<Student> is = new ArrayList<Student>();
		is = dao.list1(StudIdentityTrans.getCno());
		for(Student s:is) {
			namelabel.setText(s.Sname+"同学");
		}
	}
}
