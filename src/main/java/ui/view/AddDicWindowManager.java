package ui.view;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import thematicdictionary.ThematicDicManager;

public class AddDicWindowManager extends ViewSuper {
	private Text name;
	private Button btnAdd;
	private ThematicDicManager tdm;
	
	public AddDicWindowManager(AddDicWindow addDicWindow) {
		this.name=addDicWindow.textAddableDic;
		btnAdd=addDicWindow.btnAdd;
		tdm=engine.getTDM();
	}

	public void addDic() {
		final String dicname = name.getText();
		System.out.println("добавляем словарь "+dicname);
		
		tdm.add(dicname, false);
	}

	@Override
	public void updateContainingWordsImpl() {		
	}

	@Override
	public void updateThematicDictsTableImpl() {
	}

	public void changeEnabledAddButton() {
		if(!name.getText().isEmpty())
			btnAdd.setEnabled(true);
		else
			btnAdd.setEnabled(false);
	}

}
