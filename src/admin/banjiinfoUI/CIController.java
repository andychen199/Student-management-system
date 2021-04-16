package admin.banjiinfoUI;

import attendance.dao.AcademyDao;
import attendance.dao.PCDao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import admin.studentUI.pwdtrans;
import attendance.bean.Academy;
import attendance.bean.PC;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CIController {
	@FXML private Button reload;
	@FXML private Button newbutton;
	@FXML private Label label1;
	@FXML private Label label2;
	@FXML private Label label3;

	@FXML private Label label4;
	@FXML private ListView list;
	@FXML private TextField classidtext;
	@FXML private TextField classnametext;
	@FXML private TextField classnumbertext;
	@FXML private ComboBox xueyuantext;
	@FXML private ImageView image;
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
		PCDao dao = new PCDao();
		if(list.getSelectionModel().getSelectedItem().equals("新建班级")) {
			try {
				String a1 = classidtext.getText();
			}catch(Exception e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	    		alert.setTitle("提示");
	    		alert.setHeaderText(null);
	    		alert.setContentText("添加失败，请完善班级号信息！");
	    		alert.showAndWait();
	    		return;
			}
			try {
				String a2 = classnametext.getText();
			}catch(Exception e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	    		alert.setTitle("提示");
	    		alert.setHeaderText(null);
	    		alert.setContentText("添加失败，请完善班级名信息！");
	    		alert.showAndWait();
	    		return;
			}
			try {
				String a3 = classnumbertext.getText();
			}catch(Exception e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	    		alert.setTitle("提示");
	    		alert.setHeaderText(null);
	    		alert.setContentText("添加失败，请完善班级人数信息！");
	    		alert.showAndWait();
	    		return;
			}
			try {
				String a4 = xueyuantext.getValue().toString();
			}catch(Exception e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	    		alert.setTitle("提示");
	    		alert.setHeaderText(null);
	    		alert.setContentText("添加失败，未选择院系！");
	    		alert.showAndWait();
	    		return;
			}
			
			if(dao.add(Integer.valueOf(classidtext.getText()), classnametext.getText(), Integer.valueOf(classnumbertext.getText()), xueyuantext.getValue().toString())) {
				
	    		comfirm.setImage(pic);
	    		list.getItems().set(list.getSelectionModel().getSelectedIndex(), classnametext.getText());
	    		closebutton.setVisible(false);
	    		
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
				String a2 = classnametext.getText();
			}catch(Exception e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	    		alert.setTitle("提示");
	    		alert.setHeaderText(null);
	    		alert.setContentText("添加失败，请完善班级名信息！");
	    		alert.showAndWait();
	    		return;
			}
			try {
				String a3 = classnumbertext.getText();
			}catch(Exception e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	    		alert.setTitle("提示");
	    		alert.setHeaderText(null);
	    		alert.setContentText("添加失败，请完善班级人数信息！");
	    		alert.showAndWait();
	    		return;
			}
			try {
				String a4 = xueyuantext.getValue().toString();
			}catch(Exception e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	    		alert.setTitle("提示");
	    		alert.setHeaderText(null);
	    		alert.setContentText("添加失败，未选择院系！");
	    		alert.showAndWait();
	    		return;
			}
			if(dao.update(Integer.valueOf(classidtext.getText()), classnametext.getText(), Integer.valueOf(classnumbertext.getText()), xueyuantext.getValue().toString())){
				comfirm.setImage(pic);
				list.getItems().set(list.getSelectionModel().getSelectedIndex(), classnametext.getText());
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
		list.getItems().add("新建班级");
		list.getSelectionModel().selectLast();
		onFileListDbclicked();
	}
	public void yunxing() throws IOException {
		Platform.runLater(() -> {//创建修改密码界面窗口
		       
        	try {
        		FXMLLoader loader = new FXMLLoader(getClass().getResource("XueyuanInfoUI.fxml"));
                Parent root =loader.load();
                XIController Controller = loader.getController();
                Scene scene = new Scene(root, 573, 400);
                Stage stage1 = new Stage();
                stage1.setScene(scene);
                stage1.setTitle("学院信息管理");
                stage1.setOnHiding(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        System.out.println("监听到窗口关闭1");
                        ReloadCombo();
                        onFileListDbclicked();
                        
                        
                    }
                });
                stage1.show();
                Controller.InitUI();
        	}catch(Exception e) {
        		
        	}
		});
		//new XueyuanInfoUI().start(new Stage());
	}
	public void ReloadCombo() {
		AcademyDao acadao = new AcademyDao();
		List<Academy> acais = new ArrayList<Academy>();
		acais = acadao.list2();
		xueyuantext.getItems().clear();
		for(Academy a:acais) {
			xueyuantext.getItems().add(a.Acname);
		}
		
	}
	public void Close() {
		list.getItems().remove("新建班级");
		list.getSelectionModel().selectLast();
		onFileListDbclicked();
	}
	public void InitUI() {
		label1.setVisible(false);
		label2.setVisible(false);
		label3.setVisible(false);
		label4.setVisible(false);
		comfirm.setImage(pic1);
		closebutton.setVisible(false);
		classidtext.setVisible(false);
		classnametext.setVisible(false);
		classnumbertext.setVisible(false);
		xueyuantext.setVisible(false);
		image.setVisible(false);
		comfirm.setVisible(false);
		PCDao dao = new PCDao();
		List<PC> is = new ArrayList<PC>();
		is = dao.list2();
		for(PC p:is) {
			list.getItems().add(p.Pname);
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
		
		AcademyDao acadao = new AcademyDao();
		List<Academy> acais = new ArrayList<Academy>();
		acais = acadao.list2();
		xueyuantext.getItems().clear();
		for(Academy a:acais) {
			xueyuantext.getItems().add(a.Acname);
		}
		storage = null;
		classidtext.textProperty().addListener(new ChangeListener() {
        	@Override
        	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        		comfirm.setImage(pic1);
        		if(list.getSelectionModel().getSelectedItem().equals("新建班级")) {
        			try {
            			storage[0]=classidtext.getText();
            			System.out.println(storage[0]);
        			}catch(Exception e) {
        				
        			}
        		}
        		
        		
        	}
        });
		classnametext.textProperty().addListener(new ChangeListener() {
        	@Override
        	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        		comfirm.setImage(pic1);
        		if(list.getSelectionModel().getSelectedItem().equals("新建班级")) {
        			try {
            			storage[1]=classnametext.getText();
        			}catch(Exception e) {
        				
        			}
        		}
        		
        		
        	}
        });
		classnumbertext.textProperty().addListener(new ChangeListener() {
        	@Override
        	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        		comfirm.setImage(pic1);
        		if(list.getSelectionModel().getSelectedItem().equals("新建班级")) {
        			try {
            			storage[2]=classnumbertext.getText();
        			}catch(Exception e) {
        				
        			}
        		}
        		
        		
        		
        	}
        });
		xueyuantext.valueProperty().addListener(new ChangeListener() {
        	@Override
        	public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        		comfirm.setImage(pic1);
        		if(list.getSelectionModel().getSelectedItem().equals("新建班级")) {
        			try {
            			storage[3]=xueyuantext.getValue().toString();
        			}catch(Exception e) {
        				
        			}
        		}
        		
        		
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
                	
                	int Pno = dao.findPno(item);
                	if(dao.delete(Pno)) {
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
		label4.setVisible(true);
		comfirm.setVisible(true);
		classidtext.setVisible(true);
		classnametext.setVisible(true);
		classnumbertext.setVisible(true);
		xueyuantext.setVisible(true);
		comfirm.setImage(pic1);
		image.setVisible(true);
		
		if(list.getSelectionModel().getSelectedItem().equals("新建班级")) {
			
			try {
				System.out.println(storage[0]);
				classidtext.setText(storage[0]);
			}catch(Exception e) {
				classidtext.setText(null);
			}
			try {
				classnametext.setText(storage[1]);
			}catch(Exception e) {
				classnametext.setText(null);
			}
			try {
				classnumbertext.setText(storage[2]);
			}catch(Exception e) {
				classnumbertext.setText(null);
			}
			try {
				xueyuantext.setValue(storage[3]);
			}catch(Exception e) {
				xueyuantext.getSelectionModel().clearSelection();
			}
		}else {
			PCDao dao = new PCDao();
			int index = list.getSelectionModel().getSelectedIndex();
			classidtext.setText(String.valueOf(dao.list2().get(index).Pno));
			classnametext.setText(dao.list2().get(index).Pname);
			classnumbertext.setText(String.valueOf(dao.list2().get(index).Pnum));
			xueyuantext.setValue(dao.list2().get(index).Pcname);
		}
		
		if(list.getSelectionModel().getSelectedItem().equals("新建班级")) {
			classidtext.setDisable(false);
			closebutton.setVisible(true);
			
			
		}else {
			
			classidtext.setDisable(true);
			closebutton.setVisible(false);
		}
		
	}
}
