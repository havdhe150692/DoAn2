package com.example.doan2.repository;

import com.example.doan2.entity.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserWalletRepository extends JpaRepository<UserWallet, Integer> {

}
