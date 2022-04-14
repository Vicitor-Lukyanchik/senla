package com.senla.hotel.controller;

import com.senla.hotel.convertor.LodgerConvertor;
import com.senla.hotel.convertor.RoomConvertor;
import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Room;
import com.senla.hotel.dto.lodger.LastLodgerResponseDto;
import com.senla.hotel.dto.room.RoomCostsResponseDto;
import com.senla.hotel.dto.room.RoomResponseDto;
import com.senla.hotel.dto.room.SaveRoomRequestDto;
import com.senla.hotel.dto.room.UpdateRoomRequestDto;
import com.senla.hotel.service.LodgerService;
import com.senla.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final LodgerService lodgerService;
    private final LodgerConvertor lodgerConvertor;
    private final RoomService roomService;
    private final RoomConvertor roomConvertor;

    @GetMapping("/rooms")
    public ResponseEntity<List<RoomResponseDto>> findAll() {
        return ResponseEntity.ok(roomService.findAll()
                .stream()
                .map(roomConvertor::convertToDto)
                .collect(Collectors.toList()));
    }

    @PostMapping("/save")
    public ResponseEntity<Void> create(@Valid @RequestBody final SaveRoomRequestDto room) {
        roomService.create(roomConvertor
                .convertToEntity(room));
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{roomId}")
    public ResponseEntity<RoomResponseDto> findById(@PathVariable("roomId") Long id) {
        return ResponseEntity.ok(roomConvertor
                .convertToDto(roomService.findById(id)));
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@Valid @RequestBody final UpdateRoomRequestDto updateRoomRequestDto) {
        Room room = roomConvertor.convertToEntity(updateRoomRequestDto);
        roomService.updateCost(room.getId(), room.getCost());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/rooms_costs")
    public ResponseEntity<List<RoomCostsResponseDto>> findRoomsCosts() {
        return ResponseEntity.ok(roomService.findAll()
                .stream()
                .map(roomConvertor::convertRoomCostsToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{roomId}/last_lodgers")
    public ResponseEntity<List<LastLodgerResponseDto>> findLastLodgers(@PathVariable("roomId") Long id) {
        List<LastLodgerResponseDto> result = new ArrayList<>();
        for (Map.Entry<LocalDate, Lodger> lodgerEntry : lodgerService.findLastReservationsByRoomId(id).entrySet()) {
            result.add(lodgerConvertor.convertLastLodgerToDto(lodgerEntry.getValue().getId(), lodgerEntry.getKey()));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/not_settled")
    public ResponseEntity<Integer> findCountNotSettledRooms() {
        return ResponseEntity.ok(lodgerService.findAllNotSettledRooms());
    }

    @GetMapping("/not_settled_date")
    public ResponseEntity<List<RoomResponseDto>> findNotSettledRoomsOnDate(@RequestParam String data) {
        return ResponseEntity.ok(lodgerService.findAllNotSettledRoomOnDate(data)
                        .stream()
                        .map(roomConvertor::convertToDto)
                        .collect(Collectors.toList()));
    }

    @PutMapping("/import")
    public ResponseEntity<Void> importRooms() {
        roomService.importRooms();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{roomId}/export")
    public ResponseEntity<Void> exportRoom(@PathVariable("roomId") Long id) {
        roomService.exportRoom(id);
        return ResponseEntity.ok().build();
    }
}
