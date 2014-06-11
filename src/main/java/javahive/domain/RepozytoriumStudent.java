package javahive.domain;

import java.util.List;

import javahive.api.dto.StudentDTO;

public interface RepozytoriumStudent {
    List<Student> getStudenciPoNazwiskuHQL(String nazwisko);
    List<Student> getStudenciPoNazwiskuJPQL(String nazwisko);
    List<Student> getStudenciPoNazwiskuCRITERIA(String nazwisko);
    Student getStudentPoId(int id);
    
    // Filtry - z Hibernate
    List<Student> getStudenciZFiltorwanymNazwiskiem(String fragmentNazwiska);
    List<Student> getStudenciJPQLPoFragmencieNazwiska(String fragmentNazwiska);
    List<Student> getStudenciZIDWiekszymNizDolnaWartosc(int minID);

    // Projekcje
    List<Student> getProjekcjaStudentowPoImieNazwisko();
    List<Student> getStudenciPoNazwiskuZaczynajacymSieOdLiter(String nazwisko);

    //Edycja studentów
    void usunStudentaOZadanymId(int id);
    void dodajStudenta(StudentDTO student, String nrIndeksu);
    
    //Oceny i przedmioty
    void dodajOcene(int studentId, String nazwaPrzedmiotu, String ocena);
    Przedmiot getPrzedmiotPoNazwie(String nazwa);
}
