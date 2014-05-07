package javahive.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javahive.api.StudenciApi;
import javahive.infrastruktura.Finder;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
//import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class StudentTest {
    public static final int LICZBA_STUDENTOW_W_YAML = 10;
    public static final String NAZWISKO = "Nowak";
    public static final String FRAGMENT_NAZWISKA = "a";
    public static final int MIN_ID_VAL = 3;
    public static final int LICZBA_STUDENTOW = 11;
    
    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private Finder finder;
    @Inject
    private RepozytoriumStudent repozytoriumStudentImpl;
    @Inject
    private StudenciApi studenciApi;
    
    @Ignore
    @Test
    public void powinienZwrocic10Studentow() {
        //given
        	List<Student> listaStudentow = finder.findAll(Student.class);
        //when
        	int liczbaStudentow = listaStudentow.size();
        //then
        	assertThat(liczbaStudentow, is(LICZBA_STUDENTOW_W_YAML));
    }
    
    @Test
    public void powinienDodacStudenta() {
        //given
        	List<Student> listaStudentow = finder.findAll(Student.class);
        //when
	        int liczbaStudentow = listaStudentow.size();
	        Student s = new Student();
	        s.setImie("Jan");
	        s.setNazwisko("Kwas");
	        s.setWieczny(true);
	        entityManager.persist(s);
        //then
	        assertThat(liczbaStudentow, is(LICZBA_STUDENTOW_W_YAML + 1));
    }

    @Test
    public void sprawdzLiczbeOcen() {
        //given
        	List<Ocena> oc = finder.findAll(Ocena.class);
        //when
        	int rozmiarListyOcen = oc.size();
        //then
        	assertThat(rozmiarListyOcen, Matchers.greaterThan(0));
    }
    
    //Testy porównujące JPQL/HQL/CRITERIA
    @Test
    public void sprawdzLiczStudPoNazwiskuJPQLvsHQL(){
    	//given
	    	List<Student> listaStudentowHQL  = repozytoriumStudentImpl.getStudenciPoNazwisku_HQL(NAZWISKO);
	    	List<Student> listaStudentowJPQL = repozytoriumStudentImpl.getStudenciPoNazwisku_JPQL(NAZWISKO);
	   	//when
	    	int iloscStudHQL = listaStudentowHQL.size();
	    	int iloscStudJPQL= listaStudentowJPQL.size();
    	//then
	    	assertThat(iloscStudHQL, Matchers.is(iloscStudJPQL));
    }
    
    //ZADANIE - wypełnić
    @Ignore
    @Test
    public void sprawdzLiczbeStudentowPoNazwiskuJPQLvsCRITERIA(){
    	//given
    	//when
    	//then
    	
    }   
    @Ignore
    @Test
    public void sprawdzLiczbeStudentowPoNazwiskuCRITERIAvsHQL(){
    	//given
    	//when    		
    	//then    	
    }
    
    //Testy na użycie filtrów Hibernate 
     
    @Test
    public void sprawdzLiczbeStudentowZWiekszymIDNizZadane(){
    	//given
    		List<Student> listaStudZIDPowyzejMin =
    				repozytoriumStudentImpl.getStudenciZIDWiekszymNizDolnaWartosc(MIN_ID_VAL);
    	//when
    		int ile = listaStudZIDPowyzejMin.size();
    		
    	//then
    		System.out.println("ok:"+listaStudZIDPowyzejMin.size());
    		assertThat(ile, Matchers.is(8));		
    }
    
    @Ignore
    @Test
    public void powinienZwrocicStudentowZNazwiskiemZawierajcymFragment(){
    	//given
    		List<Student> listaStudZFiltremNaNazwisko =
    				repozytoriumStudentImpl.getStudenciZFiltorwanymNazwiskiem(FRAGMENT_NAZWISKA);
    		List<Student> listaStudentowJPQLZFragmentemNazwiska  = 
    				repozytoriumStudentImpl.getStudenciJPQLPoFragmencieNazwiska(FRAGMENT_NAZWISKA);
    		
    	//when
    		int iloscStudOdfiltrowanych = listaStudZFiltremNaNazwisko.size();
    		int iloscStudZFragmNazwiskaJPQL = listaStudentowJPQLZFragmentemNazwiska.size();
    		
    	//then		    		
    		assertThat(iloscStudOdfiltrowanych, Matchers.is(iloscStudZFragmNazwiskaJPQL));
    }
    
    // Test na użycie INTERFACEu projekcji z pakietu criteria
    @Test
    public void powinienProjekcjaZliczycStudentow(){
    	//given
    	List<Student> studenci = repozytoriumStudentImpl.getProjekcjaStudentowPoImieNazwisko();
    	//when
    	int liczbaStudentow = studenci.size();
    	//then     
    	assertThat(liczbaStudentow, Matchers.is(LICZBA_STUDENTOW));
    }
    
    @Ignore
    @Test
    public void powinienZwrocicProjekcjaStudentowRosnacoPoNumerzeIndeksu(){
    	//given
    	
    	//when
    	//then    	
    }
    
    @Ignore //skopany test, trzeba dobrze powiazać oceny
    @Test
    public void sprawdzOceny() {
        Student s = finder.findAll(Student.class).get(0);
        System.out.println("***" + s.getImie());
        assertThat(s.getOceny().size(), is(0));
        System.out.println(s.getOceny());
    }
   
    @Test
    public void powinienZwrocicWieleIndeksow() {
        List<Indeks> indeksy = finder.findAll(Indeks.class);
        System.out.println(indeksy.size());
    }


}
