package uz.pdp.proyekt.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "feedback")
@Table(name = "feedback")
public class FeedbackEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserEntity user;

    @Column(nullable = false)
    private int rate;

    @Column(nullable = false)
    private String text; 

}
