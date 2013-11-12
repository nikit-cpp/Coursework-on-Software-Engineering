package mytests;

import static org.junit.Assert.*;
import main.Engine;

import org.junit.BeforeClass;
import org.junit.*;

import runtime.dictionary.DictionaryBuilder;
import thematic.dictionary.*;

public class СalcProbabilityforDicTest {
	static Engine engine;
	static ThematicDic megaDic;
	static DictionaryBuilder builder;
	
	@BeforeClass
	public static void setUp(){
		engine = new Engine();
		builder = new DictionaryBuilder();
		
		megaDic = new ThematicDic("megaDic", true);
		
		megaDic.add("начинать", 1.0);
		megaDic.add("нас", 1.0);
		megaDic.add("спасать", 1.0);
		megaDic.add("от", 1.0);
		//megaDic.add("недобросовестный", 0.3);
		//megaDic.add("исполнитель", 0.3);
	}
	
	@Test
	public void test1() {
		double p = engine.calcProbabilityforDic(megaDic, builder.buildSentence("начинает нас спасать от недобросовестных исполнителей").getStems());
		assertEquals(true, p<=1.0);
		System.out.println(p);
		
		assertEquals(0.66, p, 0.01);
	}

	@Test
	public void test2() {
		megaDic.add("исполнитель", 1.0);
		
		double p = engine.calcProbabilityforDic(megaDic, builder.buildSentence("исполнителей исполнители").getStems());
		assertEquals(true, p<=1.0);
		System.out.println(p);
		
		assertEquals(1.00, p, 0.01);
	}
	
	@Test
	public void test3() {
		megaDic.add("образ", 0.5);
		
		double p = engine.calcProbabilityforDic(megaDic, builder.buildSentence("образом образ образы").getStems());
		assertEquals(true, p<=1.0);
		System.out.println(p);
		
		assertEquals(0.5, p, 0.01);
	}
	
	@Test
	public void test4() {
		double p = engine.calcProbabilityforDic(megaDic, builder.buildSentence("исполнителей исполнители образы").getStems());
		assertEquals(true, p<=1.0);
		System.out.println(p);
		
		assertEquals(0.83, p, 0.01);
	}
	
	@Test
	public void test5_withNonExisted() {
		double p = engine.calcProbabilityforDic(megaDic, builder.buildSentence("исполнителей, исполнители образы несуществующееслово").getStems());
		assertEquals(true, p<=1.0);
		System.out.println(p);
		
		assertEquals(0.625, p, 0.001);
	}
}
