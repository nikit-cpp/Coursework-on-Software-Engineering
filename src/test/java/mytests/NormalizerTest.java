package mytests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.hasItem;

import org.junit.BeforeClass;
import org.junit.Test;

import engine.normalizer.Normalizer;

public class NormalizerTest {
	
	static Normalizer normalizer;
		
	@BeforeClass
	public static void setUp(){
		normalizer = new Normalizer();
	}

	@Test
	public void testPrefix() {
		assertThat(normalizer.normalize("асептический"), hasItem("септический"));
	}
	
	@Test
	public void testSuffix() {
		assertThat(normalizer.normalize("атомов"), hasItem("атом"));
	}

	@Test
	public void testRisk() {
		assertThat(normalizer.normalize("риски"), hasItem("риск"));
		assertThat(normalizer.normalize("риски"), hasItem("риска"));
	}
}
