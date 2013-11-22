package thematicdictionary;

import java.util.ArrayList;

public class ThematicDicManager {
	ArrayList<ThematicDic> thematicDicts;
	
	public ThematicDicManager(){
		thematicDicts = new ArrayList<ThematicDic>();
		
		// TODO заглушечное заполнение
		thematicDicts.add(new ThematicDic("физика", true));
		thematicDicts.add(new ThematicDic("алгебра", false));
		thematicDicts.add(new ThematicDic("геометрия", true));
		
		ThematicDic informatica = new ThematicDic("информатика", true);
		informatica.add("риск", 1);
		informatica.add("XP", 1);
		thematicDicts.add(informatica);
	}

	public void turn(boolean b, int index) {
		thematicDicts.get(index).setEnabled(b);
	}
	
	public ArrayList<ThematicDic> getThematicDicts() {
		return thematicDicts;
	}

}
