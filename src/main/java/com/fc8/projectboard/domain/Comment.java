package com.fc8.projectboard.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Comment extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false)
    private Article article;

    @Setter
    @ManyToOne(optional = false)
    private User user;

    @Setter
    @Column(nullable = false, length = 500)
    private String content;

    protected Comment() {
    }

    private Comment(Article article, User user, String content) {
        this.user = user;
        this.article = article;
        this.content = content;
    }

    public static Comment of(Article article, User user, String content) {
        return new Comment(article, user, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment comment)) return false;
        return id != null && id.equals(comment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
