package upload.upload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upload.upload.domain.UploadFile;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
}