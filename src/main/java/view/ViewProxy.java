package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collection;

import main.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.widgets.*;

import runtime.dictionary.WordInfo;
import thematic.dictionary.ThematicDic;

public class ViewProxy{
	private Text txtInput;
	private Table tableWords;
	private Text txtOutput;
	private Table tableThematicDicts;
	
	private Engine engine = new Engine();
	
	public ViewProxy(MainWindow w) {
		this.txtInput=w.txtInput;
		this.tableWords=w.tableWords;
		this.txtOutput=w.txtOutput;
		this.tableThematicDicts = w.tableThematicDicts;
		
		initialize();
	}
	
	/**
	 *  Инициализация окна
	 */
	private void initialize(){
		
		txtOutput.setText("");
		
		initThematicDicTable(tableThematicDicts, engine.getThematicDicts());
	}
	
	protected void initThematicDicTable(Table table, ArrayList<ThematicDic> arrayList) {
	    tableThematicDicts.removeAll();
	    for (ThematicDic dic : arrayList) {
	        TableItem ti = new TableItem(table, SWT.NONE);
	        ti.setText(dic.toString());
	        ti.setChecked(dic.getEnabled());
	    }
	}
	
	private void initWordsTable(Table table, Collection<WordInfo> arrayList) {
		tableWords.removeAll();
		for (WordInfo item : arrayList) {
	        TableItem tableItem = new TableItem(table, SWT.NONE);
	        
	        String word = item.getString();
	        String related = item.getRelated();
	        String count = String.valueOf(item.getCount());
	        
	        String[] row = {word, related, count};
	        tableItem.setText(row);
	    }
		
	}
	
	public void msgRubricate() {				
		initWordsTable(tableWords, engine.rubricate(txtInput.getText()));
		
		// TODO Уже не помню зачем, убрать всё тело метода, что ниже данного комментария.
		for(TableItem i : tableThematicDicts.getItems()){
			logln(i+String.valueOf(i.getChecked()));
		}
	}
	
	public void msgReferate() {				
		String s = engine.referate(txtInput.getText());
		txtOutput.setText(s);
	}
	
	public void msgDicts(){
		int i=0;
		
		for(TableItem tableItem : tableThematicDicts.getItems()){
			//engine.getThematicDicts().get(i).setEnabled(tableItem.getChecked());
			engine.turnThematicDictionary(tableItem.getChecked(), i);
			i++;
		}
	}
	
	StringBuilder sb=new StringBuilder();
	private void logln(String s){
		sb.append(s+"\n");
		txtOutput.setText(sb.toString());
	}

	public void openFile(String selected) {
		StringBuilder sb = new StringBuilder();
		
		
		Charset charset = Charset.forName("cp1251"); // TODO получение как опцию
		// TODO Работа с BOM для UTF-8 : http://commons.apache.org/proper/commons-io/javadocs/api-release/index.html?org/apache/commons/io/package-summary.html
		System.out.println("sys def charset "+charset);
		java.nio.file.Path file = Paths.get(selected);
		try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
		    String line = null;
		    boolean firstline=true;
		    while ((line = reader.readLine()) != null) {
		        //System.out.println(line);
		    	if(!firstline)sb.append('\n');
		    	sb.append(line);
		    	firstline=false;
		    }
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
		/**/

		/*// 1. Чтение ввода по строкам:
		BufferedReader in;
		try {
			in = new BufferedReader(
				new FileReader(selected));
			String s;
			while((s = in.readLine())!= null)
			sb.append(s);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
		*/
		this.txtInput.setText(sb.toString());
	}
}