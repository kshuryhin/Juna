package ua.pp.juna.mentorservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.juna.mentorservice.model.VideoLink;

public interface VideoLinkRepository extends JpaRepository<VideoLink, Long> {
}
