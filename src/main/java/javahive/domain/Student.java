package javahive.domain;
import java.util.List;
import javahive.infrastruktura.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
import com.google.common.collect.Lists;
@Getter
@Setter
@Entity

//Definicja ustala na jakich atrybutach/polach encji filtr ma działać
@FilterDefs({@FilterDef(name="FILTER_TEST_STUDENT_NAZWISKO",
    parameters=@ParamDef( name="PARAM_student_Nazwisko", type="String" ) ),
    @FilterDef(name="FILTER_TEST_STUDENT_ID",
    parameters=@ParamDef( name="PARAM_student_ID", type="int" ) )
    })
    
// Fitlry określają jaki ma zostać spełniony warunek na zadanych w definicji parametrach
@Filters({
    @Filter(name = "FILTER_TEST_STUDENT_NAZWISKO", condition = "lower(nazwisko) like :PARAM_student_Nazwisko"),
        @Filter(name = "FILTER_TEST_STUDENT_ID", condition = "id > :PARAM_student_ID")
})


public class Student extends BaseEntity {
private static final String nieuzywana="unused";
    public Student(){};
        @SuppressWarnings(nieuzywana)
        private String imie;
        @SuppressWarnings(nieuzywana)
        private String nazwisko;
        @SuppressWarnings(nieuzywana)
        private boolean wieczny;
        //@OneToMany(mappedBy="student",fetch= FetchType.EAGER)
    	
    @OneToMany(mappedBy="student")
    @Cascade(CascadeType.ALL)
    @SuppressWarnings(nieuzywana)
    private List<Ocena> oceny=Lists.newArrayList();
    @OneToOne
    @Cascade(CascadeType.ALL)
    @SuppressWarnings(nieuzywana)
    private Indeks indeks;
}
        