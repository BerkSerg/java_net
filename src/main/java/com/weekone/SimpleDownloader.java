package com.weekone;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class SimpleDownloader implements Downloader{
    private final URL url;
    private final Path downloadPath;
    private String fileName;

    public SimpleDownloader(String urlAddress, String folderPath) throws IOException {
        try {
            this.url = new URL(urlAddress);
            this.url.toURI(); // проверка на валидность
        }catch(MalformedURLException e){
            throw new RuntimeException("Некорректный URL адрес " + urlAddress);
        }catch(URISyntaxException e){
            throw new RuntimeException("Невалидный URL адрес" + urlAddress);
        }

        this.downloadPath =  Path.of(folderPath);
        if (!Files.isDirectory(this.downloadPath)){
            throw new RuntimeException("Каталог не существует " + folderPath);
        }
    }

    public void download() throws IOException {

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String disposition = connection.getHeaderField("Content-Disposition");
                int contentLength = connection.getContentLength();
                if (disposition != null) {
                    int index = disposition.indexOf("filename=");
                    if (index > 0) {
                        fileName = disposition.substring(index + 10, disposition.length() - 1);
                    }
                } else {
                    fileName = url.getFile().substring(url.getFile().lastIndexOf("/") + 1);
                }

                try (FileOutputStream writer = new FileOutputStream(downloadPath.toString() + "/" + fileName);
                     InputStream reader = connection.getInputStream()) {

                    int bytesRead = -1;
                    byte[] buffer = new byte[8192];
                    long totalBytes = 0L;

                    while ((bytesRead = reader.read(buffer)) != -1){
                        writer.write(buffer, 0, bytesRead);
                        totalBytes += bytesRead;

                        if (contentLength > 0){
                            int progress = (int) ((totalBytes * 100) / contentLength);
                            int current = progress / 10;
                            String scale = "=".repeat(current);
                            String spaces = " ".repeat(10 - current);
                            System.out.printf("\rЗагрузка: [%s>%s] %d%%", scale, spaces, progress);
                        }
                    }
                }
            }else{
                System.out.println("Код ответа от сервера: " + responseCode);
            }
        }finally{
            connection.disconnect();
        }
    }
}
