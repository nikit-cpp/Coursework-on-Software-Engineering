package engine.thematicdictionary.hibernate.DAO;

import java.util.ArrayList;
import engine.thematicdictionary.ThematicDic;

/**
 * Data Access Object для таблицы "Рубрики"
 * @author Ник
 *
 */
public class RubricDAO {
	private ArrayList<ThematicDic> thematicDicts;
	
	public RubricDAO(){
		// TODO Здесь должны загружаться рубрики из БД, превращаться в ThematicDic и записываться в список thematicDicts
		// session.createCriteria(Rubric.class)
	}

	public ArrayList<ThematicDic> getAllDicts() {
		return thematicDicts;
	}

	public void addDic(ThematicDic thematicDic) {
		// TODO Auto-generated method stub
		// session.save(new Rubric(thematicDic.getName()))
		
		updateDictsArrayList();
	}

	public ThematicDic getDic(int i) {
		// TODO Auto-generated method stub
		// TODO учесть то что в БД индекс начинается с 1, а в ArrayList с 0.
		return null;
	}

	public void deleteDic(int dicIndex) {
		// TODO Auto-generated method stub
		// TODO учесть то что в БД индекс начинается с 1, а в ArrayList с 0.
		updateDictsArrayList();
	}
	
	
	/**
	 * Создаёт arrayList из полученных из БД объектов Rubric
	 * @return
	 */
	private void updateDictsArrayList() {
		// TODO Auto-generated method stub
		//thematicDicts = new ArrayList<ThematicDic>();
		//session.createCriteria();
	}


}
