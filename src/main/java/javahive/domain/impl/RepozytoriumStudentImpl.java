package javahive.domain.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javahive.api.dto.StudentDTO;
import javahive.domain.*;
import javahive.infrastruktura.Finder;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

@Component
public class RepozytoriumStudentImpl implements RepozytoriumStudent {
    private static final String parametr = "nazwisko";
    private static final String nieoczekiwany = "unchecked";
    private static final String QUERY_STUDENT_LASTNAME = "SELECT s FROM Student s "
            + "WHERE LOWER(s.nazwisko) = :nazwisko";
    private static final String QUERY_STUDENT_LIKE_LASTNAME = "FROM Student s "
            + "WHERE LOWER(s.nazwisko) = %:nazwisko%";

    private static final String QUERY_STUDENT = "FROM Student";

    @PersistenceContext
    private EntityManager entityManager;

    private static <T> List<T> castList(Class<? extends T> clazz,
            Collection<?> c) {
        List<T> r = new ArrayList<T>(c.size());
        for (Object o : c){
            r.add(clazz.cast(o));
        }
        return r;
    }
    
    @Inject
    @SuppressWarnings("unused")
    private Finder finder;
    @Override
    public List<Student> getStudenciPoNazwiskuHQL(String nazwisko) {
        Session session = entityManager.unwrap(Session.class);
        org.hibernate.Query query = session.createQuery(QUERY_STUDENT_LASTNAME);
        query.setParameter(parametr, nazwisko.toLowerCase());

        return castList(Student.class, query.list());
    }

    @Override
    public List<Student> getStudenciPoNazwiskuJPQL(String nazwisko) {
        javax.persistence.Query query = entityManager
                .createQuery(QUERY_STUDENT_LASTNAME);
        query.setParameter(parametr, nazwisko.toLowerCase());

        return castList(Student.class, query.getResultList());
    }

    @Override
    public List<Student> getStudenciPoNazwiskuCRITERIA(String nazwisko) {
        Session session = entityManager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Student.class);
        criteria.add(Restrictions.like(parametr, nazwisko.toLowerCase()));
        return castList(Student.class, criteria.list());
    }

    @Override
    public List<Student> getStudenciPoNazwiskuZaczynajacymSieOdLiter(
            String nazwisko) {
        return null;
    }

    @SuppressWarnings(nieoczekiwany)
    @Override
    public List<Student> getStudenciZFiltorwanymNazwiskiem(
            String fragmentNazwiska) {
        Session session = entityManager.unwrap(Session.class);

        Filter filter = session.enableFilter("FILTER_TEST_STUDENT_NAZWISKO");
        filter.setParameter("PARAM_student_Nazwisko",
                fragmentNazwiska.toLowerCase());

        org.hibernate.Query query = session.createQuery(QUERY_STUDENT);

        return query.list();
    }

    @SuppressWarnings(nieoczekiwany)
    public List<Student> getStudenciJPQLPoFragmencieNazwiska(
            String fragmentNazwiska) {
        javax.persistence.Query query = entityManager
                .createQuery(QUERY_STUDENT_LIKE_LASTNAME);
        query.setParameter(parametr, fragmentNazwiska.toLowerCase());
        return query.getResultList();
    }

    @SuppressWarnings(nieoczekiwany)
    @Override
    public List<Student> getStudenciZIDWiekszymNizDolnaWartosc(int minID) {
        Session session = entityManager.unwrap(Session.class);

        Filter filter = session.enableFilter("FILTER_TEST_STUDENT_ID");
        filter.setParameter("PARAM_student_ID", minID);

       

        org.hibernate.Query query = session.createQuery(QUERY_STUDENT);

        return query.list();
    }

    @SuppressWarnings(nieoczekiwany)
    @Override
    public List<Student> getProjekcjaStudentowPoImieNazwisko() {
        Session session = entityManager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Student.class);

        ProjectionList proList = Projections.projectionList();
        proList.add(Projections.property("imie"));
        proList.add(Projections.property("nazwisko"));

        criteria.setProjection(proList);

        return criteria.list();
    }

    @Override
    public void usunStudentaOZadanymId(int id) {
        Student student = getStudentPoId(id);
        entityManager.remove(student);

    }
    
    @Override
    public void dodajStudenta(StudentDTO studentDto, String nrIndeksu) {
        Student student = new Student();
        student.setImie(studentDto.getImie());
        student.setNazwisko(studentDto.getNazwisko());
        student.setWieczny(studentDto.isWieczny());
        if(!"".equals(nrIndeksu)){
            Indeks indeks= new Indeks();
            indeks.setNumer(nrIndeksu);
            student.setIndeks(indeks);
            indeks.setStudent(student);
        }
        entityManager.persist(student);
        
    }

    @Override
    public void dodajOcene(int studentId, String nazwaPrzedmiotu, String ocena) {
        Student student = getStudentPoId(studentId);
        Przedmiot przedmiot = getPrzedmiotPoNazwie(nazwaPrzedmiotu);
        Ocena nowaOcena = new Ocena();
        nowaOcena.setPrzedmiot(przedmiot);
        nowaOcena.setStudent(student);
        nowaOcena.setWysokosc(ocena);
        entityManager.persist(nowaOcena);
        student.getOceny().add(nowaOcena);
        entityManager.merge(student);
    }

    @Override
    public Student getStudentPoId(int id) {
        Session session = entityManager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Student.class);
        criteria.add(Restrictions.like("id", id));
       
        return (Student) criteria.uniqueResult();
    }

    @Override
    public Przedmiot getPrzedmiotPoNazwie(String nazwa) {
        Session session = entityManager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Przedmiot.class);
        criteria.add(Restrictions.like("nazwa", nazwa));
        return (Przedmiot) criteria.uniqueResult();
    }

    @Override
    public List<Ocena> getOcenyStudentaOId(int id) {
        List <Ocena> oceny = getStudentPoId(id).getOceny();
        List <Ocena> ocenyTravel = new ArrayList <Ocena>();
        for(Ocena ocena : oceny){
            Ocena ocenaTemp = new Ocena();
            ocenaTemp.setPrzedmiot(ocena.getPrzedmiot());
            ocenaTemp.setWysokosc(ocena.getWysokosc());
            ocenyTravel.add(ocenaTemp);
        }
        return ocenyTravel;
    }
}
