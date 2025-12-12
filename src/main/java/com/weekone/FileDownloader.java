package com.weekone;

import java.io.IOException;

public class FileDownloader {

    static final String TEST_URL = "https://djvureader.org/DjVuReader.2.0.0.27.rar";
    static final String TEST_FOLDER = "downloads";
    static boolean debugMode = true;

    public static void main(String[] args) {

        String urlParam;
        String downloadFolder;

        if (!debugMode){
            if (args.length < 2){
                throw new IllegalArgumentException("Недостаточное количество параметров");
            }
            urlParam =  args[0];
            downloadFolder = args[1];

        }else{
            urlParam =  TEST_URL;
            downloadFolder = TEST_FOLDER;
        }

        try {
            //SimpleDownloader downloader = new SimpleDownloader(urlParam, downloadFolder);
            MultiThreadDownloader downloader = new MultiThreadDownloader(urlParam, downloadFolder);
            downloader.download();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
