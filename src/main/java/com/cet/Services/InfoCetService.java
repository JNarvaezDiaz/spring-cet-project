package com.cet.Services;

import com.cet.Models.FailedInfoCet;
import com.cet.Models.InfoCet;
import com.cet.Repositories.InfoCetRepositoryI;
import com.cet.dtos.FailedInfoCetDto;
import com.cet.dtos.InfoCetDto;
import com.cet.utils.InfoCetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InfoCetService {

    @Autowired
    private InfoCetRepositoryI infoCetRepositoryI;

    @Autowired
    private FailedInfoCetService infoCetService;

    public List<InfoCet> findAll() {
        return this.infoCetRepositoryI.findAll();
    }

    public InfoCet isCreateOrUpdate(Long idConfirmado, InfoCetDto infoCetDto) {
        if(infoCetDto.getUpdatingInfoCet()){
            Optional<InfoCet> infoCet = this.infoCetRepositoryI
                    .findByIdentificacionAndCetId(infoCetDto.getIdentificacion(), infoCetDto.getCet().getId());
            if (infoCet.isPresent()) {
                InfoCetUtils.setCovidContactoAndFueConfirmado(infoCetDto.getCovidContacto(), infoCetDto.getFueConfirmado());
                infoCet.get().setCovidContacto(InfoCetUtils.getCovidContacto());
                infoCet.get().setFueConfirmado(InfoCetUtils.getFueConfirmado());
                this.update(idConfirmado, infoCetDto);
                return this.infoCetRepositoryI.findByIdentificacionAndCetId(
                                infoCetDto.getIdentificacion(),
                                infoCetDto.getCet().getId()
                        ).get();
            }
        }
        return this.save(infoCetDto);
    }

    public InfoCet save(InfoCetDto infoCetDto) {

        if(infoCetDto.getFechaExpedicion() == null) {
            infoCetDto.setFechaExpedicion(new Date(1900, Calendar.JANUARY, 1));
        }

        InfoCet infoCet = InfoCet.builder()
                .numeroCaso(infoCetDto.getNumeroCaso())
                .fechaDiagnostico(infoCetDto.getFechaDiagnostico())
                .bduaAfiliadoId(infoCetDto.getBduaAfiliadoId())
                .tipoId(infoCetDto.getTipoId())
                .identificacion(infoCetDto.getIdentificacion())
                .nombre1(infoCetDto.getNombre1())
                .nombre2(infoCetDto.getNombre2())
                .apellido1(infoCetDto.getApellido1())
                .apellido2(infoCetDto.getApellido2())
                .fechaNacimiento(infoCetDto.getFechaNacimiento())
                .sexo(infoCetDto.getSexo())
                .fallecido(infoCetDto.getFallecido())
                .codEps(infoCetDto.getCodEps())
                .productoFinanciero(infoCetDto.getProductoFinanciero())
                .entidadFinancieraId(infoCetDto.getEntidadFinancieraId())
                .giroAFamiliar(infoCetDto.getGiroAFamiliar())
                .telefonoFijo(infoCetDto.getTelefonoFijo())
                .celular(infoCetDto.getCelular())
                .fechaExpedicion(infoCetDto.getFechaExpedicion())
                .email(infoCetDto.getEmail())
                .direccion(infoCetDto.getDireccion())
                .codigoDepartamento(infoCetDto.getCodigoDepartamento())
                .codigoMunicipio(infoCetDto.getCodigoMunicipio())
                .idBduaAfConfirmado(infoCetDto.getIdBduaAfConfirmado())
                .tipoidAfConfirmado(infoCetDto.getTipoidAfConfirmado())
                .identificacionAfConfirmado(infoCetDto.getIdentificacionAfConfirmado())
                .cumpleAislamiento(infoCetDto.getCumpleAislamiento())
                .autorizaEps(infoCetDto.getAutorizaEps())
                .parentescoId(infoCetDto.getParentescoId())
                .compartenGastos(infoCetDto.getCompartenGastos())
                .noEfectividad(infoCetDto.getNoEfectividad())
                .cet(infoCetDto.getCet())
                .build();
        InfoCetUtils.restartAttributes();
        return this.infoCetRepositoryI.save(infoCet);
    }

    public Optional<InfoCet> findOne(Long id) {
        return this.infoCetRepositoryI.findOne(id);
    }

    public InfoCet update(Long idConfirmado, InfoCetDto infoCetDto) {
        InfoCet infoCet = InfoCet.builder()
                .id(infoCetDto.getId())
                .numeroCaso(infoCetDto.getNumeroCaso())
                .fechaDiagnostico(infoCetDto.getFechaDiagnostico())
                .bduaAfiliadoId(infoCetDto.getBduaAfiliadoId())
                .tipoId(infoCetDto.getTipoId())
                .identificacion(infoCetDto.getIdentificacion())
                .nombre1(infoCetDto.getNombre1())
                .nombre2(infoCetDto.getNombre2())
                .apellido1(infoCetDto.getApellido1())
                .apellido2(infoCetDto.getApellido2())
                .fechaNacimiento(infoCetDto.getFechaNacimiento())
                .sexo(infoCetDto.getSexo())
                .fallecido(infoCetDto.getFallecido())
                .codEps(infoCetDto.getCodEps())
                .productoFinanciero(infoCetDto.getProductoFinanciero())
                .entidadFinancieraId(infoCetDto.getEntidadFinancieraId())
                .giroAFamiliar(infoCetDto.getGiroAFamiliar())
                .telefonoFijo(infoCetDto.getTelefonoFijo())
                .celular(infoCetDto.getCelular())
                .fechaExpedicion(infoCetDto.getFechaExpedicion())
                .email(infoCetDto.getEmail())
                .direccion(infoCetDto.getDireccion())
                .codigoDepartamento(infoCetDto.getCodigoDepartamento())
                .codigoMunicipio(infoCetDto.getCodigoMunicipio())
                .idBduaAfConfirmado(infoCetDto.getIdBduaAfConfirmado())
                .tipoidAfConfirmado(infoCetDto.getTipoidAfConfirmado())
                .identificacionAfConfirmado(infoCetDto.getIdentificacionAfConfirmado())
                .cumpleAislamiento(infoCetDto.getCumpleAislamiento())
                .autorizaEps(infoCetDto.getAutorizaEps())
                .parentescoId(infoCetDto.getParentescoId())
                .compartenGastos(infoCetDto.getCompartenGastos())
                .cet(infoCetDto.getCet())
                .build();

        if(infoCetDto.getLocaliza()) {
            infoCet.setNoEfectividad(infoCetDto.getNoEfectividad());

            Optional<InfoCet> cabezaFamiliar = this.infoCetRepositoryI.findOne(idConfirmado);

            if(cabezaFamiliar.isPresent() && cabezaFamiliar.get().getCovidContacto() == 1) {
                infoCet.setTipoidAfConfirmado(cabezaFamiliar.get().getTipoidAfConfirmado());
                infoCet.setIdentificacionAfConfirmado(cabezaFamiliar.get().getIdentificacionAfConfirmado());
                infoCet.setIdBduaAfConfirmado(cabezaFamiliar.get().getIdBduaAfConfirmado());
            }

            this.infoCetRepositoryI.update(infoCet);
            InfoCetUtils.restartAttributes();
        } else {
            FailedInfoCetDto failedInfoCet = FailedInfoCetDto.builder()
                    .infoCet(infoCet)
                    .descripcion(infoCetDto.getNoEfectividad())
                    .build();
            this.infoCetService.save(failedInfoCet);
        }

        return this.infoCetRepositoryI.findOne(idConfirmado).get();
    }

    public InfoCet vincularContacto(Long idContacto, Long idCOnfirmado) {
        Optional<InfoCet> confirmado = this.infoCetRepositoryI.findOne(idContacto);
        Optional<InfoCet> contacto = this.infoCetRepositoryI.findOne(idCOnfirmado);

        if(confirmado.isPresent() && contacto.isPresent()) {
            InfoCetUtils.setCovidContactoAndFueConfirmado(contacto.get().getCovidContacto(), contacto.get().getFueConfirmado());
            if(contacto.get().getIdentificacionAfConfirmado().isEmpty()){
                contacto.get().setCovidContacto(InfoCetUtils.getCovidContacto());
                contacto.get().setFueConfirmado(InfoCetUtils.getFueConfirmado());
                contacto.get().setIdBduaAfConfirmado(confirmado.get().getBduaAfiliadoId());
                contacto.get().setTipoidAfConfirmado(confirmado.get().getTipoId());
                contacto.get().setIdentificacionAfConfirmado(confirmado.get().getIdentificacion());
                this.infoCetRepositoryI.save(contacto.get());
            } else {
                contacto.get().setCovidContacto(InfoCetUtils.getCovidContacto());
                contacto.get().setFueConfirmado(InfoCetUtils.getFueConfirmado());
                contacto.get().setIdBduaAfConfirmado(null);
                contacto.get().setTipoidAfConfirmado(null);
                contacto.get().setIdentificacionAfConfirmado(null);
                this.infoCetRepositoryI.save(contacto.get());
            }
        }
        return confirmado.get();
    }

    public void delete(Long id) {
        Optional<InfoCet> infoCet = this.infoCetRepositoryI.findOne(id);
        if(infoCet.isPresent()) {
            this.infoCetRepositoryI.delete(id);
        }
    }


}
