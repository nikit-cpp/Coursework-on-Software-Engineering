package thematic.dictionary;

import java.util.ArrayList;

public class ThematicDicManager {
	ArrayList<ThematicDic> thematicDicts;
	
	public ThematicDicManager(){
		thematicDicts = new ArrayList<ThematicDic>();
		
		// TODO заглушечное заполнение
		thematicDicts.add(new ThematicDic("физика", true));
		thematicDicts.add(new ThematicDic("алгебра", false));
		thematicDicts.add(new ThematicDic("геометрия", true));
		thematicDicts.add(new ThematicDic("информатика", true));
	}

	public void turn(boolean b, int index) {
		thematicDicts.get(index).setEnabled(b);
	}

	public ArrayList<ThematicDic> getThematicDicts() {
		return thematicDicts;
	}

}
