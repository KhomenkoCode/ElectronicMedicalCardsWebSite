package com.khomenkocode.graduationproject.dao;
// Generated 26.04.2018 1:06:35 by Hibernate Tools 5.1.4.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.khomenkocode.graduationproject.entities.THospitalDepartments;

/**
 * Home object for domain model class ThospitalDepartments.
 * @see com.khomenkocode.graduationproject.dao.THospitalDepartments
 * @author Hibernate Tools
 */
@Stateless
public class THospitalDepartmentsDAO {

	private static final Log log = LogFactory.getLog(THospitalDepartmentsDAO.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(THospitalDepartments transientInstance) {
		log.debug("persisting ThospitalDepartments instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(THospitalDepartments persistentInstance) {
		log.debug("removing ThospitalDepartments instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public THospitalDepartments merge(THospitalDepartments detachedInstance) {
		log.debug("merging ThospitalDepartments instance");
		try {
			THospitalDepartments result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public THospitalDepartments findById(int id) {
		log.debug("getting ThospitalDepartments instance with id: " + id);
		try {
			THospitalDepartments instance = entityManager.find(THospitalDepartments.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
