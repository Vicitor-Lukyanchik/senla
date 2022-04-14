package com.senla.hotel.controller;

import com.senla.hotel.convertor.ServiceConvertor;
import com.senla.hotel.domain.Service;
import com.senla.hotel.dto.service.SaveServiceRequestDto;
import com.senla.hotel.dto.service.ServiceResponseDto;
import com.senla.hotel.dto.service.UpdateServiceRequestDto;
import com.senla.hotel.exception.FileException;
import com.senla.hotel.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/service")
public class ServiceController {

    private final ServiceService serviceService;
    private final ServiceConvertor serviceConvertor;

    @GetMapping("/services")
    public ResponseEntity<List<ServiceResponseDto>> findAll() {
        return ResponseEntity.ok(serviceService.findAll()
                .stream()
                .map(serviceConvertor::convertToDto)
                .collect(Collectors.toList()));
    }

    @PostMapping("/save")
    public ResponseEntity<Void> create(@Valid @RequestBody final SaveServiceRequestDto service) {
        serviceService.create(serviceConvertor
                .convertToEntity(service));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@Valid @RequestBody final UpdateServiceRequestDto updateServiceRequestDto) {
        Service service = serviceConvertor.convertToEntity(updateServiceRequestDto);
        serviceService.updateCost(service.getId(), service.getCost());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/import")
    public ResponseEntity<Void> importServices() {
        serviceService.importServices();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{serviceId}/export")
    public ResponseEntity<Void> exportService(@PathVariable("serviceId") Long id) {
        serviceService.exportService(id);
        return ResponseEntity.ok().build();
    }
}
