package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import engine.thematicdictionary.ThematicDicManagerException;

public class ThematicDictsWindowManager extends ViewSuper /*implements Updateable*/ {
	private final Table tableContainsWords;
	private final Shell shell;
	private final MenuItem[] turnableContextMenuItems;
	
	/**
	 * Этот конструктор используется вместе с ThematicDictionaries
	 * @param w
	 */
	ThematicDictsWindowManager(ThematicDictsWindow w) {
		super();

		this.tableThematicDicts=w.tableDicts;
		this.tableContainsWords=w.tableWords;
		
		super.createThematicDicTable(tableThematicDicts);
		this.shell = w.shell;
		turnableContextMenuItems  =w.turnableContextMenuItems;
	}
		
	private void createContainsWordsTable(int selectedIndex) {
		tableContainsWords.removeAll();
		
		// Получаем табличную строку из словаря ThematicDic с номером selectedIndex в цикле,
		// т. к. он реализует интерфейс Iterable<String[]>
		for(String[] row : engine.getThematicDicts().get(selectedIndex)){
			TableItem tableItem = new TableItem(tableContainsWords, SWT.NONE);
	        tableItem.setText(row);
		}
	}
	
	void deleteWord(int wordIndex, int dicIndex) {
		try{
		final String dic = tableThematicDicts.getItem(dicIndex).getText();
		final String word = tableContainsWords.getItem(wordIndex).getText(0);
		System.out.println("удаляем "+dic+"("+dicIndex+"): "+word);
		engine.getTDM().deleteWord(wordIndex, dicIndex);
		}catch(ArrayIndexOutOfBoundsException e){
  	  }catch(Exception e){
  		  e.printStackTrace();
  	  }
	}

	@Override
	public void updateContainingWordsImpl() {
		try{
			createContainsWordsTable(tableThematicDicts.getSelectionIndex());
		}catch(ArrayIndexOutOfBoundsException e){
		}
	}

	@Override
	public void updateThematicDictsTableImpl() {
		super.createThematicDicTable(tableThematicDicts);
	}

	void deleteDic(int dicIndex) {
		try {
			engine.getTDM().deleteDic(dicIndex);
		} catch (ThematicDicManagerException e) {
			super.showErrorWindow(shell, e);
		}
	}

	public void renameDic(int dicIndex, String string) {
		try {
			engine.getTDM().renameDic(dicIndex, string);
		} catch (ThematicDicManagerException e) {
			super.showErrorWindow(shell, e);
		}
		ViewSuper.updateThematicDictsTable();
	}

	public void updateContextMenuVisible() {
		System.out.println("updateContextMenuVisible()");
		boolean needEnable = tableThematicDicts.getSelectionIndex() != -1;

		for(MenuItem m : turnableContextMenuItems)
			m.setEnabled(needEnable);
	}
}
