package com.khomenkocode.graduationproject.dao;
// Generated 26.04.2018 1:06:35 by Hibernate Tools 5.1.4.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.khomenkocode.graduationproject.entities.TDoctors;

/**
 * Home object for domain model class Tdoctors.
 * @see com.khomenkocode.graduationproject.dao.TDoctors
 * @author Hibernate Tools
 */
@Stateless
public class TDoctorsDAO {

	private static final Log log = LogFactory.getLog(TDoctorsDAO.class);

	@PersistenceContext(unitName="doctors")
	private EntityManager entityManager;

	public void persist(TDoctors transientInstance) {
		log.debug("persisting Tdoctors instance");
		try {
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
}
