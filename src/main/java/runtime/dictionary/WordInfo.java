package runtime.dictionary;

import java.util.HashSet;

/**
 * Информация о слове, или value в паре key:value.
 * {@code String s} - ссылка на key.
 * @author Ник
 *
 */
public class WordInfo{
	private final String s;
	private int count;
	private HashSet<String> related;
	
	public WordInfo(String s) {
		this.s=s;
		count=1;
		related = new HashSet<String>();
	}
	
	public String getString(){
		return s;
	}

	public void incrementCount() {
		count++;
	}
	
	public void addRelated(String string){
		related.add(string);
	}
	
	public void print() {
		System.out.println("" + s + ", " + count + ", related=" + related);
	}

	@Override
	public String toString() {
		return "WordInfo [s=" + s + ", count=" + count + "]";
	}

	public void setCount(int i) {
		count=i;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((s == null) ? 0 : s.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WordInfo other = (WordInfo) obj;
		if (s == null) {
			if (other.s != null)
				return false;
		} else if (!s.equals(other.s))
			return false;
		return true;
	}

	public int getCount() {
		return count;
	}
}
