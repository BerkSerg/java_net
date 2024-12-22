package org.example.demo.bookingservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "property_photos")
public class PropertyPhotos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String originalFileName;
    String storageFileName;
    String extension;
    Long size;

    @ManyToOne(fetch = FetchType.LAZY)
    Property property;

}
