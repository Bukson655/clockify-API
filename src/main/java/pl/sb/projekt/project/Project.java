//package pl.sb.projekt.project;
//
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.UUID;
//
//@Entity
//@Getter
//@Setter
//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@Table(name = "project", schema = "public")
//public class Project {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private UUID uuid = UUID.randomUUID();
//    private String name;
//    private String description;
//    private LocalDate dateOfStart;
//    private LocalDate dateOfEnd;
//    private BigDecimal budget;
//    //relacja lista ManyToMany
//}