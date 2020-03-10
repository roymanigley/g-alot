package g-alot{basePackage}.model;

package ch.bytecrowd.demo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
public class g-alot{OwnerEntity} implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "g-alot{OwnerEntity}_Id", nullable=false)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "g-alot{OwnerEntity}_g-alot{MappedEntity}",
            joinColumns = @JoinColumn(name = "g-alot{OwnerEntity}_Id"),
            inverseJoinColumns = @JoinColumn(name = "g-alot{MappedEntity}_Id")
    )
    public Set<g-alot{MappedEntity}> g-alot{mappedEntity}s = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addg-alot{MappedEntity}(g-alot{MappedEntity} g-alot{mappedEntity}) {
        g-alot{mappedEntity}.getg-alot{OwnerEntity}s().add(this);
        this.g-alot{mappedEntity}s.add(g-alot{mappedEntity});
    }

    public void removeg-alot{MappedEntity}(g-alot{MappedEntity} g-alot{mappedEntity}) {
        g-alot{mappedEntity}.getg-alot{OwnerEntity}s().add(this);
        this.g-alot{mappedEntity}s.add(g-alot{mappedEntity});
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        g-alot{OwnerEntity} that = (g-alot{OwnerEntity}) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


package g-alot{basePackage}.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
public class g-alot{MappedEntity} implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "g-alot{MappedEntity}_Id", nullable=false)
    private Long id;

    @ManyToMany(mappedBy = "g-alot{mappedEntity}s")
    private Set<g-alot{OwnerEntity}> g-alot{ownerEntity}s = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<g-alot{OwnerEntity}> getg-alot{OwnerEntity}s() {
        return g-alot{ownerEntity}s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        g-alot{MappedEntity} that = (g-alot{MappedEntity}) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


