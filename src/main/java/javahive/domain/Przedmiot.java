package javahive.domain;

import javax.persistence.Entity;

import javahive.infrastruktura.BaseEntity;
import lombok.Getter;


@Entity
@Getter
public class Przedmiot extends BaseEntity {
    @SuppressWarnings("unused")
    private String nazwa;
}
