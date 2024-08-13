package com.example.se_final.web;

import com.example.se_final.entities.Reservation;
import com.example.se_final.entities.SeatRow;
import com.example.se_final.repositories.ReservationRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
public class ReservationController {

    // The code of the project was uploaded to the following repository:
    // https://github.com/minimedik/se_final

    //this one is an example of dependency injection
    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping(path="/")
    public String reservations(Model model) {

        List<Reservation> reservations = reservationRepository.findAll();

        List<SeatRow> seatRows = getSeatsChart(reservations);

        Reservation newReservation = new Reservation();
        newReservation.setReservationDate(new Date());

        model.addAttribute("newReservation", newReservation);
        model.addAttribute("listReservations", reservations);
        model.addAttribute("listSeatRows", seatRows);
        return "reservations";
    }


    @PostMapping(path = "/addReservationTest")
    public String addReservationTest(@ModelAttribute("newReservation") Reservation newReservation) {
       // reservationRepository.save(newReservation);
        return "redirect:/";
    }

    @PostMapping(path = "/addReservation")
    public String addReservation(@ModelAttribute("newReservation") Reservation newReservation) {
        reservationRepository.save(newReservation);
        return "redirect:/";
    }

    @GetMapping("/deleteReservation")
    public String delete(Long id) {
        reservationRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/editReservation")
    public String editReservation(Model model, Long id, HttpSession session) {
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        model.addAttribute("reservation", reservation);
        return "formReservation";
    }

    @PostMapping(path="/editReservation")
    public String editReservation(@ModelAttribute("reservation") Reservation reservation) {

        reservationRepository.save(reservation);
        return "redirect:/";
    }

    // private function used to generate the seat chart, populating the the seatRow class
    // with four entries.
    private List<SeatRow> getSeatsChart(List<Reservation> reservations)
    {
        List<SeatRow> seatRows = new ArrayList<>();
        for (int i =1 ; i < 5; i++)
        {
            SeatRow seatRow = new SeatRow();
            seatRow.a = getCustomerNameOrDefault(reservations, i+"A");
            seatRow.b = getCustomerNameOrDefault(reservations, i+"B");
            seatRow.c = getCustomerNameOrDefault(reservations, i+"C");
            seatRow.d = getCustomerNameOrDefault(reservations, i+"D");
            seatRow.e = getCustomerNameOrDefault(reservations, i+"E");

            seatRows.add(seatRow);
        }

        return seatRows;
    }

    // private function to check if the current field has a customer already
    private String getCustomerNameOrDefault(List<Reservation> reservations, String seatCode) {
        for (Reservation reservation : reservations) {
            if (reservation.getSeatNumber().equals(seatCode)) {
                return reservation.getName();
            }
        }

        return seatCode;
    }

}
