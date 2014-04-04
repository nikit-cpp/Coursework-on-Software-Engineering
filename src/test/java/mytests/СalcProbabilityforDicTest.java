package mytests;

import static org.junit.Assert.*;

import org.junit.*;

import util.HibernateUtil;
import engine.Engine;
import engine.foundedwords.Builder;
import engine.thematicdictionary.*;
import entities.Rubric;

public class СalcProbabilityforDicTest {
	static ThematicDicManager tdm;
	static Rubric r0;
	static Builder builder;
	
	@BeforeClass
	public static void setUp() throws ThematicDicManagerException{
		HibernateUtil.setUp(null);
		tdm = ThematicDicManager.getInstance();
		builder = new Builder();
		
		tdm.addDic("megaDic", true);
		
		r0 = tdm.getAllDicts().get(0);
		
		tdm.addWord(0, "начинать", 1.0);
		tdm.addWord(0, "нас", 1.0);
		tdm.addWord(0, "спасать", 1.0);
		tdm.addWord(0, "от", 1.0);
		//megaDic.add("недобросовестный", 0.3);
		//megaDic.add("исполнитель", 0.3);
	}
	
	@Test
	public void testIfExistsWordInDB() throws Exception {
		double p = r0.getCalculatedProbability();
		assertEquals(true, p<=1.0);
		System.out.println(p);
		
		double pw=tdm.getProbability4Word(r0, "начинать");
		assertEquals(pw, 1.0, 0.001);
	}
	
	@Test
	public void test1() throws Exception {
		tdm.calcProbabilityforDic( r0, builder.buildMap("начинает нас спасать от недобросовестных исполнителей").getStems());
		double p = r0.getCalculatedProbability();
		assertEquals(true, p<=1.0);
		System.out.println(p);
		
		assertEquals(0.66, p, 0.01);
	}

	@Test
	public void test2() throws Exception {
		tdm.addDic("исполнитель", true);
		
		Rubric r1 = tdm.getAllDicts().get(1);
		assertEquals(r1.getName(), "исполнитель");
		
		tdm.calcProbabilityforDic(r1, builder.buildMap("исполнителей исполнители").getStems());
		
		double p = r1.getCalculatedProbability();
		assertEquals(true, p<=1.0);
		System.out.println(p);
		
		assertEquals(1.00, p, 0.01);
	}
	
	@Test
	public void test3() throws Exception {
		tdm.addWord(0, "образ", 0.5);
		
		tdm.calcProbabilityforDic(r0, builder.buildMap("образом образ образы").getStems());
		double p = r0.getCalculatedProbability();
		assertEquals(true, p<=1.0);
		System.out.println(p);
		
		assertEquals(0.5, p, 0.01);
	}
	
	@Test
	public void test4() throws Exception {
		tdm.calcProbabilityforDic(r0, builder.buildMap("исполнителей исполнители образы").getStems());
		double p = r0.getCalculatedProbability();
		assertEquals(true, p<=1.0);
		System.out.println(p);
		
		assertEquals(0.83, p, 0.01);
	}
	
	@Test
	public void test5_withNonExisted() throws Exception {
		tdm.calcProbabilityforDic(r0, builder.buildMap("исполнителей, исполнители образы несуществующееслово").getStems());
		double p = r0.getCalculatedProbability();
		assertEquals(true, p<=1.0);
		System.out.println(p);
		
		assertEquals(0.625, p, 0.001);
	}
}
