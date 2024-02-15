package ru.enorezero.pasteservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.enorezero.pasteservice.model.PasteEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasteRepository extends JpaRepository<PasteEntity,Long> {
    Optional<PasteEntity> findOptionalByPastesId(Long pastesId);
    List<PasteEntity> findPasteEntitiesByUsername(String username);
}
