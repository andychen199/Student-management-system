package student;

import java.util.ArrayList;
import java.util.List;

import attendance.bean.Course;
import attendance.bean.SC;
import attendance.dao.CourseDao;
import attendance.dao.SCDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SXController {
	//@FXML private Button commitbutton;
	@FXML private ComboBox select;
	@FXML
	protected void Confirm(ActionEvent event) {
		SCDao dao = new SCDao();
		CourseDao cdao = new CourseDao();
		List<Course> is = new ArrayList<Course>();
		is = cdao.list3(select.getValue().toString());
		if(dao.add(StudIdentityTrans.getCno(), is.get(0).Cno)) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("提示");
			alert.setHeaderText(null);
			alert.setContentText("选课成功！");
			select.getItems().clear();
			InitUI();
			alert.showAndWait();
		}
		else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("提示");
			alert.setHeaderText(null);
			alert.setContentText("未选择课程！");
			
			alert.showAndWait();
		}
	}
	public void InitUI() {
		SCDao dao = new SCDao();
		List<Course> is = new ArrayList<Course>();
		is = dao.listweixuan(StudIdentityTrans.getCno());
		for(Course c:is) {
			System.out.println(c.Cname);
			select.getItems().add(c.Cname);
		}
	}
}
