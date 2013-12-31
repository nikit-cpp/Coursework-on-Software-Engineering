package gui;
import gui.listeners.Open;
import gui.listeners.SortListenerFactory;

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
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Group;

public class MainWindow {
	private Display display;
	Shell shell;
	Text txtInput;
	Table tableWords;
	MainWindowManager view;
	Table tableThematicDicts;

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
	void open() {
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
		shell.setText("Рубрикатор");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		shell.addListener(SWT.Close, new Listener() {
		      public void handleEvent(Event event) {
		        view.delFromUpdateable();
		      }
		});
		
		MenuItem menuFileCascade = new MenuItem(menu, SWT.CASCADE);
		menuFileCascade.setText("Файл");
		
		Menu menuFile = new Menu(menuFileCascade);
		menuFileCascade.setMenu(menuFile);
		
		MenuItem itemOpen = new MenuItem(menuFile, SWT.NONE);
		itemOpen.addSelectionListener(new Open());
		itemOpen.setText("Открыть");
		
		MenuItem itemSaveInput = new MenuItem(menuFile, SWT.NONE);
		itemSaveInput.setEnabled(false);
		itemSaveInput.setText("Сохранить входной");
		
		MenuItem itemSaveOutput = new MenuItem(menuFile, SWT.NONE);
		itemSaveOutput.setEnabled(false);
		itemSaveOutput.setText("Сохранить результат");
		
		MenuItem menuWinCascade = new MenuItem(menu, SWT.CASCADE);
		menuWinCascade.setText("Окна");
		
		Menu menuWin = new Menu(menuWinCascade);
		menuWinCascade.setMenu(menuWin);
		
		MenuItem menuItemThematicDicts = new MenuItem(menuWin, SWT.NONE);
		menuItemThematicDicts.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new ThematicDictsWindow(display);
			}
		});
		menuItemThematicDicts.setText("Тематические словари");
		
		MenuItem menuItemAddingWord = new MenuItem(menuWin, SWT.NONE);
		menuItemAddingWord.setText("Добавление слов");
		menuItemAddingWord.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new AddWordWindow(display, null);
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
		sashForm.setLayoutData(fd_sashForm);
		
		Group groupInText = new Group(sashForm, SWT.NONE);
		groupInText.setText("Входной текст");
		groupInText.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		txtInput = new Text(groupInText, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtInput.setText("Введите сюда текст,\r\nлибо откройте файл в кодировке cp1251");
		
		SashForm sashFormSouth = new SashForm(sashForm, SWT.NONE);
		
		Group groupFoundedWords = new Group(sashFormSouth, SWT.NONE);
		groupFoundedWords.setText("Найденные слова");
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
		
		/*
		TableItem tableItem_2 = new TableItem(tableWords, SWT.NONE);
		tableItem_2.setText("Слово");
		
		TableItem tableItem_3 = new TableItem(tableWords, SWT.NONE);
		tableItem_3.setText("слово-2");
		*/
		
		Menu menuTableWords = new Menu(tableWords);
		tableWords.setMenu(menuTableWords);
		
		MenuItem menuItem = new MenuItem(menuTableWords, SWT.NONE);
		menuItem.setText("Добавить в словарь...");
		menuItem.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event event) {
		    	try{
			        //System.out.println(tableWords.getSelectionIndex());
			        final String word = tableWords.getItem(tableWords.getSelectionIndex()).getText(1);
			        //System.out.println(word);
			        new AddWordWindow(display, word);
		    	}catch(ArrayIndexOutOfBoundsException e){
		    		
		    	}
		      }
		    });
		
		Group groupThematicDicts = new Group(sashFormSouth, SWT.NONE);
		groupThematicDicts.setText("Тематические словари");
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
		
		/*
		TableItem tableItem_1 = new TableItem(tableThematicDicts, 0);
		tableItem_1.setText(new String[] {"физика", "сов-падение"});
		
		TableItem tableItem = new TableItem(tableThematicDicts, 0);
		tableItem.setText("электроника");
		*/
		
		sashFormSouth.setWeights(new int[] {1, 1});
		sashForm.setWeights(new int[] {202, 202});
		
		Button btnRubricate = new Button(composite, SWT.NONE);
		fd_sashForm.bottom = new FormAttachment(100, -32);
		btnRubricate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {
				view.msgRubricate();
			}
		});
		FormData fd_btnRubricate = new FormData();
		fd_btnRubricate.right = new FormAttachment(sashForm, 0, SWT.RIGHT);
		fd_btnRubricate.left = new FormAttachment(100, -122);
		fd_btnRubricate.top = new FormAttachment(sashForm, 6);
		btnRubricate.setLayoutData(fd_btnRubricate);
		btnRubricate.setText("Рубрикация");
	}
}
