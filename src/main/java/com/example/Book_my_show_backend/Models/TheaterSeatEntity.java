/* Here note that ki this is those seats when we build new theater mtlb ye ki jb theater pahili baar bna hua hai toh this is how my theater looks like */
/* it is like 2D matrix */

package com.example.Book_my_show_backend.Models;

import javax.persistence.*;
import com.example.Book_my_show_backend.Enums.SeatType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "theater_seats ")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TheaterSeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;

    // here we remove column properties
    private String seat_no;

    @Enumerated(value = EnumType.STRING)
    private SeatType seat_type;

    private int rate ;

    @ManyToOne
    @JoinColumn
    private TheaterEntity theater;

    //Creating a constructor
    public TheaterSeatEntity(String seatNo,SeatType seatType,int rate){
        this.seat_no = seatNo;
        this.seat_type = seatType;
        this.rate = rate;
    }
}
