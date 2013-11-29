package thematicdictionary;

import java.util.ArrayList;

public class ThematicDicManager {
	private ArrayList<ThematicDic> thematicDicts;
	
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

	public ThematicDicManager(String path) {
		thematicDicts = new ArrayList<ThematicDic>();
	}

	public void turn(boolean b, int index) {
		thematicDicts.get(index).setEnabled(b);
	}
	
	public ArrayList<ThematicDic> getThematicDicts() {
		return thematicDicts;
	}
	
	public void add(String dicname, boolean isEnabled){
		thematicDicts.add(new ThematicDic(dicname, isEnabled));
	}

	public void add(ThematicDic dic) {
		thematicDicts.add(dic);
	}

	public ThematicDic get(int i) {
		return thematicDicts.get(i);
	}

}
