package org.g9project4.publicData.greentour.repositories;

import org.g9project4.publicData.greentour.entities.GreenPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GreenPlaceRepository extends JpaRepository<GreenPlace, Long>, QuerydslPredicateExecutor<GreenPlace> {
}
