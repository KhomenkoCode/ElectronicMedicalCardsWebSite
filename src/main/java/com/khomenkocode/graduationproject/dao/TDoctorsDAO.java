package com.khomenkocode.graduationproject.dao;
// Generated 26.04.2018 1:06:35 by Hibernate Tools 5.1.4.Final

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.khomenkocode.graduationproject.entities.TDoctors;
import com.khomenkocode.graduationproject.entities.TMedicalRecords;
import com.khomenkocode.graduationproject.entities.TPatients;

/**
 * Home object for domain model class Tdoctors.
 * @see com.khomenkocode.graduationproject.dao.TDoctors
 * @author Hibernate Tools
 */
@Stateless
@Repository
@Transactional 
public class TDoctorsDAO {

	private static final Log log = LogFactory.getLog(TDoctorsDAO.class);

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private SessionFactory sessionFactory;

	public void persist(TDoctors transientInstance) {
		log.debug("persisting Tdoctors instance");
		try {
			Session session = sessionFactory.getCurrentSession();// rec join fetch rec.tdoctors
			String query = "from TDoctors where license_number='"+ transientInstance.getLicenseNumber()+"'";
			Query result = session.createQuery(query);
			List<TDoctors> list = result.list();
			
			if(!list.isEmpty()){
				throw new RuntimeException("license num exists");
			}
			
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TDoctors persistentInstance) {
		log.debug("removing Tdoctors instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public TDoctors merge(TDoctors detachedInstance) {
		log.debug("merging Tdoctors instance");
		try {
			TDoctors result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TDoctors findById(int id) {
		log.debug("getting Tdoctors instance with id: " + id);
		try {
			TDoctors instance = entityManager.find(TDoctors.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public TDoctors findDoctorByParams(TDoctors doctor) {
		log.debug("getting all TDoctors instance with license number: "+doctor.getLicenseNumber());
		try {
			Session session = sessionFactory.getCurrentSession();// rec join fetch rec.tdoctors
			String query = "from TMedicalRecords where doctor_surname='" + doctor.getDoctorSurname()+"' AND license_number='"+ doctor.getLicenseNumber()+"'";
			Query result = session.createQuery(query);
			List<TDoctors> list = result.list();
			
			
			
			log.debug("get successful");
			return list.get(0);
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public TDoctors findByLicenseNumberAndPassword(String license, String password) throws NoResultException {
		
		try {
			TDoctors instance = (TDoctors) entityManager.createQuery("FROM TDoctors t where t.licenseNumber = :value1 and t.password = :value2")
					.setParameter("value1", license).setParameter("value2", password).getSingleResult();
			
				
			return instance;
		
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public TDoctors findByLicenseNumber(String license) throws NoResultException {
		
		try {
			TDoctors instance = (TDoctors) entityManager.createQuery("FROM TDoctors t where t.licenseNumber = :value1")
					.setParameter("value1", license).getSingleResult();
			
				
			return instance;
		
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	

	public List<TDoctors> findAllUnconfirmed() throws NoResultException {
		
		try {
			List<TDoctors> instance = (List<TDoctors>) entityManager.createQuery("FROM TDoctors t where t.doctorIsConfirmed = :value1")
					.setParameter("value1", (short) 1).getResultList();
			
			return instance;
		
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
