/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbir;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author labuser
 */
public class Checksum {
    
    public static String checkSumSHA1(String path) throws IOException, NoSuchAlgorithmException{
         
    MessageDigest md = MessageDigest.getInstance("SHA1");
    FileInputStream fis = new FileInputStream(path);
    byte[] dataBytes = new byte[1024];
 
    int nread = 0; 
 
    while ((nread = fis.read(dataBytes)) != -1) {
      md.update(dataBytes, 0, nread);
    };
 
    byte[] mdbytes = md.digest();
 
    //convert the byte to hex format
    StringBuffer sb = new StringBuffer("");
    for (int i = 0; i < mdbytes.length; i++) {
    	sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
    }
 
 //  System.out.println("SHA1 Digest(in hex format):: " + sb.toString());
 
  return  sb.toString();
  }
    
    
    public static String checkSumMD5(String path) throws IOException, NoSuchAlgorithmException{
         
    MessageDigest md = MessageDigest.getInstance("MD5");
    FileInputStream fis = new FileInputStream(path);
    byte[] dataBytes = new byte[1024];
 
    int nread = 0; 
 
    while ((nread = fis.read(dataBytes)) != -1) {
      md.update(dataBytes, 0, nread);
    };
 
    byte[] mdbytes = md.digest();
 
    //convert the byte to hex format
    StringBuffer sb = new StringBuffer("");
    for (int i = 0; i < mdbytes.length; i++) {
    	sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
    }
 
  // System.out.println("MD5 Digest(in hex format):: " + sb.toString());
 
  return  sb.toString();
  }
    
    
    }

    
  


    

