package com.khomenkocode.graduationproject.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.khomenkocode.graduationproject.entities.TImages;
/**
 * Home object for domain model class Timages.
 * @see .Timages
 * @author Hibernate Tools
 */
@Stateless
public class TImagesDAO {

	private static final Log log = LogFactory.getLog(TImagesDAO.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(TImages transientInstance) {
		log.debug("persisting Timages instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TImages persistentInstance) {
		log.debug("removing Timages instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public TImages merge(TImages detachedInstance) {
		log.debug("merging Timages instance");
		try {
			TImages result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TImages findById(int id) {
		log.debug("getting Timages instance with id: " + id);
		try {
			TImages instance = entityManager.find(TImages.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
