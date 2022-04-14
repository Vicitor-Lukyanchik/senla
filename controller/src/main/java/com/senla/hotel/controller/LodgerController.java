package com.senla.hotel.controller;

import com.senla.hotel.convertor.LodgerConvertor;
import com.senla.hotel.convertor.ReservationConvertor;
import com.senla.hotel.convertor.ServiceConvertor;
import com.senla.hotel.convertor.ServiceOrderConvertor;
import com.senla.hotel.domain.Lodger;
import com.senla.hotel.domain.Room;
import com.senla.hotel.dto.lodger.LodgerResponseDto;
import com.senla.hotel.dto.lodger.LodgerRoomResponseDto;
import com.senla.hotel.dto.lodger.ReservationCostsResponseDto;
import com.senla.hotel.dto.lodger.SaveLodgerRequestDto;
import com.senla.hotel.dto.reservation.SaveReservationRequestDto;
import com.senla.hotel.dto.service.ServiceResponseDto;
import com.senla.hotel.dto.serviceorder.SaveServiceOrderRequestDto;
import com.senla.hotel.service.LodgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lodger")
public class LodgerController {

    private final LodgerService lodgerService;
    private final LodgerConvertor lodgerConvertor;
    private final ReservationConvertor reservationConvertor;
    private final ServiceOrderConvertor serviceOrderConvertor;
    private final ServiceConvertor serviceConvertor;

    @GetMapping(value = "/{idLodger}")
    public ResponseEntity<LodgerResponseDto> findById(@PathVariable("idLodger") Long id) {
        return ResponseEntity.ok(lodgerConvertor
                .convertToDto(lodgerService.findById(id)));
    }

    @PostMapping("/save")
    public ResponseEntity<Void> create(@Valid @RequestBody final SaveLodgerRequestDto lodger) {
        lodgerService.create(lodgerConvertor
                .convertToEntity(lodger));
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/count")
    public ResponseEntity<Integer> findCount() {
        return ResponseEntity.ok(lodgerService.findAll().size());
    }


    @PostMapping("/put")
    public ResponseEntity<Void> putReservationInRoom(@Valid @RequestBody final SaveReservationRequestDto reservation) {
        lodgerService.createReservation(reservationConvertor
                .convertToEntity(reservation));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/evict")
    public ResponseEntity<Void> update(@RequestParam Long lodgerId, @RequestParam Long roomId) {
        lodgerService.updateReservationReserved(lodgerId, roomId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/lodgers_rooms")
    public ResponseEntity<List<LodgerRoomResponseDto>> findLodgersRooms() {
        List<LodgerRoomResponseDto> result = new ArrayList<>();
        for (Map.Entry<Lodger, Room> lodgerRoomEntry : lodgerService.findAllLodgersRooms().entrySet()) {
            result.add(lodgerConvertor.convertLodgerRoomToDto(lodgerRoomEntry.getKey().getId(),
                    lodgerRoomEntry.getValue().getId()));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{lodgerId}/reservation_costs")
    public ResponseEntity<List<ReservationCostsResponseDto>> findReservationsCosts(@PathVariable Long lodgerId) {
        List<ReservationCostsResponseDto> result = new ArrayList<>();
        for (Map.Entry<Long, BigDecimal> reservationCost : lodgerService.findReservationCostByLodgerId(lodgerId).entrySet()) {
            result.add(lodgerConvertor.convertReservationCostsToDto(reservationCost.getKey(), reservationCost.getValue()));
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/order_service")
    public ResponseEntity<Void> putServiceOrderInRoom(@Valid @RequestBody final SaveServiceOrderRequestDto serviceOrder) {
        lodgerService.createServiceOrder(serviceOrderConvertor
                .convertToEntity(serviceOrder));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{lodgerId}/services")
    public ResponseEntity<List<ServiceResponseDto>> findLodgerServices(@PathVariable Long lodgerId) {
        return ResponseEntity.ok(lodgerService.findServiceOrderByLodgerId(lodgerId)
                .stream()
                .map(serviceConvertor::convertToDto)
                .collect(Collectors.toList()));
    }

    @PutMapping("/import")
    public ResponseEntity<Void> importLodgers() {
        lodgerService.importLodgers();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{lodgerId}/export")
    public ResponseEntity<Void> exportRoom(@PathVariable("lodgerId") Long id) {
        lodgerService.exportLodger(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reservation/import")
    public ResponseEntity<Void> importReservations() {
        lodgerService.importServiceOrders();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reservation/{reservationOrderId}/export")
    public ResponseEntity<Void> exportReservation(@PathVariable("reservationOrderId") Long id) {
        lodgerService.exportServiceOrder(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/service_order/import")
    public ResponseEntity<Void> importServiceOrders() {
        lodgerService.importServiceOrders();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/service_order/{serviceOrderId}/export")
    public ResponseEntity<Void> exportServiceOrder(@PathVariable("serviceOrderId") Long id) {
        lodgerService.exportServiceOrder(id);
        return ResponseEntity.ok().build();
    }
}
