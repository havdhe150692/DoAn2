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
            "JOIN toad_class tc ON tc.id = tdt.toad_class_id\n" +
            "WHERE tc.id = :id", nativeQuery = true)
    List<Market> findByToadClass(@Param("id") int id);

    @Query(value =
            "SELECT * FROM market m JOIN toad_ingame tig ON m.toad_ingame_id = tig.id " +
                    "JOIN toad_data tdt ON tdt.id = tig.toad_data_id\n" +
            "WHERE tdt.rarity = :rarityNum\n", nativeQuery = true)
    List<Market> findByRarity(@Param("rarityNum") int rarityNum);

    @Query(value = "\n" +
            "SELECT COUNT(m.id) FROM market m JOIN toad_ingame tig ON m.toad_ingame_id = tig.id " +
            "JOIN toad_data tdt ON tdt.id = tig.toad_data_id\n" +
            "WHERE tdt.rarity = :countNum", nativeQuery = true)
    int countToad(@Param("countNum") int countNum);

    @Query(value = "SELECT COUNT(m.id) FROM market m",nativeQuery = true)
    int countAllMarket();


    @Query(value ="SELECT * FROM market m ORDER BY m.price DESC\n ", nativeQuery = true)
    List<Market> sortFromHighestPrice();

    @Query(value ="SELECT * FROM market m ORDER BY m.price ASC\n ", nativeQuery = true)
    List<Market> sortFromLowestPrice();


    @Query(value = "SELECT * FROM market m JOIN toad_ingame tig ON m.toad_ingame_id = tig.id \n" +
            "JOIN toad_data tdt ON tdt.id = tig.toad_data_id \n" +
            "JOIN toad_class tc ON tc.id = tdt.toad_class_id\n" +
            "WHERE tc.id = 2", nativeQuery = true)
    List<Market> findEconomicToad();

    @Query(value = "SELECT * FROM market m JOIN toad_ingame tig ON m.toad_ingame_id = tig.id \n" +
            "JOIN toad_data tdt ON tdt.id = tig.toad_data_id \n" +
            "JOIN toad_class tc ON tc.id = tdt.toad_class_id\n" +
            "WHERE tc.id = 4", nativeQuery = true)
    List<Market> findGraphicToad();

    @Query(value = "SELECT * FROM market m JOIN toad_ingame tig ON m.toad_ingame_id = tig.id \n" +
            "JOIN toad_data tdt ON tdt.id = tig.toad_data_id \n" +
            "JOIN toad_class tc ON tc.id = tdt.toad_class_id\n" +
            "WHERE tc.id = 6", nativeQuery = true)
    List<Market> findArtistToad();

    @Query(value = "SELECT * FROM market m JOIN toad_ingame tig ON m.toad_ingame_id = tig.id \n" +
            "JOIN toad_data tdt ON tdt.id = tig.toad_data_id \n" +
            "JOIN toad_class tc ON tc.id = tdt.toad_class_id\n" +
            "WHERE tc.id = 8", nativeQuery = true)
    List<Market> findLectureToad();

    @Query(value = "SELECT * FROM market m JOIN toad_ingame tig ON m.toad_ingame_id = tig.id \n" +
            "JOIN toad_data tdt ON tdt.id = tig.toad_data_id \n" +
            "JOIN toad_class tc ON tc.id = tdt.toad_class_id\n" +
            "WHERE tc.id = 5", nativeQuery = true)
    List<Market> findAIToad();

    @Query(value = "SELECT * FROM market m JOIN toad_ingame tig ON m.toad_ingame_id = tig.id \n" +
            "JOIN toad_data tdt ON tdt.id = tig.toad_data_id \n" +
            "JOIN toad_class tc ON tc.id = tdt.toad_class_id\n" +
            "WHERE tc.id = 3", nativeQuery = true)
    List<Market> findSoftwareToad();

    @Query(value = "SELECT COUNT(m.id) FROM market m JOIN toad_ingame tig ON m.toad_ingame_id = tig.id \n" +
            "JOIN toad_data tdt ON tdt.id = tig.toad_data_id \n" +
            "JOIN toad_class tc ON tc.id = tdt.toad_class_id\n" +
            "WHERE tc.id = :id", nativeQuery = true)
    int countByToadClass(@Param("id") int id);
}
