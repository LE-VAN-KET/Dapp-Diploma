package io.ketlv.ediplomadapp.controller;

import io.ketlv.ediplomadapp.domain.GraduationCatalog;
import io.ketlv.ediplomadapp.services.GraduationCatalogService;
import io.ketlv.ediplomadapp.services.dto.CatalogPaging;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/graduation-catalog")
@RequiredArgsConstructor
public class GraduationCatalogController {
    private final GraduationCatalogService graduationCatalogService;

    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam(required = false) Long page, @RequestParam(required = false) Long pageSize) {
        try {
            CatalogPaging catalogPaging = new CatalogPaging(page, pageSize);
            return new ResponseEntity<>(graduationCatalogService.getAll(catalogPaging), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {
            //TODO Implement Your Logic To Get Data From Service Layer Or Directly From Repository Layer
            return new ResponseEntity<>(graduationCatalogService.getOneByID(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody GraduationCatalog dto) {
        try {
            return new ResponseEntity<>(graduationCatalogService.createGraduationCatalog(dto), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody GraduationCatalog dto, @PathVariable("id") Long id) {
        try {
            dto.setId(id);
            graduationCatalogService.updateCatalog(dto);
            return new ResponseEntity<>("Update success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> destroy(@PathVariable Long id) {
        try {
            graduationCatalogService.delete(id);
            return new ResponseEntity<>("Delete success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
