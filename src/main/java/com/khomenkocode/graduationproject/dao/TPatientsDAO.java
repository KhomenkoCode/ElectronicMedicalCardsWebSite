package com.khomenkocode.graduationproject.dao;
// Generated 26.04.2018 1:06:35 by Hibernate Tools 5.1.4.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.khomenkocode.graduationproject.entities.TPatients;

/**
 * Home object for domain model class Tpatients.
 * @see com.khomenkocode.graduationproject.dao.TPatients
 * @author Hibernate Tools
 */
@Stateless
public class TPatientsDAO {

	private static final Log log = LogFactory.getLog(TPatientsDAO.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(TPatients transientInstance) {
		log.debug("persisting Tpatients instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TPatients persistentInstance) {
		log.debug("removing Tpatients instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public TPatients merge(TPatients detachedInstance) {
		log.debug("merging Tpatients instance");
		try {
			TPatients result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TPatients findById(String id) {
		log.debug("getting Tpatients instance with id: " + id);
		try {
			TPatients instance = entityManager.find(TPatients.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
