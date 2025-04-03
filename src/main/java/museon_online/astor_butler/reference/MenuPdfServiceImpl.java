package museon_online.astor_butler.reference.service;

import museon_online.astor_butler.reference.MenuCategory;
import museon_online.astor_butler.reference.MenuPdf;
import museon_online.astor_butler.reference.MenuPdfRepository;
import museon_online.astor_butler.reference.MenuPdfService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuPdfServiceImpl implements MenuPdfService {

    private final MenuPdfRepository repository;

    public MenuPdfServiceImpl(MenuPdfRepository repository) {
        this.repository = repository;
    }

    @Override
    @Cacheable(value = "menu_pdf", key = "#category.name()")
    public List<MenuPdf> getPdfMenu(MenuCategory category) {
        return repository.findAllByCategoryAndVisibleIsTrueOrderByOrderIndex(category).stream()
                .map(e -> new MenuPdf(e.getTitle(), e.getFileUrl()))
                .toList();
    }
}