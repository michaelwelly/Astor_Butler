package museon_online.astor_butler.merch;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import museon_online.astor_butler.orderOLD.Order;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MerchOrder extends Order {

    @Column(nullable = false)
    private String productName;

}
