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
		assertThat(tdm.getAllProbabilitys().size(), is(0));
		
		tdm.addWord(0, "субатом", 0.3);

		assertThat(tdm.getAllDicts().size(), is(1));
		assertThat(tdm.getAllProbabilitys().size(), is(1));
		assertThat(tdm.getAllWords().size(), is(1));
		assertThat(tdm.getAllWords().get(0).getProbabilitys().size(), is(1));
		
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
	public void testDelWord() throws Exception {
		tdm = ThematicDicManager.getInstance();
		tdm.clearDbSQL();
		tdm.addDic("ФизикаСловарьТест", true);
		tdm.addWord(0, "атом", 0.8);
		assertThat(tdm.getAllDicts().get(0).getProbabilitys().size(), is(1));
		tdm.deleteWord(0, 0);
		assertThat(tdm.getAllDicts().get(0).getProbabilitys().size(), is(0));
		assertThat(tdm.getAllProbabilitys().size(), is(0)); // проверка удаления
															// сирот(вероятностей
															// из таблицы
															// вероятностей, на
															// которые не
															// ссыласется ни
															// одна рубрика) -
															// Rubric.java
															// orphan
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
	
	@Test
	public void testDelProbsAfterDelDic() throws Exception{
		tdm = ThematicDicManager.getInstance();
		tdm.clearDbSQL();
		
		tdm.addDic("ФизикаСловарьТест", true);
		tdm.addWord(0, "атом", 0.8);
		tdm.addWord(0, "мегаатом", 0.81);
		assertThat(tdm.getAllDicts().get(0).getProbabilitys().size(), is(2));
		assertThat(tdm.getAllProbabilitys().size(), is(2));
		
		tdm.deleteDic(0);
		assertThat(tdm.getAllProbabilitys().size(), is(0));
		assertThat(tdm.getAllDicts().size(), is(0));
	}
	
	@Test
	public void testDelWordsAfterDelProbability() throws Exception{
		tdm = ThematicDicManager.getInstance();
		tdm.clearDbSQL();
		
		tdm.addDic("ФизикаСловарьТест", true);
		tdm.addDic("Информатика", true);

		tdm.addWord(0, "атом", 0.8);
		tdm.addWord(0, "мегаатом", 0.81);
		tdm.addWord(1, "мегаатом", 0.021);
		assertThat(tdm.getAllDicts().get(0).getProbabilitys().size(), is(2));
		assertThat(tdm.getAllDicts().get(1).getProbabilitys().size(), is(1));
		assertThat(tdm.getAllProbabilitys().size(), is(3));
		assertThat(tdm.getAllWords().size(), is(2));
				
		tdm.deleteWord(0, 0); // удаляем атом:ФизикаСловарьТест
		tdm.deleteWord(0, 0); // удаляем мегаатом:ФизикаСловарьТест
		System.out.println("Закончили deleteWord");
		assertThat(tdm.getAllDicts().get(0).getProbabilitys().size(), is(0));
		assertThat(tdm.getAllDicts().get(1).getProbabilitys().size(), is(1));
		assertThat(tdm.getAllProbabilitys().size(), is(1));
		assertThat(tdm.getAllWords().size(), is(1)); // должен остаться только мегаатом:Информатика
		System.out.println("Оставшееся слово: "+tdm.getAllWords().get(0));
		assertThat(tdm.getAllWords().get(0).getWord(), is("мегаатом"));
		
		assertThat(tdm.getAllWords().get(0).getProbabilitys().size(), is(1)); // тестируем наличие несуществующих вероятностей у слова
	}
}
