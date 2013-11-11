package mytests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.hasItem;
import normalizer.Normalizer;

import org.junit.Before;
import org.junit.Test;

public class NormalizerTest {
	
	Normalizer normalizer;
		
	@Before
	public void setUp(){
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

}
