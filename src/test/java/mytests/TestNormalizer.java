package mytests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.Model;
import model.Normalizer;
import model.Token;

import org.junit.Before;
import org.junit.Test;

public class TestNormalizer {
	
	Model model;
	ArrayList<Token> at = new ArrayList<Token>();
	ArrayList<Token> ar = new ArrayList<Token>();
		
	@Before
	public void setUp(){
		model = new Model();
		at.clear();
		ar.clear();
	}

	@Test
	public void testPrefix() {
		at.add(new Token("асептический"));
		ar = model.normalize(at);
		assertEquals(Normalizer.convertFromUTF8ToSystemDefault("септический"), ar.get(0).value);
	}
	
	@Test
	public void testSuffix() {
		at.add(new Token("атомов"));
		ar = model.normalize(at);
		assertEquals(Normalizer.convertFromUTF8ToSystemDefault("атом"), ar.get(0).value);
	}

}
