package mytests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;

import org.junit.*;

import engine.thematicdictionary.*;
import entities.Rubric;

public class ThematicDicTest {
	static ThematicDicManager tdm;

	@Test
	public void testAdd() throws Exception{
		tdm = ThematicDicManager.getInstance();
		//tdm = new ThematicDicManager(1);
		//tdm.setSessions();
		//tdm.setSession();
		
		tdm.clearDb();
		tdm.addDic("ФизикаСловарьТест", true);
		tdm.addWord(0, "субатом", 0.3);
		
		//tdm.terminate();
	}
	
	@Test
	public void testAddWithConflict() throws Exception{
		//tdm = new ThematicDicManager(1);
		tdm = ThematicDicManager.getInstance();
		tdm.clearDb();
		tdm.addDic("ФизикаСловарьТест2", true);
		tdm.addWord(0, "атом", 0.33);
		
		tdm.addDic("Алгебра+", true);
		tdm.addWord(1, "атом", 0.1);
		
		//tdm.terminate();
	}
}
