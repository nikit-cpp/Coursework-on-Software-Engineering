package util;

import java.io.File;
import java.nio.file.Path;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class HibernateUtil {
	private static final String dbFileName = "thematicdicts";
	private static final String dbFileExt = ".h2.db";
	private static boolean fileMode = true;
    private static SessionFactory sessionFactory;
    private static String dbPathFileName;
    private static Path dbPath;
    public static String getDbFileName() {
		return dbPathFileName;
	}

    /**
     * Подготавливает HibernateUtil для дальнейшей работы
     * @param dbPath путь к "родительской" папке, куда будет помещена папка h2db, содержащая файлы БД. 
     * Если null, то будет создана одноразовая in-memory db. Для тестов.
     */
	public static void setUp(Path dbPath2) {
		dbPath = dbPath2;
		String storage;
		if(dbPath!=null){
			storage = "file:";
			dbPath = dbPath.toAbsolutePath().normalize().resolve("h2db"); // папка h2db
			dbPath.toFile().mkdirs(); // создаём её
			
			Path dbPathFile = dbPath.resolve(dbFileName); // файл "thematicdicts" , расширение(".h2.db") добавит hibernate
			String s = dbPathFile.toString();
			dbPathFileName = preparePathToH2db(s);
		}else{
			storage = "mem:";
			fileMode = false;
			dbPathFileName = "td;DB_CLOSE_DELAY=-1"; // TODO немного костыль(1*), To keep the content of an in-memory database as long as the virtual machine is alive. DB_CLOSE_DELAY=-1
			// TODO ПРАВИЛЬНО -- УБРАТЬ? ЭТУ ОПЦИЮ, И СДЕЛАТЬ СБРОС БД В паре @Before / @After, отказаться от schema.create() сделать раздельные .cfg.xml или programmatic конфиги для "продакшена" и тестов
		}
		connectionString = "jdbc:h2:"+storage+dbPathFileName+";FILE_LOCK=NO;TRACE_LEVEL_FILE=0;WRITE_DELAY=0";
	}

	private static String connectionString;
	/**
	 *  Вызывается 1 раз, потому что синглтон
	 * @return
	 */
    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            // return new Configuration().setProperty("hibernate.connection.url", connectionString).configure("hibernate.cfg.xml").buildSessionFactory();
            Configuration config = new Configuration();

            config = config.setProperty("hibernate.connection.url", connectionString).configure("hibernate.cfg.xml");

            boolean isNeedCreate = true;
            
            // Если бд - в файле(продакшн) и файл существует(создался-остался от прошлых запусков), то мы не будем создавать БД заново
            if(dbPath !=null && fileMode && (new File(dbPath.toFile(), dbFileName+dbFileExt)).exists())
            	isNeedCreate = false;
            
            if(isNeedCreate){
            	System.out.println("Создаю схему");
	            SchemaExport schema = new SchemaExport(config);
	            schema.create(true, true); // (1*) Закрывает соединение с in-memory БД, поэтому она сбросится, если не выставлено DB_CLOSE_DELAY=-1
            }
            
        	// TODO избавиться от deprecated
            return config.buildSessionFactory();
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    // возвращает один и тот же экземпляр sessionFactory, реализуя поведение синглтона
    public static SessionFactory getSessionFactory() {
    	if (sessionFactory == null){
    		sessionFactory = buildSessionFactory();
    	}
        return sessionFactory;
    }
    
    public static String preparePathToH2db(String path){
    	return path.replace('\\', '/');
    }

}