package uz.pdp.proyekt.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "imageDate")
@Builder
@Table(name = "image_date")
public class AttachmentEntity extends BaseEntity{
    private String name;
    private String contentType;
    private long size;
    @Lob
    @Column(name = "bytes")
    private byte[] bytes;
}
