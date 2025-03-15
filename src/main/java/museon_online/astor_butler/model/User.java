package museon_online.astor_butler.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String telegramId;

    private String firstName;
    private String lastName;
    private String username;

    @Column(nullable = false)
    private String phoneNumber;

    @Column
    private BigDecimal starsBalance = BigDecimal.ZERO;

    @Column
    private BigDecimal balance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.ROLE_GUEST;

}
