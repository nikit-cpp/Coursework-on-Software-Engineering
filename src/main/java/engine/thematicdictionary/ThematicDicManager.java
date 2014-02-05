package engine.thematicdictionary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import engine.thematicdictionary.hibernate.DAO.RubricDAO;

/**
 * Расширение RubricDAO для повышения юзабилити
 */
public final class ThematicDicManager extends RubricDAO{
	
	public ThematicDicManager(){
	}

	public void addDic(String dicname, boolean isEnabled){
		addDic(new ThematicDic(dicname, isEnabled));
	}
		
	public void turn(boolean b, int index) {
		this.getDic(index).setEnabled(b);
	}

	public void addWord(int dicIndex, String word, double probability){
		this.getDic(dicIndex).add(word, probability);
	}
	
	public void deleteWord(String word, int dicIndex) {
		this.getDic(dicIndex).delete(word);
	}
	
	/**
	 * Удаление файла
	 */
	public void remove(){
		File file = new File("dicts.out");
		if(file.exists())
			file.delete();
	}
	
	/**
	 * Очистка ArrayList
	 */
	public void clear(){
		
	}
}
