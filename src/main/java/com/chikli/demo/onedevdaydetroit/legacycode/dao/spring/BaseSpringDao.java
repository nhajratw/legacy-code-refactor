package com.chikli.demo.onedevdaydetroit.legacycode.dao.spring;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.chikli.demo.onedevdaydetroit.legacycode.dao.IDao;

public class BaseSpringDao<T> extends HibernateDaoSupport implements IDao<T> {
	private static final Logger LOG = Logger.getLogger(BaseSpringDao.class);

	protected Class<T> m_clazz = null;
	protected String m_entityName = null;

	public BaseSpringDao(Class<T> pClazz, String pEntityName) {
		m_clazz = pClazz;
		m_entityName = pEntityName;
	}

	public List<T> findByNamedQuery(String pQuery, Object[] pValues) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("findByNamedQuery [" + pQuery + "]");
		}
		List<T> lList = null;
		try {
			lList = getHibernateTemplate().findByNamedQuery(pQuery, pValues);
			if (LOG.isDebugEnabled()) {
				LOG.debug("findByNamedQuery [" + pQuery + "] successful, result size: " + lList.size());
			}
		}
		catch (RuntimeException re) {
			LOG.error("findByNamedQuery failed", re);
			throw re;
		}
		return lList;
	}

	public List<T> findByNamedQueryAndNamedParams(final String pQueryName, final String[] pParamNames,
			final Object[] pParamValues) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("findByNamedQueryAndNamedParams [" + pQueryName + "]");
		}
		List<T> lList = null;
		try {
			lList = getHibernateTemplate().findByNamedQueryAndNamedParam(pQueryName, pParamNames, pParamValues);
			if (LOG.isDebugEnabled()) {
				LOG.debug("findByNamedQueryAndNamedParams [" + pQueryName + "] successful, result size: "
						+ lList.size());
			}
		}
		catch (RuntimeException re) {
			LOG.error("findByNamedQueryAndNamedParams failed", re);
			throw re;
		}
		return lList;
	}

	public List findByNamedQueryAndNamedParams(final String pQueryName, final String[] pParamNames,
			final Object[] pParamValues, final int pRecordStart, final int pMaxRecords) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("findByNamedQueryAndNamedParams [" + pQueryName + "]");
		}
		try {
			return getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(final Session session) throws HibernateException, SQLException {
					final Query lQueryObject = session.getNamedQuery(pQueryName).setFirstResult(pRecordStart)
							.setMaxResults(pMaxRecords);
					if (pParamValues != null) {
						for (int i = 0; i < pParamValues.length; i++) {
							applyNamedParameterToQuery(lQueryObject, pParamNames[i], pParamValues[i]);
						}
					}
					final List lResult = lQueryObject.list();
					LOG.debug("findByNamedQueryAndNamedParams [" + pQueryName + "] successful, result size: "
							+ lResult.size());
					return lResult;
				}
			});
		}
		catch (RuntimeException re) {
			LOG.error("find by NamedQuery:" + pQueryName + " failed", re);
			throw re;
		}
	}

	public T findUniqueByNamedQueryAndNamedParams(final String pQueryName, final String[] pParamNames,
			final Object[] pParamValues) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("findUniqueByNamedQueryAndNamedParams [" + pQueryName + "]");
		}
		try {
			List<T> list = getHibernateTemplate().findByNamedQueryAndNamedParam(pQueryName, pParamNames, pParamValues);
			if (LOG.isDebugEnabled()) {
				LOG.debug("findUniqueByNamedQueryAndNamedParams [" + pQueryName + "] successful, result size: "
						+ list.size());
			}
			if (list == null || list.isEmpty())
				return null;
			if (list.size() > 1)
				throw new RuntimeException("Non-unique result, found " + list.size());
			return list.get(0);
		}
		catch (RuntimeException re) {
			LOG.error("findUniqueByNamedQueryAndNamedParams failed", re);
			throw re;
		}
	}

	public int bulkUpdate(String pQuery, Object[] pValues) {
		LOG.debug("bulk update Query");
		int lResult;
		try {
			lResult = getHibernateTemplate().bulkUpdate(pQuery, pValues);
			LOG.debug("bulk update successful");
		}
		catch (RuntimeException re) {
			LOG.error("bulk update failed", re);
			throw re;
		}
		return lResult;
	}

	public int bulkInsert(final T[] values) {
		try {
			Integer result = (Integer) getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException, SQLException {

					// don't fill up the cache the batched entities
					session.setCacheMode(CacheMode.IGNORE);
					int loopCount = 0;
					Transaction transaction = session.beginTransaction();
					for (int i = 0; i < values.length; i++) {
						T value = values[i];

						session.save(value);

						// configured jdbc batch size should match 30 to make
						// this efficient
						if (loopCount % 30 == 0) {
							session.flush();
							session.clear();
						}

					}

					transaction.commit();
					session.setCacheMode(CacheMode.NORMAL);
					return Integer.valueOf(values.length);
				}

			});

			return result.intValue();

		}
		catch (RuntimeException re) {
			LOG.error("bulk insert failed", re);
			throw re;
		}
	}

	public void initialize(Object pObject) {
		LOG.debug("intialize");
		try {
			getHibernateTemplate().initialize(pObject);
		}
		catch (RuntimeException re) {
			LOG.error("getHibernateTemplate.initialize failed", re);
			throw re;
		}
	}

	/**
	 * Apply the given name parameter to the given Query object.
	 * 
	 * @param queryObject
	 *            the Query object
	 * @param paramName
	 *            the name of the parameter
	 * @param value
	 *            the value of the parameter
	 * @throws HibernateException
	 *             if thrown by the Query object
	 */
	protected void applyNamedParameterToQuery(Query pQueryObject, String pParamName, Object pValue)
			throws HibernateException {

		if (pValue instanceof Collection) {
			pQueryObject.setParameterList(pParamName, (Collection) pValue);
		}
		else if (pValue instanceof Object[]) {
			pQueryObject.setParameterList(pParamName, (Object[]) pValue);
		}
		else {
			pQueryObject.setParameter(pParamName, pValue);
		}
	}

	public void attachDirty(T pInstance) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("attaching dirty " + m_entityName + " instance");
		}

		try {
			getHibernateTemplate().saveOrUpdate(pInstance);

			if (LOG.isDebugEnabled()) {
				LOG.debug("attach successful");
			}
		}
		catch (RuntimeException lRe) {
			LOG.error("attach failed", lRe);
			throw lRe;
		}

	}

	public void delete(T pPersistentInstance) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("deleting " + m_entityName + " instance");
		}
		try {
			getHibernateTemplate().delete(pPersistentInstance);

			if (LOG.isDebugEnabled()) {
				LOG.debug("delete successful");
			}
		}
		catch (RuntimeException lRe) {
			LOG.error("delete failed", lRe);
			throw lRe;
		}
	}

	public T findById(Integer pId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("find " + m_entityName + " instance with id: " + pId);
		}
		try {
			T lEntity = (T) getHibernateTemplate().get(m_clazz.getCanonicalName(), pId);

			if (LOG.isDebugEnabled()) {
				LOG.debug("find by Id successful");
			}
			return lEntity;
		}
		catch (RuntimeException lRe) {
			LOG.error("find by Id failed", lRe);
			throw lRe;
		}
	}

	public T findById(Long pId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("find " + m_entityName + " instance with id: " + pId);
		}
		try {
			T lEntity = (T) getHibernateTemplate().get(m_clazz.getCanonicalName(), pId);

			if (LOG.isDebugEnabled()) {
				LOG.debug("find by Id successful");
			}
			return lEntity;
		}
		catch (RuntimeException lRe) {
			LOG.error("find by Id failed", lRe);
			throw lRe;
		}
	}

	public T merge(T pDetachedInstance) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("merging " + m_entityName + " instance");
		}
		try {
			T lResult = (T) getHibernateTemplate().merge(pDetachedInstance);

			if (LOG.isDebugEnabled()) {
				LOG.debug("merge successful");
			}

			return lResult;

		}
		catch (RuntimeException lRe) {
			LOG.error("merge failed", lRe);
			throw lRe;
		}
	}

	public void save(T entity) {
		getHibernateTemplate().save(entity);
	}

	public void save(Collection<? extends T> entities) {
		for (T entity : entities) {
			save(entity);
		}
	}

	public void refresh(T pTransientInstance) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("refreshing " + m_entityName + " instance");
		}

		try {
			getHibernateTemplate().refresh(pTransientInstance);

			if (LOG.isDebugEnabled()) {
				LOG.debug("refresh successful");
			}
		}
		catch (RuntimeException lRe) {
			LOG.error("refresh failed", lRe);
			throw lRe;
		}
	}

	public void update(T pTransientInstance) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("update clean " + m_entityName + " instance");
		}
		try {
			getHibernateTemplate().update(pTransientInstance);

			if (LOG.isDebugEnabled()) {
				LOG.debug("update successful");
			}
		}
		catch (OptimisticLockingFailureException lOLFEx) {
			LOG
					.error("Update failure : OptimisticLockingFailureException Occured While updating the recipe. Exception : ["
							+ lOLFEx.getMessage() + "]");
			throw lOLFEx;
		}
		catch (CannotAcquireLockException lCALEx) {
			LOG.error("Update failure : CannotAcquireLockException Occured While updating the recipe. Exception : ["
					+ lCALEx.getMessage() + "]");
			throw lCALEx;
		}
		catch (RuntimeException lRe) {
			LOG.error("update failed", lRe);
			throw lRe;
		}
	}

	public void flush() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("flushing pending operations");
		}
		try {
			getHibernateTemplate().flush();

			if (LOG.isDebugEnabled()) {
				LOG.debug("flush successful");
			}
		}
		catch (RuntimeException lRe) {
			LOG.error("flush failed", lRe);
			throw lRe;
		}
	}

	protected void setEntityName(String pEntityName) {
		m_entityName = pEntityName;
	}

	protected String getSortOrder(String pAliasName, String pSortColumn, String pSortDirection, String pDefaultOrder) {

		String lSortOrder = " order by ";
		if ((null != pAliasName && !"".equals(pAliasName)) && (null != pSortColumn && !"".equals(pSortColumn))
				&& (null != pSortDirection && !"".equals(pSortDirection))) {

			lSortOrder += pAliasName + "." + pSortColumn + " " + pSortDirection;
		}
		else if ((null != pSortColumn && !"".equals(pSortColumn))
				&& (null != pSortDirection && !"".equals(pSortDirection))) {

			lSortOrder += pSortColumn + " " + pSortDirection;

		}
		else {
			lSortOrder = (null != pDefaultOrder && !"".equals(pDefaultOrder)) ? lSortOrder + pDefaultOrder : "";
		}
		return lSortOrder;
	}

	/**
	 * This is to construct a where clause that define the constraint for
	 * mapping a status column to a list of status enums.
	 * 
	 * @param field
	 *            the bean field for the constraint
	 * @param variablePrefix
	 *            the replaceable prefix to be used in the query
	 * @param listSize
	 *            the size of the constraint list
	 *            <ul>
	 *            <li>For the empty list, an empty clause will be returned.
	 *            <li>1 element, "field = ?" will be return.
	 *            <li>2 or more elements: "field in (?, ? ...)" will be
	 *            returned.
	 *            </ul>
	 * @return the clause that can be appended to a where clause. Example: field
	 *         = "status" variablePrefix = "stat" listSize = 3 Return: status in
	 *         (:stat0, :stat1, :stat2)
	 */
	public static String appendListConstraint(String field, String variablePrefix, int listSize) {
		StringBuffer buf = new StringBuffer();
		if (listSize > 0) {
			if (listSize == 1) {
				buf.append(field).append(" = :").append(variablePrefix).append('0');
			}
			else {
				buf.append(field).append(" in (");
				for (int i = 0; i < listSize; i++) {
					if (i > 0) {
						buf.append(',');
					}
					buf.append(':').append(variablePrefix).append(i);
				}
				buf.append(')');
			}
		}
		return buf.toString();
	}

}
