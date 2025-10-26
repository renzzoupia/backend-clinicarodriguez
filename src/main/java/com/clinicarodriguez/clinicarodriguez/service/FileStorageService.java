package com.clinicarodriguez.clinicarodriguez.service;

import com.clinicarodriguez.clinicarodriguez.config.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("No se pudo crear el directorio donde se almacenarán los archivos.", ex);
        }
    }

    /**
     * Guarda un archivo en el sistema de archivos y retorna el nombre del archivo
     */
    public String storeFile(MultipartFile file, String subdirectory) {
        // Normalizar el nombre del archivo
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        
        try {
            // Verificar si el archivo contiene caracteres inválidos
            if (originalFileName.contains("..")) {
                throw new RuntimeException("El nombre del archivo contiene una ruta inválida: " + originalFileName);
            }

            // Generar un nombre único para el archivo
            String fileExtension = "";
            if (originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

            // Crear subdirectorio si es necesario
            Path targetLocation = this.fileStorageLocation;
            if (subdirectory != null && !subdirectory.isEmpty()) {
                targetLocation = this.fileStorageLocation.resolve(subdirectory);
                Files.createDirectories(targetLocation);
            }

            // Copiar el archivo a la ubicación de destino
            Path finalPath = targetLocation.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), finalPath, StandardCopyOption.REPLACE_EXISTING);

            // Retornar la ruta relativa (subdirectorio/nombre_archivo)
            if (subdirectory != null && !subdirectory.isEmpty()) {
                return subdirectory + "/" + uniqueFileName;
            }
            return uniqueFileName;

        } catch (IOException ex) {
            throw new RuntimeException("No se pudo almacenar el archivo " + originalFileName + ". Por favor intente de nuevo.", ex);
        }
    }

    /**
     * Carga un archivo como Resource
     */
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("Archivo no encontrado: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Archivo no encontrado: " + fileName, ex);
        }
    }

    /**
     * Elimina un archivo del sistema de archivos
     */
    public void deleteFile(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            throw new RuntimeException("No se pudo eliminar el archivo: " + fileName, ex);
        }
    }
}
