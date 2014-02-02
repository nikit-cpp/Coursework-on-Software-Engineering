package engine.hibernate.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "таблица_вероятностей")
public class Probability  implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id // 1-я часть составного первичного ключа
	// Много вероятностей, указывающих на одно слово
    @ManyToOne( targetEntity = entities.Word.class ) // Целевой класс, который будет помещён во множество, возвращаемое геттером
    @JoinColumn(name="id_слова") // [внешний ключ]
    public Word getWord() { return word; }
	public void setWord(Word word) { this.word = word; }
	private Word word;
	
	@Id // 2-я часть составного первичного ключа
	// Много вероятностей, указывающих на одну рубрику
    @ManyToOne( targetEntity = entities.Rubric.class ) // Целевой класс, который будет помещён во множество, возвращаемое геттером
    @JoinColumn(name="id_рубрики") // [внешний ключ]
    public Rubric getRubric() { return rubric; }
	void setRubric(Rubric rubric) { this.rubric = rubric; }
	private Rubric rubric;
	
	@Column(name = "вероятность")
	public Double getProbability() { return probability; }
	public void setProbability(Double probability) { this.probability = probability; }
	private double probability;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(probability);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((rubric == null) ? 0 : rubric.hashCode());
		result = prime * result + ((word == null) ? 0 : word.hashCode());
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
		Probability other = (Probability) obj;
		if (Double.doubleToLongBits(probability) != Double
				.doubleToLongBits(other.probability))
			return false;
		if (rubric == null) {
			if (other.rubric != null)
				return false;
		} else if (!rubric.equals(other.rubric))
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}

	
}
