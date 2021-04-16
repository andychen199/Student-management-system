package teacher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Login.Main;
import attendance.bean.Afl;
import attendance.bean.PC;
import attendance.bean.Teacher;
import attendance.dao.AflDao;
import attendance.dao.PCDao;
import attendance.dao.TeacherDao;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class TCController {
	public String name;
	@FXML private Button kaoqinbutton;
	@FXML private Button qingjiabutton;
	@FXML private Button totalbutton;
	@FXML private Button logoutbutton;
	@FXML private Button changepwd;
	@FXML private Label namelabel;
	@FXML private Label qingjia;
	@FXML private ImageView image;
	@FXML
	protected void LoadRoutine(ActionEvent event) throws IOException {
		new TeachRoutineUI().start(new Stage());
	}
	@FXML
	protected void LoadTotal(ActionEvent event) throws IOException {
		new TeachTotalUI().start(new Stage());
	}
	@FXML
	protected void LogOut(ActionEvent event) throws IOException {
		new Main().start(new Stage());
		Stage stage = (Stage)logoutbutton.getScene().getWindow();
		stage.hide();
	}
	@FXML
	protected void Changepwd(ActionEvent event) throws IOException {
		new TeachDetail().start(new Stage());
		
	}
	@FXML
	protected void Qingjia(ActionEvent event) throws IOException {
		new TeachQingjia().start(new Stage());
		
	}
	public void getno() {
		int Tno = TeachIdentity.getTno();
		TeacherDao dao = new TeacherDao();
		List<Teacher> is =new ArrayList<Teacher>();
		is = dao.list3();
		for(Teacher t:is) {
			if(t.Tno==Tno) {
				namelabel.setText(t.Tname);
				break;
			}
		}
//		namelabel.setText("霸道总裁");
		AflDao adao = new AflDao();
		List<Afl> ais =new ArrayList<Afl>();
		ais = adao.get(Tno, "待审核");
		int count = 0;
		for(Afl a:ais) {
			count++;
		}
		if(count==0) {
			image.setVisible(false);
			qingjia.setVisible(false);
		}
		else {
			image.setVisible(true);
			qingjia.setVisible(true);
			qingjia.setText(count+"条请假消息待审核");
		}
		qingjiabutton.setId("button-custom");
	}
}
