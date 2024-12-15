package vn.edu.iuh.fit.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.iuh.fit.models.Candidate;
import vn.edu.iuh.fit.models.Job;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByCompanyId(Long companyId);

    @Query("SELECT c FROM Candidate c JOIN c.jobs j WHERE j.id = :jobId")
    List<Candidate> findCandidatesByJobId(@Param("jobId") Long jobId);
    Optional<Job> findById(Long id);
    List<Job> findByCandidates(Candidate candidate);

    @Query("SELECT DISTINCT j FROM Job j JOIN JobSkill js ON j.id = js.job.id WHERE js.skill.id = :skillId")
    List<Job> findJobsBySkillId(@Param("skillId") Long skillId);
}