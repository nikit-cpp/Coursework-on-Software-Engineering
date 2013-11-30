package ui.view;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Group;

import ui.view.View;
import ui.view.listeners.Open;
import ui.view.listeners.SortListenerFactory;

import org.eclipse.wb.swt.SWTResourceManager;

public class MainWindow {
	private Display display;
	protected Shell shell;
	protected Text txtInput;
	protected Table tableWords;
	protected MainWindowManager view;
	protected Text txtOutput;
	protected Table tableThematicDicts;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		display = Display.getDefault();
		createContents();
		
		view = new MainWindowManager(this);
		
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
		shell.setSize(695, 554);
		shell.setText("\u041A\u0443\u0440\u0441\u043E\u0432\u043E\u0439 \u043F\u0440\u043E\u0435\u043A\u0442");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem menuFileCascade = new MenuItem(menu, SWT.CASCADE);
		menuFileCascade.setText("\u0424\u0430\u0439\u043B");
		
		Menu menuFile = new Menu(menuFileCascade);
		menuFileCascade.setMenu(menuFile);
		
		MenuItem itemOpen = new MenuItem(menuFile, SWT.NONE);
		itemOpen.addSelectionListener(new Open());
		itemOpen.setText("\u041E\u0442\u043A\u0440\u044B\u0442\u044C");
		
		MenuItem itemSaveInput = new MenuItem(menuFile, SWT.NONE);
		itemSaveInput.setText("Сохранить входной");
		
		MenuItem itemSaveOutput = new MenuItem(menuFile, SWT.NONE);
		itemSaveOutput.setText("Сохранить результат");
		
		MenuItem menuWinCascade = new MenuItem(menu, SWT.CASCADE);
		menuWinCascade.setText("Окна");
		
		Menu menuWin = new Menu(menuWinCascade);
		menuWinCascade.setMenu(menuWin);
		
