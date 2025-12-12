package com.weekone;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;


public class MultiThreadDownloader implements Downloader{
    private final URL url;
    private final Path downloadPath;
    private final String fileName;
    private final int threadCount = 4;
    private final String tempFolder= "temp";

    private final ExecutorService pool;


    public MultiThreadDownloader(String urlAddress, String folderPath) {
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

         pool = Executors.newFixedThreadPool(threadCount);

        fileName = url.getFile().substring(url.getFile().lastIndexOf("/") + 1);

    }

    public void download() throws IOException{

        int fileSize = getFileSize();
        int partSize = fileSize / threadCount;
        int currentPos = 0;
        SortedSet<DownloadTask> tasks = new TreeSet<>();
        for(int i = 0; i < threadCount; i++){
            int endByte = Math.min((currentPos + partSize), fileSize);
            tasks.add(new DownloadTask(currentPos, endByte, i + 1));
            currentPos = endByte + 1;
        }
        System.out.println(tasks);

        Set<Path> downloadedParts = new TreeSet<>();

        try {
            List<Future<Path>> listResult = pool.invokeAll(tasks);

            System.out.println("task was running");

            listResult.forEach(future -> {
                try {
                    downloadedParts.add(future.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            });
            pool.shutdownNow();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mergeFiles(downloadedParts);
    }

    private void mergeFiles(Set<Path> downloadedParts) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(downloadPath + "/" + fileName);

        downloadedParts.forEach(path -> {
            try (FileInputStream fileInputStream = new FileInputStream(path.toString())){
                int readBytes = -1;
                byte[] buffer = new byte[4096];
                int totalBytes = 0;

                while ((readBytes = fileInputStream.read(buffer)) != -1){

                    fileOutputStream.write(buffer, totalBytes, readBytes);
                    totalBytes += readBytes;


                }
                fileInputStream.close();
                Files.delete(path);
                fileOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public int getFileSize() throws IOException{
        URLConnection connection = url.openConnection();
        return connection.getContentLength();
    }

    private class DownloadTask implements Callable<Path>, Comparable{
        private final int startByte;
        private final int endByte;
        private final int part;

        public DownloadTask(int startByte, int endByte, int part) {
            this.startByte = startByte;
            this.endByte = endByte;
            this.part = part;
        }

        @Override
        public Path call() throws Exception {
            String path = tempFolder + "/" + UUID.randomUUID();
            System.out.println(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            try{
                connection.setRequestProperty("Range", String.format("bytes=%d-%d", startByte, endByte));
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_PARTIAL){
                    try(var writer = new FileOutputStream(path);
                        var reader = connection.getInputStream()){

                        int readBytes = -1;
                        byte[] buffer = new byte[4096];
                        int totalBytes = 0;
                        while ((readBytes = reader.read(buffer)) != -1){
                            writer.write(buffer, 0, readBytes);
                            totalBytes += readBytes;
                        }
                        writer.flush();
                    }
               }else{
                    System.out.println("Сервер вернул код " + responseCode);
                }
            }finally {
                connection.disconnect();
            }
            return Path.of(path);
        }

        @Override
        public int compareTo(Object o) {
            return Integer.compare(this.part, ((DownloadTask) o).part);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DownloadTask that = (DownloadTask) o;
            return startByte == that.startByte && endByte == that.endByte && part == that.part;
        }

        @Override
        public int hashCode() {
            return Objects.hash(startByte, endByte, part);
        }

        @Override
        public String toString() {
            return "DownloadTask{" +
                    "startByte=" + startByte +
                    ", endByte=" + endByte +
                    ", part=" + part +
                    '}';
        }
    }
}
