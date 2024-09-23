package com.sparta.spartaoutsourcing.store.repository;

import com.sparta.spartaoutsourcing.store.entity.Favorites;
import com.sparta.spartaoutsourcing.store.entity.Store;
import com.sparta.spartaoutsourcing.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, Long> {

    Optional<Object> findByUsersAndStores(User user, Store store);
}
