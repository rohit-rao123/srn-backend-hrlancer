package org.srn.web.Recruiter.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.srn.web.Recruiter.entity.Bench;


public interface BenchSetsRepository extends JpaRepository<Bench, Long> {
	
	 
}