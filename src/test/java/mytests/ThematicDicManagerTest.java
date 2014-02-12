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
	public void testAdd() {		
		tdm = ThematicDicManager.getInstance();
		tdm.clear();
		tdm.remove();
		
		tdm.addDic("ФизикаСловарьТест", true);
		tdm.addDic("АлгебраСловарьТест", true);
		tdm.addDic("ЭкологияСловарьТест", false);
		Rubric informatica = new Rubric("информатика2", true);
		tdm.addDic(informatica);
		
		// Утверждаю что эти словпри загружаются...
		List<Rubric> loaded = tdm.getAllDicts();
		for (Rubric r : loaded){
			System.out.println(r.toString());
			
		}
		// Удаление словаря
		
	}

}
