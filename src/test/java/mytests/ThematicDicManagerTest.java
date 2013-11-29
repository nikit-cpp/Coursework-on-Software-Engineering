package mytests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import thematicdictionary.ThematicDic;
import thematicdictionary.ThematicDicManager;

public class ThematicDicManagerTest {
	static ThematicDicManager tdm;

	/*@BeforeClass
	public static void setUpBefore() throws Exception {
		tdm = new ThematicDicManager();
	}*/

	@Test
	public void testAdd() {
		tdm = new ThematicDicManager("testtdm/");
		
		tdm.add("ФизикаСловарьТест", true);
		tdm.add("АлгебраСловарьТест", true);
		tdm.add("ЭкологияСловарьТест", false);
		
		ThematicDic informatica = new ThematicDic("информатика2", true);
		informatica.add("риск", 1);
		informatica.add("XP", 0.95);
		tdm.add(informatica);
		
		// Создаём новый объект, который должен считать сохранённые на диск файлы
		//tdm = new ThematicDicManager("testtdm/");
		assertThat(tdm.getThematicDicts().size(), is(4));
		
		assertThat(tdm.get(0).getName(), is("ФизикаСловарьТест"));
		assertThat(tdm.get(0).getEnabled(), is(true));
		assertThat(tdm.get(1).getName(), is("АлгебраСловарьТест"));
		assertThat(tdm.get(1).getEnabled(), is(true));
		assertThat(tdm.get(2).getName(), is("ЭкологияСловарьТест"));
		assertThat(tdm.get(2).getEnabled(), is(false));
		assertThat(tdm.get(3).getName(), is("информатика2"));
		assertThat(tdm.get(3).getEnabled(), is(true));
	}

}
