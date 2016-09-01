/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbir;

import java.awt.image.BufferedImage;

/**
 *
 * @author labuser
 */
public class Histogram {
    
    
    public double[] createhistogram(int[] hsb) {
        
        double max, min;
        int[] histogram;
        double[] normalizedHistogram;
        
        int noOfbins=256;
       
        histogram = new int[noOfbins];
        normalizedHistogram= new double[noOfbins];
        for (int i = 0; i < hsb.length; i++) {
                histogram[(hsb[i]& 0x00000003)*(4*16) +((hsb[i]& 0x0000000C)>>2)*16 + (hsb[i]&0x000000F0)>>4]++;
        }
         min= (double)histogram[0]/(256);
         max= (double)histogram[0]/(256);
        if(histogram !=null && noOfbins > 0 )
        {
              for (int i = 0; i < histogram.length; i++) 
              {
                  normalizedHistogram[i] = (double)histogram[i]/(256);
                  
                  if (normalizedHistogram[i] > max)
                      max = normalizedHistogram[i];
                  if (normalizedHistogram[i] < min)
                      min = normalizedHistogram[i];
              }
        }
        return normalizedHistogram;
        
    }
    
    
}
