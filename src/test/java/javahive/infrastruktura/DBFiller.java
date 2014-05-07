package javahive.infrastruktura;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.fixy.Fixy;
import com.fixy.JpaFixyBuilder;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;


@Component
public class DBFiller implements ApplicationContextAware{

	@Inject
    private Finder finder;

    @Inject
    private EntityManagerFactory entityManagerFactory;

	@PostConstruct
	public void fill(){
        EntityManager entityManager=entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		Fixy fixtures = new JpaFixyBuilder(entityManager).withDefaultPackage("h2_TestData").useFieldAccess().build();
		fixtures.load("h2_TestData/Studenci.yaml");
		fixtures.load("h2_TestData/Przedmioty.yaml");
		fixtures.load("h2_TestData/Oceny.yaml");
        fixtures.load("h2_TestData/Indeksy.yaml");
		transaction.commit();
		entityManager.close();
	}



	private ApplicationContext applicationContext;
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
	}
}
