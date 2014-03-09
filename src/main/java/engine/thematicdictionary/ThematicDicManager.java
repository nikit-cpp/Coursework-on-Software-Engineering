package engine.thematicdictionary;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.hibernate.criterion.Example;
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
			Path p = (Paths.get(".")).toAbsolutePath().normalize().resolve("h2db");
			p.toFile().mkdirs();
			p = p.resolve("thematicdicts");
		    HibernateUtil.setDbFileName(p.toString());
		    
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
	public ThematicDicManager(int thisIsNoSingleton){}
		
	public void addDic(Rubric thematicDic) throws ThematicDicManagerException {
		try{
			begin();
			session.save(thematicDic);
			end();
		}catch(HibernateException e){
			cancel();
			throw new ThematicDicManagerException(e);
		}
    }

	Rubric getDic(int i) {
		return getAllDicts().get(i);
	}

	public void deleteDic(int dicIndex) throws ThematicDicManagerException {
		try{
			begin();
			Rubric r = getAllDicts().get(dicIndex);
			
			for(int i = r.getProbabilitys().size() - 1; i>=0; i--){
				probabilityAndWordDelete(r, i);
			}
			session.delete(r);
			end();
		}catch(HibernateException e){
			cancel();
			throw new ThematicDicManagerException(e);
		}catch(IndexOutOfBoundsException e){
			cancel();
			throw new ThematicDicManagerException(e);
		}
	}
	
	/**
	 * Переименовывает словарь
	 * @param dicIndex индекс словаря в List
	 * @param newName новое имя для словаря
	 * @throws Exception
	 */
	public void renameDic(int dicIndex, String newName) throws ThematicDicManagerException {
		try{
			begin();
			Rubric dic = getAllDicts().get(dicIndex);

			dic.setName(newName);
			end();
		}catch(HibernateException e){
			cancel();
			throw new ThematicDicManagerException(e);
		}catch(IndexOutOfBoundsException e){
			cancel();
			throw new ThematicDicManagerException(e);
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
		System.out.println("rollback...");
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
	
	
	
	
	
	

	public void addDic(String dicname, boolean isEnabled) throws ThematicDicManagerException{
		Rubric r = new Rubric(dicname, isEnabled);
		addDic(r);
	}
		
	public void turn(boolean b, int index) {
		this.getDic(index).setDicEnabled(b);
	}

	
	
	public void addWord(int dicIndex, String word, double probability) throws ThematicDicManagerException {
		try {
			begin();
			Word w = null;
			try {
				w = new Word(word);
				session.save(w); // Пытаемся сохранить w в БД
			} catch (ConstraintViolationException c) {
				// Если мы поймали искючение нарушения ограничений, значит(см.
				// Word.java), в БД уже сохранено такое слово
				try {
					session.evict(w); // Приказвываем сессии забыть об объекте w
				} catch (Exception e) {
				}
				// Далее, мы должны получить из БД вышеупомянутое слово
				// Запрашиваем список слов, у которых поле word совпадает с
				// таковым в объекте w
				List<Word> list = session.createCriteria(Word.class)
						.add(Example.create(w)).list();
				// учитывая ограничение уникальности Word.word, список состоит
				// из 1-го элемента, который является нужным нам словом
				w = list.get(0);
				System.out.println("Word for this index:" + w);
			}
			Probability p = new Probability(probability, getDic(dicIndex), w);
			w.getProbabilitys().add(p);
			//session.save(p); // не обязательно при каскадировании в Rubric.java
			getDic(dicIndex).getProbabilitys().add(p);
			end();
		}catch(HibernateException e){
			cancel();
			throw new ThematicDicManagerException(e);
		}catch(IndexOutOfBoundsException e){
			cancel();
			throw new ThematicDicManagerException(e);
		}
	}
	
	/**
	 * Удаляет вероятность - всегда, слово - только если не него больше нет ссылок из вероятностей
	 * @param wordIndex - индекс слова-вероятности в списке
	 * @param dicIndex - индекс словаря-рубрики в списке
	 * @throws ThematicDicManagerException
	 */
	public void deleteWord(int wordIndex, int dicIndex) throws ThematicDicManagerException {
		try {
			begin();
			
			/*Probability p = (Probability) getDic(dicIndex).getProbabilitys().get(wordIndex);
			Word w = p.getWord();
			w.getProbabilitys().remove(p);
			if(w.getProbabilitys().size()==0)
				session.delete(w);
			
			getDic(dicIndex).getProbabilitys().remove(p);*/
			probabilityAndWordDelete(getDic(dicIndex), wordIndex);
			end();
			
		}catch(HibernateException e){
			cancel();
			throw new ThematicDicManagerException(e);
		}catch(IndexOutOfBoundsException e){
			cancel();
			throw new ThematicDicManagerException(e);
		}
	}
	
	/**
	 * удаление всех вероятностей, и слов, которые остались без вероятностей
	 * @param r
	 * @param wordIndex
	 */
	private void probabilityAndWordDelete(Rubric r, int wordIndex){
		Probability p = (Probability) r.getProbabilitys().get(wordIndex);
		Word w = p.getWord();
		w.getProbabilitys().remove(p);
		if(w.getProbabilitys().size()==0)
			session.delete(w);
		
		r.getProbabilitys().remove(p);
	}
	
	
	public List<Probability> getAllProbabilitys(){
		Criteria pcrit = session.createCriteria(Probability.class);
		return pcrit.list();
	}
	
	public List<Word> getAllWords(){
		Criteria wcrit = session.createCriteria(Word.class);
		return wcrit.list();
	}
	
	
	/**
	 * Удаление файла БД
	 * @throws Exception 
	 */
	public void remove() throws Exception{
		String home = "";
		String path = HibernateUtil.getDbFileName();
		final String ext = ".h2.db";
		if(HibernateUtil.getDbFileName().startsWith("~")){
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
	public void clearDbSQL() {
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
