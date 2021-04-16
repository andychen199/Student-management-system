package admin.studentUI;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/*
 * 左侧文件列表
 * 
 */

public class FileListView extends ListView<FileItem>
{
	// 数据源
	private ObservableList<FileItem> listData = FXCollections.observableArrayList();

	public FileListView()
	{
		// 设置数据源
		setItems(listData);

		// 设置单元格生成器 （工厂）
		setCellFactory(new Callback<ListView<FileItem>, ListCell<FileItem>>()
		{

			@Override
			public ListCell<FileItem> call(ListView<FileItem> param)
			{
				return new MyListCell();
			}
		});

	}

	public ObservableList<FileItem> data()
	{
		return listData;
	}

	////////////////////////////////////////////////
	// 负责单元格Cell的显示
	static class MyListCell extends ListCell<FileItem>
	{

		@Override
		public void updateItem(FileItem item, boolean empty)
		{
			// FX框架要求必须先调用 super.updateItem()
			super.updateItem(item, empty);

			// 自己的代码
			if (item == null)
			{
				this.setText("");
			} else
			{
				this.setText(item.fileName);
			}
		}
	}

}
