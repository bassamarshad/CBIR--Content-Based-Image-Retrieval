/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbir;
import java.io.File;
import java.io.IOException;
import static java.lang.System.exit;
import java.nio.file.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Bassam
 */
public class FolderWalk {
    
    
    public static void searchForImagesAndMove1(File root) throws IOException
{
    //infoBox(root.getAbsolutePath(),"Message");
    if(root == null ) 
    { 
        return ; //just for safety   
    }
              Path start = FileSystems.getDefault().getPath(root.getAbsolutePath());
		Files.walk(start)
		     .filter( path -> path.toFile().isFile())
		     .filter( path -> (path.toString().endsWith(".jpg") || path.toString().endsWith(".png")) )
		     .forEach( System.out::println );
        Path src1 = Paths.get(root.getPath());
        Path dest1 = Paths.get("./ImageDatabase/");
    Files.copy(src1,dest1.resolve(src1.getFileName()),StandardCopyOption.REPLACE_EXISTING);

    }
    
    
    
    public static int searchForImagesAndMove(File root,File dest) throws IOException, InterruptedException {
        //String pattern = ".mp3";

  Path dest1 = Paths.get(dest.getPath());
        
        
        File listFile[] = root.listFiles();
        if (listFile != null) {
            for (int i=0; i<listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    searchForImagesAndMove(listFile[i],dest);
                } else {
                  //  if (listFile[i].getName().endsWith(".jpg")||listFile[i].getName().endsWith(".png")||listFile[i].getName().endsWith(".gif")||listFile[i].getName().endsWith(".tiff")||listFile[i].getName().endsWith(".GIF")||listFile[i].getName().endsWith(".JPG")||listFile[i].getName().endsWith(".PNG")||listFile[i].getName().endsWith(".TIFF")) {
                    //Ignoring .gif and .tiff for now as having issues displaying them on the app .. 
                    if (listFile[i].getName().endsWith(".jpg")||listFile[i].getName().endsWith(".png")||listFile[i].getName().endsWith(".JPG")||listFile[i].getName().endsWith(".PNG")) {
                    System.out.println(listFile[i].getPath());
                        Path src1 = Paths.get(listFile[i].getPath());
                        Files.copy(src1,dest1.resolve(src1.getFileName()),StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
        }
        return 1;
    }
    
    
    public static void searchForImagesAndMoveRenameDuplicates(File root) throws IOException {
        //String pattern = ".mp3";
  File dest = new File("./ImageDatabase/");
  Path dest1 = Paths.get(dest.getPath());
        if (!dest.exists()) {
            dest.mkdir();
            dest.setExecutable(true);
           dest.setReadable(true);
            dest.setWritable(true);
        }
        
        File listFile[] = root.listFiles();
        if (listFile != null) {
            for (int i=0; i<listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    searchForImagesAndMoveRenameDuplicates(listFile[i]);
                } else {
                    if (listFile[i].getName().endsWith(".jpg")||listFile[i].getName().endsWith(".png")) {
                        if(listFile[i].exists()) {listFile[i]=renameFile(listFile[i]);}
                        System.out.println(listFile[i].getPath());
                        Path src1 = Paths.get(listFile[i].getPath());
                        Files.copy(src1,dest1.resolve(src1.getFileName()),StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
        }
    }
   static int j=0;
     public static File renameFile(File sourceFile){
         j++;
        String name = sourceFile.getName();
        int i = name.contains(".") ? name.lastIndexOf('.') : name.length();
        String dstName = name.substring(0, i) + "(Copy)" +j+ name.substring(i);
        File dest = new File(sourceFile.getParent(), dstName);
        return dest;
     }
    
    
    
    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    
}
    
    
    
    
    
    
    
    

