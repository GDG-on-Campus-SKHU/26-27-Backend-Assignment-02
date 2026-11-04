package com.gdg.gdg_be_assignment02.repository;

import com.gdg.gdg_be_assignment02.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
