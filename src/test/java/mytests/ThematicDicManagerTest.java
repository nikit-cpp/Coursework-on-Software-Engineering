package mytests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;

import org.junit.*;

import engine.thematicdictionary.*;

public class ThematicDicManagerTest {
	static ThematicDicManager tdm;

	/*@BeforeClass
	public static void setUpBefore() throws Exception {
		tdm = new ThematicDicManager();
	}*/

	@Test
	public void testAdd() {		
		tdm = new ThematicDicManager();
		tdm.clear();
		tdm.remove();
		
		tdm.addDic("ФизикаСловарьТест", true);
		tdm.addDic("АлгебраСловарьТест", true);
		tdm.addDic("ЭкологияСловарьТест", false);
		
		ThematicDic informatica = new ThematicDic("информатика2", true);
		informatica.addWord("риск", 1);
		informatica.addWord("XP", 0.95);
		tdm.addDic(informatica);
		
		// Создаём новый объект, который должен считать сохранённые на диск файлы
		tdm = new ThematicDicManager();

		assertThat(tdm.getAllDicts().size(), is(4));
		
		assertThat(tdm.getDic(0).getDicName(), is("ФизикаСловарьТест"));
		assertThat(tdm.getDic(0).getDicEnabled(), is(true));
		assertThat(tdm.getDic(1).getDicName(), is("АлгебраСловарьТест"));
		assertThat(tdm.getDic(1).getDicEnabled(), is(true));
		assertThat(tdm.getDic(2).getDicName(), is("ЭкологияСловарьТест"));
		assertThat(tdm.getDic(2).getDicEnabled(), is(false));
		assertThat(tdm.getDic(3).getDicName(), is("информатика2"));
		assertThat(tdm.getDic(3).getDicEnabled(), is(true));
		
		assertThat(tdm.getDic(3).getDicName(), is("информатика2"));
		assertThat(tdm.getDic(3).getDicEnabled(), is(true));
		
		assertThat(tdm.getDic(3).getProbability4Word("риск"), is(1.0));
		
		// Удаление слова
		final String ololo = "ололо";
		final int eco=2;
		tdm.addWord(eco, ololo, 0.123);
		tdm.deleteWord(ololo, eco);
		
		// Создаём новый объект, который должен считать сохранённые на диск файлы
		tdm = new ThematicDicManager();
		// нулевая вероятность - признак отсутствия слова
		assertThat(tdm.getDic(eco).getProbability4Word(ololo), is(0.0));
	}

}
