package view;
import java.util.ArrayList;

import model.Model;
import model.Token;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

public class ViewProxy{
	private Text text;
	private Table table;
	private Model model = new Model();
	
	public ViewProxy(Text text, Table table) {
		this.text=text;
		this.table=table;
	}

	public void msgMouseUp() {
		putNewItemToList();
	}

	private void putNewItemToList() {
		// TODO сделать инициализатор и засунуть это туда
		table.removeAll();
		
		for(Token tokenized : model.tokenize(text.getText())){
			ArrayList<Token> normalizedList = model.normalize(tokenized);
			if (normalizedList.isEmpty()){
				TableItem tableItem = new TableItem(table, SWT.NONE);
				tableItem.setText(new String[] {tokenized.value, tokenized.value});
				tableItem.setGrayed(true);
			}else{
				for(Token normalized : normalizedList){
					TableItem tableItem = new TableItem(table, SWT.NONE);
					tableItem.setText(new String[] {tokenized.value, normalized.value});
				}
			}
		}
	}
	
	public void getText2(String[] sa){
		
	}
}
