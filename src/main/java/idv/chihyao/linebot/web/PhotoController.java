package idv.chihyao.linebot.web;

import com.google.common.base.Preconditions;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class PhotoController {

    private String downloadPath;

    @Value("${common.config.local.random-image-location}")
    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    @GetMapping("/${common.config.web.imagePath}/{filename}")
    public void getImage(@PathVariable("filename") String filename, HttpServletResponse response) {
        Preconditions.checkState(Files.exists(Paths.get(downloadPath)), "the file of the url is not exists");

        File targetFile = Paths.get(downloadPath,filename).toFile();

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
