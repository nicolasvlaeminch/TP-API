package com.turnosrotativos.validator;

import com.turnosrotativos.dto.EmpleadoDTO;
import com.turnosrotativos.entity.Empleado;
import com.turnosrotativos.exceptions.BusinessException;
import com.turnosrotativos.repository.IEmpleadoRepository;
import com.turnosrotativos.repository.IJornadaLaboralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class EmpleadoValidator {
    @Autowired
    private IEmpleadoRepository empleadoRepository;

    @Autowired
    private IJornadaLaboralRepository jornadaLaboralRepository;

    public void validarRegistrarEmpleado(EmpleadoDTO empleadoDTO) {
        validarEdad(empleadoDTO.getFechaNacimiento());
        validarNumeroDocumento(empleadoDTO.getNroDocumento());
        validarEmail(empleadoDTO.getEmail());
    }

    public void validarActualizarEmpleado(EmpleadoDTO empleadoDTO, Empleado empleado) {
        validarEdad(empleadoDTO.getFechaNacimiento());
        validarUnicidadNumeroDocumento(empleado, empleadoDTO);
        validarUnicidadEmail(empleado, empleadoDTO);
    }

    private void validarEdad(LocalDate fechaNacimiento) {
        if (Period.between(fechaNacimiento, LocalDate.now()).getYears() < 18) {
            throw new BusinessException("La edad del empleado no puede ser menor a 18 años.", HttpStatus.BAD_REQUEST);
        }
    }

    private void validarNumeroDocumento(Long nroDocumento) {
        if (empleadoRepository.existsByNroDocumento(nroDocumento)) {
            throw new BusinessException("Ya existe un empleado con el documento ingresado.", HttpStatus.CONFLICT);
        }
    }

    private void validarEmail(String email) {
        if (empleadoRepository.existsByEmail(email)) {
            throw new BusinessException("Ya existe un empleado con el email ingresado.", HttpStatus.CONFLICT);
        }
    }

    public void validarEmpleadoPorId(Long idEmpleado) {
        if (!empleadoRepository.existsById(idEmpleado)) {
            throw new BusinessException("No se encontró el empleado con Id: " + idEmpleado, HttpStatus.NOT_FOUND);
        }
    }

    public void validarUnicidadNumeroDocumento(Empleado empleado, EmpleadoDTO empleadoDTO) {
        if (!empleado.getNroDocumento().equals(empleadoDTO.getNroDocumento())) {
            boolean existeOtroEmpleado = empleadoRepository.existsByNroDocumento(empleadoDTO.getNroDocumento());
            if (existeOtroEmpleado) {
                throw new BusinessException("Ya existe un empleado con el número de documento: " + empleadoDTO.getNroDocumento(), HttpStatus.CONFLICT);
            }
        }
    }

    public void validarUnicidadEmail(Empleado empleado, EmpleadoDTO empleadoDTO) {
        if (!empleado.getEmail().equals(empleadoDTO.getEmail())) {
            boolean existeOtroEmpleadoConEmail = empleadoRepository.existsByEmail(empleadoDTO.getEmail());
            if (existeOtroEmpleadoConEmail) {
                throw new BusinessException("Ya existe un empleado con el email: " + empleadoDTO.getEmail(), HttpStatus.CONFLICT);
            }
        }
    }

    public Empleado buscarEmpleadoPorId(Long empleadoId) {
        return empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new BusinessException("No se encontró el empleado con Id: " + empleadoId, HttpStatus.NOT_FOUND));
    }

    public void validarJornadasAsociadas(Long empleadoId) {
        if (jornadaLaboralRepository.existsByEmpleadoId(empleadoId)) {
            throw new BusinessException("No es posible eliminar un empleado con jornadas asociadas.", HttpStatus.BAD_REQUEST);
        }
    }
}
