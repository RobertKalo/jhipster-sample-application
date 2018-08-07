package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.UnitOfMeasure;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UnitOfMeasure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnitOfMeasureRepository extends JpaRepository<UnitOfMeasure, Long> {

}
