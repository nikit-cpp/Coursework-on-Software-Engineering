package view;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;


public class MyWin {

	protected Shell shell;
	private Text txtSplitPublicString;
	private Table table;
	private ModelObserver ob;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyWin window = new MyWin();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		
		ob = new ModelObserver(txtSplitPublicString, table);
		
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(695, 504);
		shell.setText("\u041A\u0443\u0440\u0441\u043E\u0432\u043E\u0439 \u043F\u0440\u043E\u0435\u043A\u0442");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem menuFileCascade = new MenuItem(menu, SWT.CASCADE);
		menuFileCascade.setText("\u0424\u0430\u0439\u043B");
		
		Menu menuFile = new Menu(menuFileCascade);
		menuFileCascade.setMenu(menuFile);
		
		MenuItem itemOpen = new MenuItem(menuFile, SWT.NONE);
		itemOpen.setText("\u041E\u0442\u043A\u0440\u044B\u0442\u044C");
		
		MenuItem mntmNewItem = new MenuItem(menuFile, SWT.NONE);
		mntmNewItem.setText("New Item");
		
		MenuItem menuDictCascade = new MenuItem(menu, SWT.CASCADE);
		menuDictCascade.setText("\u0421\u043B\u043E\u0432\u0430\u0440\u0438");
		
		Menu menuDict = new Menu(menuDictCascade);
		menuDictCascade.setMenu(menuDict);
		
		MenuItem menuItem_3 = new MenuItem(menuDict, SWT.NONE);
		menuItem_3.setText("\u0423\u043F\u0440\u0430\u0432\u043B\u0435\u043D\u0438\u0435");
		
