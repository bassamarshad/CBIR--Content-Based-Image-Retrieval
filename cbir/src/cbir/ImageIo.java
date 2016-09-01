package cbir;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author faculty
 */
public class ImageIo {

    public static BufferedImage readImage(String fname) {
        BufferedImage bImage = null;
        try {
            File fobject = new File(fname);
            bImage = ImageIO.read(fobject);
        } catch (IOException e) {
            System.out.println("Could not open the file");
            System.exit(-1);
        }
        // Check 
        return bImage;
    }

    public static BufferedImage ceateImage(int width, int height, int r, int g, int b) {

        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        int argb, a;
        // convert to grayscale
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // Put color into one 32 bit argb number
                a = 255;
                argb = 0;
                argb += a;
                argb = argb << 8;
                argb += r;
                argb = argb << 8;
                argb += g;
                argb = argb << 8;
                argb += b;
                newImage.setRGB(i, j, argb);
            }
        }
        return newImage;
    }

    
   public static BufferedImage setGrayByteImageArray2DToBufferedImage(byte[][] byteData_2d) {
        int width=byteData_2d[0].length;
        int height=byteData_2d.length;       
        byte[] byteData_1d = new byte[width * height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                byteData_1d[i * width + j] = byteData_2d[i][j];
            }
        }
        int dataType = DataBuffer.TYPE_BYTE;

        DataBufferByte buffer = new DataBufferByte(byteData_1d, byteData_1d.length);

        int cs = ColorSpace.CS_GRAY;
        ColorSpace cSpace = ColorSpace.getInstance(cs);
        ComponentColorModel ccm ;
        if (dataType == DataBuffer.TYPE_INT || dataType == DataBuffer.TYPE_BYTE) {
            ccm = new ComponentColorModel(cSpace,
                    ((cs == ColorSpace.CS_GRAY)
                            ? new int[]{8} : new int[]{8, 8, 8}),
                    false, false, Transparency.OPAQUE, dataType);
        } else {
            ccm = new ComponentColorModel(
                    cSpace, false, false, Transparency.OPAQUE, dataType);
        }

        SampleModel sm = ccm.createCompatibleSampleModel(width, height);
        //WritableRaster raster = ccm.createCompatibleWritableRaster(width,height);
        WritableRaster raster = Raster.createWritableRaster(sm, buffer, new Point(0, 0));
        return new BufferedImage(ccm, raster, false, null);
    }
    
    
    
    public static BufferedImage toGray(BufferedImage original) {
        // Second way of converting to Gray
        BufferedImage grayKeyImage = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = grayKeyImage.createGraphics();
        g2d.drawImage(original, 0, 0, null);
        return grayKeyImage;
    }

    public BufferedImage negativeImage(BufferedImage original) {
        BufferedImage negativelKeyImage = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
        int argb, r, g, b, a;
        // convert to grayscale
        int w = original.getWidth(null);
        int h = original.getHeight(null);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {

                // Extract rgb values
                argb = original.getRGB(i, j);

                //Extract R, G, B individually & negate
                b = ((argb) & 0xFF);
                g = ((argb >> 8) & 0xFF);
                r = ((argb >> 16) & 0xFF);
                a = (argb >> 24) & 0xFF;

                //Put color into one 32 bit argb number after subtracting 255 for negative.
                argb = 0;
                argb += a;
                argb = argb << 8;
                argb += 255 - r;
                argb = argb << 8;
                argb += 255 - g;
                argb = argb << 8;
                argb += 255 - b;
                negativelKeyImage.setRGB(i, j, argb);
            }
        }
        return negativelKeyImage;
    }

    public static void writeImage(BufferedImage bImage, String type, String fname) {
        try {
            File outputfile = new File(fname);
            ImageIO.write(bImage, type, outputfile);
        } catch (IOException e) {
            System.out.println("Could not open the file");
            System.exit(-1);
        }
        // Check 
    }

    public static BufferedImage threshold(BufferedImage original, int th) {
        int argb, r, g, b, a;
        int w = original.getWidth();
        int h = original.getHeight();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                argb = original.getRGB(i, j);

                //Extract R, G, B individually & negate
                b = ((argb) & 0xFF);
                g = ((argb >> 8) & 0xFF);
                r = ((argb >> 16) & 0xFF);
                a = (argb >> 24) & 0xFF;

                if (g < th) {
                    g = 0;
                } else {
                    g = 255;
                }
                //Put color into one 32 bit argb number
                argb = 0;
                argb += a;
                argb = argb << 8;
                argb += g;
                argb = argb << 8;
                argb += g;
                argb = argb << 8;
                argb += g;
                original.setRGB(i, j, argb);
            }
        }
        return original;
    }

    public static byte[][] threshold(byte[][] original, int th) {
        int argb, r, g, b, a;
        int h = original.length;
        int w = original[0].length;

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if ((int) (original[i][j] & 0xFF) >= th) {
                    original[i][j] = (byte) 255;
                } else {
                    original[i][j] = (byte) 0;
                }
            }
        }
        return original;
    }

    public BufferedImage grayImage(BufferedImage original) {
        BufferedImage grayLevelImage = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
        int argb, r, g, b, a, gray;
        // convert to grayscale
        int w = original.getWidth(null);
        int h = original.getHeight(null);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {

                // Extract rgb values
                argb = original.getRGB(i, j);

                //Extract R, G, B individually & negate
                b = ((argb) & 0xFF);
                g = ((argb >> 8) & 0xFF);
                r = ((argb >> 16) & 0xFF);
                a = (argb >> 24) & 0xFF;
                gray = (int) (0.21 * r + 0.71 * g + 0.07 * b);
                //Put color into one 32 bit argb number
                argb = 0;
                argb += a;
                argb = argb << 8;
                argb += gray;
                argb = argb << 8;
                argb += gray;
                argb = argb << 8;
                argb += gray;

                grayLevelImage.setRGB(i, j, argb);
            }
        }
        return grayLevelImage;
    }

    public static int[] getIntImageArray1DFromBufferedImage(BufferedImage image) {
        byte[] byteData_1d;
        int[] intData_1d;
        int Rows = image.getHeight();
        int Cols = image.getWidth();
        int m, n, i, i4, blue;
        int pixelLength;
        getBufferedImageType(image, "getImageArray1DFromBufferedImage");
        System.out.println("Row_Siz= " + Rows + " Cols_Size = " + Cols);
        intData_1d = new int[image.getHeight() * image.getWidth()];
        byteData_1d = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.out.println("Image Length (1-D Array) = " + byteData_1d.length);
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;
        if (hasAlphaChannel) {
            pixelLength = 4; //a,r,g,b;
        } else {
            pixelLength = 3; //a,r,g,b;
        }

        if (image.getType() == BufferedImage.TYPE_BYTE_GRAY || image.getType() == BufferedImage.TYPE_USHORT_GRAY) {
            pixelLength = 1;
        }
        System.out.println("Pixel Length (Alpha=4 or No Alpha=3) = " + pixelLength);

        for (i = 0; i < byteData_1d.length; i += pixelLength) {
            i4 = i / pixelLength;
            // convert 1d to 2d
            m = i4 / Cols; //Row index;
            n = i4 % Cols;
            if (image.getType() == BufferedImage.TYPE_BYTE_GRAY || image.getType() == BufferedImage.TYPE_USHORT_GRAY) {
                blue = ((int) byteData_1d[i] & 0xff);
            } else {
                blue = ((int) byteData_1d[i + 1] & 0xff);
            }
            intData_1d[i4] = blue;                // choose the blue; intData_1d[i] intData_1d[i+1] intData_1d[i+2] intData_1d[i+3]; a,g,b,r;
        }

        return intData_1d;
    }

    public static byte[] getByteImageArray1DFromBufferedImage(BufferedImage image) {
        byte[] byteData_1d;
        int Rows = image.getHeight();
        int Cols = image.getWidth();
        getBufferedImageType(image, "getByteImageArray1DFromBufferedImage");
        if (image.getType() != BufferedImage.TYPE_BYTE_GRAY) {
            System.out.println("Type is other than gray 1-byte");
            return null;
        }

        System.out.println("Row_Siz= " + Rows + " Cols_Size = " + Cols);
        byteData_1d = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.out.println("Image Length (1-D Array) = " + byteData_1d.length);
        return byteData_1d;
    }

    public static byte[][] getByteImageArray2DFromBufferedImage(BufferedImage image) {
        byte[] byteData_1d;
        int Rows = image.getHeight();
        int Cols = image.getWidth();
        getBufferedImageType(image, "getByteImageArray1DFromBufferedImage");
        if (image.getType() != BufferedImage.TYPE_BYTE_GRAY) {
            System.out.println("Type is other than gray 1-byte");
            return null;
        }

        System.out.println("Row_Siz= " + Rows + " Cols_Size = " + Cols);
        byteData_1d = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        byte[][] byteData_2d = new byte[Rows][Cols];

        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Cols; j++) {
                byteData_2d[i][j] = byteData_1d[i * Cols + j];
            }
        }

        System.out.println("Image Length (1-D Array) = " + byteData_1d.length);
        System.out.println("Image Row Length (2-D Array) = " + byteData_2d.length);
        System.out.println("Image Column Length (2-D Array) = " + byteData_2d[0].length);
        return byteData_2d;
    }

