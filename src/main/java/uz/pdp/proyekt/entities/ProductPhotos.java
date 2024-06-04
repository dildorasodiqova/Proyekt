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
@Entity(name = "product_photos")
@Table(name = "product_photos")
public class ProductPhotos  extends BaseEntity {
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private ProductEntity product;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private AttachmentEntity photo;

    private int orderIndex;
}