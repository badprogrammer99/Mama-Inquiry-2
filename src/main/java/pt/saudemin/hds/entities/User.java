package pt.saudemin.hds.entities;

import lombok.*;

import javax.persistence.*;

import java.util.Set;

@Entity
@Table(indexes = {
    @Index(columnList = "personal_id", name = "user_personal_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "personal_id", nullable = false, unique = true)
    private Integer personalId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_inquiry",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "inquiry_id")
    )
    private Set<Inquiry> inquiries;
}
