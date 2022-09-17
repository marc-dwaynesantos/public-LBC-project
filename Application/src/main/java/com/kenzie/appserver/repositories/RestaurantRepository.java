package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.RestaurantRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface RestaurantRepository extends CrudRepository<RestaurantRecord, String> {
}
