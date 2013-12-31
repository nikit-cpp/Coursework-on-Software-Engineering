package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import engine.filesystem.FileReader;
import engine.foundedwords.WordInfo;
import engine.options.OptId;
import engine.options.Options;
import gui.listeners.OpenFileDialog;

public class MainWindowManager extends ViewSuper /*implements Updateable*/ {
	private final Text txtInput;
	private final Table tableWords;
	private final Shell shell;
		
	/**
	 * Этот конструктор используется вместе с MainWindow
	 * @param w
	 */
	MainWindowManager(MainWindow w) {
		super();
		
		this.txtInput=w.txtInput;
		this.tableWords=w.tableWords;
		this.tableThematicDicts = w.tableThematicDicts;
		this.shell=w.shell;
		
		OpenFileDialog.staticInit(shell, this);
		
		super.createThematicDicTable(tableThematicDicts);
	}
	
	/**
	 * В(ы)ключает словари в соответствии с таблицей.
	 */
	void msgTurnDicts(){
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
	void msgRubricate() {
		engine.rubricate(txtInput.getText());
		createWordsTable();
		super.createThematicDicTable(tableThematicDicts);
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
	public void updateContainingWordsImpl() {		
	}

	@Override
	public void updateThematicDictsTableImpl() {
		super.createThematicDicTable(tableThematicDicts);
	}
}
