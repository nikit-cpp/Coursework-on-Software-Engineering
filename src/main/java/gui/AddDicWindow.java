package gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class AddDicWindow {
	private Display display;
	protected Shell shell;
	protected Text textAddableDic;
	protected Button btnAdd;

	protected AddDicWindowManager view;
	
	public AddDicWindow(Display display) {
		this.display=display;
		open();
	}

	/**
	 * Open the window.
	 */
	public void open() {
		createContents();
		
		view = new AddDicWindowManager(this);
		
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
		shell.setSize(360, 142);
		shell.setText("Добавление словаря");
		shell.addListener(SWT.Close, new Listener() {
		      public void handleEvent(Event event) {
		        view.delFromUpdateable();
		        shell.dispose();
		      }
		});
		
		btnAdd = new Button(shell, SWT.NONE);
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				view.addDic();
				ViewSuper.updateThematicDictsTable();
			}
		});
		btnAdd.setBounds(272, 83, 77, 26);
		btnAdd.setText("Добавить");
		btnAdd.setEnabled(false);
		
		Group group_2 = new Group(shell, SWT.NONE);
		group_2.setText("Добавляемый словарь");
		group_2.setBounds(10, 10, 339, 67);
		
		textAddableDic = new Text(group_2, SWT.BORDER);
		textAddableDic.setBounds(10, 35, 319, 22);		
		textAddableDic.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				view.changeEnabledAddButton();
			}
		});
	}
}
