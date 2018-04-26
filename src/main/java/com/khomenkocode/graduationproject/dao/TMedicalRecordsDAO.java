package com.khomenkocode.graduationproject.dao;
// Generated 26.04.2018 1:06:35 by Hibernate Tools 5.1.4.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.khomenkocode.graduationproject.entities.TMedicalRecords;

/**
 * Home object for domain model class TmedicalRecords.
 * @see com.khomenkocode.graduationproject.dao.TMedicalRecords
 * @author Hibernate Tools
 */
@Stateless
public class TMedicalRecordsDAO {

	private static final Log log = LogFactory.getLog(TMedicalRecordsDAO.class);

	@PersistenceContext
	private EntityManager entityManager;

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
}
