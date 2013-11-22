// http://www.chrisnewland.com/swt-best-practice-single-display-multiple-shells-111

package ui.view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;

public class ThematicDictionaries {
	private Display display;
	protected Shell shell;
	private Table table;

	/**
	 * Launch the application.
	 * @param args
	 */
	/*public static void main(String[] args) {
		try {
			ThematicDictionaries window = new ThematicDictionaries();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	public ThematicDictionaries(Display display){
		this.display=display;
		open();
	}

	/**
	 * Open the window.
	 */
	public void open() {
		//Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		/*while (!shell.isDisposed()) {
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
		shell.setText("Тематические словари");
		
		SashForm sashForm = new SashForm(shell, SWT.NONE);
		sashForm.setBounds(10, 88, 367, 128);
		
		List list = new List(sashForm, SWT.BORDER);
		
		table = new Table(sashForm, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		sashForm.setWeights(new int[] {1, 1});
		
		shell.addListener(SWT.Close, new Listener()
        {
           //@Override
           public void handleEvent(Event event)
           {
              //System.out.println("Child Shell handling Close event, about to dispose this Shell");
              shell.dispose();
           }
        });
	}
}
