package com.clinicarodriguez.clinicarodriguez.service.serviceimpl;

import com.clinicarodriguez.clinicarodriguez.model.Documentos;
import com.clinicarodriguez.clinicarodriguez.repository.DocumentosRepository;
import com.clinicarodriguez.clinicarodriguez.service.DocumentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentosServiceImpl implements DocumentosService {

    @Autowired
    private DocumentosRepository documentosRepository;

    @Override
    public List<Documentos> listarTodos() {
        return documentosRepository.findAll();
    }

    @Override
    public Optional<Documentos> buscarPorId(Long id) {
        return documentosRepository.findById(id);
    }

    @Override
    public Documentos guardar(Documentos documento) {
        return documentosRepository.save(documento);
    }

    @Override
    public Documentos actualizar(Long id, Documentos documento) {
        if (documentosRepository.existsById(id)) {
            documento.setDocuId(id);
            return documentosRepository.save(documento);
        }
        throw new RuntimeException("Documento con ID " + id + " no encontrado");
    }

    @Override
    public void eliminar(Long id) {
        if (documentosRepository.existsById(id)) {
            documentosRepository.deleteById(id);
        } else {
            throw new RuntimeException("Documento con ID " + id + " no encontrado");
        }
    }

    @Override
    public List<Documentos> buscarPorPaciente(Long paciId) {
        return documentosRepository.findByPacientePaciId(paciId);
    }

    @Override
    public List<Documentos> buscarPorHistoria(Long histId) {
        return documentosRepository.findByHistoriaHistId(histId);
    }

    @Override
    public List<Documentos> buscarPorPacienteYEstado(Long paciId, Boolean estado) {
        return documentosRepository.findByPacientePaciIdAndDocuEstado(paciId, estado);
    }

    @Override
    public List<Documentos> buscarVisiblesParaPaciente(Long paciId, Boolean visiblePaciente, Boolean estado) {
        return documentosRepository.findByPacientePaciIdAndDocuVisiblePacienteAndDocuEstado(paciId, visiblePaciente, estado);
    }

    @Override
    public List<Documentos> buscarConfidenciales(Boolean confidencial, Boolean estado) {
        return documentosRepository.findByDocuConfidencialAndDocuEstado(confidencial, estado);
    }

    @Override
    public List<Documentos> buscarPorTipo(String tipo) {
        return documentosRepository.findByDocuTipo(tipo);
    }

    @Override
    public List<Documentos> buscarPorTipoYPaciente(String tipo, Long paciId) {
        return documentosRepository.findByDocuTipoAndPacientePaciId(tipo, paciId);
    }

    @Override
    public List<Documentos> buscarPorEstado(Boolean estado) {
        return documentosRepository.findByDocuEstado(estado);
    }
}
