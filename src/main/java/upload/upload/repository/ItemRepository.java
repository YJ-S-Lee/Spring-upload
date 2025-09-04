package upload.upload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import upload.upload.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}