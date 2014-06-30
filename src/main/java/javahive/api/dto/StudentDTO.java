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
    @SuppressWarnings(StudentDTO.nieuzywana)
    private int id;
    @SuppressWarnings(StudentDTO.nieuzywana)
    private String imie;
    @SuppressWarnings(StudentDTO.nieuzywana)
    private String nazwisko;
    @SuppressWarnings(StudentDTO.nieuzywana)
    private boolean wieczny;
    @SuppressWarnings(StudentDTO.nieuzywana)
    private String numerIndeksu;
}
