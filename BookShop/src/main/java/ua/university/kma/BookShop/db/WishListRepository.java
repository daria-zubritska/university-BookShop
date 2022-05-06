package ua.university.kma.BookShop.db;


import org.springframework.data.jpa.repository.JpaRepository;
import ua.university.kma.BookShop.dto.model.WishList;

public interface WishListRepository extends JpaRepository<WishList, Long> {
}
