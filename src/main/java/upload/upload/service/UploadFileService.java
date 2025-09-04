package upload.upload.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import upload.upload.domain.UploadFile;
import upload.upload.repository.UploadFileRepository;

@Service
@RequiredArgsConstructor
public class UploadFileService {

    private final UploadFileRepository uploadFileRepository;

    //저장
    public Long save(UploadFile uploadFile) {
        uploadFileRepository.save(uploadFile);
        return uploadFile.getId();
    }
}