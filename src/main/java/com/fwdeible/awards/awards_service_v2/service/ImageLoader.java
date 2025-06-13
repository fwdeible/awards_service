package com.fwdeible.awards.awards_service_v2.service;

import com.fwdeible.awards.awards_service_v2.model.Award;
import com.fwdeible.awards.awards_service_v2.repository.AwardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

@Component
@Slf4j
public class ImageLoader {

        @Autowired
        AwardRepository awardRepository;

        public  BufferedImage loadImage(String imageName)  {
            log.debug("getImage: {}", imageName);
            try (InputStream is = ImageLoader.class.getResourceAsStream("/static/images/" + imageName)) {
                if (is == null) {
                    throw new IllegalArgumentException("Image not found: " + imageName);
                }
                return ImageIO.read(is);
            } catch(Exception e) {
                log.error("error loading image", e);
                return null;
            }
        }

        public  BufferedImage loadImage(Long id) {
            // TODO look up image name by ID
            String imageName = awardRepository.findById(id)
                    .map(Award::getImageName)
                    .orElse(null);


            log.info("loading image by name: {}", imageName);
            try (InputStream is = ImageLoader.class.getResourceAsStream("/static/images/" + imageName)) {
                if (is == null) {
                    throw new IllegalArgumentException("Image not found: " + imageName);
                }
                return ImageIO.read(is);
            } catch (Exception e) {
                log.error("Exception loading image", e);
                return null;
            }
        }

}
