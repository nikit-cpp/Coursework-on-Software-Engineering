package mytests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.hasItem;

import java.util.Collection;

import org.junit.*;

import engine.foundedwords.Builder;
import engine.foundedwords.WordInfo;
import engine.foundedwords.WordsMap;

public class DictionaryBuilderTest {
	static Builder sentenceBuilder;
	
	@BeforeClass
	public static void setUp(){
		sentenceBuilder = new Builder();
	}
	
	@Test
	public void testOlolo() {
		WordsMap dc = sentenceBuilder.buildMap("Точками точкой точкой точки");
		dc.print();
		
		Collection<WordInfo> words = dc.getWords();
		assertEquals(3, words.size());
		assertThat(words, hasItem(new WordInfo("точки")));
		assertThat(words, hasItem(new WordInfo("точкой")));
		assertThat(words, hasItem(new WordInfo("Точками")));
		
		Collection<WordInfo> stems = dc.getStems();
		assertEquals(1, stems.size());
		assertThat(stems, hasItem(new WordInfo("точка")));
		assertEquals(4, stems.iterator().next().getCount());
	}

	@Test
	public void testRisk() {
		WordsMap dc = sentenceBuilder.buildMap("риски");
		dc.print();
		
		Collection<WordInfo> words = dc.getWords();
		assertEquals(1, words.size());
		assertThat(words, hasItem(new WordInfo("риски")));
		assertEquals(2, words.iterator().next().getCount());
		
		Collection<WordInfo> stems = dc.getStems();
		assertEquals(2, stems.size());
		assertThat(stems, hasItem(new WordInfo("риск")));
		assertThat(stems, hasItem(new WordInfo("риска")));
	}
}
