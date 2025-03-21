package museon_online.astor_butler.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MerchOrder extends Order {

    @Column(nullable = false)
    private String productName;

}
