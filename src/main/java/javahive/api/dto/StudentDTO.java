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
    private static final String nieuzywana="unused";
    @SuppressWarnings(nieuzywana)
    private int id;
    @SuppressWarnings(nieuzywana)
    private String imie;
    @SuppressWarnings(nieuzywana)
    private String nazwisko;
    @SuppressWarnings(nieuzywana)
    private boolean wieczny;
    @SuppressWarnings(nieuzywana)
    private String numerIndeksu;
}
