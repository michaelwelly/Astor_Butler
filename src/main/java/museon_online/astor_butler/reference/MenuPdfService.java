package museon_online.astor_butler.reference;

import java.util.List;

public interface MenuPdfService {
    List<MenuPdf> getPdfMenu(MenuCategory category);
}