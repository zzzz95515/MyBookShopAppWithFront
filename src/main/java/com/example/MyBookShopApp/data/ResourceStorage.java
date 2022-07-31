package com.example.MyBookShopApp.data;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

@Service
public class ResourceStorage {

    @Value("${upload.path}")
    String uploadPath;


    public String saveNewBookImage(MultipartFile file, String slug) throws IOException {

        String resourceURI=null;
        if (!file.isEmpty()){
            if(!new File(uploadPath).exists()){
                Files.createDirectories(Paths.get(uploadPath));
                Logger.getLogger(ResourceStorage.class.getSimpleName()).info("created directory: "+uploadPath);
            }

            String fileName = slug +"."+ FilenameUtils.getExtension(file.getOriginalFilename());
            Path path = Paths.get(uploadPath,fileName);
            resourceURI="/books-img/"+fileName;
            file.transferTo(path);
            Logger.getLogger(ResourceStorage.class.getSimpleName()).info(fileName+"uploaded Ok");
        }

    return resourceURI;
    }
}
