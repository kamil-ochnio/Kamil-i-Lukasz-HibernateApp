package javahive.domain;


import javahive.infrastruktura.BaseEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToOne;


@Data
@Entity
@ToString
public class Indeks extends BaseEntity {
    public Indeks(){};
    @OneToOne
    @SuppressWarnings("unused")
    private Student student;
    @SuppressWarnings("unused")
    private String numer;
}
