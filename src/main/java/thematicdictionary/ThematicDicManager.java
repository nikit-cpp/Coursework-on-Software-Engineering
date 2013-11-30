package thematicdictionary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ThematicDicManager {
	static final long serialVersionUID = 1L;
	private ArrayList<ThematicDic> thematicDicts;
	
	public ThematicDicManager(/*tring path*/){
		thematicDicts = new ArrayList<ThematicDic>();
		
		/* TODO заглушечное заполнение
		thematicDicts.add(new ThematicDic("физика", true));
		thematicDicts.add(new ThematicDic("алгебра", false));
		thematicDicts.add(new ThematicDic("геометрия", true));
		
		ThematicDic informatica = new ThematicDic("информатика", true);
		informatica.add("риск", 1);
		informatica.add("XP", 1);
		thematicDicts.add(informatica);
		*/
		// TODO save-dic импортировать в ArrayList словари из path
		load();
	}

	public void turn(boolean b, int index) {
		thematicDicts.get(index).setEnabled(b);
	}
	
	public ArrayList<ThematicDic> getThematicDicts() {
		return thematicDicts;
	}
	
	public void add(String dicname, boolean isEnabled){
		thematicDicts.add(new ThematicDic(dicname, isEnabled));
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
	
	public void remove(){
		File del = new File(filename);
		if(del.exists())
			del.delete();
	}
	
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
	}
}
