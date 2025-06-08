package com.example.springboottest.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProxyEntity {

    @Id
    private Long id;

    public ProxyEntity(Long id) {
        this.id = id;
    }
}
