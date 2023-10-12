/* Here note that ki this is those seats when we build new theater mtlb ye ki jb theater pahili baar bna hua hai toh this is how my theater looks like */
/* it is like 2D matrix */


package com.example.Book_my_show_backend.Models;

import com.example.Book_my_show_backend.Enums.SeatType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "theater_seats ")
@Data
@NoArgsConstructor
public class TheaterSeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;

    @Column(columnDefinition = "seat_no",nullable = false)
    private String seatNO;

    @Enumerated(value = EnumType.STRING)
    private SeatType seatType;

    private int rate ;

    @ManyToOne
    @JoinColumn
    private TheaterEntity theater;

}
