package com.example.doan2.service.Impl;

import com.example.doan2.entity.Feedback;
import com.example.doan2.repository.FeedbackRepository;
import com.example.doan2.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Service
public class FeedbackServiceImp implements FeedbackService {
    @Autowired
    FeedbackRepository feedbackRepo;

    @Override
    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepo.save(feedback);
    }

    @Override
    public List<Feedback> findAll() {
        return feedbackRepo.findAll();
    }

    @Override
    public Feedback userFeedback(int userId) {
        return feedbackRepo.userFeedback(userId);
    }

    @Transactional
    @Override
    public void updateUserFeedback(int ratePoint, String info, int userId, Timestamp timePost) {
        feedbackRepo.updateUserFeedback(ratePoint, info, userId, timePost);
    }


}
