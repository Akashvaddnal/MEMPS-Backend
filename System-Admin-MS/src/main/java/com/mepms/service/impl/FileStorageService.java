package com.mepms.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final Path fileStorageLocation = Paths.get("uploads/").toAbsolutePath().normalize();

    public String storeFile(MultipartFile file) {
      try {
        if (!Files.exists(fileStorageLocation)) {
          Files.createDirectories(fileStorageLocation);
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path targetLocation = fileStorageLocation.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/" + fileName; // Or another URL format
      } catch (IOException ex) {
    	  ex.printStackTrace();
        throw new FileStorageException("Could not store file " + file.getOriginalFilename(), ex);
      }
    }
}
