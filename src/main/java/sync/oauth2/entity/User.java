package sync.oauth2.entity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String username;
    private String nickname;
    private String position;
    @Lob
    private String introduction;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "authentication_id")
    private Authentication authentication;
}
