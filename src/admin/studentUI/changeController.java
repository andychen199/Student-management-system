package admin.studentUI;

import java.util.ArrayList;
import java.util.List;

import attendance.bean.Student;
import attendance.dao.StudentDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class changeController {
	@FXML private Button confirmButton;
	@FXML private TextField pwdField;
	@FXML
	protected void comfirmAction(ActionEvent event) {
		//Stage stage = (Stage) rootGridPane.getScene().getWindow();
	    //stage.close();
		
		StudentDao dao= new StudentDao();
    	  List<Student> is =new ArrayList<Student>();
    	  
    	  //FileItem item = fileList.data().get(index);
    	  is =dao.list3();
    		
    		for (Student b : is) {
    			if(pwdtrans.getName().equals(b.Sname))
            	{
    				System.out.printf("%s\t%s\t%s\t%d\t%s\t%d\t%s\t%s\n",b.Pcname,b.Pname,b.Sname,b.Sno,b.Sex,b.Sage,b.Sbirthtime,b.Snative);	           
    				//System.out.printf("%d\t%s\t%s\t%d\t%s\t%s\n",b.Sno,b.Sname,b.Sex,b.Sage,b.Snative,b.Spassword);
        			System.out.println();
        			//String item = String.valueOf(b.Sno);
        			System.out.print(b);
        				if(b.Sno==pwdtrans.getSno()) {
        					if(dao.update2(pwdtrans.getSno(), pwdField.getText())==true) {
        						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        						alert.setTitle("提示");
        						alert.setHeaderText(null);
        						alert.setContentText("修改成功");
        						pwdtrans.setpwd(pwdField.getText());
        						alert.showAndWait();
        					}
        				}
        			
            	}
    			else continue;
        	  //openFile(item);
            }
		Stage stage = (Stage)confirmButton.getScene().getWindow();
	    stage.close();
    }
	public void Initpwd() {
		pwdField.setText(pwdtrans.getpwd());
	}
	
}


