package view;
import model.Model;
import model.Token;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

public class ModelObserver{
	private Text text;
	private Table table;
	private Model model = new Model();
	
	public ModelObserver(Text text, Table table) {
		this.text=text;
		this.table=table;
	}

	public void msgMouseUp() {
		putNewItemToList();
	}

	private void putNewItemToList() {
		// TODO сделать инициализатор и засунуть это туда
		table.removeAll();
		for(Token t : model.tokenize(text.getText())){
			String s = t.value;
			TableItem tableItem = new TableItem(table, SWT.NONE);
			tableItem.setText(s);
		}
	}
	
	public void getText2(String[] sa){
		
	}
}
