package pt.saudemin.hds.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.Set;

@Entity
@Table(indexes = {
    @Index(columnList = "personal_id", name = "user_personal_id")
})
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Getter
    @Setter
    @Column(name = "personal_id", nullable = false, unique = true)
    @NotNull
    private Integer personalId;

    @Getter
    @Setter
    @Column(nullable = false)
    @NotBlank
    private String name;

    @Getter
    @Setter
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Getter
    @Setter
    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_inquiry",
        joinColumns = @JoinColumn(name = "inquiry_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<Inquiry> inquiries;
}
