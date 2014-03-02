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
		
		tdm.addDic("ФизикаСловарьТест", true);
		tdm.clearDbSQL();
		
		assertThat(tdm.getAllDicts().size(), is(0));
		
		tdm.addDic("ФизикаСловарьТест", true);
		assertThat(tdm.getAllDicts().get(0).getProbabilitys().size(), is(0));
		
		tdm.addWord(0, "субатом", 0.3);

		assertThat(tdm.getAllDicts().size(), is(1));
		
		// Покрывает строчку ThematicDicManager:196
		// "getDic(dicIndex).getProbabilitys().add(p);"
		assertThat(tdm.getAllDicts().get(0).getProbabilitys().size(), is(1));
	}
	
	@Test
	public void testAddWithConflict() throws Exception{
		tdm = ThematicDicManager.getInstance();
		tdm.clearDbSQL();
		tdm.addDic("ФизикаСловарьТест", true);
		tdm.addWord(0, "атом", 0.33);
		
		tdm.addDic("Алгебра+", true);
		tdm.addWord(1, "атом", 0.1);
	}
}
