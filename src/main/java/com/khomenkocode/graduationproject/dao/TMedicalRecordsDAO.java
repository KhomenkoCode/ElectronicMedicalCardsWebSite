package com.khomenkocode.graduationproject.dao;
// Generated 26.04.2018 1:06:35 by Hibernate Tools 5.1.4.Final

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.khomenkocode.graduationproject.entities.TDoctors;
import com.khomenkocode.graduationproject.entities.TMedicalRecords;
import com.khomenkocode.graduationproject.entities.TPatients;

/**
 * Home object for domain model class TmedicalRecords.
 * @see com.khomenkocode.graduationproject.dao.TMedicalRecords
 * @author Hibernate Tools
 */
@Stateless
@Repository
@Transactional 
public class TMedicalRecordsDAO {

	private static final Log log = LogFactory.getLog(TMedicalRecordsDAO.class);

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	SessionFactory sessionFactory;

	public void persist(TMedicalRecords transientInstance) {
		log.debug("persisting TmedicalRecords instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TMedicalRecords persistentInstance) {
		log.debug("removing TmedicalRecords instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public TMedicalRecords merge(TMedicalRecords detachedInstance) {
		log.debug("merging TmedicalRecords instance");
		try {
			TMedicalRecords result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TMedicalRecords findById(int id) {
		log.debug("getting TmedicalRecords instance with id: " + id);
		try {
			TMedicalRecords instance = entityManager.find(TMedicalRecords.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public List<TMedicalRecords> findAllByPatient(TPatients patient) {
		log.debug("getting all TmedicalRecords instance with patient id: " + patient.getPatientId());
		try {
			Session session = sessionFactory.getCurrentSession();// rec join fetch rec.tdoctors
			String query = "from TMedicalRecords where patient_id='" + patient.getPatientId()+"'";
			Query result = session.createQuery(query);
			List<TMedicalRecords> list = result.list();		
			log.debug("get successful");
			return list;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	

	public List<TMedicalRecords> findAllByDoctor(TDoctors doctor) {
		log.debug("getting all TmedicalRecords instance with doctor id: " + doctor.getDoctorId());
		try {
			Session session = sessionFactory.getCurrentSession();
			String query = "from TMedicalRecords where doctor_id='" + doctor.getDoctorId()+"'";
			Query result = session.createQuery(query);
			List<TMedicalRecords> list = result.list();		
			log.debug("get successful");
			return list;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
