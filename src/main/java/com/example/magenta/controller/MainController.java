package com.example.magenta.controller;

import com.example.magenta.dto.CalculationType;
import com.example.magenta.dto.CityDto;
import com.example.magenta.dto.DistanceDto;
import com.example.magenta.service.CityService;
import com.example.magenta.service.DistanceService;
import com.example.magenta.service.XmlUploadService;
import com.example.magenta.xml.UploadData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final CityService cityService;
    private final DistanceService distanceService;
    private final XmlUploadService xmlUploadService;

    @GetMapping
    public Page<CityDto> getCities(@RequestParam @NonNull Integer pageNumber,
                                   @RequestParam @NonNull Integer pageSize) {
        return cityService.getAllCities(pageNumber, pageSize);
    }

    @GetMapping("/calculate")
    public List<DistanceDto> getDistances(
            @RequestParam(required = false, defaultValue = "DISTANCE_MATRIX") CalculationType calculationType,
            @RequestParam List<String> from,
            @RequestParam List<String> to) {
        return distanceService.getDistances(calculationType, from, to);
    }

    @PostMapping(value = "/upload",
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
            produces = { MediaType.APPLICATION_XML_VALUE })
    public void uploadXml(@RequestPart("file") UploadData data) {
        xmlUploadService.uploadXml(data);
    }
}
