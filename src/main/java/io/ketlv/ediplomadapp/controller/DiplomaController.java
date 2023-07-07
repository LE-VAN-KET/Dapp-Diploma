package io.ketlv.ediplomadapp.controller;

import io.ketlv.ediplomadapp.domain.Diploma;
import io.ketlv.ediplomadapp.services.DiplomaService;
import io.ketlv.ediplomadapp.services.dto.DiplomaSearchDto;
import io.ketlv.ediplomadapp.services.dto.DiplomaStatusReq;
import io.ketlv.ediplomadapp.services.dto.UpdateDiplomaReq;
import io.ketlv.ediplomadapp.services.dto.VerifiedDiplomaReq;
import io.ketlv.ediplomadapp.services.impl.ExcelService;
import io.ketlv.ediplomadapp.utils.ExcelUtil;
import io.ketlv.ediplomadapp.utils.MinioUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/diploma")
@RequiredArgsConstructor
public class DiplomaController {
     private final DiplomaService diplomaService;
     private final ExcelService excelService;
     private final MinioUtil minioUtil;

     @GetMapping
     public ResponseEntity<?> getAll(@RequestParam(required = false) Long page,
                                     @RequestParam(required = false) Long pageSize,
                                     @RequestParam(required = false) Long yearGraduation) {
         try {
             return new ResponseEntity<>(diplomaService.findAll(page, pageSize, yearGraduation), HttpStatus.OK);
         } catch (Exception e) {
             e.printStackTrace();
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
         }
     }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable String id) {
        try {
            return new ResponseEntity<>(diplomaService.findOne(Long.parseLong(id)), HttpStatus.OK);
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
                                                      @RequestParam("graduationCatalogId") String graduationCatalogId,
                                                      @RequestParam("yearGraduation") String yearGraduation,
                                                      @RequestParam("signer") String signer,
                                                      @RequestParam("signerTitle") String signerTitle) throws IOException {
        if (ExcelUtil.hasExcelFormat(file)) {
            excelService.uploadListDiplomas(file, schoolSymbol, graduationCatalogId, yearGraduation, signer, signerTitle);
        } else {
            throw new RuntimeException("Format diploma incorrect!");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/verified")
    public ResponseEntity<?> verifyDiploma(@RequestBody VerifiedDiplomaReq req) {
         diplomaService.verifiedDiplomas(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{refNumber}")
    public ResponseEntity<?> updateStatusDiploma(@PathVariable("refNumber") String refNumber, @Valid @RequestBody DiplomaStatusReq req) throws Exception {
         diplomaService.updateStatus(req, refNumber);
        return new ResponseEntity<>("Update status successfully.", HttpStatus.OK);
    }

    @PutMapping(value = "/{serialNumber}", headers="Content-Type=multipart/form-data")
    public ResponseEntity<?> updateDiploma(@PathVariable("serialNumber") String serialNumber,
                                           @RequestPart("diploma")UpdateDiplomaReq req,
                                           @RequestParam(value = "file", required = false) MultipartFile file)  {
         diplomaService.partialUpdate(req, file);
         return new ResponseEntity<>("Update diploma successfully,", HttpStatus.OK);
    }

    @GetMapping(value = "/file/{hash}")
    public ResponseEntity<byte[]> getFile(@PathVariable("hash") String hash) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", MediaType.ALL_VALUE);
        byte[] bytes = diplomaService.loadFileDiploma(hash);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(bytes);

    }

    @PostMapping("/upload-file")
    public ResponseEntity<?> uploadFile(MultipartFile file) {
        return new ResponseEntity<>(minioUtil.uploadFile(file), HttpStatus.OK);
    }

    @GetMapping("/years")
    public ResponseEntity<?> getListYearGraduation() {
        return new ResponseEntity<>(diplomaService.getListYearGraduation(), HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody DiplomaSearchDto diplomaSearchDto) {
         return new ResponseEntity<>(diplomaService.search(diplomaSearchDto), HttpStatus.OK);
    }

}
