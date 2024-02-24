package com.example.Book_my_show_backend.Service;

import com.example.Book_my_show_backend.Dtos.BookTicketRequestDto;
import com.example.Book_my_show_backend.Models.ShowEntity;
import com.example.Book_my_show_backend.Models.ShowSeatEntity;
import com.example.Book_my_show_backend.Models.TicketEntity;
import com.example.Book_my_show_backend.Models.UserEntity;
import com.example.Book_my_show_backend.Repository.ShowRepository;
import com.example.Book_my_show_backend.Repository.TicketRepository;
import com.example.Book_my_show_backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    ShowRepository showRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketRepository ticketRepository;

    public String bookTicket(BookTicketRequestDto bookTicketRequestDto) throws Exception{

        // get the requested seats

        List<String> requestedSeats = bookTicketRequestDto.getRequestSeats();

        ShowEntity showEntity = showRepository.findById(bookTicketRequestDto.getShowId()).get();

        UserEntity userEntity = userRepository.findById(bookTicketRequestDto.getUserId()).get();


        // Get the showSeats from showEntity
        List<ShowSeatEntity> showSeats = showEntity.getListOfSeats();

        // validate if I can allocate these seats to the requested by user

        List<ShowSeatEntity> bookedSeats = new ArrayList<>();

        for(ShowSeatEntity showSeat : showSeats) {

            String seatNo = showSeat.getSeatNo();

            if (showSeat.isBooked() == false && requestedSeats.contains(seatNo)) {
                bookedSeats.add(showSeat);
            }

        }

        if(bookedSeats.size() != requestedSeats.size()){

                // simply means that the seats the userRequested are not available
                throw new Exception("Requested seats are not available");

        }

        // this means the bookseats actually contains the booked seats

        TicketEntity ticketEntity = new TicketEntity();

        double totalAmount = 0 ;
        double multiplier =  showEntity.getMultiplier();

        String  allotedDSeats = "";

        int rate = 0 ;

        // Calculating amount, settings Book Status , settings
        for(ShowSeatEntity bookedSeat : bookedSeats){
            bookedSeat.setBooked(true);
            bookedSeat.setBookedAt(new Date());
            bookedSeat.setTicket(ticketEntity);
            bookedSeat.setShow(showEntity);

           String seatNo = bookedSeat.getSeatNo();

           allotedDSeats = allotedDSeats + seatNo + "," ;

           if(seatNo.charAt(0) == '1')
               rate = 100;
           else
               rate = 200;
            totalAmount = totalAmount + multiplier * rate;
        }
        ticketEntity.setBooked_at(new Date());
        ticketEntity.setAmount((int)totalAmount);
        ticketEntity.setShow(showEntity);
        ticketEntity.setUser(userEntity);
        ticketEntity.setAlloted_seats(allotedDSeats);

        ticketEntity.setBookedSeats(bookedSeats);

        // Bidirectional part is remaining

        ticketRepository.save(ticketEntity);

        return "Successfully created a ticket ";
    }
}