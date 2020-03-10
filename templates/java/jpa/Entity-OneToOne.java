package g-alot{basePackage}.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table
public class g-alot{OwnerEntity} implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable=false)
    private Long id;

    @OneToOne
    @JoinColumn
    public g-alot{MappedEntity} g-alot{mappedEntity};

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public g-alot{MappedEntity} getg-alot{MappedEntity}() {
        return g-alot{mappedEntity};
    }

    public void setg-alot{MappedEntity}(g-alot{MappedEntity} g-alot{mappedEntity}) {
        if (g-alot{mappedEntity} != null)
            g-alot{mappedEntity}.setg-alot{OwnerEntity}(this);
        this.g-alot{mappedEntity} = g-alot{mappedEntity};
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
import java.util.Objects;

@Entity
@Table
public class g-alot{MappedEntity} implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable=false)
    private Long id;

    @OneToOne(mappedBy = "g-alot{mappedEntity}")
    private g-alot{OwnerEntity} g-alot{ownerEntity};

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public g-alot{OwnerEntity} getg-alot{OwnerEntity}() {
        return g-alot{ownerEntity};
    }

    public void setg-alot{OwnerEntity}(g-alot{OwnerEntity} g-alot{ownerEntity}) {
        if (g-alot{ownerEntity} != null)
            g-alot{ownerEntity}.setg-alot{MappedEntity}(this);
        this.g-alot{ownerEntity} = g-alot{ownerEntity};
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

