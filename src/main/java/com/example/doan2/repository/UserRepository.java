package com.example.doan2.repository;


import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

//    @Query("")
    User findByName(String username);

    User findById(int Id);

   // @Query("SELECT u FROM USER u WHERE u.email = ?1")
    User findByEmail(String email);



}
