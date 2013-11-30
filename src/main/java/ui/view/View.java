package ui.view;

import java.util.HashMap;

import main.*;
import options.OptId;
import options.Options;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

import foundedwords.WordInfo;
import thematicdictionary.ThematicDic;
import ui.filemanager.FileReader;
import ui.view.listeners.*;

public class View{		
	private Text txtInput;
	private Table tableWords;
	private Text txtOutput;
	private Table tableThematicDicts;
	private Table tableContainsWords;
	private Shell shell;
	//private 
	
	private Engine engine;
	
	/**
	 * Этот конструктор используется вместе с MainWindow
	 * @param w
	 */
	public View(MainWindow w) {
		this.txtInput=w.txtInput;
		this.tableWords=w.tableWords;
		this.txtOutput=w.txtOutput;
		this.tableThematicDicts = w.tableThematicDicts;
		this.shell=w.shell;
		
		OpenFileDialog.staticInit(shell, this);
		initialize();
		
		txtOutput.setText("");
	}
	
	/**
	 * Этот корнструктор используется вместе с ThematicDictionaries
	 * @param w
	 */
	public View(ThematicDictionaries w) {
		this.tableThematicDicts=w.tableDicts;
		this.tableContainsWords=w.tableWords;
		
		initialize();
	}
	
	protected Text txtProbability;
	protected Text textAddableWord;
	protected Button btnAdd;
	
	public View(AddWord addWord) {
		this.tableThematicDicts=addWord.tableDicts;
		this.btnAdd=addWord.btnAdd;
		this.txtProbability=addWord.textProbability;
		this.textAddableWord=addWord.textAddableWord;
		initialize();
	}

	/**
	 *  Общая инициализация
	 */
	private void initialize(){
		engine = Engine.getInstance();
		createThematicDicTable();
	}
	
	/**
	 * Удаляет старую и создаёт таблицу словарей на основе их списка,
	 * полученного с помощью engine.getThematicDicts()
	 */
	protected void createThematicDicTable() {
	    tableThematicDicts.removeAll();
		//clearTable(tableThematicDicts);
		
	    int i=0;
	    for (ThematicDic dic : engine.getThematicDicts()) {
	    	WrappedTableItem wti = new WrappedTableItem(tableThematicDicts, SWT.NONE);
	    	wti.arrListPos=i;
	        
	        wti.setText(dic.getRow());
	        
	        wti.setChecked(dic.getEnabled());
	        i++;
	    }
	}
	
	/**
	 * В(ы)ключает словари в соответствии с таблицей.
	 */
	public void msgTurnDicts(){
		for(TableItem tableItem : tableThematicDicts.getItems()){
			WrappedTableItem w = (WrappedTableItem) tableItem;
			engine.turnThematicDictionary(tableItem.getChecked(), w.arrListPos);
		}
	}
	
	/**
	 * Удаляет старую и создаёт таблицу слов на основе их списка,
	 * полученного с помощью engine.getThematicDicts()
	 */
	private void createWordsTable() {
		tableWords.removeAll();
		for (WordInfo wordInfo : engine.getStems()) {
	        TableItem tableItem = new TableItem(tableWords, SWT.NONE);
	        
	        tableItem.setText(wordInfo.getRow());
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
	
	private void clearTable(Table table){
		while ( table.getColumnCount() > 0 ) {
		    table.getColumns()[ 0 ].dispose();
		}
	}

	public void createContainsWordsTable(int selectedIndex) {
		tableContainsWords.removeAll();
		
		// Пулучаем табличную строку из словаря ThematicDic с номером selectedIndex в цикле,
		// т. к. он реализует интерфейс Iterable<String[]>
		for(String[] row : engine.getThematicDicts().get(selectedIndex)){
			TableItem tableItem = new TableItem(tableContainsWords, SWT.NONE);
	        tableItem.setText(row);
		}
	}
	
	public void changeEnabledAddButton(){
		if(tableThematicDicts.getSelectionCount() > 0 && textAddableWord.getText().length()>0){
			try{
				double p = Double.parseDouble(txtProbability.getText());
				// TODO убрать дублирование: засунуть в ThematicDic и побороть конфликт сериализации и static
				btnAdd.setEnabled(true);
				if(p>1.0 || p<0.0)
					btnAdd.setEnabled(false);
			}catch(/*java.lang.NumberFormat*/Exception e){
				System.err.println("Введённый текст невозможно распарсить как double.");
				btnAdd.setEnabled(false);
			}
		}else{
			btnAdd.setEnabled(false);
		}
	}

	public void addWord() {
		engine.getTDM().addWord(tableThematicDicts.getSelectionIndex(), textAddableWord.getText(), Double.parseDouble(txtProbability.getText()));
	}

	public void deleteWord(String word, int dicIndex) {
		final String dic = tableThematicDicts.getItem(dicIndex).getText();
		System.out.println("удаляем "+dic+"("+dicIndex+"): "+word);
		engine.getTDM().deleteWord(word, dicIndex);
	}
}