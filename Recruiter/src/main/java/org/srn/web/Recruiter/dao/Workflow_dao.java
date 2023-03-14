package org.srn.web.Recruiter.dao;

import java.util.List;

import org.srn.web.Recruiter.entity.Workflow;

public interface Workflow_dao {

	public boolean newWorkflow(Workflow flow);

	public boolean deleteWorkflowById(long workflow_id);

	public List<Workflow> fetchById(long workflow_id);

	
}
