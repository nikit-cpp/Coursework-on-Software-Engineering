package engine.thematicdictionary;

import java.io.File;
import java.util.ArrayList;

/**
 * Расширение RubricDAO для повышения юзабилити
 */
public final class ThematicDicManager /*extends RubricDAO*/{
	private ArrayList<ThematicDic> thematicDicts;

	public ThematicDicManager(){
	}
	
	public ArrayList<ThematicDic> getAllDicts() {
		return thematicDicts;
	}

	public void addDic(ThematicDic thematicDic) {
		// TODO Auto-generated method stub
		// session.save(new Rubric(thematicDic.getName()))
		
		updateDictsArrayList();
	}

	public ThematicDic getDic(int i) {
		// TODO Auto-generated method stub
		// TODO учесть то что в БД индекс начинается с 1, а в ArrayList с 0.
		return null;
	}

	public void deleteDic(int dicIndex) {
		// TODO Auto-generated method stub
		// TODO учесть то что в БД индекс начинается с 1, а в ArrayList с 0.
		updateDictsArrayList();
	}
	
	
	/**
	 * Создаёт arrayList из полученных из БД объектов Rubric
	 * @return
	 */
	private void updateDictsArrayList() {
		// TODO Auto-generated method stub
		//thematicDicts = new ArrayList<ThematicDic>();
		//session.createCriteria();
	}

	
	
	
	
	
	

	public void addDic(String dicname, boolean isEnabled){
		addDic(new ThematicDic(dicname, isEnabled));
	}
		
	public void turn(boolean b, int index) {
		this.getDic(index).setDicEnabled(b);
	}

	public void addWord(int dicIndex, String word, double probability){
		this.getDic(dicIndex).addWord(word, probability);
	}
	
	public void deleteWord(String word, int dicIndex) {
		this.getDic(dicIndex).deleteWord(word);
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
