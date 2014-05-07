package javahive.domain.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javahive.domain.RepozytoriumStudent;
import javahive.domain.Student;

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
	private static final String QUERY_STUDENT_LASTNAME = "SELECT s FROM Student s " +
															"WHERE LOWER(s.nazwisko) = :nazwisko";
	private static final String QUERY_STUDENT_LIKE_LASTNAME = "FROM Student s " +
															"WHERE LOWER(s.nazwisko) = %:nazwisko%";
	
	private static final String QUERY_STUDENT = "FROM Student";
	
    @PersistenceContext
    private EntityManager entityManager;

		    private static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
		        List<T> r = new ArrayList<T>(c.size());
		        for(Object o: c)
		          r.add(clazz.cast(o));
		        return r;
		    }
    
    @Override
    public List<Student> getStudenciPoNazwisku_HQL(String nazwisko) {
        Session session=entityManager.unwrap(Session.class);
        org.hibernate.Query query = session.createQuery(QUERY_STUDENT_LASTNAME);
        query.setParameter("nazwisko", nazwisko.toLowerCase());
      
        return castList(Student.class, query.list()); //session close?
    }

	@Override
	public List<Student> getStudenciPoNazwisku_JPQL(String nazwisko) {
		 javax.persistence.Query query = entityManager.createQuery(QUERY_STUDENT_LASTNAME);
		 query.setParameter("nazwisko", nazwisko.toLowerCase()); 
		 
		return castList(Student.class, query.getResultList());
	}

	@Override
	public List<Student> getStudenciPoNazwisku_CRITERIA(String nazwisko) {
		Session session=entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Student.class);
		criteria.add(Restrictions.like("nazwisko",nazwisko.toLowerCase()));
		//return session.createCriteria(nu).uniqueResult();
		return castList(Student.class, criteria.list());
	}
	
    @Override
    public List<Student> getStudenciPoNazwiskuZaczynajacymSieOdLiter(String nazwisko) {
        return null;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getStudenciZFiltorwanymNazwiskiem(String fragmentNazwiska) {
		Session session=entityManager.unwrap(Session.class);
		
		Filter filter = session.enableFilter("FILTER_TEST_STUDENT_NAZWISKO");
		filter.setParameter("PARAM_student_Nazwisko",  fragmentNazwiska.toLowerCase());
		
		org.hibernate.Query query = session.createQuery(QUERY_STUDENT);
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Student> getStudenciJPQLPoFragmencieNazwiska(String fragmentNazwiska){
		 javax.persistence.Query query = entityManager.createQuery(QUERY_STUDENT_LIKE_LASTNAME);
		 query.setParameter("nazwisko", fragmentNazwiska.toLowerCase());	
		 return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getStudenciZIDWiekszymNizDolnaWartosc(int minID) {
		Session session=entityManager.unwrap(Session.class);
		
		Filter filter = session.enableFilter("FILTER_TEST_STUDENT_ID");
		filter.setParameter("PARAM_student_ID",  minID);
		
		/*Filter filter = session.enableFilter("studentFilter");
    	filter.setParameter("studentFilterID", minID);*/
		
		org.hibernate.Query query = session.createQuery(QUERY_STUDENT);
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Student> getProjekcjaStudentowPoImieNazwisko() {
		Session session=entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Student.class);
		
		ProjectionList proList = Projections.projectionList();
        proList.add(Projections.property("imie"));
        proList.add(Projections.property("nazwisko"));
        
        criteria.setProjection(proList);
		
		return criteria.list();
	}
}
