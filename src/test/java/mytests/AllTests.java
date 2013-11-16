package mytests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	LexerTest.class,
	NormalizerTest.class,
	DictionaryBuilderTest.class,
	ThematicDicTest.class,
	СalcProbabilityforDicTest.class
})
public class AllTests {

}
