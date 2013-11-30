package ui.view;

import java.util.ArrayList;

import main.Engine;
import options.OptId;
import options.Options;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import thematicdictionary.ThematicDic;
import ui.filemanager.FileReader;
import ui.view.listeners.OpenFileDialog;
import foundedwords.WordInfo;

public class AddWordManager extends View1 {
	private Text txtInput;
	private Table tableWords;
	private Text txtOutput;
	private Table tableThematicDicts;
	private Table tableContainsWords;
	private Shell shell;
	private static ArrayList<Updateable> upds = new ArrayList<Updateable>();
	
	private Engine engine;
	
	protected Text txtProbability;
	protected Text textAddableWord;
	protected Button btnAdd;
	
	public AddWordManager(AddWord addWord) {
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
}
