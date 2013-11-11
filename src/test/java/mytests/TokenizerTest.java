package mytests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import lexer.Tag;
import lexer.Token;
import lexer.Tokenizer;

import org.junit.Before;
import org.junit.Test;

public class TokenizerTest {
	
	Tokenizer tokenizer;
	ArrayList<Token> at = new ArrayList<Token>();
	ArrayList<String> as = new ArrayList<String>();
	
	void generateStringsList(){
		for(Token t: at)
			as.add(t.value);
	}
	
	@Before
	public void setUp(){
		tokenizer = new Tokenizer();
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
	
	@Test
	public void testDot() {
		at = tokenizer.tokenize("public String { ololo  split.");
		assertEquals(5, at.size());
		assertEquals(at.get(4), new Token(".", Tag.DOT));
	}
	
	@Test
	public void testSingleDot() {
		at = tokenizer.tokenize(".");
		assertEquals(1, at.size());
		assertEquals(at.get(0), new Token(".", Tag.DOT));
	}
	
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
	public void testWordDotQ() {
		at = tokenizer.tokenize("давно.,");
		System.out.println(at);
		assertEquals(2, at.size());
		generateStringsList();
		assertArrayEquals(new String[]{"давно", "."},  as.toArray());
		assertEquals(at.get(1), new Token(".", Tag.DOT));
	}
	
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
