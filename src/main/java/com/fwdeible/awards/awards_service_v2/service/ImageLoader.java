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

        public  BufferedImage loadImage(Long id) {

            String imageName = awardRepository.findById(id)
                    .map(Award::getImageName)
                    .orElse(null);

            return loadImage(imageName);

        }
        public  BufferedImage loadImage(String imageName)  {

            try (InputStream is = ImageLoader.class.getResourceAsStream("/static/images/" + imageName)) {
                if (is != null) {
                    return ImageIO.read(is);
                } else {
                    log.warn("Image not found for " + imageName + ", falling back to placeholder.png");
                    InputStream placeholder = ImageLoader.class.getResourceAsStream("/static/images/placeholder.png");
                    if (placeholder != null) {
                        return ImageIO.read(placeholder);
                    } else {
                        log.error("Placeholder image also not found.");
                        throw new IllegalStateException("Placeholder image missing.");
                    }
                }
            } catch (Exception e) {
                log.error("Error loading image: " + imageName, e);
                return null;
            }
        }

}
