//package com.schoolerp.repository;
//
//import com.schoolerp.entity.UserAuthority;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {
//    List<UserAuthority> findByUserId(Long userId);
//    boolean existsByUserIdAndAuthorityName(Long userId, String authorityName);
//    void deleteByUserIdAndAuthorityName(Long userId, String authorityName);
//
//    @Query("SELECT ua FROM UserAuthority ua WHERE ua.user.id = :userId AND ua.authority.name = :authorityName")
//    Optional<UserAuthority> findByUserIdAndAuthorityName(@Param("userId") Long userId,
//                                                         @Param("authorityName") String authorityName);
//}