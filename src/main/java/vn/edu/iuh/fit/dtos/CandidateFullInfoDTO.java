package vn.edu.iuh.fit.dtos;

import vn.edu.iuh.fit.models.Address;
import vn.edu.iuh.fit.models.Candidate;
import vn.edu.iuh.fit.models.CandidateSkill;
import vn.edu.iuh.fit.models.Experience;

import java.util.List;

public class CandidateFullInfoDTO {
    private Candidate candidate;
    private Address address;
    private List<Experience> experiences;
    private List<CandidateSkill> candidateSkills;

    // Getters và Setters
    public Candidate getCandidate() { return candidate; }
    public void setCandidate(Candidate candidate) { this.candidate = candidate; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public List<Experience> getExperiences() { return experiences; }
    public void setExperiences(List<Experience> experiences) { this.experiences = experiences; }

    public List<CandidateSkill> getCandidateSkills() { return candidateSkills; }
    public void setCandidateSkills(List<CandidateSkill> candidateSkills) { this.candidateSkills = candidateSkills; }
}
