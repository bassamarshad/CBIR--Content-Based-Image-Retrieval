/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//http://imagedatabase.cs.washington.edu/groundtruth/ - Image Database located here ...
package cbir;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author labuser
 */
public class Cbir {

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InterruptedException {
//        // TODO code application logic here
//
//        //  Thread t1,t2;
//        BufferedImage myImage = ImageIo.readImage("3.jpg");
//
//        double[] hist1 = getNormalizedHistogram(myImage);
//
//        BufferedImage myImage1 = ImageIo.readImage("5.jpg");
//
//        double[] hist2 = getNormalizedHistogram(myImage1);
//        //   double [] hist2=getNormalizedHistogram(myImage1);
//
//        float d1, d2, d3;
//
//        Distances dist = new Distances();
//
//        d1 = dist.minkowskiDist(hist1, hist2);
//        d2 = dist.histogramIntersectionDist(hist1, hist2);
//        d3 = dist.relativeDeviationDist(hist1, hist2);
//
//        System.out.println("Similarity Measure Between Images :");
//        System.out.println("Minkowski : " + d1);
//        System.out.println("Histogram Intersection : " + d2);
//        System.out.println("Relative Deviation : " + d3);
//
//        HashMapIndexing hashmapobj = new HashMapIndexing();
//       // hashmapobj.createMap();
//
//        //   long startTime = System.currentTimeMillis();
//        //hashmapobj.createMapMultiThreaded();
//        // hashmapobj.createMap();
//        //  hashmapobj.deserializeHashMap("hashmap.ser");
//        //Updating an Image in image-db
//        File compareImage = new File("C:\\Users\\Bassam\\Google Drive\\Spring2015\\DIP-Spring2015\\FINAL_Project\\prog5\\cbir\\3.jpg");
//        // hashmapobj.addImageToHashMap(compareImage);  
//        // long stopTime = System.currentTimeMillis();
//        //long elapsedTime = stopTime - startTime;
//        //System.out.println("Time  "+elapsedTime);
//
//        hashmapobj.returnNearest5(compareImage, "Minkowski");
//        hashmapobj.returnNearest5(compareImage, "RelativeDeviation");
//        hashmapobj.returnNearest5(compareImage, "HistogramIntersection");
//
//        if (hashmapobj.getNewMap().isEmpty()) {
//            System.out.println("Map is Empty");
//        } else {
//            System.out.println("Map has values");
//        }
//
//    }

    public static double[] getNormalizedHistogram(BufferedImage inImage) {

        float[] hsb;

        int argb, r, g, b;
        // int HSBColorArray2D[][] = new int[inImage.getWidth()][inImage.getHeight()];
        int HSBInt[] = new int[inImage.getWidth() * inImage.getHeight()];
        int c = 0;
        double[] NormHisto;
        for (int i = 0; i < inImage.getWidth(); i++) {
            for (int j = 0; j < inImage.getHeight(); j++) {

                argb = inImage.getRGB(i, j);
                //Extract R, G, B individually & negate
                b = ((argb) & 0xFF);
                g = ((argb >> 8) & 0xFF);
                r = ((argb >> 16) & 0xFF);
                hsb = Color.RGBtoHSB(r, g, b, null);
                HSBInt[c++] = quantizeHSB1(hsb);

            }
        }

        //  System.out.println("h " +hsb[0] +"  s  "+ hsb[1]+"  b "+hsb[2]);
//        for(int i=0;i<HSBImage.getWidth();i++){
//            for(int j=0;j<HSBImage.getHeight();j++){
//                HSBImage.setRGB(i, j, HSBColorArray2D[i][j]);
//            }
//        }
//        ImageIo.writeImage(HSBImage, "jpg", "ConvertedUTB.jpg");
        Histogram hist = new Histogram();
        NormHisto = hist.createhistogram(HSBInt);

        return NormHisto;

    }

    public static int quantizeHSB1(float[] hsb) {
        // float[] hsbNew = new float[3];
        int h = Float.floatToIntBits((hsb[0] * 15f) / 4194303f);
        int s = Float.floatToIntBits((hsb[1] * 3f) / 4194303f);
        int b = Float.floatToIntBits((hsb[2] * 3f) / 4194303f);

        h = h & 0x007fffff;
        h = (h >> 19) & 0xF;

        s = s & 0x007fffff;
        s = (s >> 21) & 0x3;

        b = b & 0x007fffff;
        b = (b >> 21) & 0x3;

        int single = 0;

        single += h & 0xF;
        single = (single << 2);
        single += s & 0x3;
        single = (single << 2);
        single += b & 0x3;

        //   System.out.println(" h -- "+h+"s ---"+s+"b ---"+b);
        //  System.out.println(single);
//        System.out.println(" h -- "+Integer.toBinaryString(h)+"s ---"+Integer.toBinaryString(s)+"b ---"+Integer.toBinaryString(b));
//        System.out.println(Integer.toBinaryString(single));
        return single;
    }

    public static float[] convert8bitInttoFloatArray(int num) {
        //System.out.println(num);
        float[] hsbNew = new float[3];
        //
        int b = (num & 0x00000003);
        String DecToFloatb = "." + Integer.toString(b);
        hsbNew[2] = Float.valueOf(DecToFloatb);

        int s = (num & 0x0000000C);
        s = s >> 2;
        String DecToFloats = "." + Integer.toString(s);
        hsbNew[1] = Float.valueOf(DecToFloats);

        int h = (num & 0x000000F0);
        h = h >> 4;
        String DecToFloath = "." + Integer.toString(h);
        hsbNew[0] = Float.valueOf(DecToFloath);
        // System.out.println("h -- "+hsbNew[0] +"  s---- "+hsbNew[1] +"  b--- "+hsbNew[2]);

        return hsbNew;
    }

    public static int[] convert8bitInttoIntArray(int num) {
        //System.out.println(num);
        int[] hsbNew = new int[3];
        //
        int b = (num & 0x00000003);
        //String DecToFloatb = "."+Integer.toString(b); 
        hsbNew[2] = b;

        int s = (num & 0x0000000C);
        s = s >> 2;
        //String DecToFloats = "."+Integer.toString(s); 
        hsbNew[1] = s;

        int h = (num & 0x000000F0);
        h = h >> 4;
        //String DecToFloath = "."+Integer.toString(h); 
        hsbNew[0] = h;
        // System.out.println("h -- "+hsbNew[0] +"  s---- "+hsbNew[1] +"  b--- "+hsbNew[2]);

        return hsbNew;
    }

}
