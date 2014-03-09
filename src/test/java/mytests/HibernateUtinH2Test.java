package mytests;

import static org.junit.Assert.*;

import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.HibernateUtil;

public class HibernateUtinH2Test {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String in = (Paths.get(".")).toAbsolutePath().normalize().resolve("dbpath").toString();
		String need = in.replace('\\', '/');
		System.out.println(in);
		String out = HibernateUtil.preparePathToH2db(in);
		
		assertEquals(need, out);
		System.out.println(out);
	}

}
