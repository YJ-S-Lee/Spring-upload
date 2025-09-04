package upload.upload.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    private String itemName;

    //첨부파일 한 개
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "upload_file_id")
    private UploadFile attachFile;

    //이미지 여러 개
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<UploadFile> imageFiles = new ArrayList<>();

}