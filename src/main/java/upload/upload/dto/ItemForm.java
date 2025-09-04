package upload.upload.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import upload.upload.domain.UploadFile;

import java.util.List;

@Data
public class ItemForm {

    private Long itemId;
    private String itemName;

    //이미지를 다중으로 업로드 하기 위해 UploadFile 대신 MultipartFile 사용
    private List<MultipartFile> imageFiles;
    //단일 첨부파일
    private MultipartFile attachFile;
}