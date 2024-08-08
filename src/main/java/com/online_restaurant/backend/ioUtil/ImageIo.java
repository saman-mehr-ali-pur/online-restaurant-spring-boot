package com.online_restaurant.backend.ioUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class ImageIo {


    public boolean saveImage(String pathDir, String fileName,byte[] bytes) throws IOException {

        File dir = new File(pathDir);
        if (!Files.exists(Paths.get(pathDir))){
            try {
                Files.createFile(Paths.get(pathDir));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        String fullPath = pathDir+fileName;
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
