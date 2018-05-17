package com.khomenkocode.graduationproject.dao;
// Generated 26.04.2018 1:06:35 by Hibernate Tools 5.1.4.Final

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.khomenkocode.graduationproject.entities.THospitals;

/**
 * Home object for domain model class Thospitals.
 * @see com.khomenkocode.graduationproject.dao.THospitals
 * @author Hibernate Tools
 */
@Stateless
@Repository
@Transactional 
public class THospitalsDAO {

	private static final Log log = LogFactory.getLog(THospitalsDAO.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	SessionFactory sessionFactory;
	
	public void persist(THospitals transientInstance) {
		log.debug("persisting Thospitals instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(THospitals persistentInstance) {
		log.debug("removing Thospitals instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public THospitals merge(THospitals detachedInstance) {
		log.debug("merging Thospitals instance");
		try {
			THospitals result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public THospitals findById(int id) {
		log.debug("getting Thospitals instance with id: " + id);
		try {
			THospitals instance = entityManager.find(THospitals.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public List<THospitals> getAll(){
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from THospitals").list();
	}
}
