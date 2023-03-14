package org.srn.web.Recruiter.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.srn.web.Recruiter.entity.Jobs;

public interface Jobs_dao {

	public List<Jobs> fetchById(HttpServletRequest request, long id);

	public List<Jobs> findAllJobs(HttpServletRequest request);

	public boolean saveJobs(Jobs job);

	public boolean deleteJobsById(long id);

//	public boolean updateJobsById(long job_id, String head_count,
//			String hired_count, String max_exp, String max_salary, String min_exp, String open,
//			String domain, int status);

	public List<Jobs> fetchByName(String jobs_name);

	public boolean updateJobsId(long job_id, String head_count, String hired_count, String max_exp, String max_salary,
			String min_exp, String open, String domain, int status);

	public Jobs getById(long id);

	public List<Jobs> fetchByCIdAndJobName(int client_id, String jobs_name);

	public List<Jobs> findLatestJobs();

    public List<Jobs> findByClientId(HttpServletRequest request,int  clientId);

    public List<Jobs> findAllJobs(HttpSession session);

    public List<Jobs> fetchById(HttpSession session, long id);

	public List<Jobs> findByClientId(HttpSession session, int clientId);

	public List<Jobs> findAll(HttpSession session);

	public String getJobDescription(long jobId);
    
    

}
