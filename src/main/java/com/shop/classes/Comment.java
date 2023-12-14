package com.shop.classes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    private String comment;
    private LocalDate postedAt;
    @ManyToOne
    private User postedBy;

    public Comment(String comment, LocalDate postedAt, User postedBy) {
        this.comment = comment;
        this.postedAt = postedAt;
        this.postedBy = postedBy;
    }
}
