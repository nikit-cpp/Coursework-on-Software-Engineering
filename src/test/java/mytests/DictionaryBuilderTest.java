package mytests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.hasItem;

import java.util.Collection;

import org.junit.*;

import runtime.dictionary.DictionaryBuilder;
import runtime.dictionary.DictionaryContainer;
import runtime.dictionary.WordInfo;

public class DictionaryBuilderTest {
	static DictionaryBuilder sentenceBuilder;
	
	@BeforeClass
	public static void setUp(){
		sentenceBuilder = new DictionaryBuilder();
	}
	
	@Test
	public void testOlolo() {
		DictionaryContainer dc = sentenceBuilder.buildSentence("Точками точкой точкой точки");
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

}
