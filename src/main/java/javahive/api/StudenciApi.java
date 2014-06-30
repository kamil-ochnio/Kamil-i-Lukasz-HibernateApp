package javahive.api;

import javahive.api.dto.StudentDTO;
import javahive.domain.Indeks;
import javahive.domain.Ocena;
import javahive.domain.Przedmiot;
import javahive.domain.RepozytoriumStudent;
import javahive.domain.Student;
import javahive.infrastruktura.Finder;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.validation.UnexpectedTypeException;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by m on 29.04.14.
 */

@Component
@Transactional(propagation = Propagation.REQUIRED)
public class StudenciApi {
    @Inject
    private Finder finder;
    @Inject
    private RepozytoriumStudent repozytoriumStudent;
    public List<StudentDTO> getListaWszystkichStudentow(){
        List <StudentDTO> studentciDTO=new ArrayList<StudentDTO>();
        for(Student student: finder.findAll(Student.class)){
            StudentDTO studentDTO=new StudentDTO();
            studentDTO.setId(student.getId());
            studentDTO.setImie(student.getImie());
            studentDTO.setNazwisko(student.getNazwisko());
            studentDTO.setWieczny(student.isWieczny());
            if(student.getIndeks()!=null){
                studentDTO.setNumerIndeksu(student.getIndeks().getNumer());
            }else{
                studentDTO.setNumerIndeksu("brak");
            }
            studentciDTO.add(studentDTO);
        }
        return studentciDTO;
    }


    public int getLiczbaStudentow(){
        return finder.findAll(Student.class).size();
    }
    
    public List<Przedmiot> getPrzemioty(){
        return finder.findAll(Przedmiot.class);
    }
    

    
    
    public void usunStudentaOZadanymId(int id)throws UnexpectedTypeException{
        List <Student> studenty = finder.findAll(Student.class);
        boolean zawieraSie = false;
        for(Student student : studenty){
            if(student.getId()==id){
                zawieraSie = true;
            }
        }
        if(zawieraSie){
            repozytoriumStudent.usunStudentaOZadanymId(id);
        }else{
            throw new UnexpectedTypeException("Student o zadanym id nie figuruje w bazie danych");
        }
        
    }
    
    public void dodajStudenta(StudentDTO student, String numerIndeksu) throws UnexpectedTypeException{
        List <Indeks> indeksy = finder.findAll(Indeks.class);
        for(Indeks indeks : indeksy){
            if (indeks.getNumer().equals(numerIndeksu)){
                throw new UnexpectedTypeException("Ten nr. indeksu jest juz zajety");
            }
        }
        repozytoriumStudent.dodajStudenta(student, numerIndeksu);
    }
    
    public List <Ocena> getOcenyStudentaOId(int id){
        return repozytoriumStudent.getOcenyStudentaOId(id);
    }
    
    public void dodajOceneStudentowi(int studentId, String nazwaPrzedmiotu, String ocena){
        repozytoriumStudent.dodajOcene(studentId, nazwaPrzedmiotu, ocena);
    }
}
