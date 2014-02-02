package engine.thematicdictionary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import engine.hibernate.entities.Rubric;

/**
 * Фасад
 * @author Ник
 *
 */
public final class Rubricator {

	static final long serialVersionUID = 1L;
	private ArrayList<RubricView> rubrics;
	
	public Rubricator(){
		rubrics = new ArrayList<RubricView>();
		load();
	}


	public void turn(boolean b, int index) {
		rubrics.get(index).setEnabled(b);
		save();
	}
	
	public ArrayList<RubricView> getAllRubrics() {
		return rubrics;
	}
	
	public void addRubric(String dicname, boolean isEnabled){
		rubrics.add(new RubricView(dicname, isEnabled));
		save();
	}

	public void addRubric(RubricView dic) {
		rubrics.add(dic);
		save();
	}

	public RubricView get(int i) {
		return rubrics.get(i);
	}
	
	public void addWord(int dicIndex, String word, double probability){
		rubrics.get(dicIndex).add(word, probability);
		save();
	}
	
	public void deleteWord(String word, int dicIndex) {
		rubrics.get(dicIndex).delete(word);
		save();
	}

	public void deleteRubric(int dicIndex) {
		// TODO выяснить, удаляются ли слова при удалении содержащего их словаря
		rubrics.remove(dicIndex);
		save();
	}
	
	public double getProbability(Rubric rubric, String word){
		return 0;
		// TODO Auto-generated method stub
	}
	
	private void save() {
		// TODO Auto-generated method stub
	}
	
	private void load() {
		// TODO Auto-generated method stub
	}
}
