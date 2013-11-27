// http://www.chrisnewland.com/swt-best-practice-single-display-multiple-shells-111

package ui.view;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TableColumn;

public class ThematicDictionaries {
	private Display display;
	protected Shell shell;
	protected Table tableDicts;
	protected View view;
	protected Table tableWords;
	private TableColumn tableColumn;
	private TableColumn tableColumn_1;
	private TableColumn tableColumn_2;
	
	public ThematicDictionaries(Display display){
		this.display=display;
		open();
	}

	/**
	 * Open the window.
	 */
	public void open() {
		createContents();
		
		view = new View(this);
		
		shell.open();
		shell.layout();
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(display, SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.RESIZE);
		shell.setSize(450, 300);
		shell.setText("Тематические словари");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm = new SashForm(shell, SWT.NONE);
		
		tableDicts = new Table(sashForm, SWT.BORDER | SWT.FULL_SELECTION);
		tableDicts.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent e) {
		    	  //System.out.println(tableDicts.getSelectionIndex());
		    	  view.createContainsWordsTable(tableDicts.getSelectionIndex());
		      }
		});
		tableDicts.setHeaderVisible(true);
		tableDicts.setLinesVisible(true);
		
		tableColumn = new TableColumn(tableDicts, SWT.NONE);
		tableColumn.setWidth(100);
		tableColumn.setText("Словари");
		
		tableWords = new Table(sashForm, SWT.BORDER | SWT.FULL_SELECTION);
		tableWords.setHeaderVisible(true);
		tableWords.setLinesVisible(true);
		
		tableColumn_1 = new TableColumn(tableWords, SWT.NONE);
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("Слово");
		
		tableColumn_2 = new TableColumn(tableWords, SWT.NONE);
		tableColumn_2.setWidth(100);
		tableColumn_2.setText("Вероятность");
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
