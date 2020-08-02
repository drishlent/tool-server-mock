package com.das.repository;

import com.das.domain.model.User;
import com.drishlent.dlite.annotation.Repository;
import com.drishlent.dlite.repository.CrudRepository;


@Repository
public interface UserRepository extends CrudRepository<User> {

}
