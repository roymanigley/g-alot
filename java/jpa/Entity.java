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
