package com.example.doan2.repository;

import com.example.doan2.entity.Market;
import com.example.doan2.entity.ToadIngame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketRepositoty extends JpaRepository<Market, Integer> {


    Market findByToadIngame(ToadIngame t);

    List<Market> findAll();

    Market findById(int id);

    @Query(
            value = "SELECT * FROM market m WHERE m.is_selling = 1", nativeQuery = true
    )
    List<Market> findAllToad();

    @Query(
            value = "SELECT * FROM market m WHERE m.price BETWEEN :from AND :to WHERE m.is_selling = 1", nativeQuery = true
    )
    List<Market> findBetweenPrice(@Param("from") int from, @Param("to") int to);

    @Query(value =
            "SELECT * FROM market m JOIN toad_ingame tig ON m.toad_ingame_id = tig.id \n" +
                    "JOIN toad_data tdt ON tdt.id = tig.toad_data_id \n" +
                    "WHERE tdt.name = :name AND m.is_selling = 1", nativeQuery = true)
    List<Market> findByName(@Param("name") String name);

    @Query(value =
            "SELECT * FROM market m JOIN toad_ingame tig ON m.toad_ingame_id = tig.id \n" +
                    "JOIN toad_data tdt ON tdt.id = tig.toad_data_id \n" +
                    "WHERE tdt.name LIKE %:nameContain% AND m.is_selling = 1", nativeQuery = true)
    List<Market> findByNameContain(@Param("nameContain") String nameContain);


    @Query(value = "SELECT * FROM market m JOIN toad_ingame tig ON m.toad_ingame_id = tig.id \n" +
            "JOIN toad_data tdt ON tdt.id = tig.toad_data_id \n" +
            "JOIN toad_class tc ON tc.id = tdt.toad_class_id\n" +
            "WHERE tc.id = :id AND m.is_selling = 1", nativeQuery = true)
    List<Market> findByToadClass(@Param("id") int id);

    @Query(value =
            "SELECT * FROM market m JOIN toad_ingame tig ON m.toad_ingame_id = tig.id " +
                    "JOIN toad_data tdt ON tdt.id = tig.toad_data_id\n" +
                    "WHERE tdt.rarity = :rarityNum AND m.is_selling = 1\n", nativeQuery = true)
    List<Market> findByRarity(@Param("rarityNum") int rarityNum);

    @Query(value = "\n" +
            "SELECT COUNT(m.id) FROM market m JOIN toad_ingame tig ON m.toad_ingame_id = tig.id " +
            "JOIN toad_data tdt ON tdt.id = tig.toad_data_id\n" +
            "WHERE tdt.rarity = :countNum AND m.is_selling = 1", nativeQuery = true)
    int countToad(@Param("countNum") int countNum);

    @Query(value = "SELECT COUNT(m.id) FROM market m WHERE m.is_selling = 1", nativeQuery = true)
    int countAllMarket();


    @Query(value = "SELECT * FROM market m ORDER BY m.price DESC WHERE m.is_selling = 1\n ", nativeQuery = true)
    List<Market> sortFromHighestPrice();

    @Query(value = "SELECT * FROM market m ORDER BY m.price ASC WHERE m.is_selling = 1\n ", nativeQuery = true)
    List<Market> sortFromLowestPrice();

    @Query(value = "SELECT COUNT(m.id) FROM market m JOIN toad_ingame tig ON m.toad_ingame_id = tig.id \n" +
            "JOIN toad_data tdt ON tdt.id = tig.toad_data_id \n" +
            "JOIN toad_class tc ON tc.id = tdt.toad_class_id\n" +
            "WHERE tc.id = :id AND m.is_selling = 1", nativeQuery = true)
    int countByToadClass(@Param("id") int id);

    @Query(value = "SELECT * FROM market m JOIN user u ON m.seller_id = u.id WHERE m.id = :toadId", nativeQuery = true)
    Market findSellerToad(@Param("toadId") int toadId);

    @Query(value = "SELECT * FROM market m WHERE m.is_selling = 1 AND m.seller_id = :sellerId", nativeQuery = true)
    List<Market> findListToadBySellerAtMarket(@Param("sellerId") int sellerId);

    @Query(value = "SELECT * FROM market m \n" +
            "JOIN toad_ingame tig ON m.toad_ingame_id = tig.id WHERE m.toad_ingame_id = :toadId AND m.is_selling = 1", nativeQuery = true)
    Market findToadBySellerAtMarket(@Param("toadId") int toadId);


    @Modifying
    @Query(value = "UPDATE toad_ingame tig SET tig.is_selling = 0 WHERE m.toad_ingame_id = :toadIngameId", nativeQuery = true)
    void cancelSellToadAtMarket(@Param("toadIngameId") int toadIngameId);

    @Modifying
    @Query(value = "UPDATE market m SET m.is_selling = 0 WHERE m.toad_ingame_id = :toadIngameId", nativeQuery = true)
    void removeToadAtMarket(@Param("toadIngameId") int toadIngameId);

    @Query(value = "SELECT COUNT(tig.id) FROM toad_ingame tig WHERE tig.is_selling = 1", nativeQuery = true)
    int countMyToadAtMarket(@Param("countNum") int countNum);

}
