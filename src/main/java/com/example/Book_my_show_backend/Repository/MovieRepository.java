package com.example.Book_my_show_backend.Repository;

import com.example.Book_my_show_backend.Models.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface MovieRepository extends JpaRepository<MovieEntity,Integer> {

    MovieEntity findByMovieName(String movieName);
}
