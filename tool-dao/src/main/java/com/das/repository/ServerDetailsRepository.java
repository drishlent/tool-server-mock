package com.das.repository;

import com.das.domain.model.ServerDetails;
import com.drishlent.dlite.annotation.Repository;
import com.drishlent.dlite.repository.CrudRepository;

@Repository
public interface ServerDetailsRepository extends CrudRepository<ServerDetails> {

}
