package admin.teachinfoUI;

import java.util.ArrayList;
import java.util.List;

import attendance.bean.Teacher;
import attendance.dao.TeacherDao;
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
		
		TeacherDao dao= new TeacherDao();
    	  List<Teacher> is =new ArrayList<Teacher>();
    	  
    	  //FileItem item = fileList.data().get(index);
    	  is =dao.list3();
    		
    		for (Teacher b : is) {
    			if(pwdtrans.getName().equals(b.Tname))
            	{
    				//System.out.printf("%s\t%s\t%s\t%d\t%s\t%d\t%s\t%s\n",b.Tcname,b.Tname,b.Tname,b.Sno,b.Sex,b.Sage,b.Sbirthtime,b.Snative);	           
    				//System.out.printf("%d\t%s\t%s\t%d\t%s\t%s\n",b.Sno,b.Tname,b.Sex,b.Sage,b.Snative,b.Spassword);
        			//System.out.println();
        			//String item = String.valueOf(b.Sno);
        			System.out.print(b);
        				if(b.Tno==pwdtrans.getSno()) {
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


