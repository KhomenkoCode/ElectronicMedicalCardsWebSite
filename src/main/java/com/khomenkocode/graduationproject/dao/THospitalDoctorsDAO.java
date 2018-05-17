package com.khomenkocode.graduationproject.dao;
// Generated 07.05.2018 15:17:08 by Hibernate Tools 5.1.4.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.khomenkocode.graduationproject.entities.THospitalDoctors;

/**
 * Home object for domain model class ThospitalDoctors.
 * @see com.khomenkocode.graduationproject.entities.ThospitalDoctors
 * @author Hibernate Tools
 */
@Stateless
@Repository
@Transactional
public class THospitalDoctorsDAO {

	private static final Log log = LogFactory.getLog(THospitalDoctorsDAO.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(THospitalDoctors transientInstance) {
		log.debug("persisting ThospitalDoctors instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(THospitalDoctors persistentInstance) {
		log.debug("removing ThospitalDoctors instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public THospitalDoctors merge(THospitalDoctors detachedInstance) {
		log.debug("merging ThospitalDoctors instance");
		try {
			THospitalDoctors result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public THospitalDoctors findById(int id) {
		log.debug("getting ThospitalDoctors instance with id: " + id);
		try {
			THospitalDoctors instance = entityManager.find(THospitalDoctors.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
