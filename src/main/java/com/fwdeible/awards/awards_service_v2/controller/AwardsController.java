package com.fwdeible.awards.awards_service_v2.controller;

import com.fwdeible.awards.awards_service_v2.model.Award;
import com.fwdeible.awards.awards_service_v2.model.dto.CombineAwardsRequest;
import com.fwdeible.awards.awards_service_v2.model.dto.CompletedImageDTO;
import com.fwdeible.awards.awards_service_v2.repository.AwardRepository;
import com.fwdeible.awards.awards_service_v2.service.ImageFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
public class AwardsController {

    @Autowired
    AwardRepository awardRepository;

    @Autowired
    ImageFormatter imageFormatter;

    @PostMapping(path="/all")
    public @ResponseBody void getAllAwards() {
      log.debug("get ALL awards");




    }

    @GetMapping(path="/test")
    public void handleTest() {
        log.debug("TEST!");
    }

    @GetMapping(path="test2-electric")
    public ResponseEntity handleTest2() {
        System.out.println("****************************");
        log.info("Test 2!");
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path="/getAwardsList")
    public ResponseEntity<List<Award>> getAwardsList() {
        log.info("getAwardsList");

//        List<Award> awardList = new ArrayList<>();
//
//        Award a = new Award();
//        a.setId(1L);
//        a.setAwardName("Award Name");
//        a.setName("Name");
//        a.setCannonicalName("Caonon name");
//        a.setPrecedence(1);
//        a.setDescription("description");
//
//        awardList.add(a);



        return new ResponseEntity<>(awardRepository.findAll(), HttpStatus.OK);

    }

//    @GetMapping(path="/submit-selected")
//    public @ResponseBody CompletedImageDTO submitSelectedAwards() {
//
//        List<Integer> awardIdList = new ArrayList<>();
//        awardIdList.add(1);
//        awardIdList.add(2);
//
//        CompletedImageDTO ci = new CompletedImageDTO();
//        ci.setText("Completed Image");
//
//        //TODO inject
//        ImageFormatter imageFormatter = new ImageFormatter();
//        ci.setImage(imageFormatter.createCombinedImage(awardIdList));
//        return ci;
//    }

//    @GetMapping("/my-awards")
//    public ResponseEntity<byte[]> getPreviewImage()  {
//
//        log.debug("my-awards called ");
//        ImageFormatter imageFormatter = new ImageFormatter();
//
//        List<Integer> awardIdList = new ArrayList<>();
//        awardIdList.add(1);
//        awardIdList.add(2);
//
//        byte[] imageBytes = imageFormatter.createCombinedImage(awardIdList);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_PNG);
//        headers.setContentLength(imageBytes.length);
//
//        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
//    }

//    @GetMapping("/combine-awards")
//    public ResponseEntity<CompletedImageDTO> getCombinedAwards() {
//        log.debug("combine-awards called ");
//        ImageFormatter imageFormatter = new ImageFormatter();
//
//        List<Integer> awardIdList = new ArrayList<>();
//        awardIdList.add(1);
//        awardIdList.add(2);
//
//        String bases64Image = imageFormatter.getCombinedBase64Image(awardIdList);
//
//        // TODO: This should be in the service!
//        CompletedImageDTO completedImage = new CompletedImageDTO();
//        completedImage.setBase64image(bases64Image);
//        completedImage.setText("Completed Image");
//
//        return new ResponseEntity<>(completedImage, HttpStatus.OK);
//
//
//    }

    // this works!
//    @GetMapping(value = "/combine-awards", produces = MediaType.IMAGE_PNG_VALUE)
//    public ResponseEntity<byte[]> getCombinedAwardsImage() {
//
//
//        List<Integer> awardIdList = new ArrayList<>();
//        awardIdList.add(1);
//        awardIdList.add(2);
//        ImageFormatter imageFormatter = new ImageFormatter();
//        byte[] imageBytes = imageFormatter.getByteArray( imageFormatter.createCombinedImage(awardIdList));
//        return ResponseEntity
//                .ok()
//                .contentType(MediaType.IMAGE_PNG)
//                .body(imageBytes);
//    }

    @PostMapping(value = "/combine-awards", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getCombinedAwardsImage(@RequestBody() CombineAwardsRequest req) {

        List<Long> ids = req.getAwardIds();
        log.debug("getCombinedAwardsImage ids: " + ids);
//        List<Integer> awardIdList = new ArrayList<>();
//        awardIdList.add(1);
//        awardIdList.add(2);
//        ImageFormatter imageFormatter = new ImageFormatter();
        byte[] imageBytes = imageFormatter.getByteArray( imageFormatter.createCombinedImage(ids));
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(imageBytes);
    }

}
