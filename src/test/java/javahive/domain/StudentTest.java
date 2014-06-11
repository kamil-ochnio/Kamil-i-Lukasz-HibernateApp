package javahive.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import javahive.api.StudenciApi;
import javahive.api.dto.StudentDTO;
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
    public static final int LICZBA_STUDENTOW_W_YAML = 11;
    public static final String NAZWISKO = "Nowak";
    public static final String FRAGMENT_NAZWISKA = "a";
    public static final int MIN_ID_VAL = 3;
    public static final int LICZBA_PRZEDMIOTOW = 5;
    
    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private Finder finder;
    @Inject
    private RepozytoriumStudent repozytoriumStudentImpl;
    @Inject
    private StudenciApi studenciApi;
    
    
    @Test
    public void powinienZwrocic11Studentow() {
        //given
        	List<Student> listaStudentow = finder.findAll(Student.class);
        //when
        	int liczbaStudentow = listaStudentow.size();
        //then
        	assertThat(liczbaStudentow, is(LICZBA_STUDENTOW_W_YAML));
    }
    
    
    @Test
    public void powinienZwrocicListePrzedmiotow() {
        //given
        	List<Przedmiot> listaPrzedmiotow = finder.findAll(Przedmiot.class);
        //when
        	int liczbaPrzedmiotow = listaPrzedmiotow.size();
        //then
        	assertThat(liczbaPrzedmiotow, is(LICZBA_PRZEDMIOTOW));
    }
    
    @Test
    public void powinienDodacStudenta() {
        //given
            Student s = new Student();
            s.setImie("Jan");
            s.setNazwisko("Kwas");
            s.setWieczny(true);
        //when
	        entityManager.persist(s);
	        List<Student> listaStudentow = finder.findAll(Student.class);
	        int liczbaStudentow = listaStudentow.size();
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
	    	List<Student> listaStudentowHQL  = repozytoriumStudentImpl.getStudenciPoNazwiskuHQL(NAZWISKO);
	    	List<Student> listaStudentowJPQL = repozytoriumStudentImpl.getStudenciPoNazwiskuJPQL(NAZWISKO);
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
    	assertThat(liczbaStudentow, Matchers.is(LICZBA_STUDENTOW_W_YAML));
    }
    
    @Test
    public void powinienZwrocicWieleIndeksow() {
        List<Indeks> indeksy = finder.findAll(Indeks.class);
        System.out.println(indeksy.size());
    }
    
    @Test
    public void powinienDodacStudentaZIndeksem(){
        //given
        String nrIndeksu = "12322121";
        StudentDTO student = new StudentDTO();
        student.setImie("Łukasz");
        student.setNazwisko("Winiarczyk");
        student.setWieczny(true);
        int iloscStudentowPrzedDodaniem = finder.findAll(Student.class).size();
        //when
        repozytoriumStudentImpl.dodajStudenta(student, nrIndeksu);
        //then
        int iloscStudentowPoDodaniu = finder.findAll(Student.class).size();
        assertThat(iloscStudentowPoDodaniu, is(iloscStudentowPrzedDodaniem+1));
    }
    
    @Test (expected = Exception.class)
    public void powinienZwrocicWyjatekPrzyDodaniuDuplikatuIndeksu() throws Exception{
        //given
        String nrIndeksu = "1234563"; //indeks juz istnieje w yaml
        StudentDTO student = new StudentDTO();
        student.setImie("Łukasz");
        student.setNazwisko("Winiarczyk");
        student.setWieczny(true);
        //when
        studenciApi.dodajStudenta(student, nrIndeksu);
        //then
        fail();
    }
    
    @Test 
    public void powinienUsunacStudentaOZadanymId() {
        //given
        int id=1;
        int iloscStudentowPrzedUsunieciem = finder.findAll(Student.class).size();
        //when
        repozytoriumStudentImpl.usunStudentaOZadanymId(id);
        int iloscStudentowPoUsunieciu = finder.findAll(Student.class).size();
        //then
        assertThat(iloscStudentowPoUsunieciu, is(iloscStudentowPrzedUsunieciem-1));
    }
    
    @Test(expected = Exception.class)
    public void powinienZwrocicWyjatekPobujacUsunacStudentaONieistniejacymId() throws Exception{
        //given
        int id=1000;
        //when
        studenciApi.usunStudentaOZadanymId(id);
        //then
        fail();
    }
    
    @Test
    public void powinienDodacOceneStudentowi(){
        //given
        String ocena = "3";
        String nazwaPrzedmiotu = "Informatyka";
        int idStudenta = 1;
        int iloscOcenStudenta = repozytoriumStudentImpl.getStudentPoId(idStudenta).getOceny().size();
        //when
        repozytoriumStudentImpl.dodajOcene(idStudenta, nazwaPrzedmiotu, ocena);
        int nowaIloscOcenStudenta = repozytoriumStudentImpl.getStudentPoId(idStudenta).getOceny().size();
        //then
        assertThat(iloscOcenStudenta+1, is(nowaIloscOcenStudenta));
    }
    
    @Test
    public void powinienDobrzeZliczycOcenyStudentaOZadanymId(){
        //given
        int idStudenta = 1;
        int iloscOcenStudentaWBazie = 1;
        //when
        int iloscOcenStudentaPobrana = studenciApi.getOcenyStudentaOId(idStudenta).size();
        //then
        assertThat(iloscOcenStudentaPobrana, is(iloscOcenStudentaWBazie));
        
    }

}
