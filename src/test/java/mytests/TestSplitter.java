package mytests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.*;

import org.junit.Before;
import org.junit.Test;

public class TestSplitter {
	
	Model model;
	ArrayList<Token> at = new ArrayList<Token>();
	ArrayList<String> as = new ArrayList<String>();
	
	void generateStringsList(){
		for(Token t: at)
			as.add(t.value);
	}
	
	@Before
	public void setUp(){
		model = new Model();
		at.clear();
		as.clear();
	}

	@Test
	public void testNull() {
		model.tokenize(null);
	}
	
	@Test
	public void testBrackets() {
		at = model.tokenize("public String(ololo) split");
		assertEquals(4, at.size());
		generateStringsList();
		assertArrayEquals(as.toArray(), new String[]{"public", "String", "ololo", "split"} );
	}
	
	@Test
	public void testSquareBrackets() {
		at = model.tokenize("public String[ololo] split");
		assertEquals(4, at.size());
		generateStringsList();
		assertArrayEquals(as.toArray(), new String[]{"public", "String", "ololo", "split"} );
	}
	
	@Test
	public void testCurveBrackets() {
		at = model.tokenize("public String { ololo } split");
		assertEquals(4, at.size());
		generateStringsList();
		assertArrayEquals(as.toArray(), new String[]{"public", "String", "ololo", "split"} );
	}
	
	@Test
	public void testDot() {
		at = model.tokenize("public String { ololo  split.");
		assertEquals(5, at.size());
		assertEquals(at.get(4), new Token(".", Tag.DOT));
	}
	
	@Test
	public void testColon() {
		at = model.tokenize("public String ololo:split");
		assertEquals(4, at.size());
		generateStringsList();
		assertArrayEquals(as.toArray(), new String[]{"public", "String", "ololo", "split"} );
	}
		
	@Test
	public void testQuote() {
		at = model.tokenize("public String \"ololo\"split");
		assertEquals(4, at.size());
		generateStringsList();
		assertArrayEquals(as.toArray(), new String[]{"public", "String", "ololo", "split"} );
	}
}
