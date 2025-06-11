package com.github.mediummaterial.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="users")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    private Long id;

    private String userCode;

    private String fullName;

    private String password;

    private String email;

    private String phone;

    private String address;

    private String profilePicture;

    protected String modifiedBy;
    @Temporal(TemporalType.TIMESTAMP)
    protected Date modifiedDate;

    @Column(
            name = "is_deleted"
    )
    protected Boolean deleted = false;
    protected String createdBy;
    @Column(
            updatable = false
    )
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate;
}
