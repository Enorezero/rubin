package ru.enorezero.pasteservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pastes")
public class PasteEntity {
    @Id
    @SequenceGenerator(name="hibernate_sequence", sequenceName="hibernate_sequence", allocationSize=1, initialValue = 10000000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence")
    @Column(name = "pastes_id", nullable = false)
    private Long pastesId;
    @Column(name = "storage_key_name", nullable = false)
    private String key;
    @Column(name = "expiration_time", nullable = false)
    private LocalDateTime expirationTime;
    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Visibility status;
    @Column(name = "username")
    private String username;
}
