package io.ketlv.ediplomadapp.controller;

import io.ketlv.ediplomadapp.domain.Diploma;
import io.ketlv.ediplomadapp.services.DiplomaService;
import io.ketlv.ediplomadapp.services.impl.ExcelService;
import io.ketlv.ediplomadapp.utils.ExcelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/diploma")
@RequiredArgsConstructor
public class DiplomaController {
     private final DiplomaService diplomaService;
     private final ExcelService excelService;

     @GetMapping
     public ResponseEntity<?> getAll() {
         try {
             return new ResponseEntity<>(diplomaService.findAll(), HttpStatus.OK);
         } catch (Exception e) {
             e.printStackTrace();
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
         }
     }

    @GetMapping("/{serialNumber}")
    public ResponseEntity<?> getOne(@PathVariable String serialNumber) {
        try {
            return new ResponseEntity<>(diplomaService.findOneBySerialNumber(serialNumber), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Diploma> createDiploma(@RequestBody Diploma diploma) {
        return new ResponseEntity<>(diplomaService.save(diploma), HttpStatus.CREATED);
    }

    @PostMapping("/upload-diplomas")
    public ResponseEntity<?> uploadStudentsGraduation(@RequestParam("file") MultipartFile file,
                                                      @RequestParam("schoolSymbol") String schoolSymbol,
                                                      @RequestParam("graduationCatalogId") String graduationCatalogId) throws IOException {
        if (ExcelUtil.hasExcelFormat(file)) {
            excelService.uploadListDiplomas(file, schoolSymbol, graduationCatalogId);
        } else {
            throw new RuntimeException("Format diploma incorrect!");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

//    public ResponseEntity<?> verifyDiploma(@RequestParam("serialnumber") String serialNumber,
//                                           @RequestParam("referencenumber") String referenceNumber) {
//        return new ResponseEntity<>()(diplomaService.)
//    }

}
