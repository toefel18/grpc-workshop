package nl.toefel.exercise1;

import nl.toefel.scratch.ScratchService;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static nl.toefel.scratch.ScratchService.HotelBooking;

public class Exercise1 {

    public static void main(String[] args) throws IOException {
        HotelBooking booking = HotelBooking.newBuilder()
                .setArrivalDate("2018-10-12")
                .setPrice(23.53345467)
                .setDepartureDate("2019-01-01")
                .addGuests(ScratchService.Guest.newBuilder()
                        .setAge(3)
                        .setGender(ScratchService.Gender.FLUID)
                        .setName("Sjakie")
                        .build())
                .build();

        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream("hotel-booking-2.binary"))) {
            booking.writeTo(outputStream);
        }

        HotelBooking hotelBooking = HotelBooking.parseFrom(new FileInputStream("hotel-booking-2.binary"));


        System.out.println(hotelBooking);
    }
}
