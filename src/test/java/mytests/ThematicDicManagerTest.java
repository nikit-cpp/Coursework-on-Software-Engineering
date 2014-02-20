package mytests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;
import java.util.List;

import org.junit.*;

import engine.thematicdictionary.*;
import entities.Rubric;

public class ThematicDicManagerTest {
	static ThematicDicManager tdm;

	/*@BeforeClass
	public static void setUpBefore() throws Exception {
		tdm = new ThematicDicManager();
	}*/

	@Test
	public void testAdd() throws Exception {		
		tdm = ThematicDicManager.getInstance();
		tdm.clearDb();
		
		tdm.addDic("ФизикаСловарьТест", true);
		tdm.addDic("АлгебраСловарьТест", true);
		tdm.addDic("ЭкологияСловарьТест", false);
		Rubric informatica = new Rubric("информатика2", true);
		tdm.addDic(informatica);
				
		// Утверждаю что эти словари загружаются...
		List<Rubric> loaded = tdm.getAllDicts();
		for (Rubric r : loaded){
			System.out.println(r.toString());
		}
		assertThat(loaded.get(3), is(informatica));
		assertThat(loaded.size(), is(4));	
	}
	
	@Test
	public void testRemove() throws Exception {		
		tdm = ThematicDicManager.getInstance();
		tdm.clearDb();
		
		tdm.addDic("ФизикаСловарьТест", true);
		tdm.addDic("АлгебраСловарьТест", true);
		tdm.addDic("ЭкологияСловарьТест", false);
		Rubric informatica = new Rubric("информатика2", true);
		tdm.addDic(informatica);
				
		// Утверждаю что эти словари загружаются...
		List<Rubric> loaded = tdm.getAllDicts();
		for (Rubric r : loaded){
			System.out.println(r.toString());
		}
		assertThat(loaded.get(3), is(informatica));
		assertThat(loaded.size(), is(4));
		
		// Удаление словарей
		System.out.println("Удаление словарей");
		for (int i=loaded.size()-1; i>=0; i--){
			tdm.deleteDic(i);
		}
		assertThat(loaded.size(), is(0));
	}

	@Test
	public void testRename() throws Exception {		
		tdm = ThematicDicManager.getInstance();
		tdm.clearDb();
		
		tdm.addDic("ФизикаСловарьТест", true);
		tdm.addDic("АлгебраСловарьТест", true);
		tdm.addDic("ЭкологияСловарьТест", false);
		Rubric informatica = new Rubric("информатика2", true);
		tdm.addDic(informatica);
						
		// Переименование
		System.out.println("Переименование");
		int eco = 2;
		tdm.renameDic(eco, "Новая Экология");
		
		// Утверждаю что эти словари загружаются...
		List<Rubric> loaded = tdm.getAllDicts();
		for (Rubric r : loaded){
			System.out.println(r.toString());
		}
		assertThat(loaded.get(3), is(informatica));
		assertThat(loaded.size(), is(4));

		assertThat(loaded.get(eco), is(new Rubric("Новая Экология")));
	}
	
	@Test
	public void testIsWorkAddAfterErrorAdd(){
		tdm = ThematicDicManager.getInstance();
		tdm.clearDb();

		try {
			tdm.addDic("словарь1", true);
			tdm.addDic("словарь1", true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			tdm.addDic("словарь2", true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Утверждаю что эти словари загружаются...
		List<Rubric> loaded = tdm.getAllDicts();
		for (Rubric r : loaded){
			System.out.println(r.toString());
		}
		assertThat(loaded.size(), is(2));

	}

	@Test
	public void testIsWorkRenameAfterErrorAdd(){
		tdm = ThematicDicManager.getInstance();
		tdm.clearDb();

		try {
			tdm.addDic("словарь1", true);
			tdm.addDic("словарь1", true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			tdm.renameDic(0, "словарь 333");
		} catch (Exception e) {
		}
		
		// Утверждаю что эти словари загружаются...
		List<Rubric> loaded = tdm.getAllDicts();
		for (Rubric r : loaded){
			System.out.println(r.toString());
		}
		assertThat(loaded.get(0).getName(), is("словарь 333"));

	}
	
	@Test
	public void testIsWorkRenameAfterErrorRename(){
		tdm = ThematicDicManager.getInstance();
		tdm.clearDb();

		try {
			tdm.addDic("словарь0", true);
			tdm.addDic("словарь1", true);
			tdm.addDic("словарь2", true);
		} catch (Exception e) {
		}

		try {
			tdm.renameDic(0, "словарь2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Утверждаю что эти словари загружаются...
		List<Rubric> loaded = tdm.getAllDicts();
		for (Rubric r : loaded){
			System.out.println(r.toString());
		}
		assertThat(loaded.size(), is(3));
		// Утверждаю что не появилось 2 одинаковых словаря
		assertThat(loaded.get(0).getName(), is("словарь0"));
		assertThat(loaded.get(1).getName(), is("словарь1"));
		assertThat(loaded.get(2).getName(), is("словарь2"));

	}


}
