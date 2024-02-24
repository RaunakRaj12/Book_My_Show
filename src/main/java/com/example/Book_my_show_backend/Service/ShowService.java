package com.example.Book_my_show_backend.Service;

import com.example.Book_my_show_backend.Dtos.ShowRequestDto;
import com.example.Book_my_show_backend.Models.*;
import com.example.Book_my_show_backend.Repository.MovieRepository;
import com.example.Book_my_show_backend.Repository.ShowRepository;
import com.example.Book_my_show_backend.Repository.ShowSeatRepository;
import com.example.Book_my_show_backend.Repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    TheaterRepository theaterRepository;

    @Autowired
    ShowSeatRepository showSeatRepository;

    @Autowired
    ShowRepository showRepository;


    public String addShow(ShowRequestDto showRequestDto){

        ShowEntity showEntity = ShowEntity.builder().showDate(showRequestDto.getShowDate()).multiplier(showRequestDto.getMultiplier()).showTime(showRequestDto.getShowTime()).build();

        // you need to get movie Repo
        MovieEntity movieEntity = movieRepository.findByMovieName(showRequestDto.getMovieName());

        // you need to get theater Repo
        TheaterEntity theaterEntity = theaterRepository.findById(showRequestDto.getTheaterId()).get();

        showEntity.setTheater(theaterEntity);
        showEntity.setMovie(movieEntity);

        // bcz we are doing a bidirectional mapping:

        movieEntity.getListOfShows().add(showEntity);
        theaterEntity.getListOfShows().add(showEntity);

        //theaterRepository.save(theaterEntity);

    List<ShowSeatEntity> seatEntityList = createShowSeats(theaterEntity.getTheaterSeatEntityList());

    showEntity.setListOfSeats(seatEntityList);

    //for each show seat : we need to mark that to which show it belong to(frogien key will be filled)

        for(ShowSeatEntity showSeat:seatEntityList){
            showSeat.setShow(showEntity);
        }

        movieRepository.save(movieEntity);
        theaterRepository.save(theaterEntity);
       //showRepository.save(showEntity);

        return "show added successfully";

    }

    public List<ShowSeatEntity> createShowSeats(List<TheaterSeatEntity> theaterSeatEntityList){

        List<ShowSeatEntity> seats = new ArrayList<>();

        for (TheaterSeatEntity theaterSeat:theaterSeatEntityList){
            ShowSeatEntity showSeat= ShowSeatEntity.builder().seatNo(theaterSeat.getSeat_no()).seatType(theaterSeat.getSeat_type()).build();

            seats.add(showSeat);
        }
        showSeatRepository.saveAll(seats);
        return seats;

    }
}
