package com.example.accessingdatamysql_v2;

import org.springframework.data.repository.CrudRepository;

import com.example.accessingdatamysql_v2.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> {

}
