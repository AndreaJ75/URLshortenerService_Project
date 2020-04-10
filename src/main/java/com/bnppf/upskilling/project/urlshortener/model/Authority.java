package com.bnppf.upskilling.project.urlshortener.model;

import javax.persistence.*;

@Entity
public class Authority {

    @Id
    @SequenceGenerator(name="authority_level_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_level_seq")
    private Long id;

    @Column(name="authority_level")
    @Enumerated(EnumType.STRING)
    private AuthorityLevel authorityLevel;

    /**
     * GETTER accessor for all attributes
     * @return
     */

    public Long getId() {
        return id;
    }

    public AuthorityLevel getAuthorityLevel() {
        return authorityLevel;
    }

    /**
     * SETTER accessor for all attributes
     * @return
     */
    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthorityLevel(AuthorityLevel authorityLevel) {
        this.authorityLevel = authorityLevel;
    }

}
