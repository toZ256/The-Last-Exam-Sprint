package id.ac.ui.tom.backend.repository;

import id.ac.ui.tom.backend.entity.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    List<Achievement> findByMinScoreRequiredLessThanEqualOrderByMinScoreRequiredDesc(
            Integer score
    );
}