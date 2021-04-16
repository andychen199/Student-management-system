package admin.banjiinfoUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import attendance.bean.Academy;
import attendance.bean.PC;
import attendance.dao.AcademyDao;
import attendance.dao.PCDao;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class XIController {
	@FXML private ImageView reload;
	@FXML private ImageView newbutton;
	@FXML private Label label1;
	@FXML private Label label2;
	@FXML private Label label3;

	@FXML private ListView list;
	@FXML private TextField xueyuanname;
	@FXML private TextField chairman;
	@FXML private TextField address;
	
	
	@FXML private ImageView comfirm;
	@FXML private ImageView closebutton;
	String[] storage = null;
	int count=0;
	Image pic = new Image("admin/banjiinfoUI/check_filled.png");
	Image pic1 = new Image("admin/banjiinfoUI/check.png");
	Image close = new Image("admin/banjiinfoUI/close.png");
	@FXML
    protected void Add(ActionEvent event) {
		
	}
	public void Save() {
		System.out.println("现在是保存");
		
//		if(count==0) {
//			comfirm.setImage(pic);
//			count=1;
//		}
//		else if(count==1) {
//			comfirm.setImage(pic1);
//			count=0;
//		}
		AcademyDao dao = new AcademyDao();
		if(list.getSelectionModel().getSelectedItem().equals("新建学院")) {
			try {
				String a1 = xueyuanname.getText();
			}catch(Exception e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	    		alert.setTitle("提示");
	    		alert.setHeaderText(null);
	    		alert.setContentText("添加失败，请完善学院信息！");
	    		alert.showAndWait();
	    		return;
			}
			try {
				String a2 = chairman.getText();
			}catch(Exception e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	    		alert.setTitle("提示");
	    		alert.setHeaderText(null);
	    		alert.setContentText("添加失败，请完善系主任信息！");
	    		alert.showAndWait();
	    		return;
			}
			try {
				String a3 = address.getText();
			}catch(Exception e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	    		alert.setTitle("提示");
	    		alert.setHeaderText(null);
	    		alert.setContentText("添加失败，请完善地址信息！");
	    		alert.showAndWait();
	    		return;
			}
			
			
			if(dao.add(xueyuanname.getText(), chairman.getText(), address.getText())) {
				
	    		comfirm.setImage(pic);
	    		list.getItems().set(list.getSelectionModel().getSelectedIndex(), xueyuanname.getText());
			}
			else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	    		alert.setTitle("提示");
	    		alert.setHeaderText(null);
	    		alert.setContentText("添加失败，请检查班级号是否冲突或班级人数是否小于5！");
	    	    alert.showAndWait();
	    		return;
			}
		}else {
			try {
				String a2 = xueyuanname.getText();
			}catch(Exception e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	    		alert.setTitle("提示");
	    		alert.setHeaderText(null);
	    		alert.setContentText("添加失败，请完善学院信息！");
	    		alert.showAndWait();
	    		return;
			}
			try {
				String a3 = chairman.getText();
			}catch(Exception e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	    		alert.setTitle("提示");
	    		alert.setHeaderText(null);
	    		alert.setContentText("添加失败，请完善系主任信息！");
	    		alert.showAndWait();
	    		return;
			}
			try {
				String a4 = address.getText();
			}catch(Exception e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	    		alert.setTitle("提示");
	    		alert.setHeaderText(null);
	    		alert.setContentText("添加失败，未选择院系！");
	    		alert.showAndWait();
	    		return;
			}
			if(dao.update(xueyuanname.getText(), chairman.getText(), address.getText())){
				comfirm.setImage(pic);
				list.getItems().set(list.getSelectionModel().getSelectedIndex(), xueyuanname.getText());
			}
			else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	    		alert.setTitle("提示");
	    		alert.setHeaderText(null);
	    		alert.setContentText("修改失败！");
	    		alert.showAndWait();
	    		onFileListDbclicked();
	    		return;
			}
		}
		
		//comfirm.setImage(null);
	}
	public void Reload() {
		list.getItems().clear();
		InitUI();
	}
	public void AddClass() {
		list.getItems().add("新建学院");
		list.getSelectionModel().selectLast();
		onFileListDbclicked();
	}
	public void yunxing() throws IOException {
		new XueyuanInfoUI().start(new Stage());
	}
	public void Close() {
		list.getItems().remove("新建学院");
		list.getSelectionModel().selectLast();
		onFileListDbclicked();
	}
	public void InitUI() {
		label1.setVisible(false);
		label2.setVisible(false);
		label3.setVisible(false);
		
		comfirm.setImage(pic1);
		closebutton.setVisible(false);
		xueyuanname.setVisible(false);
		chairman.setVisible(false);
		address.setVisible(false);
		
		comfirm.setVisible(false);
		AcademyDao dao = new AcademyDao();
		List<Academy> is = new ArrayList<Academy>();
		is = dao.list2();
		for(Academy a:is) {
			list.getItems().add(a.Acname);
		}
		list.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event)
			{
				if(event.getClickCount()== 1)
				{
					try {
						
						onFileListDbclicked();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}			
		});
		
		storage = null;
		xueyuanname.textProperty().addListener(new ChangeListener() {
        	@Override
        	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        		comfirm.setImage(pic1);
//        		if(list.getSelectionModel().getSelectedItem().equals("新建班级")) {
//        			try {
//            			storage[0]=classidtext.getText();
//            			System.out.println(storage[0]);
//        			}catch(Exception e) {
//        				
//        			}
//        		}
        		
        		
        	}
        });
		chairman.textProperty().addListener(new ChangeListener() {
        	@Override
        	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        		comfirm.setImage(pic1);
//        		if(list.getSelectionModel().getSelectedItem().equals("新建班级")) {
//        			try {
//            			storage[1]=classnametext.getText();
//        			}catch(Exception e) {
//        				
//        			}
//        		}
        		
        		
        	}
        });
		address.textProperty().addListener(new ChangeListener() {
        	@Override
        	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        		comfirm.setImage(pic1);
//        		if(list.getSelectionModel().getSelectedItem().equals("新建班级")) {
//        			try {
//            			storage[2]=classnumbertext.getText();
//        			}catch(Exception e) {
//        				
//        			}
//        		}
        		
        		
        		
        	}
        });
		Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
		confirmation.setTitle("提示");
        confirmation.setHeaderText(null);
        confirmation.setContentText("确认删除？");
		list.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem deleteItem = new MenuItem();
            deleteItem.textProperty().bind(Bindings.format("删除 \"%s\"", cell.itemProperty()));
            deleteItem.setOnAction(event -> {
                Optional<ButtonType> result = confirmation.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK){
                	String item = list.getSelectionModel().getSelectedItem().toString();
                	
                	
                	if(dao.delete(item)) {
                		try {
                    		int index = list.getSelectionModel().getSelectedIndex();
                    		list.getItems().remove(index);
    						list.getSelectionModel().selectLast();
    						onFileListDbclicked();
    					} catch (Exception e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
                    	
                    	confirmation.hide();
                	}
                	else {
                		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setTitle("提示");
						alert.setHeaderText(null);
						alert.setContentText("修改失败");
						alert.showAndWait();
                	}
                	
                }
                
            });
            contextMenu.getItems().addAll(deleteItem);
            cell.textProperty().bind(cell.itemProperty());
            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });
            return cell ;
        });
	}
	public void onFileListDbclicked() {
		label1.setVisible(true);
		label2.setVisible(true);
		label3.setVisible(true);
		
		comfirm.setVisible(true);
		xueyuanname.setVisible(true);
		chairman.setVisible(true);
		address.setVisible(true);
		
		comfirm.setImage(pic1);
		
		try {
			if(list.getSelectionModel().getSelectedItem().equals("新建学院")) {
				
				try {
					System.out.println(storage[0]);
					xueyuanname.setText(storage[0]);
				}catch(Exception e) {
					xueyuanname.setText(null);
				}
				try {
					chairman.setText(storage[1]);
				}catch(Exception e) {
					chairman.setText(null);
				}
				try {
					address.setText(storage[2]);
				}catch(Exception e) {
					address.setText(null);
				}
				
			}else {
				AcademyDao dao = new AcademyDao();
				int index = list.getSelectionModel().getSelectedIndex();
				xueyuanname.setText(String.valueOf(dao.list2().get(index).Acname));
				chairman.setText(dao.list2().get(index).Acchairman);
				address.setText(String.valueOf(dao.list2().get(index).Acaddress));
				
			}
		}catch(Exception e) {
			e.printStackTrace();
			return;
		}
		
		
		if(list.getSelectionModel().getSelectedItem().equals("新建学院")) {
			xueyuanname.setDisable(false);
			closebutton.setVisible(true);
			
			
		}else {
			
			xueyuanname.setDisable(true);
			closebutton.setVisible(false);
		}
		
	}
}
