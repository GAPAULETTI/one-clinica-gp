package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.direccion.DatosDireccion;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping()
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                          UriComponentsBuilder uriComponentsBuilder){
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(), medico.getDocumento(), medico.getEspecialidad(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getProvincia()));

        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(url).body(datosRespuestaMedico);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> getMedicoById(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(), medico.getDocumento(), medico.getEspecialidad(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getProvincia()));
        return ResponseEntity.ok(datosMedico);
    }
    /*@GetMapping
    public List<DatosListadoMedicos> listadoDeMedicos(){
        return medicoRepository.findAll().stream().map(DatosListadoMedicos::new).toList();
    }*/
    // Trabajar paginacion utilizando PAGE
    @GetMapping
    public ResponseEntity<Page<DatosListadoMedicos>>  listadoDeMedicos(@PageableDefault(size = 10) Pageable paginacion){
        //return medicoRepository.findAll(paginacion).map(DatosListadoMedicos::new);
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedicos::new));
    }
    @PutMapping
    @Transactional
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(), medico.getDocumento(), medico.getEspecialidad(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getProvincia())));
    }
    //Delete lógico
    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
    }

    /* Método Delete permanente
    public void eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medicoRepository.delete(medico);
    }*/
}
