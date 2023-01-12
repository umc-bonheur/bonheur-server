package com.bonheur.domain.tag.model;

import com.bonheur.domain.boardtag.model.BoardTag;
import com.bonheur.domain.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
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

    @Builder
    public Tag(String name){
        this.name = name;
    }
}