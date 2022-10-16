package ru.bigint.webapp.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.bigint.webapp.dto.KmlJobParams;
import ru.bigint.webapp.entity.KmlJob;
import ru.bigint.webapp.service.iface.KmlJobService;

import java.util.List;

@Controller
public class KmlController {

    private final KmlJobService kmlJobService;

    public KmlController(KmlJobService kmlJobService) {
        this.kmlJobService = kmlJobService;
    }

    @GetMapping("/jobs/{id}.kml")
    @ResponseBody
    public ResponseEntity<byte[]> kml(@PathVariable String id) {
        byte[] result = kmlJobService.getKmlResultById(id);
        return ResponseEntity.ok()
                .header("Content-Type", "application/vnd.google-earth.kml+xml")
                .body(result);
    }

    @GetMapping("/jobs/map/{id}")
    public ModelAndView showMap(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("jobId", id);
        modelAndView.setViewName("jobs/map");
        return modelAndView;
    }

    @GetMapping("/jobs/list")
    public ModelAndView list() {
        List<KmlJob> jobs = kmlJobService.listAllOrderedByDate();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("jobs", jobs);
        modelAndView.setViewName("jobs/list");
        return modelAndView;
    }

    @GetMapping("/jobs/create")
    public String createForm() {
        return "jobs/create";
    }

    @PostMapping(path = "/jobs/create", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String createAction(KmlJobParams params) {
        kmlJobService.createJob(params);
        return "redirect:/jobs/list";
    }
}
