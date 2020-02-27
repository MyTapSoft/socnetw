package com.socnetw.socnetw.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Table(name = "POST")
@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"usersTagged", "userPagePosted"})
@ToString
public class Post  {
    @Id
    @SequenceGenerator(name = "POST_SEQ", sequenceName = "POST_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "POST_SEQ")
    @Column(name = "POST_ID")
    private Long id;

    @NotBlank
    @Size(min = 2, max = 1024)
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-DD HH:mm:ss")
    private LocalDate datePosted;

    @NotBlank
    @Size(min = 2, max = 128)
    private String location;

    @ManyToMany
    @JoinTable(
            name = "USERS_POST",
            joinColumns = {@JoinColumn(name = "POST_ID")},
            inverseJoinColumns = {@JoinColumn(name = "USER_ID")}
    )
    private Set<User> usersTagged = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "USER_POSTED")
    @JsonBackReference
    private User userPosted;

    @ManyToOne
    @JoinColumn(name = "USER_PAGE_POSTED")
    private User userPagePosted;

}
