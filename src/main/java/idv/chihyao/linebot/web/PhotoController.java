package idv.chihyao.linebot.web;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Paths;

@RestController
public class PhotoController {
//    @GetMapping("/image/{pathname}/{size}")
//    public void getImage(HttpServletRequest request, HttpServletResponse response) {
//        try (ServletInputStream inputStream = request.getInputStream()) {
//            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//            IOUtils.copy(inputStream, response.getOutputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @GetMapping("/image/{filename}")
    public void getImage(@PathVariable("filename") String filename, HttpServletResponse response) {

        File file = Paths.get("D:/image/").toFile();

        if (file.isDirectory() != true) new Exception();

        File[] files = file.listFiles();//240,300.....

        File targetFile = null;
        for (File file1 : files) {
            if (file1.getName().equals(filename)) {
                targetFile = file1;
                break;
            }
        }

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(targetFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        try {
            IOUtils.copy(fis, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/image/hello")
    public void test(HttpServletRequest request) {
        InputStream is;
        BufferedReader br = null;
        StringBuilder body = new StringBuilder();
        try {
            //取得request的inputStram
            is = request.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.equalsIgnoreCase("quit")) {
                    break;
                }
//                System.out.println("Line entered : " + line);
                body.append(line);
            }
        } catch (IOException ioe) {
            System.out.println("Exception while reading input " + ioe);
        } finally {
            // close the streams using close method
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ioe) {
                System.out.println("Error while closing stream: " + ioe);
            }
        }
        System.out.println(body.toString());
    }
}
