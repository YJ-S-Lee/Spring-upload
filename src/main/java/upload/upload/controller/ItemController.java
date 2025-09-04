package upload.upload.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import upload.upload.domain.Item;
import upload.upload.domain.UploadFile;
import upload.upload.dto.ItemForm;
import upload.upload.file.FileStore;
import upload.upload.service.ItemService;
import upload.upload.service.UploadFileService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final FileStore fileStore;
    private final UploadFileService uploadFileService;

    @GetMapping("/items/new")
    public String newItem(@ModelAttribute ItemForm form) {
        return "item-form";
    }

    @PostMapping("/items/new")
    public String saveItem(@ModelAttribute ItemForm form, RedirectAttributes redirectAttributes) throws IOException {

        //단일 파일 저장
        UploadFile attachFile = fileStore.storeFile(form.getAttachFile());

        //여러가지 이미지 파일들 저장
        List<UploadFile> storeImageFiles = fileStore.storeFiles((form.getImageFiles()));

        log.info("itemName={}", form.getItemName());
        log.info("attachFile={}", form.getAttachFile());
        log.info("imageFile={}", form.getImageFiles());
        log.info("첨부파일={}", attachFile);
        log.info("이미지={}", storeImageFiles);

        Item item = new Item();
        item.setItemName(form.getItemName());
        item.setAttachFile(attachFile);
        item.setImageFiles(storeImageFiles);

        //연관관계 설정 - 반드시 필요!
        attachFile.setAttachFile(item); //1:1의 역방향

        for(UploadFile file : storeImageFiles) {
            file.setItem(item); //1:N의 주인에게 설정
        }

        //UploadFile을 따로 save할 필요 없음 -> CascadeType.ALL 덕분에 자동 자장
        itemService.save(item);
        redirectAttributes.addAttribute("itemId", item.getId());
        return "redirect:/items/{itemId}";
    }

    //보여주는 기능
    @GetMapping("/items/{id}")
    public String items(@PathVariable Long id, Model model) {
        Optional<Item> item = itemService.findOneItem(id);
        model.addAttribute("item", item.get());
        return "item-view";
    }

    //본문에 이미지 보여주는 기능
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    //요청본문 requestBody, 응답본문 responseBody를 담아서 보내야 한다.
}