package options;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import main.NonCriticalException;

/**
 * Класс для хранения значения по умолчанию
 * */
class Option<T> {
	T defaultValue;
	// TODO допустимые значения(тоже T) и их проверку
	Option(T defaultValue) {
		this.defaultValue = defaultValue;
	}
}

/**
 * Хранит настройки буфера и парсера, предоставляет к ним доступ: получение
 * значения get*(); установка нового значения set*(); сброс значения to default
 * reset*()
 * */

@SuppressWarnings("rawtypes")
public final class Options {
	private static Options instance; // Singleton
 
    public static Options getInstance()
    {
        if (instance == null)
        {
            instance = new Options();
        }
        return instance;
    }
	
	
	// Ид : Опция
	private HashMap<OptId, Option> opts = new HashMap<OptId, Option>();
	
	// Ид : Значение
	private HashMap<OptId, Object> optsVals = new HashMap<OptId, Object>();

	// Конструктор
	@SuppressWarnings("unchecked")
	private Options() {
		this.add(OptId.DIC_PATH, new Option("resources/ru_RU.dic"));	
		this.add(OptId.AFF_PATH, new Option("resources/ru_RU.aff"));
	}

	// Добавление опций
	private void add(OptId id, Option o) {
		opts.put(id, o);
		optsVals.put(id, o.defaultValue);
	}

	// Перезапись значений. Object должен совпадать с типом <T>@Option
	public void set(OptId id, Object o) throws Exception {
		Object defaultObj = optsVals.get(id);
		if(defaultObj==null){
			throw new Exception("Нет значения по умолчанию для "+id);
		}
		if (o.getClass() != defaultObj.getClass()) { // проверка типа
			// System.err.println("Неверный класс "+o.getClass()+", требуется "+optsVals.get(id).getClass());
			throw new NonCriticalException("Неверный класс " + o.getClass()
					+ ", требуется " + optsVals.get(id).getClass());
		}
		// System.err.println("класс "+o.getClass().getName()+", перезаписал класс "+optsVals.get(id).getClass().getName());
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

	public double getDouble(OptId id) {
		return (Double) optsVals.get(id);
	}

	public boolean getBoolean(OptId id) {
		return (Boolean) optsVals.get(id);
	}

	public String getString(OptId id) {
		return (String) optsVals.get(id);
	}
}
