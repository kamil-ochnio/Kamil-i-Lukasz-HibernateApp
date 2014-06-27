package javahive.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by m on 29.04.14.
 */

@Getter
@Setter
@ToString

public class StudentDTO implements Serializable{
    @SuppressWarnings("unused")
    private int id;
    @SuppressWarnings("unused")
    private String imie;
    @SuppressWarnings("unused")
    private String nazwisko;
    @SuppressWarnings("unused")
    private boolean wieczny;
    @SuppressWarnings("unused")
    private String numerIndeksu;
}
