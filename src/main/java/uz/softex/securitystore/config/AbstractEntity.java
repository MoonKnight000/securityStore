package uz.softex.securitystore.config;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.softex.securitystore.workers.entity.Workers;

import javax.persistence.*;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private java.sql.Timestamp createdTime;

    @Column(nullable = false)
    @UpdateTimestamp
    private java.sql.Timestamp updatedAt;

    @JoinColumn(updatable = false)//user obyekt bolganligin uchun @Columnnni o`rniga @JoinColumnni ishlatamiz
    @CreatedBy
    @ManyToOne
    private Workers createdByUser;

    @LastModifiedBy
    @ManyToOne
    private Workers updatedByUser;



}
