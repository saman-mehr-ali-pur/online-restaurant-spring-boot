package com.online_restaurant.backend.ioUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class ImageIo {


    public boolean saveImage( String fileName,String pathDir,byte[] bytes) throws IOException {
        System.out.println(fileName);
        File dir = new File(pathDir);
        System.out.println(Files.exists(Paths.get(pathDir)));
        if (!Files.exists(Paths.get(pathDir))){
            try {
                System.out.println("create dir");

                Files.createDirectory(Paths.get(pathDir));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        String fullPath = pathDir+"/"+fileName;
        File imgFile = new File(fullPath);
        if(!imgFile.exists()){
            imgFile.createNewFile();

        }
        BufferedOutputStream bfout = new BufferedOutputStream(new FileOutputStream((imgFile)));
        bfout.write(bytes);
        bfout.flush();
        bfout.close();
        return true;
    }



    public byte[] getImage(String path) throws IOException {
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(path));
       byte[] bytes = bin.readAllBytes();
       bin.close();
       return bytes;

    }

    public boolean deleteImage(String path){

        if (!Files.exists(Paths.get(path))){
            throw new RuntimeException("not found file: "+path);
        }

        File file = new File(path);
        return  file.delete();

    }

}
