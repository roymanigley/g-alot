package g-alot{basePackage}.model;

import java.io.Serializable;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table
public class g-alot{Entity} implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable=false)
    private Long id;

    @Column
    private LocalDate date;
     
    @Column
    private LocalDateTime dateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        g-alot{Entity} g-alot{entity} = (g-alot{Entity}) o;
        return Objects.equals(id, g-alot{entity}.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /*

    --- RELATIONS ---

    public class Employee {
    @ManyToOne
    @JoinColumn(name="ID_DEPARTMENT") 
    private Department department;
    }

    public class Department {
        @OneToMany(mappedBy="department")
        private List<Employee> employees;
    }

    public class Project {
        @ManyToMany
        @JoinTable(name="EMPLOYEE_PROJECT",
            joinColumns=@JoinColumn(name="PROJECT_ID"),
            inverseJoinColumns=@JoinColumn(name="EMPLOYEE_ID")
        )
        private List<Employee> relatedEmployees;
    }

    public class Employee {    
        @ManyToMany(mappedBy="relatedEmployees")
        private List<Project> relatedProjects;
    }
    */
}
