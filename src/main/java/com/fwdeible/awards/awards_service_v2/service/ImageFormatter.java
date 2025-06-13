package com.fwdeible.awards.awards_service_v2.service;


import com.fwdeible.awards.awards_service_v2.model.Award;
import com.fwdeible.awards.awards_service_v2.repository.AwardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

@Slf4j
@Component
public class ImageFormatter {


    @Autowired
    ImageLoader imageLoader;

    /*
     * Colors
     */

    //	private  final Color OD_GREEN = new Color(85, 107, 47);
    private static final Color CLEAR = new Color(0, 0, 0, 0);

    private static final int RIBBONS_PER_ROW = 3;
    private static final int MARGIN = 5;


    /*
     * Helper Method - convert byte[] to BufferedImage
     */
    public static BufferedImage getBufferedImage(byte[] imgBytes) {
        InputStream in = new ByteArrayInputStream(imgBytes);
        BufferedImage i;
        try {
            i = ImageIO.read(in);
            return i;
        } catch (IOException e) {
            log.error("Error in image formatter" , e);
            return null;
        }
    }

    /*
     * Helper method - convert BufferedImage to byte[]
     */
    public static byte[] getByteArray(BufferedImage image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageInByte = null;
        try {
            ImageIO.write(image, "png", baos);

            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            log.error("Error getting byte array", e);
        }

        return imageInByte;
    }

    public String getCombinedBase64Image(List objects) {
        BufferedImage combinedImage = createCombinedImage(objects);
        return getBase64Image(combinedImage);
    }

    public String getBase64Image(BufferedImage image) {

        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            String base64Image = Base64.getEncoder().encodeToString(baos.toByteArray());

            return base64Image;
        }catch(IOException e) {
            log.error("exception converting image byte array to base64", e);
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    public  BufferedImage createCombinedImage(List objects) {

        List<BufferedImage> imageList = new ArrayList<BufferedImage>();
        for (Object o : objects) {
            byte[] imgBytes;
            if (o instanceof Award) {
                log.debug("TODO"); //TODO
//                imgBytes = ((Award) o).getRibbonImage();
//                imageList.add(getBufferedImage(imgBytes));
            } else if (o instanceof byte[]) {
                imgBytes = (byte[]) o;
                imageList.add(getBufferedImage(imgBytes));
            } else if (o instanceof Long ) {
                imageList.add( getAwardImageById((Long)o));
            } else {
                log.error("createCombinedImage called with invalid object list");
                return null;
            }

        }
        return createCombinedImage(imageList.toArray(new BufferedImage[imageList.size()]));

    }

    public  BufferedImage getAwardImageById(Long id) {

        log.debug("getAwardimageById: {}", id);
//        try {
//            switch(id) {
//                case 1:
//                    return LocalImageLoader.loadImage("army_service_ribbon.png");
//                case 2:
//                    return LocalImageLoader.loadImage("air_force_cross.png");
//            }
//        } catch(Exception e) {
//            log.error("Error loading image", e);
//        }

        return imageLoader.loadImage(id);

    }

    public BufferedImage createSortedCombinedImage(List<Award> awards) {
        Collections.sort(awards, new SortByPrecedence());

        return createCombinedImage(awards);
    }

    public static BufferedImage createCombinedImage(BufferedImage... bufferedImages) {

        final int totalImageCount = bufferedImages.length;

        /*
         * Set up new image
         */
        int newImageWidth = (bufferedImages[0].getWidth() + MARGIN) * RIBBONS_PER_ROW;

        int newImageHeight = (bufferedImages[0].getHeight() + MARGIN) * (1 + (totalImageCount / RIBBONS_PER_ROW));

        // BufferedImage newImage = new BufferedImage(newImageWidth, 500,
        // BufferedImage.TYPE_INT_ARGB);
        BufferedImage newImage = new BufferedImage(newImageWidth, newImageHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = newImage.createGraphics();

        g2.setPaint(CLEAR);
        g2.fillRect(0, 0, newImage.getWidth(), newImage.getHeight());
        int x = MARGIN;
        int y = MARGIN;

        Graphics g = newImage.getGraphics();

        /*
         * First Row
         */

        int firstRowCount = totalImageCount % RIBBONS_PER_ROW;
        if (firstRowCount != 0) {

            BufferedImage img1 = (BufferedImage) bufferedImages[0];

            //one award in first row
            if (firstRowCount == 1) {
                x += img1.getWidth() + MARGIN;
                g.drawImage(img1, x, y, null);
            }

            // two awards in first row
            if (firstRowCount == 2) {

                BufferedImage img2 = (BufferedImage) bufferedImages[1];

                // indent 1/2 award width
                x += (img1.getWidth() / 2) + (MARGIN / 2);

                // draw first award
                g.drawImage(img1, x, y, null);

                // push over one image's width
                x += img1.getWidth() + MARGIN;

                g.drawImage(img2, x, y, null);
            }

            // reset to beginning of second row
            x = MARGIN;
            y += img1.getHeight() + MARGIN;
        }

        /*
         * Remaining images
         */

        if (totalImageCount > firstRowCount) {
            int currentImage = firstRowCount;
            for (int i = 0; i < totalImageCount - firstRowCount; i++) {
                BufferedImage img = (BufferedImage) bufferedImages[currentImage];

                g.drawImage(img, x, y, null);

                if ((i + 1) % RIBBONS_PER_ROW == 0) {
                    x = MARGIN;
                    y += img.getHeight() + MARGIN;
                } else {
                    x += img.getWidth() + MARGIN;
                }

                currentImage++;
            }
        }

        return newImage;

//        return getByteArray(newImage);
    }


}

class SortByPrecedence implements Comparator<Award> {
    public int compare(Award a, Award b) {
        return a.getPrecedence() - b.getPrecedence();
    }
}
