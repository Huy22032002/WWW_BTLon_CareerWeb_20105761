package vn.edu.iuh.fit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.iuh.fit.models.Job;
import vn.edu.iuh.fit.models.JobSkill;
import vn.edu.iuh.fit.models.JobSkillId;
import vn.edu.iuh.fit.models.Skill;

import java.util.List;

public interface JobSkillRepository extends JpaRepository<JobSkill, JobSkillId> {
    List<JobSkill> findByIdJobId(Long jobId);

    @Query("SELECT js.skill FROM JobSkill js WHERE js.job.id = :jobId")
    List<Skill> findSkillByJobId(Long jobId);

    @Query("SELECT js.job FROM JobSkill js WHERE js.skill.id = :id")
    List<Job> findJobBySkillId(Long id);
}