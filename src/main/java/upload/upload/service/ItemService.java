package upload.upload.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import upload.upload.domain.Item;
import upload.upload.repository.ItemRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    //저장
    public Long save(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    //아이디로 조회하기
    public Optional<Item> findOneItem(Long itemId) {
        return itemRepository.findById(itemId);
    }
}