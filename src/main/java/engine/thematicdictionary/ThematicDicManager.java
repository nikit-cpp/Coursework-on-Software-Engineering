package engine.thematicdictionary;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.persister.entity.AbstractEntityPersister;
import util.HibernateUtil;
import entities.Rubric;
/**
 * 
 */
public final class ThematicDicManager {
	
	private static ThematicDicManager instance;
	
	private List<Rubric> thematicDicts = new ArrayList<Rubric>();
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
		// Каскадные удаления для -- применять(дописать в класс Rubric), когда в Рубриках появятся Вероятности
		// http://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html_single/#objectstate-transitive
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

		//thematicDicts = crit.list();
		thematicDicts.clear();
		thematicDicts.addAll(crit.list());
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
	 * Удаление файла БД
	 * @throws Exception 
	 */
	public void remove() throws Exception{
		String home = "";
		String path = HibernateUtil.dbFileName;
		final String ext = ".h2.db";
		if(HibernateUtil.dbFileName.startsWith("~")){
			home = System.getProperty("user.home");
			System.out.println("user.home: "+ home);
			path = path.substring(1);
		}
		File file = new File(home+path+ext);
		boolean isException = false;
		if(file.exists()){
			System.out.println("сейчас будет удалён файл базы данных: "+ file.getAbsolutePath());
			boolean success = file.delete();
			System.out.println("Файл удалён: " + success);
			isException = !success;
		}else{
			System.out.println("не удалось найти файл базы данных: "+ file.getAbsolutePath());
			isException = true;
		}
		if(isException){
			throw new Exception("Проблемы при удалении файла БД.");
		}
	}
	
	/**
	 * Очистка БД -- Удаляет содержимое всех таблиц
	 */
	public void clearDb() {
		System.out.println("Очистка БД...");

		Map m = HibernateUtil.getSessionFactory().getAllClassMetadata();

		// iterate map :
		// http://stackoverflow.com/questions/46898/how-do-i-iterate-over-each-entry-in-a-map/46905#46905
		Iterator entries = m.entrySet().iterator();
		while (entries.hasNext()) {
			Entry thisEntry = (Entry) entries.next();
			Object key = thisEntry.getKey();
			Object value = thisEntry.getValue();
			System.out.println("\tСохранённый класс " + key + "; " + value);

			// Get all table names set up in SessionFactory
			// http://stackoverflow.com/questions/4813122/get-all-table-names-set-up-in-sessionfactory
			AbstractEntityPersister aep = (AbstractEntityPersister) m.get(key);
			String tableName = aep.getTableName();
			System.out.println("\tДля класса: " + key + " Имя таблицы: "
					+ tableName);
			System.out.println("\tУдаляю таблицу " + tableName);
			SQLQuery q = session.createSQLQuery("DELETE " + tableName + ";");
			q.executeUpdate();
		}

		updateDictsArrayList();
	}
}
