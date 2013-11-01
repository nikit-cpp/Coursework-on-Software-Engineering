import java.util.Collection;

/**
 * Интерфейс Наблюдателя
 * @author Ник
 *
 */
public interface IModel {
	void setText(Itext text);
	Collection RootsAndCounts(IRoot root);
	void addToDic(IRoot root, IThematicDic dict);
	void delFromDic(IRoot root, IThematicDic dict);
	boolean find(Collection<IRoot> roots, Collection<IThematicDic> dicts);
	double calculateSimilarity(IThematicDic dict);
}
