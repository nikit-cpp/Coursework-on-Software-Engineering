package mytests;

import static org.junit.Assert.*;
import normalizer.Normalizer;
import options.OptId;
import options.Options;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestOptions {
	static Options options;
	@BeforeClass
	public static void setUp(){
		options = Options.getInstance();
	}
	
	@Test
	public void testString() {
		assertEquals("resources/ru_RU.aff", options.getString(OptId.AFF_PATH));
	}

}
