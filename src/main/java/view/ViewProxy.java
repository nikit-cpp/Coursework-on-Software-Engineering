package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.Collection;
import main.*;
import options.OptId;
import options.Options;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import runtime.dictionary.WordInfo;
import thematic.dictionary.ThematicDic;
import view.listeners.Open;

public class ViewProxy{
	private Text txtInput;
	private Table tableWords;
	private Text txtOutput;
	private Table tableThematicDicts;
	private Shell shell;
	private Options options;
	
	private Engine engine = new Engine();
	
	public ViewProxy(MainWindow w) {
		this.txtInput=w.txtInput;
		this.tableWords=w.tableWords;
		this.txtOutput=w.txtOutput;
		this.tableThematicDicts = w.tableThematicDicts;
		this.shell=w.shell;
		options=Options.getInstance();
		
		initialize();
	}
	
	/**
	 *  Инициализация окна
	 */
	private void initialize(){
		Open.staticInit(shell, this);
		txtOutput.setText("");
		
		initThematicDicTable();
	}
	
	/**
	 * Создаёт таблицу словарей на основе их списка,
	 * полученного с помощью engine.getThematicDicts()
	 */
	protected void initThematicDicTable() {
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
	
	/**
	 * Обрабатывает нажатие кнопки "Рубрикация"
	 */
	public void msgRubricate() {				
		initWordsTable(tableWords, engine.rubricate(txtInput.getText()));
		
		initThematicDicTable();
		
		// TODO Уже не помню зачем, убрать всё тело метода, что ниже данного комментария.
		for(TableItem i : tableThematicDicts.getItems()){
			logln(i+String.valueOf(i.getChecked()));
		}
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
	
	/**
	 * Открывает файл и выводит его содержимое в {@code txtInput},
	 * после чего закрывает файл.
	 * @param selected путь к файлу
	 */
	public void openFile(String selected) {
		StringBuilder sb = new StringBuilder();
				
		Charset charset = Charset.forName(options.getString(OptId.CHARSET));
		// TODO Работа с BOM для UTF-8 : http://commons.apache.org/proper/commons-io/javadocs/api-release/index.html?org/apache/commons/io/package-summary.html
		
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(selected), charset)) {
		    String line = null;
		    boolean firstline=true;
		    while ((line = reader.readLine()) != null) {
		    	if(!firstline) 
		    		sb.append('\n');
		    	sb.append(line);
		    	firstline=false;
		    }
		    reader.close();
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
		
		this.txtInput.setText(sb.toString());
	}

	public void saveFile(String selected) {
		// TODO Auto-generated method stub
		
	}
}