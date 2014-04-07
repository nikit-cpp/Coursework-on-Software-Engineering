package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(	name = "рубрики",
		uniqueConstraints= @UniqueConstraint(
			name="unique_rubric", 
			columnNames={"название_рубрики"} 
	) 
)
public class Rubric implements Serializable, Iterable<String[]>{
	public Rubric() { }
	public Rubric(String string) {
		this.name=string;
	}
	
	public Rubric(String dicname, boolean isEnabled) {
		this.name=dicname;
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_рубрики")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long getRubricId() { return rubricId; }
	void setRubricId(long rubricId) { this.rubricId = rubricId; }
	private long rubricId;
	
	@Column(name = "название_рубрики")
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	private String name;
	
	@Transient
	public double getCalculatedProbability() {System.out.println("getCalculatedProbability() "+calculatedProbability); return calculatedProbability;	}
	public void setCalculatedProbability(double calculatedProbability) { System.out.println("setCalculatedProbability() " + calculatedProbability); this.calculatedProbability = calculatedProbability;	}
	private double calculatedProbability;

	
	// Одна рубрика, много вероятностей, ссылающихся на эту рубрику
	// Целевой класс, который будет помещён во множество, возвращаемое геттером,
	// Каскадность - добавляем объект в коллекцию - он автоматически ассоциируется с сессией и добавляется в БД, удаляем - он удаляется из БД
	// удаление сирот - из таблицы вероятностей будут автоматически удалены вер-ти, на которые больше нет ссылок из таблицы рубрик
    @OneToMany( targetEntity = entities.Probability.class, cascade=CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name="id_рубрики")
    public List getProbabilitys() { return probabilitys; }
	public void setProbabilitys(List probabilitys) { this.probabilitys = probabilitys; }
	private List probabilitys = new ArrayList();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Rubric other = (Rubric) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "\tRubric [rubricId=" + rubricId + ", name=" + name
				+ ", probability=" /*+ probability*/ + "]";
	}
	
	public void setDicEnabled(boolean b) {
		// TODO Auto-generated method stub
		
	}
	@Transient
	public boolean getDicEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Transient
	/**
	 * Возвращает массив строк : [Название словаря, Вычисленная вероятность]
	 */
	public String[] getRow() {
		String dicName = getName();
        String probabilitty = String.valueOf(getCalculatedProbability());
        String[] row = {dicName, probabilitty};
		
		return row;
	}
	
	/**
	 * Возвращает итератор по 2-хэлементным массивам[слово:веоянтость] 
	 * - с его помощью получаем все слова, принадлежащие данной рубрике
	 */
	@Override
	public Iterator<String[]> iterator() {
		return new Iterator<String[]>() {
			Iterator<Probability> keyIterator = getProbabilitys().iterator();

			public boolean hasNext() {
				return keyIterator.hasNext();
			}

			public String[] next() {
				Probability p = keyIterator.next();
				String word = p.getWord().getWord();
				String[] row = {
						word,
						String.valueOf((p.getProbability())) 
						};
				return row;
			}

			public void remove() { // He реализован
				throw new UnsupportedOperationException();
			}
		};
	}
}
