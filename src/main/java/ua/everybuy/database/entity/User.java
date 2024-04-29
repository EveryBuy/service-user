package ua.everybuy.database.entity;


import jakarta.persistence.*;
import lombok.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_full_name")
    private String fullName;

    @Column(name = "user_photo_url")
    private String userPhotoUrl;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "last_activity_date")
    private Date lastActivityDate;

    @Column(name = "is_block")
    private boolean isBlock;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @PrePersist
    public void onCreate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        creationDate = new Date();
        String formattedDate = sdf.format(creationDate);
        try {
            creationDate = sdf.parse(formattedDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        isBlock = false;
    }

    public User (Long id){
        this.id = id;
    }

}

