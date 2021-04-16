package admin.xueqinfoUI;

import attendance.dao.XueqiDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import admin.studentUI.pwdtrans;
import attendance.bean.Xueqi;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

public class XQController {
	@FXML private DatePicker startdatecombo;
	@FXML private DatePicker enddatecombo;
	@FXML private ComboBox oneortwo;
	@FXML private Button refreshbutton;
	@FXML private Button addbutton;
	@FXML private ListView xueqilist;
	private final String pattern = "yyyy-MM-dd";
	int count1 = 0;
	String[][] storage = null;
    int i = 0;
    @FXML
    protected void Add(ActionEvent event) throws ParseException {
        //actiontarget.setText("Sign in button pressed");
    	String start = null;
    	String end = null;
    	String xueqistring = null;
    	try {
    		start = startdatecombo.getValue().toString();
    		end = enddatecombo.getValue().toString();
    		xueqistring = oneortwo.getValue().toString();
    	}catch(Exception e) {
    		e.printStackTrace();
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setTitle("提示");
    		alert.setHeaderText(null);
    		alert.setContentText("添加失败，请完善学期信息！");
    		alert.showAndWait();
    	}
    	XueqiDao xueqi = new XueqiDao();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Date start1 = sdf.parse(start);
	    Date end1 = sdf.parse(end);
	    int success = 0;
	    
    	if(xueqistring.equals("一")) {
    		xueqi.add(start1, end1, 1);
    		success = 1;
    		
    	}
    	else if(xueqistring.equals("二")) {
    		xueqi.add(start1, end1, 2);
    		success = 1;
    		
    	}
    	if(success == 1) {
    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
    		alert.setTitle("提示");
    		alert.setHeaderText(null);
    		alert.setContentText("添加成功");
    		String s = new SimpleDateFormat("yyyy").format(start1);
        	String e = new SimpleDateFormat("yyyy").format(end1);
        	int intstart = 0;
        	int intend = 0;
        	intstart = Integer.valueOf(s);
        	intend = Integer.valueOf(e);
        	if(intstart==intend) {
        		intstart = intstart - 1;
        	}
        	else if((intstart+1)==intend) {
        		
        	}
        	String all = intstart + " - " + intend + "学年" + "第" + xueqistring + "学期" + " ( " + startdatecombo.getValue().toString() + " ~ " + enddatecombo.getValue().toString() + " ) ";
    		xueqilist.getItems().addAll(all);
    		xueqilist.refresh();
    		alert.showAndWait();
    		startdatecombo.setValue(null);
    		enddatecombo.setValue(null);
    		oneortwo.getSelectionModel().clearSelection();
    	}
    	
    }
    @FXML
    protected void Refresh(ActionEvent event) {
        InitList();
    }
    public void InitUI() {
    	oneortwo.getItems().addAll("一", "二");
    	StringConverter converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                DateTimeFormatter.ofPattern(pattern);
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };    
        startdatecombo.setConverter(converter);
        enddatecombo.setConverter(converter);
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
		confirmation.setTitle("提示");
        confirmation.setHeaderText(null);
        confirmation.setContentText("确认删除？");
        xueqilist.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem deleteItem = new MenuItem();
            deleteItem.textProperty().bind(Bindings.format("删除 \"%s\"", cell.itemProperty()));
            deleteItem.setOnAction(event -> {
                Optional<ButtonType> result = confirmation.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK){
                	int num = xueqilist.getSelectionModel().getSelectedIndex();
                	XueqiDao dao = new XueqiDao();
                	if(dao.delete(storage[num][0], storage[num][1])) {
                		
                    	xueqilist.getItems().remove(cell.getItem());
                    	
                    	confirmation.hide();
                	}
                	else {
                		Alert alert = new Alert(Alert.AlertType.ERROR);
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
        InitList();
    }
    public void InitList() {
    	XueqiDao xueqi = new XueqiDao();
        List<Xueqi> is = new ArrayList<Xueqi>();
        is = xueqi.getList();
        xueqilist.getItems().clear();
        for(Xueqi x:is) {
        	count1++;
        }
        storage = new String[count1][2];

        
        for(Xueqi x:is) {
        	int start = 0;
        	int end = 0;
        	String s = new SimpleDateFormat("yyyy").format(x.startdate);
        	String e = new SimpleDateFormat("yyyy").format(x.enddate);
        	start = Integer.valueOf(s);
        	end = Integer.valueOf(e);
        	if(start==end) {
        		start = start - 1;
        	}
        	else if((start+1)==end) {
        		
        	}
        	String count = null;
        	if(x.Xueqi==1) {
        		count = "一";
        	}
        	else if(x.Xueqi==2) {
        		count = "二";
        	}
        	String all = start + " - " + end + "学年" + "第" + count + "学期" + " ( " + x.startdate + " ~ " + x.enddate + " ) ";
        	xueqilist.getItems().addAll(all);
        	storage[i][0]=x.startdate.toString();
        	storage[i][1]=x.enddate.toString();
        	i++;
        }
        for(i=0;i<count1;i++) {
        	System.out.println(storage[i][0]);
        	System.out.println(storage[i][1]);
        }
    }
}
