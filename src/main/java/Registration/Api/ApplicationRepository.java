package Registration.Api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<StoredApplication, UUID> {
    Page<StoredApplication> findByStatus(ApplicationStatus status, Pageable pageable);
    boolean existsByRequestKey(String requestKey);
}
