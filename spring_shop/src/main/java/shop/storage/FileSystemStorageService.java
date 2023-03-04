package shop.storage;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileSystemStorageService implements StorageService {
    private final Path rootLocation;

    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        try {
            if (!Files.exists(rootLocation))
                Files.createDirectories(rootLocation);
        } catch (IOException ex) {
            throw new StorageException("Помилка створення папки", ex);
        }
    }

    @Override
    public Resource loasAsResource(String fileName) {
        try {
            Path file = rootLocation.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable())
                return resource;
            throw new StorageException("Проблема при роботі із файлом " + fileName);
        } catch (MalformedURLException e) {
            throw new StorageException("Файл не знайдено " + fileName);
        }
    }

    @Override
    public String save(String base64) {
        try {
            if(base64.isEmpty()) {
                throw new StorageException("Пустий base64");
            }
            UUID uuid = UUID.randomUUID();
            String [] charArray = base64.split(",");
            String extension;
            switch(charArray[0]) {
                case "data:image/png;base64":
                    extension="png";
                    break;
                default:
                    extension="jpg";
                    break;
            }
            String randomFileName = uuid.toString()+"."+extension;
            Base64.Decoder decoder = Base64.getDecoder();
            byte [] bytes = new byte[0];
            bytes = decoder.decode(charArray[1]);
            int [] imageSize = {32,150, 300, 600, 1200};
            try(var byteStream = new ByteArrayInputStream(bytes)) {
                var image = ImageIO.read(byteStream);
                for(int size : imageSize) {
                    String fileSaveItem = rootLocation.toString()+"/"+size+"_"+randomFileName;
                    BufferedImage newImg = ImageUtils.resizeImage(image,
                            extension=="jpg" ? ImageUtils.IMAGE_JPEG: ImageUtils.IMAGE_PNG, size, size);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ImageIO.write(newImg, extension, byteArrayOutputStream);
                    byte [] newBytes = byteArrayOutputStream.toByteArray();
                    FileOutputStream out = new FileOutputStream(fileSaveItem);
                    out.write(newBytes);
                    out.close();
                }
            } catch(IOException e) {
                throw new StorageException("Проблема перетворення фото", e);
            }
            return randomFileName;

        } catch(StorageException e) {
            throw new StorageException("Проблема при збережні та перетворені base64",e);
        }
    }
    @Override
    public String saveMultipartFile(MultipartFile file) {
        try {
            UUID uuid = UUID.randomUUID();
            String extension="jpg";
            String randomFileName = uuid.toString()+"."+extension;
            Base64.Decoder decoder = Base64.getDecoder();
            byte [] bytes = file.getBytes();
            int [] imageSize = {32,150, 300, 600, 1200};
            try(var byteStream = new ByteArrayInputStream(bytes)) {
                var image = ImageIO.read(byteStream);
                for(int size : imageSize) {
                    String fileSaveItem = rootLocation.toString()+"/"+size+"_"+randomFileName;
                    BufferedImage newImg = ImageUtils.resizeImage(image,
                            extension=="jpg" ? ImageUtils.IMAGE_JPEG: ImageUtils.IMAGE_PNG, size, size);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ImageIO.write(newImg, extension, byteArrayOutputStream);
                    byte [] newBytes = byteArrayOutputStream.toByteArray();
                    FileOutputStream out = new FileOutputStream(fileSaveItem);
                    out.write(newBytes);
                    out.close();
                }
            } catch(IOException e) {
                throw new StorageException("Проблема перетворення фото", e);
            }
            return randomFileName;

        } catch(Exception e) {
            throw new StorageException("Проблема при збережні та перетворені base64",e);
        }
    }
    @Override
    public void removeFile(String removeFile) {
        int[] imageSize = {32, 150, 300, 600, 1200};
        for (int size : imageSize) {
            Path filePath = load(size + "_" + removeFile);
            File file = new File(filePath.toString());
            if (file.delete()) {
                System.out.println(removeFile + " Файл видалено.");
            } else System.out.println(removeFile + " Файл не знайдено.");
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

}