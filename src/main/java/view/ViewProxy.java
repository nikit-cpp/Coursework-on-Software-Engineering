package view;

import java.util.ArrayList;
import java.util.Collection;

import main.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

import runtime.dictionary.WordInfo;
import thematic.dictionary.ThematicDic;

public class ViewProxy{
	private Text txtInput;
	private Table tableWords;
	private Text txtOutput;
	private Table tableThematicDicts;
	
	private Engine engine = new Engine();
	
	public ViewProxy(MainWindow w) {
		this.txtInput=w.txtInput;
		this.tableWords=w.tableWords;
		this.txtOutput=w.txtOutput;
		this.tableThematicDicts = w.tableThematicDicts;
		
		initialize();
	}
	
	/**
	 *  Инициализация окна
	 */
	private void initialize(){
		
		txtOutput.setText("");
		
		initThematicDicTable(tableThematicDicts, engine.getThematicDicts());
	}
	
	protected void initThematicDicTable(Table table, ArrayList<ThematicDic> arrayList) {
	    tableThematicDicts.removeAll();
	    for (ThematicDic item : arrayList) {
	        TableItem ti = new TableItem(table, SWT.NONE);
	        ti.setText(item.toString());
	        ti.setChecked(item.getEnabled());
	    }
	}
	
	private void initWordsTable(Table table, Collection<WordInfo> arrayList) {
		tableWords.removeAll();
		for (WordInfo item : arrayList) {
	        TableItem tableItem = new TableItem(table, SWT.NONE);
	        
	        String word = item.getString();
	        String related = item.getRelated();
	        String count = String.valueOf(item.getCount());
	        
	        String[] row = {word, related, count};
	        tableItem.setText(row);
	    }
		
	}
	
	public void msgRubricate() {				
		initWordsTable(tableWords, engine.rubricate(txtInput.getText()));
		
		// TODO Уже не помню зачем, убрать всё тело метода, что ниже данного комментария.
		for(TableItem i : tableThematicDicts.getItems()){
			logln(i+String.valueOf(i.getChecked()));
		}
		
		engine.turnThematicDictionaries();
	}
	
	public void msgReferate() {				
		String s = engine.referate(txtInput.getText());
		txtOutput.setText(s);
	}
	
	StringBuilder sb=new StringBuilder();
	private void logln(String s){
		sb.append(s+"\n");
		txtOutput.setText(sb.toString());
	}
}
