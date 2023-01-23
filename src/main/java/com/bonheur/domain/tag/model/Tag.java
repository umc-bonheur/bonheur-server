package com.bonheur.domain.tag.model;

import com.bonheur.domain.boardtag.model.BoardTag;
import com.bonheur.domain.common.BaseEntity;
import com.bonheur.domain.membertag.model.MemberTag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Tag extends BaseEntity {
    @Column(nullable = false, length = 10)
    private String name;

    @OneToMany(mappedBy = "tag")
    private List<BoardTag> boardTags = new ArrayList<>();

    @OneToMany(mappedBy = "tag")
    private List<MemberTag> memberTags = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    private Tag(String name){
        this.name = name;
    }
    public static Tag newTag(String name){
        return Tag.builder()
                .name(name)
                .build();
    }
}