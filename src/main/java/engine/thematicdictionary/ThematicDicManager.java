package engine.thematicdictionary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public final class ThematicDicManager {
	static final long serialVersionUID = 1L;
	private ArrayList<ThematicDic> thematicDicts;
	
	public ThematicDicManager(/*tring path*/){
		thematicDicts = new ArrayList<ThematicDic>();
		// TODO save-dic импортировать в ArrayList словари из path
		load();
	}

	public void turn(boolean b, int index) {
		thematicDicts.get(index).setEnabled(b);
		save();
	}
	
	public ArrayList<ThematicDic> getThematicDicts() {
		return thematicDicts;
	}
	
	public void add(String dicname, boolean isEnabled){
		thematicDicts.add(new ThematicDic(dicname, isEnabled));
		save();
	}

	public void add(ThematicDic dic) {
		thematicDicts.add(dic);
		save();
	}

	public ThematicDic get(int i) {
		return thematicDicts.get(i);
	}
	
	public void addWord(int dicIndex, String word, double probability){
		thematicDicts.get(dicIndex).add(word, probability);
		save();
	}

	final String filename = "dicts.out";
	
	/**
	 * Удаление файла
	 */
	public void remove(){
		File file = new File(filename);
		if(file.exists())
			file.delete();
	}
	
	/**
	 * Очистка ArrayList
	 */
	public void clear(){
		thematicDicts.clear();
	}

	public void save(){
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(new FileOutputStream(filename));
			out.writeObject(thematicDicts);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void load(){
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(new FileInputStream(filename));
			thematicDicts = (ArrayList<ThematicDic>) in.readObject();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void deleteWord(String word, int dicIndex) {
		thematicDicts.get(dicIndex).delete(word);
		save();
	}

	public void deleteDic(int dicIndex) {
		// TODO выяснить, удаляются ли слова при удалении содержащего их словаря
		thematicDicts.remove(dicIndex);
		save();
	}
}
