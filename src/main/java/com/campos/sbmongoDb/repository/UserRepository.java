package com.campos.sbmongoDb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.campos.sbmongoDb.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

}
