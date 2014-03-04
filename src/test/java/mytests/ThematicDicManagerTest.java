package mytests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;

import org.junit.*;

import engine.thematicdictionary.*;
import entities.Probability;
import entities.Rubric;
import entities.Word;

public class ThematicDicManagerTest {
	static ThematicDicManager tdm;

	/*@BeforeClass
	public static void setUpBefore() throws Exception {
		tdm = new ThematicDicManager();
	}*/

	@Test
	public void testAdd() throws Exception {		
		tdm = ThematicDicManager.getInstance();
		tdm.clearDbSQL();
		
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
		tdm.clearDbSQL();
		
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
	public void testRemoveProbabilitysAndWords() throws Exception {		
		tdm = ThematicDicManager.getInstance();
		tdm.clearDbSQL();
		
		tdm.addDic("ФизикаСловарьТест", true);
		tdm.addWord(0, "атом", 0.833);
		tdm.addWord(0, "производная", 0.13);
		
		tdm.addDic("АлгебраСловарьТест", true);
		tdm.addWord(1, "производная", 0.77);
		tdm.addDic("ЭкологияСловарьТест", false);
		Rubric informatica = new Rubric("информатика2", true);
		tdm.addDic(informatica);
				
		// Проверяем корректность наполнения
		assertThat(tdm.getAllDicts().size(), is(4));
		assertThat(tdm.getAllProbabilitys().size(), is(3));
		assertThat(tdm.getAllWords().size(), is(2));
		
		// Удаление Физики
		tdm.deleteDic(0);
		assertThat(tdm.getAllDicts().size(), is(3));
		assertThat(tdm.getAllProbabilitys().size(), is(1));
		
		assertThat(tdm.getAllDicts().get(0).getName(), is("АлгебраСловарьТест"));
		assertThat(tdm.getAllDicts().get(0).getProbabilitys().size(), is(1));
		assertThat(((Word)((Probability)tdm.getAllDicts().get(0).getProbabilitys().get(0)).getWord()).getWord(), is("производная"));
		//assertThat(tdm.getAllWords().size(), is(1));
		assertThat(tdm.getAllWords().get(0).getProbabilitys().size(), is(1)); // по смыслу не правильно - ссылка на несуществующую вер-ть
		assertThat(tdm.getAllWords().get(0).getWord(), is("производная"));
		assertThat(tdm.getAllWords().get(0).getProbabilitys().size(), is(1));
	}

	@Test
	public void testRename() throws Exception {		
		tdm = ThematicDicManager.getInstance();
		tdm.clearDbSQL();
		
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
		tdm.clearDbSQL();

		try {
			tdm.addDic("словарь1", true);
			tdm.addDic("словарь1", true);
		} catch (ThematicDicManagerException e) {
			e.printStackTrace();
		}

		try {
			tdm.addDic("словарь2", true);
		} catch (ThematicDicManagerException e) {
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
		tdm.clearDbSQL();

		try {
			tdm.addDic("словарь1", true);
			tdm.addDic("словарь1", true);
		} catch (ThematicDicManagerException e) {
			e.printStackTrace();
		}

		try {
			tdm.renameDic(0, "словарь 333");
		} catch (ThematicDicManagerException e) {
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
		tdm.clearDbSQL();

		try {
			tdm.addDic("словарь0", true);
			tdm.addDic("словарь1", true);
			tdm.addDic("словарь2", true);
		} catch (ThematicDicManagerException e) {
		}

		try {
			tdm.renameDic(0, "словарь2");
		} catch (ThematicDicManagerException e) {
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

	/**
	 * 
	 */
	@Test
	public void testIsWorkRenameAfterErrorDelete(){
		tdm = ThematicDicManager.getInstance();
		tdm.clearDbSQL();

		try {
			tdm.addDic("словарь0", true);
			tdm.addDic("словарь1", true);
			tdm.addDic("словарь2", true);
		} catch (ThematicDicManagerException e) {
		}

		try {
			tdm.deleteDic(-1);
		} catch (ThematicDicManagerException e) {
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
