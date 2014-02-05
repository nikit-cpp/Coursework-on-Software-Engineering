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
		informatica.add("риск", 1);
		informatica.add("XP", 0.95);
		tdm.addDic(informatica);
		
		// Создаём новый объект, который должен считать сохранённые на диск файлы
		tdm = new ThematicDicManager();

		assertThat(tdm.getAllDicts().size(), is(4));
		
		assertThat(tdm.getDic(0).getName(), is("ФизикаСловарьТест"));
		assertThat(tdm.getDic(0).getEnabled(), is(true));
		assertThat(tdm.getDic(1).getName(), is("АлгебраСловарьТест"));
		assertThat(tdm.getDic(1).getEnabled(), is(true));
		assertThat(tdm.getDic(2).getName(), is("ЭкологияСловарьТест"));
		assertThat(tdm.getDic(2).getEnabled(), is(false));
		assertThat(tdm.getDic(3).getName(), is("информатика2"));
		assertThat(tdm.getDic(3).getEnabled(), is(true));
		
		assertThat(tdm.getDic(3).getName(), is("информатика2"));
		assertThat(tdm.getDic(3).getEnabled(), is(true));
		
		assertThat(tdm.getDic(3).getProbability("риск"), is(1.0));
		
		// Удаление слова
		final String ololo = "ололо";
		final int eco=2;
		tdm.addWord(eco, ololo, 0.123);
		tdm.deleteWord(ololo, eco);
		
		// Создаём новый объект, который должен считать сохранённые на диск файлы
		tdm = new ThematicDicManager();
		// нулевая вероятность - признак отсутствия слова
		assertThat(tdm.getDic(eco).getProbability(ololo), is(0.0));
	}

}
