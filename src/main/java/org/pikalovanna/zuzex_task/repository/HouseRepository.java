package org.pikalovanna.zuzex_task.repository;

import org.pikalovanna.zuzex_task.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
}
