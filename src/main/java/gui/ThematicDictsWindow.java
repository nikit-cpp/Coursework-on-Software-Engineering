// http://www.chrisnewland.com/swt-best-practice-single-display-multiple-shells-111

package gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class ThematicDictsWindow {
	private Display display;
	Shell shell;
	Table tableDicts;
	ThematicDictsWindowManager view;
	Table tableWords;
	private TableColumn tableColumn;
	private TableColumn tableColumn_1;
	private TableColumn tableColumn_2;
	
	ThematicDictsWindow(Display display){
		this.display=display;
		open();
	}

	/**
	 * Open the window.
	 */
	void open() {
		createContents();
		
		view = new ThematicDictsWindowManager(this);
		
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
		shell.addListener(SWT.Close, new Listener() {
		      public void handleEvent(Event event) {
		        view.delFromUpdateable();
		        shell.dispose();
		      }
		});
		
		SashForm sashForm = new SashForm(shell, SWT.NONE);
		
		tableDicts = new Table(sashForm, SWT.BORDER | SWT.FULL_SELECTION);
		tableDicts.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent e) {
		    	  //System.out.println(tableDicts.getSelectionIndex());
		    	  //view.createContainsWordsTable(tableDicts.getSelectionIndex());
		    	  view.updateContainingWordsImpl();
		      }
		});
		tableDicts.setHeaderVisible(true);
		tableDicts.setLinesVisible(true);
		
		tableColumn = new TableColumn(tableDicts, SWT.NONE);
		tableColumn.setWidth(100);
		tableColumn.setText("Словари");
		
		Menu menuDicts = new Menu(tableDicts);
		tableDicts.setMenu(menuDicts);
		
		MenuItem mntmAddDic = new MenuItem(menuDicts, SWT.NONE);
		mntmAddDic.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				new AddDicWindow(display);
			}
		});
		mntmAddDic.setText("Добавить словарь");
		
		MenuItem mntmDelDic = new MenuItem(menuDicts, SWT.NONE);
		mntmDelDic.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				try{
					final int dicIndex = tableDicts.getSelectionIndex();
			    	  
					view.deleteDic(dicIndex);
					ViewSuper.updateThematicDictsTable();
				}catch(ArrayIndexOutOfBoundsException e){
				}catch(Exception e){
		    		  e.printStackTrace();
				}
			}
		});
		mntmDelDic.setText("Удалить словарь");
		
		final TableEditor editor = new TableEditor(tableDicts);
	    editor.horizontalAlignment = SWT.LEFT;
	    editor.grabHorizontal = true;

		MenuItem mntmRenameDic = new MenuItem(menuDicts, SWT.NONE);
		mntmRenameDic.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				try{
				final int dicIndex = tableDicts.getSelectionIndex();
				int index = dicIndex;
				boolean visible = false;
				final TableItem item = tableDicts.getItem(index);
				int i = 0; // Столбец
				final int column = i;

				final Text text = new Text(tableDicts, SWT.NONE);
				Listener textListener = new Listener() {
					public void handleEvent(final Event e) {
						switch (e.type) {
						case SWT.FocusOut:
							item.setText(column, text.getText());
							view.renameDic(dicIndex, text.getText());
							text.dispose();
							break;
						case SWT.Traverse:
							switch (e.detail) {
							case SWT.TRAVERSE_RETURN:
								item.setText(column, text.getText());
								view.renameDic(dicIndex, text.getText());
								// FALL THROUGH
							case SWT.TRAVERSE_ESCAPE:
								text.dispose();
								e.doit = false;
							}
							break;
						}
					}
				};
				text.addListener(SWT.FocusOut, textListener);
				text.addListener(SWT.Traverse, textListener);
				editor.setEditor(text, item, i);
				text.setText(item.getText(i));
				text.selectAll();
				text.setFocus();
				text.setVisible(true);
				
				return;
				}catch(ArrayIndexOutOfBoundsException e){
				}catch(Exception e){
		    		  e.printStackTrace();
				}
			}
		});
		mntmRenameDic.setText("Переименовать словарь");
		
		tableWords = new Table(sashForm, SWT.BORDER | SWT.FULL_SELECTION);
		tableWords.setHeaderVisible(true);
		tableWords.setLinesVisible(true);
		
		tableColumn_1 = new TableColumn(tableWords, SWT.NONE);
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("Слово");
		
		tableColumn_2 = new TableColumn(tableWords, SWT.NONE);
		tableColumn_2.setWidth(100);
		tableColumn_2.setText("Вероятность");
		
		Menu menuWord = new Menu(tableWords);
		tableWords.setMenu(menuWord);
		
		MenuItem menuItemAddWord = new MenuItem(menuWord, SWT.NONE);
		menuItemAddWord.setText("Добавить");
		menuItemAddWord.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event event) {
		    	  new AddWordWindow(display, null); // TODO посылка аргумента - выбранного словаря
		      }
		});
		
		MenuItem menuItemDeleteWord = new MenuItem(menuWord, SWT.NONE);
		menuItemDeleteWord.setText("Удалить");
		menuItemDeleteWord.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event event) {
		    	  try{
			    	  final String word = tableWords.getItem(tableWords.getSelectionIndex()).getText(0);
			    	  final int dicIndex = tableDicts.getSelectionIndex();
			    	  
			    	  view.deleteWord(word, dicIndex);
			    	  //view.updateContainingWords();
			    	  ViewSuper.updateContainingWords();
		    	  }catch(ArrayIndexOutOfBoundsException e){
		    	  }catch(Exception e){
		    		  e.printStackTrace();
		    	  }
		      }
		    });
		
		sashForm.setWeights(new int[] {1, 1});
	}
}
