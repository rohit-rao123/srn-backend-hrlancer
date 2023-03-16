package org.srn.web.Recruiter.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.srn.web.Recruiter.entity.Bench;

public interface Bench_dao {
	
	public boolean saveBench(Bench bench);
	
	public List<Bench> getByBenchId(HttpSession session, long bench_id);
	
	public List<Bench> getByClientId(HttpSession session, long client_id);
	public List<Bench> getByBenchName(HttpSession session, String name);
	
	public List<Bench> getByContact(HttpSession session, String contact);
	public List<Bench> getByEmail(HttpSession session, String email);
	
	public List<Bench> getByMinExp(HttpSession session, double min_exp);
	
	public List<Bench> getByMaxExp(HttpSession session, double max_exp);
	
	public List<Bench> getByDomain(HttpSession session, String domain);
	
	public List<Bench> getByBenchType(HttpSession session, String bench_type);
	
	public List<Bench> getBypSkill(HttpSession session, String primary_skill);
	
	public List<Bench> getBysSkill(HttpSession session, String secondary_skill);
	
	public List<Bench> getByCurrentRole(HttpSession session, String current_role);
	
	public List<Bench> getByQualification(HttpSession session, String qualification);
	
	public List<Bench> getByResume(HttpSession session, String resume);
	
	public List<Bench> getByStartDt(HttpSession session, String bench_startDt);
	
	public List<Bench> getByEndDt(HttpSession session, String bench_endDt);
	
	public List<Bench> getByBenchStatus(HttpSession session, String bench_status);
	
	public List<Bench> getByCreateBy(HttpSession session, String created_by);
	
	public List<Bench> getByDt(HttpSession session, String dt);
	
	public List<Bench> getByStatus(HttpSession session, int status);
	
	public List<Bench> getAllBenchs(HttpSession session);

}
