package com.fertilizer.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fertilizer.dao.CityDao;
import com.fertilizer.enums.BooleanEnum;
import com.fertilizer.enums.EntityConstant;
import com.fertilizer.enums.Status;
import com.fertilizer.model.City;
import com.fertilizer.model.State;
import com.fertilizer.request1.CitySearchDto;
import com.fertilizer.request1.StateSearchDto;
import com.fertilizer.util.QueryUtil;

@Repository
public class CityDaoImpl implements CityDao {
	private static final Logger logger = LogManager.getLogger(CityDaoImpl.class);

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<City> getCity(CitySearchDto citySearchDto) {

		logger.debug("get CityDao Method Called.");

		CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<City> query = queryBuilder.createQuery(City.class);
		Root<City> queryRoot = query.from(City.class);

		if (null != citySearchDto.getSortBy() && null != citySearchDto.getSortDir()) {
			if (citySearchDto.getSortDir().compareToIgnoreCase(EntityConstant.ASC.getName()) == 0)
				query.orderBy(queryBuilder.asc(queryRoot.get(citySearchDto.getSortBy())));
			else
				query.orderBy(queryBuilder.desc(queryRoot.get(citySearchDto.getSortBy())));
		} else {
			query.orderBy(queryBuilder.asc(queryRoot.get(EntityConstant.CITYNAME.getName())));
		}

		List<Predicate> predicates = getCityPredicates(citySearchDto, queryBuilder, queryRoot);
		if (citySearchDto.getSmartSearch() != null) {
			setSearchLikeFilter(citySearchDto, queryBuilder, queryRoot, predicates);
		}
		query.where(queryBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

		TypedQuery<City> typedQuery = entityManager.createQuery(query);
		if (citySearchDto.getPage() != null && citySearchDto.getSize() != null && citySearchDto.getPage() != 0
				&& citySearchDto.getSize() != 0) {
			typedQuery.setFirstResult((citySearchDto.getPage().intValue() - 1) * citySearchDto.getSize().intValue());
			typedQuery.setMaxResults(citySearchDto.getSize().intValue());
			// sort_by
			// sort_dir asc desc
		}

		return typedQuery.getResultList();
	}

	private List<Predicate> getCityPredicates(CitySearchDto citySearchDto, CriteriaBuilder queryBuilder,
			Root<City> queryRoot) {
		List<Predicate> predicates = new ArrayList<>();

		predicates.add(queryBuilder.notEqual(queryRoot.get(EntityConstant.STATUS.getName()), Status.DELETED));

		// status
		if (citySearchDto.getStatus() != null) {
			List<Status> statusList = Arrays.asList(citySearchDto.getStatus().split(",")).stream()
					.map(Status::fromShortName).collect(Collectors.toList());
			Predicate statusPredicate = queryRoot.get(EntityConstant.STATUS.getName()).in(statusList);
			predicates.add(statusPredicate);
		}
		
		if (citySearchDto.getPublicUse() != null) {
			List<BooleanEnum> publicUseList = Arrays.asList(citySearchDto.getPublicUse().split(",")).stream()
					.map(BooleanEnum::fromShortName).collect(Collectors.toList());
			Predicate statusPredicate = queryRoot.get(EntityConstant.PUBLICUSE.getName()).in(publicUseList);
			predicates.add(statusPredicate);
			}
		
		if (citySearchDto.getId() != null) {
			predicates.add(queryBuilder.equal(queryRoot.get(EntityConstant.ID.getName()), citySearchDto.getId()));
		}
		if (citySearchDto.getCountryId() != null) {
			predicates.add(queryBuilder.equal(queryRoot.get(EntityConstant.COUNTRYID.getName()),
					citySearchDto.getCountryId()));
		}
		if (citySearchDto.getStateId() != null) {
			predicates.add(
					queryBuilder.equal(queryRoot.get(EntityConstant.STATEID.getName()), citySearchDto.getStateId()));
		}
		if (citySearchDto.getCityName() != null) {
			predicates.add(
					queryBuilder.equal(queryRoot.get(EntityConstant.CITYNAME.getName()), citySearchDto.getCityName()));
		}
		if (citySearchDto.getCityType() != null) {
			predicates.add(
					queryBuilder.equal(queryRoot.get(EntityConstant.CITYTYPE.getName()), citySearchDto.getCityType()));
		}
		return predicates;
	}
	
	private void setSearchLikeFilter(CitySearchDto citySearchDto, CriteriaBuilder queryBuilder,
			Root<City> queryRoot, List<Predicate> predicates) {

		logger.debug("setSearchLikeFilter Method Called in CityDao.");

		String toBeSearched = QueryUtil.convertForLike(citySearchDto.getSmartSearch());

		List<Predicate> smartSearchPredicateList = new ArrayList<>();

		// user name
		smartSearchPredicateList.add(queryBuilder.like(queryRoot.get(EntityConstant.CITYNAME.getName()), toBeSearched));


		Predicate[] p = new Predicate[smartSearchPredicateList.size()];
		smartSearchPredicateList.toArray(p);
		predicates.add(queryBuilder.or(p));

	}
	@Override
	public Long count(CitySearchDto citySearchDto) {
		logger.debug("Count CityDao Method Called.");

		CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = queryBuilder.createQuery(Long.class);
		Root<City> queryRoot = query.from(City.class);
		query.select(queryBuilder.count(queryRoot));

		List<Predicate> predicates = getCityPredicates(citySearchDto, queryBuilder, queryRoot);

		query.where(queryBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

		return entityManager.createQuery(query).getSingleResult();
	}

	@Override
	public List<State> getState(StateSearchDto stateSearchDto) {
		logger.debug("CityDao Method getState Called.");

		CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<State> query = queryBuilder.createQuery(State.class);
		Root<State> queryRoot = query.from(State.class);

		if (null != stateSearchDto.getSortBy() && null != stateSearchDto.getSortDir()) {
			if (stateSearchDto.getSortDir().compareToIgnoreCase(EntityConstant.ASC.getName()) == 0)
				query.orderBy(queryBuilder.asc(queryRoot.get(stateSearchDto.getSortBy())));
			else
				query.orderBy(queryBuilder.desc(queryRoot.get(stateSearchDto.getSortBy())));
		} else {
			query.orderBy(queryBuilder.asc(queryRoot.get(EntityConstant.STATENAME.getName())));
		}

		List<Predicate> predicates = getStatePredicates(stateSearchDto, queryBuilder, queryRoot);
		if (stateSearchDto.getSmartSearch() != null) {
			setStateSearchLikeFilter(stateSearchDto, queryBuilder, queryRoot, predicates);
		}
		query.where(queryBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

		TypedQuery<State> typedQuery = entityManager.createQuery(query);
		if (stateSearchDto.getPage() != null && stateSearchDto.getSize() != null && stateSearchDto.getPage() != 0
				&& stateSearchDto.getSize() != 0) {
			typedQuery.setFirstResult((stateSearchDto.getPage().intValue() - 1) * stateSearchDto.getSize().intValue());
			typedQuery.setMaxResults(stateSearchDto.getSize().intValue());
		}

		return typedQuery.getResultList();
	}
	
	private List<Predicate> getStatePredicates(StateSearchDto stateSearchDto, CriteriaBuilder queryBuilder,
			Root<State> queryRoot) {
		List<Predicate> predicates = new ArrayList<>();

		predicates.add(queryBuilder.notEqual(queryRoot.get(EntityConstant.STATUS.getName()), Status.DELETED));

		// status
		if (stateSearchDto.getStatus() != null) {
			List<Status> statusList = Arrays.asList(stateSearchDto.getStatus().split(",")).stream()
					.map(Status::fromShortName).collect(Collectors.toList());
			Predicate statusPredicate = queryRoot.get(EntityConstant.STATUS.getName()).in(statusList);
			predicates.add(statusPredicate);
		}
		
		if (stateSearchDto.getCountryId() != null) {
			predicates.add(queryBuilder.equal(queryRoot.get(EntityConstant.COUNTRYID.getName()),
					stateSearchDto.getCountryId()));
		}
		if (stateSearchDto.getStateName() != null) {
			predicates.add(
					queryBuilder.equal(queryRoot.get(EntityConstant.STATENAME.getName()), stateSearchDto.getStateName()));
		}
		if (stateSearchDto.getStateCode() != null) {
			predicates.add(
					queryBuilder.equal(queryRoot.get(EntityConstant.STATECODE.getName()), stateSearchDto.getStateCode()));
		}
		return predicates;
	}
	
	
	private void setStateSearchLikeFilter(StateSearchDto stateSearchDto, CriteriaBuilder queryBuilder,
			Root<State> queryRoot, List<Predicate> predicates) {

		logger.debug("setSearchLikeFilter Method Called in CityDao.");

		String toBeSearched = QueryUtil.convertForLike(stateSearchDto.getSmartSearch());

		List<Predicate> smartSearchPredicateList = new ArrayList<>();

		// user name
		smartSearchPredicateList.add(queryBuilder.like(queryRoot.get(EntityConstant.STATENAME.getName()), toBeSearched));


		Predicate[] p = new Predicate[smartSearchPredicateList.size()];
		smartSearchPredicateList.toArray(p);
		predicates.add(queryBuilder.or(p));

	}

	@Override
	public Long countState(StateSearchDto stateSearchDto) {
		logger.debug("countState CityDao Method Called.");

		CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = queryBuilder.createQuery(Long.class);
		Root<State> queryRoot = query.from(State.class);
		query.select(queryBuilder.count(queryRoot));

		List<Predicate> predicates = getStatePredicates(stateSearchDto, queryBuilder, queryRoot);

		query.where(queryBuilder.and(predicates.toArray(new Predicate[predicates.size()])));

		return entityManager.createQuery(query).getSingleResult();
	}

}
