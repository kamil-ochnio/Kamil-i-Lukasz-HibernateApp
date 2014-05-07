package javahive.api;

import javahive.api.dto.StudentDTO;
import javahive.domain.Student;
import javahive.infrastruktura.Finder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
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
    public List<StudentDTO> getListaWszystkichStudentow(){
        List studentciDTO=new ArrayList<StudentDTO>();
        for(Student student: finder.findAll(Student.class)){
            StudentDTO studentDTO=new StudentDTO();
            studentDTO.setImie(student.getImie());
            studentDTO.setNazwisko(student.getNazwisko());
            studentDTO.setWieczny(student.isWieczny());
            if(student.getIndeks()!=null){
                studentDTO.setNumerIndeksu(student.getIndeks().getNumer());
            }
            studentciDTO.add(studentDTO);
        }
        return studentciDTO;
    }


    public int getLiczbaStudentow(){
        return finder.findAll(Student.class).size();
    }
}
