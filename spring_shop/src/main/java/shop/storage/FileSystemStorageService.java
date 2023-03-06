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
            // Генерування випадкового ідентифікатора для імені файлу
            UUID uuid = UUID.randomUUID();
            // Розбивка переданого рядка на тип зображення та код в форматі base64
            String [] charArray = base64.split(",");
            String extension;
            // Визначення типу зображення
            switch(charArray[0]) {
                case "data:image/png;base64":
                    extension="png";
                    break;
                default:
                    extension="jpg";
                    break;
            }
            //Формування імені файлу з випадковим ідентифікатором та визначенням типу зображення
            String randomFileName = uuid.toString()+"."+extension;
            // Декодування коду зображення в форматі base64
            Base64.Decoder decoder = Base64.getDecoder();
            byte [] bytes = new byte[0];
            bytes = decoder.decode(charArray[1]);
            // Визначення розмірів зображень для збереження
            int [] imageSize = {32,150, 300, 600, 1200};
            // Створення байтового потоку з декодованим зображенням
            try(var byteStream = new ByteArrayInputStream(bytes)) {
                // Читання зображення з байтового потоку
                var image = ImageIO.read(byteStream);
                // Ітерація по масиву розмірів зображень для збереження
                for(int size : imageSize) {
                    // Формування шляху для збереження файлу з конкретним розміром
                    String fileSaveItem = rootLocation.toString()+"/"+size+"_"+randomFileName;
                    // Зменшення розміру зображення та його форматування
                    BufferedImage newImg = ImageUtils.resizeImage(image,
                            extension=="jpg" ? ImageUtils.IMAGE_JPEG: ImageUtils.IMAGE_PNG, size, size);
                    // Створення байтового потоку для запису зображення
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    // Запис зменшеного зображення у байтовий потік
                    ImageIO.write(newImg, extension, byteArrayOutputStream);
                    // Конвертація байтів зображення з потоку у масив байтів
                    byte [] newBytes = byteArrayOutputStream.toByteArray();
                    // Запис зображення у файл
                    FileOutputStream out = new FileOutputStream(fileSaveItem);
                    out.write(newBytes);
                    out.close();
                }
                // Обробка помилок
            } catch(IOException e) {
                throw new StorageException("Проблема перетворення фото", e);
            }
            return randomFileName;

        } catch(StorageException e) {
            throw new StorageException("Проблема при збережні та перетворені base64",e);
        }
    }

    // метод збереження зображення з файлу
    @Override
    public String saveMultipartFile(MultipartFile file) {
        try {
            // створення унікального ідентифікатора
            UUID uuid = UUID.randomUUID();
            // змінна для збереження розширення файлу
            String extension="jpg";
            // створення імені файлу
            String randomFileName = uuid.toString()+"."+extension;
            // створення декодера Base64
            Base64.Decoder decoder = Base64.getDecoder();
            // зчитування байтів файлу
            byte [] bytes = file.getBytes();
            // масив розмірів зображення
            int [] imageSize = {32,150, 300, 600, 1200};
            try(var byteStream = new ByteArrayInputStream(bytes)) {
                // зчитування зображення з байтів
                var image = ImageIO.read(byteStream);
                // цикл для збереження зображення в різних розмірах
                for(int size : imageSize) {
                    // створення шляху для збереження файлу
                    String fileSaveItem = rootLocation.toString()+"/"+size+"_"+randomFileName;
                    // зміна розміру зображення
                    BufferedImage newImg = ImageUtils.resizeImage(image,
                            extension=="jpg" ? ImageUtils.IMAGE_JPEG: ImageUtils.IMAGE_PNG, size, size);
                    // створення потоку для байтів зображення
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    // збереження зображення у байтах в потік
                    ImageIO.write(newImg, extension, byteArrayOutputStream);
                    // збереження байтів з потоку у масив
                    byte [] newBytes = byteArrayOutputStream.toByteArray();
                    // створення потоку виводу
                    FileOutputStream out = new FileOutputStream(fileSaveItem);
                    // запис байтів у файл
                    out.write(newBytes);
                    // закриття потоку виводу
                    out.close();
                }
                // обробка помилок
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