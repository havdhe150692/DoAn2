package com.example.doan2.repository;



import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToadIngameRepository extends JpaRepository<ToadIngame, Integer> {



    List<ToadIngame> findAllByOwner(User owner);

    List<ToadIngame> findAllToadByOwner(User owner);

    ToadIngame findById(int id);

    @Query(value =
            "SELECT * FROM toad_ingame tig JOIN toad_data tdt ON tdt.id = tig.toad_data_id \n" +
                    "WHERE tdt.rarity = :rarityNum AND tig.owner_id = :user", nativeQuery = true)
    List<ToadIngame> findUserToadByRarity(@Param("rarityNum") int rarityNum, @Param("user") int userId);


    @Query(value = "SELECT COUNT(tig.id) FROM toad_ingame tig WHERE tig.owner_id = :user", nativeQuery = true)
    int countAllUserToad(@Param("user") int userId);

    @Query(value = "SELECT COUNT(tig.id) FROM toad_ingame tig \n" +
            "JOIN toad_data tdt ON tdt.id = tig.toad_data_id \n" +
            "WHERE tig.owner_id = :user AND tdt.rarity = :rarityNum", nativeQuery = true)
    int countUserToadByRarity(@Param("user") int userId, @Param("rarityNum") int rarityNum);

    @Query(value = "SELECT * FROM toad_ingame tig JOIN \n" +
            " toad_data tdt ON tdt.id = tig.toad_data_id WHERE \n" +
            " tdt.name = :name AND tig.owner_id = :user", nativeQuery = true)
    List<ToadIngame> findUserToadByName(@Param("name") String name,@Param("user") int userId);

    @Modifying
    @Query(value = "UPDATE toad_ingame tig SET tig.owner_id = :ownerId AND tig.is_selling = 0 WHERE tig.id = :toadId", nativeQuery = true)
    void changeToadOwner(@Param("ownerId") int userId, @Param("toadId") int toadId);
}
