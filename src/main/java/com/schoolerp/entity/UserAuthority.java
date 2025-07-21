//package com.schoolerp.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "user_authorities")
//@NoArgsConstructor
//@AllArgsConstructor
//public class UserAuthority extends BaseEntity {
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "authority_id", nullable = false)
//    private Authority authority;
//
//    @Column(name = "granted_by")
//    private Long grantedBy; // Who gave this permission
//
//    @Column(name = "granted_at")
//    private LocalDateTime grantedAt;
//
//    // Getters and Setters
//    public User getUser() { return user; }
//    public void setUser(User user) { this.user = user; }
//
//    public Authority getAuthority() { return authority; }
//    public void setAuthority(Authority authority) { this.authority = authority; }
//
//    public Long getGrantedBy() { return grantedBy; }
//    public void setGrantedBy(Long grantedBy) { this.grantedBy = grantedBy; }
//
//    public LocalDateTime getGrantedAt() { return grantedAt; }
//    public void setGrantedAt(LocalDateTime grantedAt) { this.grantedAt = grantedAt; }
//}
