package museon_online.astor_butler.reference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuPdf implements Serializable {
    private String title;
    private String fileUrl;
}