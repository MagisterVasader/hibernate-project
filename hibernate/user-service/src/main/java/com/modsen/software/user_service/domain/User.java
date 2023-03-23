package com.modsen.software.user_service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
// TODO: NOTE
// While using strategy READ_ONLY you can mark object @Immutable
// and add the property hibernate.query.immutable_entity_update_query_handling_mode=exception
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    // TODO: NOTE
    // If we cache a collection, we need to add @Cache to the collection and to the object,
    // because the collections in the cache contain object IDs.
    @Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "roles")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Set<Role> roles;
}
