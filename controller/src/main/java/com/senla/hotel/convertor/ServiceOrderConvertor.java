package com.senla.hotel.convertor;

import com.senla.hotel.domain.*;
import com.senla.hotel.dto.serviceorder.SaveServiceOrderRequestDto;
import com.senla.hotel.dto.serviceorder.ServiceOrderResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ServiceOrderConvertor {
    private final ModelMapper modelMapper;

    public ServiceOrderConvertor() {
        this.modelMapper = new ModelMapper();
    }

    public ServiceOrder convertToEntity(SaveServiceOrderRequestDto saveServiceOrderRequestDto) {
        return buildServiceOrder(saveServiceOrderRequestDto.getDate(), saveServiceOrderRequestDto.getLodgerId(),
                saveServiceOrderRequestDto.getServiceId());
    }

    public SaveServiceOrderRequestDto convertSaveToDto(ServiceOrder serviceOrder) {
        return modelMapper.map(serviceOrder, SaveServiceOrderRequestDto.class);
    }
    
    public ServiceOrderResponseDto convertToDto(ServiceOrder serviceOrder) {
        return modelMapper.map(serviceOrder, ServiceOrderResponseDto.class);
    }

    public ServiceOrder convertToEntity(ServiceOrderResponseDto serviceOrderResponseDto) {
        return buildServiceOrder(serviceOrderResponseDto.getDate(), serviceOrderResponseDto.getLodgerId(),
                serviceOrderResponseDto.getServiceId());
    }

    private ServiceOrder buildServiceOrder(LocalDate date, Long lodgerId, Long serviceId) {
        Lodger lodger = new Lodger();
        Service service = new Service();
        lodger.setId(lodgerId);
        service.setId(serviceId);
        return new ServiceOrder(date, lodger, service);
    }
}
