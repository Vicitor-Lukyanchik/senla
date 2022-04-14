package com.senla.hotel.convertor;

import com.senla.hotel.domain.Lodger;
import com.senla.hotel.dto.lodger.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class LodgerConvertor {
    private final ModelMapper modelMapper;

    public LodgerConvertor() {
        this.modelMapper = new ModelMapper();
    }

    public LodgerResponseDto convertToDto(Lodger lodger) {
        return modelMapper.map(lodger, LodgerResponseDto.class);
    }

    public LastLodgerResponseDto convertLastLodgerToDto(Long lodgerId, LocalDate date) {
        LastLodgerResponseDto lastLodgerResponseDto = new LastLodgerResponseDto();
        lastLodgerResponseDto.setLodgerId(lodgerId);
        lastLodgerResponseDto.setDate(date);
        return lastLodgerResponseDto;
    }

    public LodgerRoomResponseDto convertLodgerRoomToDto(Long lodgerId, Long roomId){
        LodgerRoomResponseDto result = new LodgerRoomResponseDto();
        result.setRoomId(roomId);
        result.setLodgerId(lodgerId);
        return result;
    }

    public ReservationCostsResponseDto convertReservationCostsToDto(Long roomId, BigDecimal cost){
        ReservationCostsResponseDto result = new ReservationCostsResponseDto();
        result.setRoomId(roomId);
        result.setCost(cost);
        return result;
    }

    public Lodger convertToEntity(SaveLodgerRequestDto updateLodgerDto) {
        return modelMapper.map(updateLodgerDto, Lodger.class);
    }

    public SaveLodgerRequestDto convertSaveToDto(Lodger lodger) {
        return modelMapper.map(lodger, SaveLodgerRequestDto.class);
    }
    
    public Lodger convertToEntity(LodgerResponseDto lodgerResponseDto) {
        return modelMapper.map(lodgerResponseDto, Lodger.class);
    }
}
