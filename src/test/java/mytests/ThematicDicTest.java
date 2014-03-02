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
	
	@Test(expected=ThematicDicManagerException.class)
	public void testAddFailSafe1() throws Exception{
		tdm = ThematicDicManager.getInstance();
		tdm.clearDbSQL();
		tdm.addDic("ФизикаСловарьТест", true);
		//try{
			tdm.addWord(0, "атом", 0.33);
			tdm.addWord(0, "атом", 0.1);
		/*}catch(Exception e){
			
		}*/
	}
	
	@Test(expected=ThematicDicManagerException.class)
	public void testAddFailSafe2() throws Exception{
		tdm = ThematicDicManager.getInstance();
		tdm.clearDbSQL();
		tdm.addDic("ФизикаСловарьТест", true);
		tdm.addWord(0, "атом", 0.8);
		tdm.addWord(0, "атом", 0.8);
	}

	@Test
	public void testDelWord() throws Exception{
		tdm = ThematicDicManager.getInstance();
		tdm.clearDbSQL();
		tdm.addDic("ФизикаСловарьТест", true);
		tdm.addWord(0, "атом", 0.8);
		assertThat(tdm.getAllDicts().get(0).getProbabilitys().size(), is(1));
		tdm.deleteWord(0, 0);
		assertThat(tdm.getAllDicts().get(0).getProbabilitys().size(), is(0));
	}
	
	@Test
	public void testDel2Word() throws Exception{
		tdm = ThematicDicManager.getInstance();
		tdm.clearDbSQL();
		tdm.addDic("ФизикаСловарьТест", true);
		tdm.addWord(0, "атом", 0.8);
		tdm.addWord(0, "мегаатом", 0.8);
		assertThat(tdm.getAllDicts().get(0).getProbabilitys().size(), is(2));
		tdm.deleteWord(1, 0);
		tdm.deleteWord(0, 0);
		assertThat(tdm.getAllDicts().get(0).getProbabilitys().size(), is(0));
	}
	
	@Test(expected=ThematicDicManagerException.class)
	public void testDelWordNonExists() throws Exception{
		tdm = ThematicDicManager.getInstance();
		tdm.clearDbSQL();
		tdm.addDic("ФизикаСловарьТест", true);
		tdm.addWord(0, "атом", 0.8);
		assertThat(tdm.getAllDicts().get(0).getProbabilitys().size(), is(1));
		tdm.deleteWord(1, 0);
		assertThat(tdm.getAllDicts().get(0).getProbabilitys().size(), is(0));
	}
}
