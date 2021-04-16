package student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import attendance.bean.Course;
import attendance.bean.SC;
import attendance.dao.CourseDao;
import attendance.dao.SCDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class SAController {
	@FXML private Button delete;
	@FXML private ListView list;
	@FXML
	protected void Delete(ActionEvent event) throws IOException {
		SCDao dao = new SCDao();
		CourseDao cdao = new CourseDao();
		List<Course> is = new ArrayList<Course>();
		is = cdao.list3(list.getSelectionModel().getSelectedItem().toString());
		if(dao.delete(StudIdentityTrans.getCno(), is.get(0).Cno)) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("提示");
			alert.setHeaderText(null);
			alert.setContentText("退选成功！");
			alert.showAndWait();
			list.getItems().clear();
			InitUI();
		}else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("提示");
			alert.setHeaderText(null);
			alert.setContentText("未选择课程！");
			alert.showAndWait();
		}
	}
	public void InitUI() {
		SCDao dao = new SCDao();
		List<SC> is = new ArrayList<SC>();
		is = dao.list2(StudIdentityTrans.getCno());
		for(SC s:is) {
			list.getItems().add(s.Cname);
		}
	}
}
