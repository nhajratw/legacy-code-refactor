package com.chikli.demo.onedevdaydetroit.legacycode.dao;

import java.util.Collection;
import java.util.List;

/**
 * @version $Revision:417 $ $Date:2007-09-11 23:41:47 -0700 (Tue, 11 Sep 2007) $ by $Author: $
 */
public interface IDao<T> {

	/**
	 * Execute a query for persistent instances, binding one value to a "?"
	 *
	 * @param pQuery
	 * @param pValues
	 */
	List<T> findByNamedQuery(String pQuery, Object[] pValues);

	/**
	 * Execute a named query, binding one value to a ":" named parameter in the query string.
	 * A named query is defined in a Hibernate mapping file.
	 * 
	 * @param pQueryName - the name of a Hibernate query in a mapping file
	 * @param pParamName[] - the name of the parameter
	 * @param value[] - the value of the parameter
	 * @return a List containing the results of the query execution
	 */
	List findByNamedQueryAndNamedParams(
			final String pQueryName, final String[] pParamNames,
			final Object[] pParamValues);

	/**
	 * This method helps us to get results from a named query for the given named params.
	 * It is very much helpful for pagination.
	 * 
	 * @param pQueryName   : Query Name defined in *.hbm.xml file.
	 * @param pParamNames  : Array of param names to be pased to the Query.
	 * @param pParamValues : Array of values for the corresponding param names.
	 * @param pRecordStart : Initial record number from which the query needs to fetch the matching results.
	 * @param pMaxRecords  : End record Number till which the query needs to fetch the matching results.
	 * @return List        : Result from the query.
	 */
	List findByNamedQueryAndNamedParams(
			final String pQueryName, final String[] pParamNames,
			final Object[] pParamValues, final int pRecordStart,
			final int pMaxRecords);

	/**
	 * Return a unique instance of entity type T, throwing a RuntimeException if more than one is found.
	 * 
	 * 
	 * @param pQueryName
	 * @param pParamNames
	 * @param pParamValues
	 * @return
	 */
	T findUniqueByNamedQueryAndNamedParams(
			final String pQueryName, final String[] pParamNames,
			final Object[] pParamValues);

	/**
	 * Update/delete all objects according to the given query.
	 * Return the number of entity instances updated/deleted.
	 * @param queryString an update/delete query expressed in Hibernate's query language
	 * @param values the values of the parameters
	 * @return the number of instances updated/deleted
	 * @throws org.springframework.dao.DataAccessException in case of Hibernate errors
	 * @see org.springframework.orm.hibernate3.support.HibernateTemplate#blukUpdate
	 *
	 */
	int bulkUpdate(String pQuery, Object[] pValues);

	int bulkInsert(final T[] pValues);

	/**
	 * Pre-initialize the persistent object
	 *
	 * @param pObject Persistent object to be initialized
	 */
	void initialize(Object pObject);

	void attachDirty(T pInstance);

	void delete(T pPersistentInstance);

	T findById(Integer pId);

	T findById(Long pId);

	T merge(T pDetachedInstance);

	void save(T pTransientInstance);
	void save(Collection<? extends T> entities);

	void update(T pTransientInstance);

	void flush();
}
