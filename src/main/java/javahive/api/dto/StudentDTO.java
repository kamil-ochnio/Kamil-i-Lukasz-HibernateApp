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
	private int id;//NOSONAR
    private String imie;//NOSONAR
    private String nazwisko;//NOSONAR
    private boolean wieczny;//NOSONAR
    private String numerIndeksu;//NOSONAR
}
