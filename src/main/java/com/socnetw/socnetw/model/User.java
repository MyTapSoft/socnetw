package com.socnetw.socnetw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"posts"})
@Entity
@Table(name = "USERS")
@ToString(exclude = {"posts"})
public class User implements Serializable {
    @Id
    @SequenceGenerator(name = "USER1_SEQ", sequenceName = "USER1_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "USER1_SEQ")
    @Column(name = "USER_ID")
    private Long id;

    @NotBlank
    @Size(min = 2, max = 16)
    private String username;

    @NotBlank
    @Size(min = 2, max = 32)
    private String realName;

    private String email;

    // @Pattern(regexp = "^((\\+7|7|8)+([0-9]){10})$")
    private String phoneNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-DD HH:mm:ss")
    private LocalDate birthDate;

    @OneToMany(targetEntity = Post.class, mappedBy = "userPosted", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Post> posts = new HashSet<>();

    @NotBlank
    // @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id")})
    private Set<UserAuthority> authorities = new HashSet<>();

}
