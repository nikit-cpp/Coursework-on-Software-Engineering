package engine.hibernate.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(	name = "слова",
		uniqueConstraints= @UniqueConstraint(
			name="unique_word", 
			columnNames={"слово"} 
		) 
)
public class Word  implements Serializable{
	public Word(){	}
	public Word(String word){
		this.word=word;
	}
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id_слова")
	public long getWordId() { return wordId; }
	void setWordId(long wordId) { this.wordId = wordId; }
	private long wordId;
	
	@Column(name = "слово")
	public String getWord() { return word; }
	public void setWord(String word) { this.word = word; }
	private String word;
	
	// Одно слово, много вероятностей, ссылающихся на это слово
    @OneToMany( targetEntity = entities.Probability.class ) // Целевой класс, который будет помещён во множество, возвращаемое геттером
    @JoinColumn(name="id_слова")
    public List getProbabilitys() { return probabilitys; }
	public void setProbabilitys(List probabilitys) { this.probabilitys = probabilitys; }
	private List probabilitys = new ArrayList();

	@Override
	public String toString() {
		return "\tWord [wordId=" + wordId + ", word=" + word + "]";
	}
	/**
	 * Генерирует хэшкод только на основе так называемого бизнес-ключа
	 * -- ключа, который действительно имеет значение для пользователя(word),
	 * а не суррогатного wordId
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}
	/**
	 * Сравнивает только на основе так называемого бизнес-ключа
	 * -- ключа, который действительно имеет значение для пользователя(word),
	 * а не суррогатного wordId
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}
}
