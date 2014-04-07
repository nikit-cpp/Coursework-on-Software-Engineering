package entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "таблица_вероятностей", uniqueConstraints= @UniqueConstraint(
		name="unique_word_rubric", 
		columnNames={"id_слова", "id_рубрики"} ))
//@SecondaryTable(name="слова")
public class Probability  implements Serializable{
	private static final long serialVersionUID = 1L;

	public Probability(double d, Rubric rubric1, Word word1) {
		this.probability = d;
		this.rubric = rubric1;
		this.word = word1;
	}
	
	public Probability() {	}
	
	@Id
	@Column(name = "id_вероятности")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long getProbabilityId() { return probabilityId; }
	void setProbabilityId(long probabilityId) { this.probabilityId = probabilityId; }
	private long probabilityId;

	
	
	// 1-я часть составного первичного ключа
	// Много вероятностей, указывающих на одно слово
    @ManyToOne( targetEntity = entities.Word.class/*, cascade={CascadeType.REMOVE}*/) // Целевой класс, который будет помещён во множество, возвращаемое геттером
    // @OnDelete(action=OnDeleteAction.CASCADE) // OnDelete - на уровне БД, cascade delete constraint, cascade={CascadeType.REMOVE - на уровне ORM, с инициализацией (загрузкой из БД) всех удаляемых объектов
    @JoinColumn(name="id_слова") // [внешний ключ]
    public Word getWord() { return word; }
	public void setWord(Word word) { this.word = word; }
	private Word word;
	
	//@Id // 2-я часть составного первичного ключа
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
	
	/*@Column(name = "слово", table = "слова")
	public String getWord(){return this.name;}
	public void setWord(String name){*/
	
}
