package engine.hibernate.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(	name = "рубрики",
		uniqueConstraints= @UniqueConstraint(
			name="unique_rubric", 
			columnNames={"название_рубрики"} 
	) 
)
public class Rubric  implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_рубрики")
	public long getRubricId() { return rubricId; }
	void setRubricId(long rubricId) { this.rubricId = rubricId; }
	private long rubricId;
	
	@Column(name = "название_рубрики")
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	private String name;
	
	// Одна рубрика, много вероятностей, ссылающихся на эту рубрику
    @OneToMany( targetEntity = entities.Probability.class ) // Целевой класс, который будет помещён во множество, возвращаемое геттером
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
	
	
}
