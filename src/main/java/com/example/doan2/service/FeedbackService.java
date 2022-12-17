package com.example.doan2.service;

import com.example.doan2.entity.Feedback;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public interface FeedbackService {

    Feedback saveFeedback(Feedback feedback);

    List<Feedback> findAll();


    Feedback userFeedback(int userId);

    void updateUserFeedback(int ratePoint , String info, int userId, Timestamp timePost);

}
