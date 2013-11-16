package runtime.dictionary;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Информация о слове, или value в паре key:value.
 * {@code String s} - ссылка на key.
 * @author Ник
 * TODO Будет заменен таблицами sqlite.
 */
public class WordInfo{
	private final String s;
	private int count;
	private HashSet<String> related;
	private int num; // Порядковый номер слова
	
	public WordInfo(String s) {
		this.s=s;
		this.num=0;
		count=1;
		related = new HashSet<String>();
	}
	
	public WordInfo(String s, int num) {
		this.s=s;
		this.num=num;
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
		if(string==null) return;
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

	public String getRelated() {
		if(related.size()==0) return "";
		//if(related.size()==1 && related.iterator().next() == null) return "";
		
		StringBuilder sb = new StringBuilder();
		
		Iterator<String> it = related.iterator();
		for (int i=0; it.hasNext(); i++) {
			String s = it.next();
			if(i>0)
				sb.append(", ");
			sb.append(s);
		}
				
		return sb.toString();
	}
	
	public int getNum(){
		return num;
	}
}
