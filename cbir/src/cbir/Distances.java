/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbir;

/**
 *
 * @author Bassam
 */
public class Distances {
    
    public  float minkowskiDist(double[] hist1,double[] hist2)
    {
        float minkDist = 0;
                
             for (int i=0;i<hist1.length-1;i++)
             {
                 minkDist+=Math.pow((hist1[i]-hist2[i]),2);
             }
             return (float) Math.sqrt(minkDist);   
    }
    
    
    public  float histogramIntersectionDist(double[] hist1,double[] hist2)
    {
        float HIDist = 0;
                
             for (int i=0;i<hist1.length-1;i++)
             {
                 HIDist+=Math.min(hist1[i],hist2[i]);
             }
             
             return HIDist;   
    }
    
    
    public  float relativeDeviationDist(double[] hist1,double[] hist2)
    {
        float RDDist = 0;
        float Numerator=0;
        float Denom1=0;
        float Denom2=0;
                
             for (int i=0;i<hist1.length-1;i++)
             {
                 Numerator+= Math.sqrt(Math.pow((hist1[i]-hist2[i]),2));
                 Denom1+=Math.sqrt(Math.pow(hist1[i],2));
                 Denom2+=Math.sqrt(Math.pow(hist2[i],2));
             }
             RDDist=2*Numerator/(Denom1+Denom2);
             return RDDist;   
    }
    
    
    
    
    
    
    
    
    
    
    
}
