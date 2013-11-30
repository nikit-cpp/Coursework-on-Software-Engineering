package ui.view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class AddWord {
	private Display display;
	protected Shell shell;
	protected Text textProbability;
	protected Text textAddableWord;
	private String addableWord;
	protected Table tableDicts;
	protected Button btnAdd;

	protected AddWordManager view;
	
	public AddWord(Display display, String word) {
		this.display=display;
		this.addableWord=word;
		open();
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		
		view = new AddWordManager(this);
		
		shell.open();
		shell.layout();
		/* благодаря этому коду это окно остаётся на экране при закрытии главного окна
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}*/
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(display, SWT.CLOSE);
		shell.setSize(450, 300);
		shell.setText("Добавление слова");
		shell.addListener(SWT.Close, new Listener() {
		      public void handleEvent(Event event) {
		        view.delFromUpdateable();
		      }
		});
		
		btnAdd = new Button(shell, SWT.NONE);
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				view.addWord();
				ViewSuper.updateContainingWordsSuper();
			}
		});
		btnAdd.setBounds(352, 234, 77, 26);
		btnAdd.setText("Добавить");
		btnAdd.setEnabled(false);
		
		Group group = new Group(shell, SWT.NONE);
		group.setText("Вероятность");
		group.setBounds(203, 170, 226, 54);
		
		textProbability = new Text(group, SWT.BORDER);
		textProbability.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				view.changeEnabledAddButton();
			}
		});
		textProbability.setBounds(10, 23, 171, 22);
		
		Group group_1 = new Group(shell, SWT.NONE);
		group_1.setText("Словари");
		group_1.setBounds(10, 10, 179, 250);
		
		tableDicts = new Table(group_1, SWT.BORDER);
		tableDicts.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				view.changeEnabledAddButton();
			}
		});
		tableDicts.setBounds(10, 20, 159, 220);
		
		Group group_2 = new Group(shell, SWT.NONE);
		group_2.setText("Добавляемое слово");
		group_2.setBounds(203, 10, 226, 67);
		
		textAddableWord = new Text(group_2, SWT.BORDER);
		textAddableWord.setEditable(false);
		textAddableWord.setBounds(10, 35, 194, 22);
		if(addableWord==null){
			textAddableWord.setEditable(true);
		}else{
			textAddableWord.setText(addableWord);
		}
		
	}
}
