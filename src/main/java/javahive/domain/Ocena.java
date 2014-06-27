package javahive.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import javahive.infrastruktura.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Ocena extends BaseEntity {
	public Ocena(){};
	private String wysokosc;//NOSONAR
	@ManyToOne(optional = true)
	private Student student;//NOSONAR
	@ManyToOne(optional = true)
	private Przedmiot przedmiot;//NOSONAR
}
