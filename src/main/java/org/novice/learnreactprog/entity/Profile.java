package org.novice.learnreactprog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "profiles")
@Data
@NoArgsConstructor@AllArgsConstructor
public class Profile {

    @Id
    private Long id;

    @Column("user_id")
    private Long userId; //as there are no mappings, in r2dbc

    private String bio;

    @Column("avatar_url")
    private String avatarUrl;



}
