package com.senla.hotel.convertor;

import com.senla.hotel.entity.Lodger;
import com.senla.hotel.entity.Reservation;
import com.senla.hotel.entity.Room;
import com.senla.hotel.dto.reservation.ReservationResponseDto;
import com.senla.hotel.dto.reservation.SaveReservationRequestDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ReservationConvertor {
    private final ModelMapper modelMapper;

    public ReservationConvertor() {
        this.modelMapper = new ModelMapper();
    }

    public Reservation convertToEntity(SaveReservationRequestDto saveReservationResponseDto) {
        return buildReservation(saveReservationResponseDto.getStartDate(), saveReservationResponseDto.getEndDate(),
                saveReservationResponseDto.getLodgerId(), saveReservationResponseDto.getRoomId(), saveReservationResponseDto.isReserved());
    }

    public SaveReservationRequestDto convertSaveToDto(Reservation reservation) {
        return modelMapper.map(reservation, SaveReservationRequestDto.class);
    }

    public ReservationResponseDto convertToDto(Reservation reservation) {
        return new ReservationResponseDto(reservation.getId(), reservation.getStartDate(),
                reservation.getEndDate(), reservation.getLodger().getId(), reservation.getRoom().getId(),
                reservation.isReserved());
    }

    public Reservation convertToEntity(ReservationResponseDto reservationResponseDto) {
        return buildReservation(reservationResponseDto.getStartDate(), reservationResponseDto.getEndDate(),
                reservationResponseDto.getLodgerId(), reservationResponseDto.getRoomId(), reservationResponseDto.isReserved());
    }

    private Reservation buildReservation(LocalDate startDate, LocalDate endDate, Long lodgerId, Long roomId, Boolean isReserved) {
        Lodger lodger = new Lodger();
        Room room = new Room();
        lodger.setId(lodgerId);
        room.setId(roomId);
        return new Reservation(startDate, endDate, lodger, room, isReserved);
    }
}
