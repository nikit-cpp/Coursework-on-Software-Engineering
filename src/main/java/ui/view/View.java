package ui.view;

import main.*;
import options.OptId;
import options.Options;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

import runtime.dictionary.WordInfo;
import thematic.dictionary.ThematicDic;
import ui.filemanager.FileReader;
import ui.view.listeners.*;

public class View{
	private Text txtInput;
	private Table tableWords;
	private Text txtOutput;
	private Table tableThematicDicts;
	private Shell shell;
	
	private Engine engine;
	
	public View(MainWindow w) {
		this.txtInput=w.txtInput;
		this.tableWords=w.tableWords;
		this.txtOutput=w.txtOutput;
		this.tableThematicDicts = w.tableThematicDicts;
		this.shell=w.shell;
		
		OpenFileDialog.staticInit(shell, this);
		engine = new Engine();
		
		initialize();
	}
	
	/**
	 *  Инициализация окна
	 */
	private void initialize(){
		txtOutput.setText("");
		
		createThematicDicTable();
	}
	
	/**
	 * Удаляет старую и создаёт таблицу словарей на основе их списка,
	 * полученного с помощью engine.getThematicDicts()
	 */
	protected void createThematicDicTable() {
	    tableThematicDicts.removeAll();
	    for (ThematicDic dic : engine.getThematicDicts()) {
	        TableItem ti = new TableItem(tableThematicDicts, SWT.NONE);
	        
	        String dicName = dic.toString();
	        String probabilitty = dic.getProbabilityString();
	        String[] row = {dicName, probabilitty};
	        
	        ti.setText(row);
	        ti.setChecked(dic.getEnabled());
	    }
	}
	
	/**
	 * В(ы)ключает словари в соответствии с таблицей.
	 */
	public void msgTurnDicts(){
		int i=0;
		
		for(TableItem tableItem : tableThematicDicts.getItems()){
			//engine.getThematicDicts().get(i).setEnabled(tableItem.getChecked());
			engine.turnThematicDictionary(tableItem.getChecked(), i);
			i++;
		}
	}
	
	/**
	 * Удаляет старую и создаёт таблицу слов на основе их списка,
	 * полученного с помощью engine.getThematicDicts()
	 */
	private void createWordsTable() {
		tableWords.removeAll();
		for (WordInfo item : engine.getStems()) {
	        TableItem tableItem = new TableItem(tableWords, SWT.NONE);
	        
	        String word = item.getString();
	        String related = item.getRelated();
	        String count = String.valueOf(item.getCount());
	        String num = String.valueOf(item.getNum());
	        
	        String[] row = {num, word, related, count };
	        tableItem.setText(row);
	    }
		
	}
	
	/**
	 * Обрабатывает нажатие кнопки "Рубрикация"
	 */
	public void msgRubricate() {
		engine.rubricate(txtInput.getText());
		createWordsTable();
		createThematicDicTable();
	}
	
	/**
	 * Обрабатывает нажатие кнопки "Реферирование"
	 */
	public void msgReferate() {				
		String s = engine.referate(txtInput.getText());
		txtOutput.setText(s);
	}

	/*
	StringBuilder sb=new StringBuilder();
	private void logln(String s){
		sb.append(s+"\n");
		txtOutput.setText(sb.toString());
	}
	*/
	
	/**
	 * Заполняет {@code txtInput} содержимым файла.
	 * @param selected путь к файлу
	 */
	public void openFile(String selected) {		
		txtInput.setText(FileReader.readTextFromFileToString(selected));
		
		if(Options.getInstance().getBoolean(OptId.RUBRICATE_ON_FILEOPEN))
			msgRubricate();
	}

	public void saveFile(String selected) {
		// TODO Auto-generated method stub
		
	}
}