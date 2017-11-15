package com.vart.springbootsecurityjwtdemo.dao;

import com.vart.springbootsecurityjwtdemo.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRolename(String rolename);
}
