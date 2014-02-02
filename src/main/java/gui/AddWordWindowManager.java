package gui;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

public class AddWordWindowManager extends ViewSuper {
	private final Text txtProbability;
	private final Text textAddableWord;
	private final Button btnAdd;
	
	AddWordWindowManager(AddWordWindow addWord) {
		super();
		
		this.tableThematicDicts=addWord.tableDicts;
		this.btnAdd=addWord.btnAdd;
		this.txtProbability=addWord.textProbability;
		this.textAddableWord=addWord.textAddableWord;
		
		super.createThematicDicTable(tableThematicDicts);
	}
	
	void changeEnabledAddButton(){
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

	void addWord() {
		try{
			engine.getRubricator().addWord(tableThematicDicts.getSelectionIndex(), textAddableWord.getText(), Double.parseDouble(txtProbability.getText()));
		}catch(ArrayIndexOutOfBoundsException e){
			
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
