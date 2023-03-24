package com.modsen.software.user_service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@NamedEntityGraph(
        name = "user.roles",
        attributeNodes = @NamedAttributeNode(
                value = "roles"
        )
)
@FetchProfile(
        name = "user.roles",
        fetchOverrides = {
                @FetchProfile.FetchOverride(
                        entity = User.class,
                        association = "roles",
                        mode = FetchMode.JOIN
                )
        }
)
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
    @Fetch(FetchMode.JOIN)
    private Set<Role> roles;
}
