package API;

import org.example.demo.GiaoDienChung;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class RenImage extends GiaoDienChung {

    /**
     * API down ảnh về bằng url của ảnh
     *
     * @param imageUrl url của ảnh trên web
     * @throws IOException bắt exception nếu không tồn tại thư mục
     */
    public static void downImage(String imageUrl) throws IOException {
        String saveDir = "F:\\btlOOP\\demo\\src\\main\\resources\\image";

        File directory = new File(saveDir);
        if (!directory.exists()) {
            System.out.println("Thư mục không tồn tại. Đang tạo thư mục...");
            boolean created = directory.mkdirs(); // Tạo thư mục nếu chưa tồn tại
            if (!created) {
                throw new IOException("Không thể tạo thư mục lưu trữ: " + saveDir);
            }
        }

        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Failed to fetch image: " + responseCode);
        }

        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        File saveFile = new File(saveDir, fileName);

        try (InputStream inputStream = connection.getInputStream();
             OutputStream outputStream = new FileOutputStream(saveFile)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }


}
