package ru.bigint.webapp.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bigint.webapp.service.iface.KmlJobService;

@RestController
@RequestMapping(value = "/api/jobs")
public class KmlRestController {

    private final KmlJobService kmlJobService;

    public KmlRestController(KmlJobService kmlJobService) {
        this.kmlJobService = kmlJobService;
    }

    @GetMapping("/{id}.txt")
    @ResponseBody
    public ResponseEntity<byte[]> kml(@PathVariable String id) {
        byte[] result = kmlJobService.getKmlResultById(id);
        return ResponseEntity.ok()
                .header("Content-Type", "text/plain")
                .body(result);
    }

}
