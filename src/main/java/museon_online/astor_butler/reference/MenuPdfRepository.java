package museon_online.astor_butler.reference;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MenuPdfRepository extends JpaRepository<MenuPdf, UUID> {
    List<MenuPdf> findAllByCategoryAndVisibleIsTrueOrderByOrderIndex(MenuCategory category);
}