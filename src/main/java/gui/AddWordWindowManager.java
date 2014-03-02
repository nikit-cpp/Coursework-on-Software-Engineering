package gui;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import engine.thematicdictionary.ThematicDicManager;
import engine.thematicdictionary.ThematicDicManagerException;

public class AddWordWindowManager extends ViewSuper {
	private final Text txtProbability;
	private final Text textAddableWord;
	private final Button btnAdd;
	private final Shell shell;
	
	AddWordWindowManager(AddWordWindow addWord) {
		super();
		
		this.tableThematicDicts=addWord.tableDicts;
		this.btnAdd=addWord.btnAdd;
		this.txtProbability=addWord.textProbability;
		this.textAddableWord=addWord.textAddableWord;
		this.shell = addWord.shell;
		super.createThematicDicTable(tableThematicDicts);
	}
	
	void changeEnabledAddButton(){
		if(tableThematicDicts.getSelectionCount() > 0 && textAddableWord.getText().length()>0){
			try{
				double p = Double.parseDouble(txtProbability.getText());
				// TODO убрать дублирование: засунуть в ThematicDic и побороть конфликт сериализации и static
				btnAdd.setEnabled(true);

				// Проверяем введённую вероятность
				try{
					ThematicDicManager.checkProbabilityBounds(p);
				}catch(IllegalArgumentException ie){
					ie.printStackTrace();
					btnAdd.setEnabled(false);
				}
			}catch(/*java.lang.NumberFormat*/Exception e){
				System.err.println("Введённый текст невозможно распарсить как double.");
				btnAdd.setEnabled(false);
			}
		}else{
			btnAdd.setEnabled(false);
		}
	}

	void addWord() {
		try{
			engine.getTDM().addWord(tableThematicDicts.getSelectionIndex(), textAddableWord.getText(), Double.parseDouble(txtProbability.getText()));
		}catch(ThematicDicManagerException e){
			super.showErrorWindow(shell, e);
		}
	}

	@Override
	public void updateContainingWordsImpl() {
	}

	@Override
	public void updateThematicDictsTableImpl() {
		super.createThematicDicTable(tableThematicDicts);
	}
}
