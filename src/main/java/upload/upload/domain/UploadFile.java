package upload.upload.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "upload_file_id")
    private Long id;

    //고객이 업로드한 파일명
    private String uploadFileName;

    //서버 내부에서 관리하는 파일명
    private String storeFileName;

    //첨부파일의 역방향(1:1관계)
    //fetch = FetchType.LAZY 는 필요할 때만 DB에서 연관 데이터를 가져오기 위해 넣는 것
    //@ManyToOne, @OneToOne → 기본이 EAGER라서 반드시 LAZY로 바꿔주는 것 권장
    //(무조건 연관된 걸 다 끌고 와서 성능 문제 자주 발생함)
    @OneToOne(mappedBy = "attachFile", fetch = FetchType.LAZY)
    private Item attachFile;

    //이미지의 역방향(N:1 관계)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    //두 개의 이름이 들어왔을 때 바꿔주는 생성자
    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}