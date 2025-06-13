package com.fwdeible.awards.awards_service_v2.controller;

import com.fwdeible.awards.awards_service_v2.model.Award;
import com.fwdeible.awards.awards_service_v2.model.dto.CombineAwardsRequest;
import com.fwdeible.awards.awards_service_v2.repository.AwardRepository;
import com.fwdeible.awards.awards_service_v2.service.ImageFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Slf4j
@Controller
public class AwardsController {

    @Autowired
    AwardRepository awardRepository;

    @Autowired
    ImageFormatter imageFormatter;

    @GetMapping(path="/test")
    public String handleTest() {
        log.debug("TEST!");
        return "TEST OK";
    }


    @GetMapping(path="/getAwardsList")
    public ResponseEntity<List<Award>> getAwardsList() {
        log.info("getAwardsList");

        return new ResponseEntity<List<Award>>(awardRepository.findAll(), HttpStatus.OK);

    }

    @PostMapping(value = "/combine-awards", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getCombinedAwardsImage(@RequestBody() CombineAwardsRequest req) {

        List<Long> ids = req.getAwardIds();
        log.debug("getCombinedAwardsImage ids: {}", ids);

        byte[] imageBytes = ImageFormatter.getByteArray( imageFormatter.createCombinedImage(ids));
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(imageBytes);
    }

}
