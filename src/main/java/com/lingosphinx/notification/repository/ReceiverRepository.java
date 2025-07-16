package com.lingosphinx.notification.repository;

import com.lingosphinx.notification.domain.Receiver;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface ReceiverRepository extends JpaRepository<Receiver, Long>, JpaSpecificationExecutor<Receiver> {
    @EntityGraph(attributePaths = {"channels"})
    Optional<Receiver> findById(Long id);

    @EntityGraph(attributePaths = {"channels"})
    Optional<Receiver> findByUserId(UUID userId);
}
