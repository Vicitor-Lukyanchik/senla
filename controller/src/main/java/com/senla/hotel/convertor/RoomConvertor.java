package com.senla.hotel.convertor;

import com.senla.hotel.entity.Room;
import com.senla.hotel.dto.room.RoomCostsResponseDto;
import com.senla.hotel.dto.room.RoomResponseDto;
import com.senla.hotel.dto.room.SaveRoomRequestDto;
import com.senla.hotel.dto.room.UpdateRoomRequestDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoomConvertor {
    private final ModelMapper modelMapper;

    public RoomConvertor() {
        this.modelMapper = new ModelMapper();
    }

    public RoomCostsResponseDto convertRoomCostsToDto(Room room){
        RoomCostsResponseDto roomCostsResponseDto = new RoomCostsResponseDto();
        roomCostsResponseDto.setId(room.getId());
        roomCostsResponseDto.setCost(room.getCost());
        return roomCostsResponseDto;
    }

    public UpdateRoomRequestDto convertUpdateToDto(Room room) {
        return modelMapper.map(room, UpdateRoomRequestDto.class);
    }

    public Room convertToEntity(UpdateRoomRequestDto updateRoomDto) {
        return modelMapper.map(updateRoomDto, Room.class);
    }

    public Room convertToEntity(SaveRoomRequestDto updateRoomDto) {
        return modelMapper.map(updateRoomDto, Room.class);
    }

    public SaveRoomRequestDto convertSaveToDto(Room room) {
        return modelMapper.map(room, SaveRoomRequestDto.class);
    }

    public RoomResponseDto convertToDto(Room room) {
        return modelMapper.map(room, RoomResponseDto.class);
    }

    public Room convertToEntity(RoomResponseDto roomResponseDto) {
        return modelMapper.map(roomResponseDto, Room.class);
    }
}
