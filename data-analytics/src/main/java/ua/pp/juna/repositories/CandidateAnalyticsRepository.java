package ua.pp.juna.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ua.pp.juna.domain.CandidateAnalytics;

public interface CandidateAnalyticsRepository extends MongoRepository<CandidateAnalytics, String> {
    CandidateAnalytics findByEmail(String email);
}