		MenuItem menuItemThematicDicts = new MenuItem(menuWin, SWT.NONE);
		menuItemThematicDicts.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new ThematicDictionaries(display);
			}
		});
		menuItemThematicDicts.setText("Тематические словари");
		
		MenuItem menuItemAddingWord = new MenuItem(menuWin, SWT.NONE);
		menuItemAddingWord.setText("Добавление слов");
		menuItemAddingWord.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new AddWord(display, null);
			}
		});
		
		MenuItem menuItemExit = new MenuItem(menu, SWT.NONE);
		menuItemExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.exit(0);
			}
		});
		menuItemExit.setText("Выход");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new FormLayout());
		
		SashForm sashForm = new SashForm(composite, SWT.VERTICAL);
		FormData fd_sashForm = new FormData();
		fd_sashForm.top = new FormAttachment(0, 10);
		fd_sashForm.left = new FormAttachment(0, 10);
		fd_sashForm.right = new FormAttachment(100);
		Button btnReferate = new Button(composite, SWT.NONE);
		fd_sashForm.bottom = new FormAttachment(btnReferate, -6);
		sashForm.setLayoutData(fd_sashForm);
		
		SashForm sashFormNorth = new SashForm(sashForm, SWT.NONE);
		
		Group groupInText = new Group(sashFormNorth, SWT.NONE);
		groupInText.setText("Входной текст");
		groupInText.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		txtInput = new Text(groupInText, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtInput.setText("Каким образом XP снижает перечисленные ранее риски?\r\nТочка точки точкой точками точка");
		
		Group groupOutText = new Group(sashFormNorth, SWT.NONE);
		groupOutText.setText("Логгер/Примерный смысл");
		groupOutText.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		txtOutput = new Text(groupOutText, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtOutput.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		txtOutput.setText("asdfgh");
		sashFormNorth.setWeights(new int[] {333, 333});
		
		SashForm sashFormSouth = new SashForm(sashForm, SWT.NONE);
		
		Group groupFoundedWords = new Group(sashFormSouth, SWT.NONE);
		groupFoundedWords.setText("\u041D\u0430\u0439\u0434\u0435\u043D\u043D\u044B\u0435 \u0441\u043B\u043E\u0432\u0430");
		groupFoundedWords.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		tableWords = new Table(groupFoundedWords, SWT.BORDER | SWT.FULL_SELECTION);
		tableWords.setHeaderVisible(true);
		tableWords.setLinesVisible(true);
		
		TableColumn colNumber = new TableColumn(tableWords, SWT.NONE);
		colNumber.setMoveable(true);
		colNumber.setWidth(47);
		colNumber.setText("#");
		colNumber.addListener(SWT.Selection, SortListenerFactory.getListener(SortListenerFactory.INT_COMPARATOR));
		
		TableColumn colWord = new TableColumn(tableWords, SWT.NONE);
		colWord.setMoveable(true);
		colWord.setWidth(121);
		colWord.setText("Слово");
		colWord.addListener(SWT.Selection, SortListenerFactory.getListener(SortListenerFactory.STRING_COMPARATOR));
		
		TableColumn colRelated = new TableColumn(tableWords, SWT.NONE);
		colRelated.setMoveable(true);
		colRelated.setWidth(96);
		colRelated.setText("Связанные");
		
		TableColumn colCount = new TableColumn(tableWords, SWT.NONE);
		colCount.setMoveable(true);
		colCount.setWidth(40);
		colCount.setText("Количество");
		colCount.addListener(SWT.Selection, SortListenerFactory.getListener(SortListenerFactory.INT_COMPARATOR));
		
		TableItem tableItem_2 = new TableItem(tableWords, SWT.NONE);
		tableItem_2.setText("Слово");
		
		TableItem tableItem_3 = new TableItem(tableWords, SWT.NONE);
		tableItem_3.setText("слово-2");
		
		Menu menuTableWords = new Menu(tableWords);
		tableWords.setMenu(menuTableWords);
		
		MenuItem menuItem = new MenuItem(menuTableWords, SWT.NONE);
		menuItem.setText("Добавить в словарь...");
		menuItem.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event event) {
		        //System.out.println(tableWords.getSelectionIndex());
		        final String word = tableWords.getItem(tableWords.getSelectionIndex()).getText(1);
		        //System.out.println(word);
		        new AddWord(display, word);
		      }
		    });
		
		Group groupThematicDicts = new Group(sashFormSouth, SWT.NONE);
		groupThematicDicts.setText("\u0421\u043B\u043E\u0432\u0430\u0440\u0438");
		groupThematicDicts.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		tableThematicDicts = new Table(groupThematicDicts, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION);
		tableThematicDicts.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				view.msgTurnDicts();
			}
		});
		tableThematicDicts.setLinesVisible(true);
		tableThematicDicts.setHeaderVisible(true);
		
		TableColumn colDicName = new TableColumn(tableThematicDicts, SWT.NONE);
		colDicName.addListener(SWT.Selection, SortListenerFactory.getListener(SortListenerFactory.STRING_COMPARATOR));
		colDicName.setWidth(131);
		colDicName.setText("Словарь");
		colDicName.setMoveable(true);
		
		TableColumn colProbability = new TableColumn(tableThematicDicts, SWT.NONE);
		colProbability.addListener(SWT.Selection, SortListenerFactory.getListener(SortListenerFactory.DOUBLE_COMPARATOR));
		colProbability.setWidth(99);
		colProbability.setText("Совпадение");
		colProbability.setMoveable(true);
		
		TableItem tableItem_1 = new TableItem(tableThematicDicts, 0);
		tableItem_1.setText(new String[] {"физика", "сов-падение"});
		
		TableItem tableItem = new TableItem(tableThematicDicts, 0);
		tableItem.setText("New TableItem");
		sashFormSouth.setWeights(new int[] {1, 1});
		sashForm.setWeights(new int[] {202, 202});
		FormData fd_btnReferate = new FormData();
		fd_btnReferate.left = new FormAttachment(100, -136);
		fd_btnReferate.top = new FormAttachment(100, -26);
		fd_btnReferate.bottom = new FormAttachment(100);
		fd_btnReferate.right = new FormAttachment(100);
		btnReferate.setLayoutData(fd_btnReferate);
		
		btnReferate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				view.msgReferate();
			}
		});
		btnReferate.setText("Реферирование");
		
		Button btnRubricate = new Button(composite, SWT.NONE);
		btnRubricate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				view.msgRubricate();
			}
		});
		FormData fd_btnRubricate = new FormData();
		fd_btnRubricate.left = new FormAttachment(btnReferate, -118, SWT.LEFT);
		fd_btnRubricate.bottom = new FormAttachment(100);
		fd_btnRubricate.right = new FormAttachment(btnReferate, -6);
		btnRubricate.setLayoutData(fd_btnRubricate);
		btnRubricate.setText("Рубрикация");
	}
}
