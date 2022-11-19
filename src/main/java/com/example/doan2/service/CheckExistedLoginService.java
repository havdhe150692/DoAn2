package com.example.doan2.service;

import com.example.doan2.entity.User;
import com.example.doan2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckExistedLoginService {

    @Autowired
    private UserRepository userRepo;

    //    public Long checkLoginInfo(String email, String password)
//    {
//        User result = userRepo.findByEmail(email);
//        if ((result == null)
//                || (!email.equals(result.getEmail()))
//                || (!password.equals(result.getPassword()))) {
//            return -1l;
//        } else {
//            return 1l;
//        }
//    }
    public boolean checkEmail(String email) {
        User result = userRepo.findByEmail(email);
        if (result != null) {
            if (email.equals(result.getEmail())) {
                System.out.println("Email have already Existed");
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    public boolean checkUserName(String username) {
        User result = userRepo.findByName(username);
        if(result != null) {
            if (username.equals(result.getName())) {
                System.out.println("Name have already Existed");
                return false;
            } else {
                return true;
            }
        }
        return true;
    }
}
