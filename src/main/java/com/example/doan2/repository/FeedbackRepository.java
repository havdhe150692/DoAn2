package com.example.doan2.repository;

import com.example.doan2.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    List<Feedback> findAll();

    @Query(value = "SELECT * FROM feedback fb WHERE user_id = :userId", nativeQuery = true)
    Feedback userFeedback(@Param("userId") int userId);


    @Modifying
    @Query(value = "UPDATE feedback f SET f.rate_point = :ratePoint AND f.info = :info AND f.time_post = :timePost WHERE f.user_id = :userId", nativeQuery = true)
    void updateUserFeedback(@Param("ratePoint") int ratePoint, @Param("info") String info, @Param("userId") int userId, @Param("timePost") Timestamp timepost);


}
