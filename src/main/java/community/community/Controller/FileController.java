package community.community.Controller;

import community.community.Provider.AWSProvider;
import community.community.dto.FileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FileController {
    @Autowired
    private AWSProvider awsProvider;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request) {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartHttpServletRequest.getFile("editormd-image-file");
        String url = awsProvider.generatePresignedUrlAndUploadObject(file.getOriginalFilename(), file.getContentType(), file);
        System.out.println("url: " + url);
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl(url);
        return fileDTO;
    }
}
