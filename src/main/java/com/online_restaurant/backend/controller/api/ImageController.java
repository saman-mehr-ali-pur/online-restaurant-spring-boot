package com.online_restaurant.backend.controller.api;


import com.online_restaurant.backend.ioUtil.ImageIo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/image")
@CrossOrigin("*")
public class ImageController {


    @Autowired
    private ImageIo imageIo;

    @GetMapping
    public ResponseEntity<?> getImage(@RequestParam("path") String path) throws IOException {
        System.out.println(path);
        byte[] bytes = imageIo.getImage(path);
        System.out.println(bytes.length);
        return ResponseEntity.ok().
                header("content-type",path.endsWith(".jpg") ? "image/jpg":"image/png").
                body(bytes);
    }

}
