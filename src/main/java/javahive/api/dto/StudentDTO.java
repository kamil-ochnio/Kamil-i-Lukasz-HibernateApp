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
    private String imie;
    private String nazwisko;
    private boolean wieczny;
    private String numerIndeksu;
}
