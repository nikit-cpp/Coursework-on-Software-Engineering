package mytests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import engine.lexer.Lexer;
import engine.lexer.Tag;
import engine.lexer.Token;

public class LexerTest {
	
	Lexer tokenizer;
	ArrayList<Token> at = new ArrayList<Token>();
	ArrayList<String> as = new ArrayList<String>();
	
	void generateStringsList(){
		for(Token t: at)
			as.add(t.value);
	}
	
	@Before
	public void setUp(){
		tokenizer = new Lexer();
		at.clear();
		as.clear();
	}

	@Test
	public void testNull() {
		tokenizer.tokenize(null);
	}
	
	@Test
	public void testBrackets() {
		at = tokenizer.tokenize("public String(ololo) split");
		assertEquals(4, at.size());
		generateStringsList();
		assertArrayEquals(as.toArray(), new String[]{"public", "String", "ololo", "split"} );
	}
	
	@Test
	public void testSquareBrackets() {
		at = tokenizer.tokenize("public String[ololo] split");
		assertEquals(4, at.size());
		generateStringsList();
		assertArrayEquals(as.toArray(), new String[]{"public", "String", "ololo", "split"} );
	}
	
	@Test
	public void testCurveBrackets() {
		at = tokenizer.tokenize("public String { ololo } split");
		assertEquals(4, at.size());
		generateStringsList();
		assertArrayEquals(as.toArray(), new String[]{"public", "String", "ololo", "split"} );
	}
	
	// TODO игнорируемые тесткейсы -- для задачи-максимума
	@Ignore
	@Test
	public void testDot() {
		at = tokenizer.tokenize("public String { ololo  split.");
		assertEquals(5, at.size());
		assertEquals(at.get(4), new Token(".", Tag.DOT));
	}
	
	@Ignore
	@Test
	public void testSingleDot() {
		at = tokenizer.tokenize(".");
		assertEquals(1, at.size());
		assertEquals(at.get(0), new Token(".", Tag.DOT));
	}
	
	@Ignore
	@Test
	public void testWordDot() {
		at = tokenizer.tokenize("давно.");
		System.out.println(at);
		assertEquals(2, at.size());
		generateStringsList();
		assertArrayEquals(new String[]{"давно", "."},  as.toArray());
		assertEquals(at.get(1), new Token(".", Tag.DOT));
	}
	
	@Test
	public void testWord() {
		at = tokenizer.tokenize("недавно");
		System.out.println(at);
		assertEquals(1, at.size());
		generateStringsList();
		assertArrayEquals(new String[]{"недавно"},  as.toArray());
	}
	
	@Test
	public void testTwoWord() {
		at = tokenizer.tokenize("недавно давно");
		System.out.println(at);
		assertEquals(2, at.size());
		generateStringsList();
		assertArrayEquals(new String[]{"недавно", "давно"},  as.toArray());
	}
	
	@Test
	public void testWordEng() {
		at = tokenizer.tokenize("late");
		System.out.println(at);
		assertEquals(1, at.size());
		generateStringsList();
		assertArrayEquals(new String[]{"late"},  as.toArray());
	}
	
	@Ignore
	@Test
	public void testWordDotQ() {
		at = tokenizer.tokenize("давно.,");
		System.out.println(at);
		assertEquals(2, at.size());
		generateStringsList();
		assertArrayEquals(new String[]{"давно", "."},  as.toArray());
		assertEquals(at.get(1), new Token(".", Tag.DOT));
	}
	
	@Ignore
	@Test
	public void testWordQuestion() {
		at = tokenizer.tokenize("давно?");
		assertEquals(2, at.size());
		generateStringsList();
		assertArrayEquals(new String[]{"давно", "?"},  as.toArray());
	}
	
	@Test
	public void testColon() {
		at = tokenizer.tokenize("public String ololo:split");
		assertEquals(4, at.size());
		generateStringsList();
		assertArrayEquals(as.toArray(), new String[]{"public", "String", "ololo", "split"} );
	}
		
	@Test
	public void testQuote() {
		at = tokenizer.tokenize("public String \"ololo\"split");
		assertEquals(4, at.size());
		generateStringsList();
		assertArrayEquals(as.toArray(), new String[]{"public", "String", "ololo", "split"} );
	}
}
