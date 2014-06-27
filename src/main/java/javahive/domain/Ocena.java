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
    @SuppressWarnings("unused")
    private String wysokosc;
    @ManyToOne(optional = true)
    @SuppressWarnings("unused")
    private Student student;
    @ManyToOne(optional = true)
    @SuppressWarnings("unused")
    private Przedmiot przedmiot;
}
