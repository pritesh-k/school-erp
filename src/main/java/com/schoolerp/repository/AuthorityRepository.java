//package com.schoolerp.repository;
//
//import com.schoolerp.entity.Authority;
//import com.schoolerp.enums.AuthorityCategory;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface AuthorityRepository extends JpaRepository<Authority, Long> {
//    Optional<Authority> findByName(String name);
//    List<Authority> findByCategory(AuthorityCategory category);
//}
