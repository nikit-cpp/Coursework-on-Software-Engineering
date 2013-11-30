package ui.view;

import options.OptId;
import options.Options;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import ui.filemanager.FileReader;
import ui.view.listeners.OpenFileDialog;
import foundedwords.WordInfo;

public class MainWindowManager extends ViewSuper /*implements Updateable*/ {
	private Text txtInput;
	private Table tableWords;
	private Text txtOutput;
	private Table tableThematicDicts;
	private Shell shell;
		
	/**
	 * Этот конструктор используется вместе с MainWindow
	 * @param w
	 */
	public MainWindowManager(MainWindow w) {
		super();
		
		this.txtInput=w.txtInput;
		this.tableWords=w.tableWords;
		this.txtOutput=w.txtOutput;
		this.tableThematicDicts = w.tableThematicDicts;
		this.shell=w.shell;
		
		OpenFileDialog.staticInit(shell, this);
		
		txtOutput.setText("");
		super.createThematicDicTable(tableThematicDicts);
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
		super.createThematicDicTable(tableThematicDicts);
	}
	
	/**
	 * Обрабатывает нажатие кнопки "Реферирование"
	 */
	public void msgReferate() {				
		String s = engine.referate(txtInput.getText());
		txtOutput.setText(s);
	}

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
	
	@Override
	public void updateContainingWords() {		
	}
}