//    public static BufferedImage setByteImageArray1DFromBufferedImage(byte[] byteData_1d, int Cols, int Rows) {
//        BufferedImage grayImage = new BufferedImage(Cols, Rows, BufferedImage.TYPE_BYTE_GRAY);        
//        System.out.println("Row_Siz= " + Rows + " Cols_Size = " + Cols);
//        try {
//            ByteArrayInputStream in = new ByteArrayInputStream(byteData_1d);
//            return ImageIO.read(in);            
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//        return grayImage;
//    }
    // Simple clipping of the a pixel value to the range 0-255
    int clip(float xx) {
        int x = Math.round(xx);
        if (x > 255) {
            x = 255;
        }
        if (x < 0) {
            x = 0;
        }
        return x;
    }

    public static BufferedImage imageCopy(BufferedImage input) {
        BufferedImage output = ceateImage(input.getWidth(), input.getHeight(), 0, 0, 0);
        for (int i = 0; i < input.getWidth(); i++) {
            for (int j = 0; j < input.getHeight(); j++) {
                output.setRGB(i, j, input.getRGB(i, j));
            }
        }
        return output;
    }

    public static void getBufferedImageType(BufferedImage bImage, String from) {

//        TYPE_INT_RGB, TYPE_INT_ARGB, TYPE_INT_ARGB_PRE, TYPE_INT_BGR, 
//        TYPE_3BYTE_BGR, TYPE_4BYTE_ABGR, TYPE_4BYTE_ABGR_PRE, 
//        TYPE_BYTE_GRAY, TYPE_BYTE_BINARY, 
//        TYPE_BYTE_INDEXED, TYPE_USHORT_GRAY, 
//        TYPE_USHORT_565_RGB, 
//        TYPE_USHORT_555_RGB, 
//        TYPE_CUSTOM
        switch (bImage.getType()) {
            case BufferedImage.TYPE_INT_RGB:
                System.out.println(from + " " + "Color TYPE_INT_RGB");
                break;
            case BufferedImage.TYPE_INT_ARGB:
                System.out.println(from + " " + "Color TYPE_INT_ARGB");
                break;
            case BufferedImage.TYPE_INT_ARGB_PRE:
                System.out.println(from + " " + "Color TYPE_INT_ARGB_PRE");
                break;
            case BufferedImage.TYPE_INT_BGR:
                System.out.println(from + " " + "Color TYPE_INT_BGR");
                break;
            case BufferedImage.TYPE_3BYTE_BGR:
                System.out.println(from + " " + "Color TYPE_3BYTE_BGR");
                break;
            case BufferedImage.TYPE_4BYTE_ABGR:
                System.out.println(from + " " + "Color TYPE_4BYTE_ABGR");
                break;
            case BufferedImage.TYPE_4BYTE_ABGR_PRE:
                System.out.println(from + " " + "Color TYPE_4BYTE_ABGR_PRE");
                break;
            case BufferedImage.TYPE_BYTE_GRAY:
                System.out.println(from + " " + "Gray-Byte TYPE_BYTE_GRAY");
                break;
            case BufferedImage.TYPE_BYTE_BINARY:
                System.out.println(from + " " + "Binary-Byte TYPE_BYTE_BINARY");
                break;
            case BufferedImage.TYPE_USHORT_GRAY:
                System.out.println(from + " " + "Gray-UShort TYPE_USHORT_GRAY");
                break;
            default:
                System.out.println(from + " " + "Other/None!");
                break;
        }
    }

    public static BufferedImage setByteImageArray1DToBufferedImage(byte[] byteData_1d, int width, int height) {
        int dataType = DataBuffer.TYPE_BYTE;
        DataBufferByte buffer = new DataBufferByte(byteData_1d, byteData_1d.length);

        int cs = ColorSpace.CS_GRAY;
        ColorSpace cSpace = ColorSpace.getInstance(cs);
        ComponentColorModel ccm = null;
        if (dataType == DataBuffer.TYPE_INT || dataType == DataBuffer.TYPE_BYTE) {
            ccm = new ComponentColorModel(cSpace,
                    ((cs == ColorSpace.CS_GRAY)
                    ? new int[]{8} : new int[]{8, 8, 8}),
                    false, false, Transparency.OPAQUE, dataType);
        } else {
            ccm = new ComponentColorModel(
                    cSpace, false, false, Transparency.OPAQUE, dataType);
        }

        SampleModel sm = ccm.createCompatibleSampleModel(width, height);
        //WritableRaster raster = ccm.createCompatibleWritableRaster(width,height);
        WritableRaster raster = Raster.createWritableRaster(sm, buffer, new Point(0, 0));
        return new BufferedImage(ccm, raster, false, null);
    }

    public static BufferedImage setByteImageArray2DToBufferedImage(byte[][] byteData_2d, int width, int height) {
        byte[] byteData_1d = new byte[width * height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                byteData_1d[i * width + j] = byteData_2d[i][j];
            }
        }
        int dataType = DataBuffer.TYPE_BYTE;

        DataBufferByte buffer = new DataBufferByte(byteData_1d, byteData_1d.length);

        int cs = ColorSpace.CS_GRAY;
        ColorSpace cSpace = ColorSpace.getInstance(cs);
        ComponentColorModel ccm = null;
        if (dataType == DataBuffer.TYPE_INT || dataType == DataBuffer.TYPE_BYTE) {
            ccm = new ComponentColorModel(cSpace,
                    ((cs == ColorSpace.CS_GRAY)
                    ? new int[]{8} : new int[]{8, 8, 8}),
                    false, false, Transparency.OPAQUE, dataType);
        } else {
            ccm = new ComponentColorModel(
                    cSpace, false, false, Transparency.OPAQUE, dataType);
        }

        SampleModel sm = ccm.createCompatibleSampleModel(width, height);
        //WritableRaster raster = ccm.createCompatibleWritableRaster(width,height);
        WritableRaster raster = Raster.createWritableRaster(sm, buffer, new Point(0, 0));
        return new BufferedImage(ccm, raster, false, null);
    }

}
