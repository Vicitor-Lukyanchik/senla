package com.senla.hotel.convertor;

import com.senla.hotel.entity.Service;
import com.senla.hotel.dto.service.SaveServiceRequestDto;
import com.senla.hotel.dto.service.ServiceResponseDto;
import com.senla.hotel.dto.service.UpdateServiceRequestDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ServiceConvertor {
    private final ModelMapper modelMapper;

    public ServiceConvertor() {
        this.modelMapper = new ModelMapper();
    }
    
    public UpdateServiceRequestDto convertUpdateToDto(Service service) {
        return modelMapper.map(service, UpdateServiceRequestDto.class);
    }

    public Service convertToEntity(UpdateServiceRequestDto updateServiceDto) {
        return modelMapper.map(updateServiceDto, Service.class);
    }

    public Service convertToEntity(SaveServiceRequestDto updateServiceDto) {
        return modelMapper.map(updateServiceDto, Service.class);

    }

    public SaveServiceRequestDto convertSaveToDto(Service service) {
        return modelMapper.map(service, SaveServiceRequestDto.class);
    }

    public ServiceResponseDto convertToDto(Service service) {
        return modelMapper.map(service, ServiceResponseDto.class);
    }

    public Service convertToEntity(ServiceResponseDto serviceResponseDto) {
        return modelMapper.map(serviceResponseDto, Service.class);
    } 
}
