package engine.thematicdictionary;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import util.HibernateUtil;
import entities.Rubric;
import entities.Word;
/**
 * 
 */
public final class ThematicDicManager {
	
	private static ThematicDicManager instance;
	
	private List<Rubric> thematicDicts;
	private Session session;
	private Criteria crit;

	public static ThematicDicManager getInstance(){
		if( instance==null ){
		      instance = new ThematicDicManager();
		}
		return instance;
	}
	
	
	/**
	 * Конструктор
	 */
	private ThematicDicManager(){
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
    	crit = session.createCriteria(Rubric.class); // создаем критерий запроса
    	updateDictsArrayList();
	}
	
	public List<Rubric> getAllDicts() {
		return thematicDicts;
	}

	public void addDic(Rubric thematicDic) {
		session.save(thematicDic);
		updateDictsArrayList();
	}

	public Rubric getDic(int i) {
		return thematicDicts.get(i);
	}

	public void deleteDic(int dicIndex) {
		session.delete(thematicDicts.get(dicIndex));
		updateDictsArrayList();
	}
	
	
	/**
	 * Создаёт arrayList из полученных из БД объектов Rubric
	 * @return
	 */
	private void updateDictsArrayList() {
		session.flush();
		session.clear();

		thematicDicts = crit.list();
		
	}

	
	
	
	
	
	

	public void addDic(String dicname, boolean isEnabled){
		Rubric r = new Rubric(dicname, isEnabled);
		addDic(r);
	}
		
	public void turn(boolean b, int index) {
		this.getDic(index).setDicEnabled(b);
	}

	public void addWord(int dicIndex, String word, double probability){
		//this.getDic(dicIndex).addWord(word, probability);
	}
	
	public void deleteWord(String word, int dicIndex) {
		//this.getDic(dicIndex).deleteWord(word);
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
