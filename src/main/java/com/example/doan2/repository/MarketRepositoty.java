package com.example.doan2.repository;

import com.example.doan2.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketRepositoty extends JpaRepository<Market, Integer> {

    List<Market> findAll();

    Market findById(int id);

    @Query(
            value = "SELECT * FROM market m WHERE m.price BETWEEN :from AND :to", nativeQuery = true
    )
    List<Market> findBetweenPrice(@Param("from") int from, @Param("to") int to);

    @Query(value =
            "SELECT * FROM market m JOIN toad_ingame tig ON m.toad_ingame_id = tig.id \n" +
            "JOIN toad_data tdt ON tdt.id = tig.toad_data_id \n" +
            "WHERE tdt.name = :name", nativeQuery = true)
    List<Market> findByName(@Param("name") String name);

    @Query(value =
            "SELECT * FROM market m JOIN toad_ingame tig ON m.toad_ingame_id = tig.id \n" +
            "JOIN toad_data tdt ON tdt.id = tig.toad_data_id \n" +
            "WHERE tdt.name LIKE %:nameContain%", nativeQuery = true)
    List<Market> findByNameContain(@Param("nameContain") String nameContain);


    @Query(value = "SELECT * FROM market m JOIN toad_ingame tig ON m.toad_ingame_id = tig.id \n" +
            "JOIN toad_data tdt ON tdt.id = tig.toad_data_id \n" +
            "JOIN toad_class tc ON tc.id = tdt.toad_class_id" +
            "WHERE tc.id = :id", nativeQuery = true)
    List<Market> findByToadClass(@Param("id") int id);

}