		MenuItem menuItem_1 = new MenuItem(menu, SWT.NONE);
		menuItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.exit(0);
			}
		});
		menuItem_1.setText("\u0412\u044B\u0445\u043E\u0434");
		
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		
		Composite composite_3 = new Composite(tabFolder, SWT.NONE);
		composite_3.setLayout(new FormLayout());
		
		Button button = new Button(composite_3, SWT.CENTER);
		FormData fd_button = new FormData();
		fd_button.bottom = new FormAttachment(100, -10);
		fd_button.right = new FormAttachment(100, -240);
		fd_button.left = new FormAttachment(0, 271);
		button.setLayoutData(fd_button);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		button.setText("\u0412\u044B\u044F\u0441\u043D\u0438\u0442\u044C");
		
		TabItem tabItem_1 = new TabItem(tabFolder, SWT.NONE);
		tabItem_1.setText("\u0420\u0443\u0431\u0440\u0438\u043A\u0430\u0446\u0438\u044F");
		
		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tabItem_1.setControl(composite_1);
		composite_1.setLayout(new FormLayout());
		
		SashForm sashForm = new SashForm(composite_1, SWT.NONE);
		FormData fd_sashForm = new FormData();
		fd_sashForm.top = new FormAttachment(0, 5);
		fd_sashForm.right = new FormAttachment(100, -10);
		fd_sashForm.left = new FormAttachment(0, 5);
		sashForm.setLayoutData(fd_sashForm);
		
		txtSplitPublicString = new Text(sashForm, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtSplitPublicString.setText(" split\r\n\r\npublic String[] split(String regex,\r\n                      int limit)\r\n\r\n    Splits this string around matches of the given regular expression.\r\n\r\n    The array returned by this method contains each substring of this string that is terminated by another substring that matches the given expression or is terminated by the end of the string. The substrings in the array are in the order in which they occur in this string. If the expression does not match any part of the input then the resulting array has just one element, namely this string.\r\n\r\n    The limit parameter controls the number of times the pattern is applied and therefore affects the length of the resulting array. If the limit n is greater than zero then the pattern will be applied at most n - 1 times, the array's length will be no greater than n, and the array's last entry will contain all input beyond the last matched delimiter. If n is non-positive then the pattern will be applied as many times as possible and the array can have any length. If n is zero then the pattern will be applied as many times as possible, the array can have any length, and trailing empty strings will be discarded.\r\n\r\n    The string \"boo:and:foo\", for example, yields the following results with these parameters:\r\n\r\n        Regex \tLimit \tResult\r\n        : \t2 \t{ \"boo\", \"and:foo\" }\r\n        : \t5 \t{ \"boo\", \"and\", \"foo\" }\r\n        : \t-2 \t{ \"boo\", \"and\", \"foo\" }\r\n        o \t5 \t{ \"b\", \"\", \":and:f\", \"\", \"\" }\r\n        o \t-2 \t{ \"b\", \"\", \":and:f\", \"\", \"\" }\r\n        o \t0 \t{ \"b\", \"\", \":and:f\" }\r\n\r\n    An invocation of this method of the form str.split(regex, n) yields the same result as the expression\r\n\r\n        Pattern.compile(regex).split(str, n) \r\n\r\n    Parameters:\r\n        regex - the delimiting regular expression\r\n        limit - the result threshold, as described above \r\n    Returns:\r\n        the array of strings computed by splitting this string around matches of the given regular expression \r\n    Throws:\r\n        PatternSyntaxException - if the regular expression's syntax is invalid\r\n    Since:\r\n        1.4\r\n    See Also:\r\n        Pattern\r\n\r\n");
		
		table = new Table(sashForm, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableItem tableItem = new TableItem(table, SWT.NONE);
		tableItem.setText("New TableItem");
		
		TableItem tableItem_1 = new TableItem(table, SWT.NONE);
		tableItem_1.setText("\u0444\u0438\u0437\u0438\u043A\u0430");
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setMoveable(true);
		tableColumn.setWidth(131);
		tableColumn.setText("\u0421\u043B\u043E\u0432\u0430\u0440\u044C");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setMoveable(true);
		tableColumn_1.setWidth(99);
		tableColumn_1.setText("\u0421\u043E\u0432\u043F\u0430\u0434\u0435\u043D\u0438\u0435");
		sashForm.setWeights(new int[] {227, 227});
		
		Button btnNewButton = new Button(composite_1, SWT.NONE);
		
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				ob.msgMouseUp();
			}
		});
		
		
		fd_sashForm.bottom = new FormAttachment(100, -42);
		FormData fd_btnNewButton = new FormData();
		fd_btnNewButton.top = new FormAttachment(sashForm, 6);
		fd_btnNewButton.right = new FormAttachment(sashForm, 0, SWT.RIGHT);
		fd_btnNewButton.left = new FormAttachment(100, -146);
		btnNewButton.setLayoutData(fd_btnNewButton);
		btnNewButton.setText("\u041E\u0431\u0440\u0430\u0431\u043E\u0442\u0430\u0442\u044C");
		
		TabItem tabItem_2 = new TabItem(tabFolder, SWT.NONE);
		tabItem_2.setText("\u0421\u043C\u044B\u0441\u043B");
		
		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		tabItem_2.setControl(composite_2);
		composite_2.setLayout(new FormLayout());
		
		SashForm sashForm_1 = new SashForm(composite_2, SWT.NONE);
		FormData fd_sashForm_1 = new FormData();
		fd_sashForm_1.top = new FormAttachment(0, 57);
		fd_sashForm_1.left = new FormAttachment(0, 111);
		fd_sashForm_1.bottom = new FormAttachment(100, -98);
		fd_sashForm_1.right = new FormAttachment(100, -163);
		sashForm_1.setLayoutData(fd_sashForm_1);
		
		Label label = new Label(sashForm_1, SWT.NONE);
		label.setText("\u041F\u043E\u043A\u0430 \u043D\u0438\u0447\u0435\u0433\u043E...");
		
		Button button_1 = new Button(composite_2, SWT.NONE);
		button_1.setText("\u041E\u0431\u0440\u0430\u0431\u043E\u0442\u0430\u0442\u044C-2");
		FormData fd_button_1 = new FormData();
		fd_button_1.top = new FormAttachment(0, 390);
		fd_button_1.left = new FormAttachment(0, 533);
		fd_button_1.right = new FormAttachment(100, -10);
		button_1.setLayoutData(fd_button_1);

	}
}
