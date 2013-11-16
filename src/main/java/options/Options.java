package options;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import sqliteDB.SqliteDB;
import main.NonCriticalException;

/**
 * Хранит настройки буфера и парсера, предоставляет к ним доступ: получение
 * значения get*(); установка нового значения set*(); сброс значения to default
 * reset*()
 * */
@SuppressWarnings("rawtypes")
public final class Options {
	private static Options instance; // Singleton
	private SqliteDB db;
 
    public static Options getInstance()
    {
        if (instance == null) {
            instance = new Options();
        }
        return instance;
    }
    	
	// Конструктор
	@SuppressWarnings("unchecked")
	private Options() {
		try {
			db = new SqliteDB("options.sqlite");
			
			db.executeUpdate("CREATE TABLE IF NOT EXISTS STRINGS "
				+ "("
				+ "OPTID TEXT PRIMARY KEY NOT NULL,"
				+ "VALUE TEXT NOT NULL,"
				+ "DEFAULTVALUE TEXT NOT NULL"
				+");"
			);
			
			addDeafults();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}		
	}

	private void addDeafults() {
		this.add(OptId.DIC_PATH, "resources/ru_RU.dic");	
		this.add(OptId.AFF_PATH, "resources/ru_RU.aff");
		this.add(OptId.CHARSET, "cp1251");
		this.add(OptId.RUBRICATE_ON_FILEOPEN, true);
	}

	/**
	 *  Добавление опций
	 * @param id Ид опции
	 * @param o значение по умолчанию
	 */
	private void add(OptId id, Object o) {
		if(o.getClass()==Boolean.class)
			putBoolean(id, (Boolean)o);
		if(o.getClass()==Integer.class)
			putInteger(id, (Integer)o);
		if(o.getClass()==String.class)
			putString(id, (String)o);
	}

	private void putString(OptId id, String o) {
		try {
			db.executeUpdate("INSERT INTO STRINGS (OPTID, VALUE)"+
					"VALUES(" +id.toString()+ ", "+o+");");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void putInteger(OptId id, Integer o) {
		// TODO Auto-generated method stub
		
	}

	private void putBoolean(OptId id, Boolean o) {
		
	}
	
	
	public String getString(OptId id){
		try {
			ResultSet rs = db.executeQuery(
					"SELECT VALUE FROM STRINGS WHERE OPTID="+id.toString()+";");
			return rs.getString("OPTID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	// Перезапись значений. Object должен совпадать с типом <T>@Option
	public void set(OptId id, Object o) throws Exception {
		Object defaultObj = optsVals.get(id);
		if(defaultObj==null){
			throw new Exception("Нет значения по умолчанию для "+id);
		}
		if (o.getClass() != defaultObj.getClass()) { // проверка типа
			throw new NonCriticalException("Неверный класс " + o.getClass()
					+ ", требуется " + optsVals.get(id).getClass());
		}
		optsVals.put(id, o);
	}


	// Сброс
	public void reset(OptId id) {
		Option getted4getDefault = opts.get(id);
		optsVals.put(id, getted4getDefault.defaultValue);
		System.out.println("Сброшена опция " + id.toString() + " в "
				+ getted4getDefault.defaultValue);
	}

	public void resetAll() {
		Iterator<Entry<OptId, Object>> it = optsVals.entrySet().iterator();
		while (it.hasNext()) {
			Entry<OptId, Object> li = it.next();
			// System.out.println(""+li.getKey() + " " + li.getValue());
			reset(li.getKey());
		}
	}

	// Вывод Ид : Значение
	public void printAll() {
		Iterator<Entry<OptId, Object>> it = optsVals.entrySet().iterator();
		while (it.hasNext()) {
			Entry<OptId, Object> li = it.next();
			System.out.println("" + li.getKey() + " " + li.getValue());
		}
	}


	// Получение значения
	public int getInt(OptId id) {
		return (Integer) optsVals.get(id);
	}

	public boolean getBoolean(OptId id) {
		return (Boolean) optsVals.get(id);
	}

	public String getString(OptId id) {
		return (String) optsVals.get(id);
	}*/
}
