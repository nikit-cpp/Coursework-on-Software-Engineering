package mytests;

import static org.junit.Assert.*;

import org.junit.*;

import engine.Engine;
import engine.foundedwords.Builder;
import engine.thematicdictionary.*;

public class СalcProbabilityforDicTest {
	static Engine engine;
	static RubricView megaDic;
	static Builder builder;
	
	@BeforeClass
	public static void setUp(){
		engine = Engine.getInstance();
		builder = new Builder();
		
		megaDic = new RubricView("megaDic", true);
		
		megaDic.add("начинать", 1.0);
		megaDic.add("нас", 1.0);
		megaDic.add("спасать", 1.0);
		megaDic.add("от", 1.0);
		//megaDic.add("недобросовестный", 0.3);
		//megaDic.add("исполнитель", 0.3);
	}
	
	@Test
	public void test1() {
		double p = engine.calcProbabilityforDic(megaDic, builder.buildMap("начинает нас спасать от недобросовестных исполнителей").getStems());
		assertEquals(true, p<=1.0);
		System.out.println(p);
		
		assertEquals(0.66, p, 0.01);
	}

	@Test
	public void test2() {
		megaDic.add("исполнитель", 1.0);
		
		double p = engine.calcProbabilityforDic(megaDic, builder.buildMap("исполнителей исполнители").getStems());
		assertEquals(true, p<=1.0);
		System.out.println(p);
		
		assertEquals(1.00, p, 0.01);
	}
	
	@Test
	public void test3() {
		megaDic.add("образ", 0.5);
		
		double p = engine.calcProbabilityforDic(megaDic, builder.buildMap("образом образ образы").getStems());
		assertEquals(true, p<=1.0);
		System.out.println(p);
		
		assertEquals(0.5, p, 0.01);
	}
	
	@Test
	public void test4() {
		double p = engine.calcProbabilityforDic(megaDic, builder.buildMap("исполнителей исполнители образы").getStems());
		assertEquals(true, p<=1.0);
		System.out.println(p);
		
		assertEquals(0.83, p, 0.01);
	}
	
	@Test
	public void test5_withNonExisted() {
		double p = engine.calcProbabilityforDic(megaDic, builder.buildMap("исполнителей, исполнители образы несуществующееслово").getStems());
		assertEquals(true, p<=1.0);
		System.out.println(p);
		
		assertEquals(0.625, p, 0.001);
	}
}
