package mytests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import engine.thematicdictionary.Rubric;

public class ThematicDicTest {
	static Rubric td;
	
	@BeforeClass
	public static void setUp(){
		td = new Rubric("", true);
	}

	@Test(expected = IllegalArgumentException.class, timeout = 2000)
	public void testBoundsL() {
		td.addWord("string", -0.2);
	}
	
	@Test(expected = IllegalArgumentException.class, timeout = 2000)
	public void testBoundsH() {
		td.addWord("string", 1.3);
	}
	
	@Test
	public void testNotFound(){
		assertEquals(0.0, td.getProbability4Word("несуществующее в словаре слово"), 0.0001);
	}
	
	@Test
	public void testAdd(){
		assertEquals(0, td.getSize());
		final String word = "слово";
		td.addWord(word, 1.0);
		assertEquals(1, td.getSize());
		assertEquals(1.0, td.getProbability4Word(word), 0.0001);
	}
}
