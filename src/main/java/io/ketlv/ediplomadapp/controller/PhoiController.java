package io.ketlv.ediplomadapp.controller;

import io.ketlv.ediplomadapp.services.PhoiService;
import io.ketlv.ediplomadapp.services.dto.PhoiDtoReq;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/phoi")
@AllArgsConstructor
public class PhoiController {
    private final PhoiService phoiService;

    @PostMapping
    public ResponseEntity<?> createPhoi(@Valid @RequestBody PhoiDtoReq req) {
        phoiService.addPhoi(req);
        return new ResponseEntity<>("Tạo phôi thành công.", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> revoke(@PathVariable("id") String id) {
        phoiService.revokePhoi(Long.parseLong(id));
        return new ResponseEntity<>("Thu hồi phôi thành công.", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false) Long page, @RequestParam(required = false) Long pageSize) {
        return new ResponseEntity<>(phoiService.getAll(page, pageSize), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @Valid @RequestBody PhoiDtoReq req) {
        phoiService.update(req,Long.parseLong(id));
        return new ResponseEntity<>("Cập nhật thành công.", HttpStatus.OK);
    }

}
