package engine.thematicdictionary;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;

import util.HibernateUtil;
import engine.foundedwords.WordInfo;
import entities.Probability;
import entities.Rubric;
import entities.Word;
/**
 * 
 */
public final class ThematicDicManager extends ThematicDicList {
	
	private static ThematicDicManager instance;
	
	private Session session;
	private SessionFactory sessions;
	private Transaction tx;

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
		//session = sessions.openSession();
		open();
		
		begin();
    	end();
	}
	
	/**
	 * Use in tests  only
	 */
	public ThematicDicManager(int thisIsNoSingleton){
	}
		
	public void addDic(Rubric thematicDic) throws Exception {
		try{
			begin();
			session.save(thematicDic);
			end();
		}catch(HibernateException e){
			cancel();
			throw new Exception(e.getMessage());
		}
    }

	Rubric getDic(int i) {
		return getAllDicts().get(i);
	}

	public void deleteDic(int dicIndex) throws Exception {
		// Каскадные удаления для Вероятностей -- применять(дописать в класс Rubric), когда в Рубриках появятся Вероятности
		// http://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html_single/#objectstate-transitive
		try{
			begin();
			session.delete(getAllDicts().get(dicIndex));
			end();
		}catch(HibernateException e){
			cancel();
			throw new Exception(e.getMessage());
		}catch(IndexOutOfBoundsException e){
			cancel();
			throw new Exception(e.getMessage());
		}

	}
	
	/**
	 * Переименовывает словарь
	 * @param dicIndex индекс словаря в List
	 * @param newName новое имя для словаря
	 * @throws Exception
	 */
	public void renameDic(int dicIndex, String newName) throws Exception {
		try{
			begin();
			Rubric dic = getAllDicts().get(dicIndex);

			dic.setName(newName);
			end();
		}catch(HibernateException e){
			cancel();
			throw new Exception(e.getMessage());
		}catch(IndexOutOfBoundsException e){
			cancel();
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * Стартует транзакцию
	 */
	private void begin(){
		tx = session.beginTransaction();
	}
	
	/**
	 * Обновляет список рубрик и закрывает транзакцию.
	 */
	private void end() {
		//thematicDicts.clear();
		crit = session.createCriteria(Rubric.class); // создаем критерий //
														// запроса
		//thematicDicts.addAll(crit.list());
		refresh();
		tx.commit();

		session.flush();
	}	

	/**
	 * Отмена транзакции и очистка сессии, вызывается перед rethrow
	 * необрабатываемого (насл. java.lang.RunTimeException ->
	 * ConstraintViolationException) в обрабатываемое (насл.
	 * java.lang.Exception). Очищает сессию и помечает лист недействительным(т.
	 * к. сессия очищена - выборка по критерию теряет с ней связь), для его
	 * последущего обновления при getAllDicts()
	 */
	private void cancel(/*Object object*/) {
		tx.rollback();
		session.clear(); // очищаем сессию, если этого не сделать, будет ошибка
							// во время flush() не нарушающей uniqueConstraint
							// рубрики, т. к. в сессии остался неправильный
							// объект(нарушающий) с Id = null
		//if(object!=null)
		//session.evict(object); // удаляем заблудшую душу из сессии
		markListNotTracked(); // помечаем что лист неперь не отслеживается по
								// причине очистки сессии, для того, чтобы при
								// вызове getAllDicts() он был обновлён в
								// соответствии с последним критерием
	}	
	
	
	
	
	
	

	public void addDic(String dicname, boolean isEnabled) throws Exception{
		Rubric r = new Rubric(dicname, isEnabled);
		addDic(r);
	}
		
	public void turn(boolean b, int index) {
		this.getDic(index).setDicEnabled(b);
	}

	
	
	public void addWord(int dicIndex, String word, double probability) {
		// getDic(dicIndex).addWord(word, probability);
		begin();
		Word w = null;
		try {
			w = new Word(word);
			session.save(w);
		} catch (ConstraintViolationException c) {
			try{
				session.evict(w);
			}catch(Exception e){ }
			List<Word> list = session.createCriteria(Word.class).list();
			long index = 0;
			for(Word e : list){
				index = e.getWordId();
				if(e.getWord().equals(word))
					break;
			}
			System.out.println("index = " + index);
			w = (Word) session.get(Word.class, index);
			System.out.println("Word for this index:" + w);
		}
		Probability p = new Probability(probability, w);
		session.save(p);
		getDic(dicIndex).getProbabilitys().add(p);
		end();
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

		Map<String, ClassMetadata> m = HibernateUtil.getSessionFactory().getAllClassMetadata();

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
			System.out.println("\tУдаляю содержимое таблицы " + tableName);
			SQLQuery q = session.createSQLQuery("DELETE " + tableName + ";");
			q.executeUpdate();
		}

		begin();
		end();
	}
	
	
	/**
	 * Вычисляет вероятность того, что данный текст относится к рубрике, представленной словарём.
	 * @param dic - Тематический словарь, представляющий данную рубрику
	 * @param stems - стемы(начальные формы)
	 * @return вероятность
	 */
	public void calcProbabilityforDic(Rubric dic, Collection<WordInfo> stems) {
		double p = 0;
		int size = 0;
		
		for(WordInfo word : stems){
			String s = word.getString();
			int count = word.getCount();
			p += (getProbability4Word(dic, s) * count);
			
			size+=count;
		}
		p /= size;
		
		dic.setCalculatedProbability(p);
	}
	
	private double getProbability4Word(Rubric r, String s){
		return 0.0;
	}
	
	public static void checkProbabilityBounds(double probability){
		if (probability>1.0 || probability<0.0) throw new IllegalArgumentException("Вероятность может быть только 0.0...1.0");
	}

	/**
	 * Открывает фабрику сессий и сессию
	 */
	private void open(){
		sessions = HibernateUtil.getSessionFactory();
		session = sessions.openSession();
	}
	
	/**
	 * Закрытие фабрики сессий, влекущее за собой закрытие соединения с БД.
	 */
	public void terminate() {
		session.close();
		sessions.close();
	}


	/**
	 * Use in tests  only
	 */
	public void setSessions(SessionFactory sessions) {
		this.sessions=sessions;
	}

	/**
	 * Use in tests  only
	 */
	public void setSession(Session session) {
		this.session = session;
	}
}

/**
 * Класс, обслуживающий список рубрик
 * @author Ник
 *
 */
class ThematicDicList {
	private boolean isTracked = true;
	private List<Rubric> thematicDicts = new ArrayList<Rubric>();
	Criteria crit;
	
	void markListNotTracked() {
		isTracked = false;
	}
	
	public List<Rubric> getAllDicts() {
		if(!isTracked){
			refresh();
			isTracked = true;
		}
		return thematicDicts;
	}	
	
	/**
	 * Перед вызовом метода в наследующем классе д. б. проинициализирован критерий
	 */
	void refresh(){
		thematicDicts.clear();
		thematicDicts.addAll(crit.list());
	}

}
