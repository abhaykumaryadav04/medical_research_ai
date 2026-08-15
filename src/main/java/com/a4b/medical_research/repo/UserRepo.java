package com.a4b.medical_research.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a4b.medical_research.model.User;
@Repository
public interface UserRepo  extends JpaRepository<User,Long> {

}
