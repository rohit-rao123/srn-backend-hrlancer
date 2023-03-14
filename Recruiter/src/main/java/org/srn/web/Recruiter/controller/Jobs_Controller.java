package org.srn.web.Recruiter.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.service_impl.Jobs_Service_Impl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false",allowedHeaders = {"Authorization"} )
//@CrossOrigin(origins= {"http://localhost:8000"}, maxAge = 3600, allowCredentials = "false" )

@RestController
@RequestMapping(value = "/Recruit/Jobs")
//@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
@Api(value = "Job_microservice", description = "Jobs Management Operation ", tags = "Jobs Controller")
public class Jobs_Controller {

	@Autowired
	private Jobs_Service_Impl jobs_service;

	@GetMapping("/search")
	@ApiOperation(value = "search jobs", notes = "This Rest Api fetch the jobs records ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> search(HttpSession session, @RequestHeader("Authorization") String token,
			@ApiParam(value = "search by job_id", required = false) @RequestParam(defaultValue = "0", required = false) long job_id,
			@ApiParam(value = "search by client_id", required = false) @RequestParam(defaultValue = "0", required = false) int client_id,
			@ApiParam(value = "search by jobtype ", required = false) @RequestParam(defaultValue = "", value = "job_type", required = false) String job_type,
			@ApiParam(value = "search by domain", required = false) @RequestParam(defaultValue = "", required = false) String domain,
			@ApiParam(value = "search by skills", required = false) @RequestParam(defaultValue = "", required = false) String skills,
			@ApiParam(value = "search by location", required = false) @RequestParam(defaultValue = "", required = false) String location,
			@ApiParam(value = "search by min_yr_exp", required = false) @RequestParam(defaultValue = "0", required = false) double min_yr_exp,
			@ApiParam(value = "search by max_yr_exp", required = false) @RequestParam(defaultValue = "0", required = false) double max_yr_exp,
			@ApiParam(value = "search by max_yr_budget", required = false) @RequestParam(defaultValue = "0", required = false) double max_yr_budget,
			@ApiParam(value = "search by working_mode", required = false) @RequestParam(defaultValue = "", required = false) String working_mode,
			@ApiParam(value = "search by open", required = false) @RequestParam(defaultValue = "-1", required = false) int open) {
		System.out.println("controller");
		return jobs_service.searchByOptions(session, token, job_id, client_id, job_type, domain, skills, location,
				min_yr_exp, max_yr_exp, max_yr_budget, working_mode, open);
	}

	@PostMapping("/add/jobs")
	@ApiOperation(value = " add jobs data", notes = "This Rest Api  add new  jobs records in the database ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> addJobsParameter(HttpSession session,
			@RequestHeader("Authorization") String token,
			@RequestParam(defaultValue = "0", required = false) int client_id,
			@RequestParam(defaultValue = "", required = false) String domain,
			@RequestParam(defaultValue = "", required = false) String job_role,
			@RequestParam(defaultValue = "", required = false) String skills,
			@RequestParam(defaultValue = "", required = false) String jd,
			@RequestParam(defaultValue = "0", required = false) double min_yr_exp,
			@RequestParam(defaultValue = "0", required = false) double max_yr_exp,
			@RequestParam(defaultValue = "0", required = false) double max_yr_budget,
			@RequestParam(defaultValue = "", required = false) String qualification,
			@RequestParam(defaultValue = "", required = false) String location,
			@RequestParam(defaultValue = "", required = false) String job_type,
			@RequestParam(defaultValue = "", required = false) String working_mode,
			@RequestParam(defaultValue = "0", required = false) int head_count,
			@RequestParam(defaultValue = "0", required = false) int notice) {

		return jobs_service.addrecord(session, token, client_id, domain, job_role, skills, jd, min_yr_exp, max_yr_exp,
				max_yr_budget, qualification, location, job_type, working_mode, head_count, notice);
	}

	@GetMapping("/getAll/jobs")
	@ApiOperation(value = "get all jobs", notes = "This Rest Api fetch all the jobs records ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<ResponseDto> getAll(HttpSession session, @RequestHeader("Authorization") String token) {
		return jobs_service.fetchAllRecords(session, token);
	}

//   /Recruit/Jobs/download_pdf
	@GetMapping(value = "/download-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	@ApiOperation(value = "download job description", notes = "This Rest Api get the pdf file of job description")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad request error"),
			@ApiResponse(code = 401, message = "Unauthorized client error"),
			@ApiResponse(code = 403, message = "Forbidden client error"),
			@ApiResponse(code = 404, message = "Invalid data"),
			@ApiResponse(code = 500, message = "Internal server error") })
	public ResponseEntity<InputStreamResource> createAndDownloadPdf(HttpSession session,
			@RequestHeader("Authorization") String token, @RequestParam(required = false) String job_id) {
		try {
			return jobs_service.generatePdf(session, token, job_id);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

//	@GetMapping("/getBy/id")
//	@ApiOperation(value = " get by Jobs_id", notes = "This Rest Api fetch the Jobs detail by job id")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> getById(HttpServletRequest request,
//			@ApiParam(value = "To access the Jobs service record by jobs_id number", required = true) @RequestParam long Jobs_id) {
//
//		return jobs_service.fetchById(request, Jobs_id);
//	}
//
//	@GetMapping("/getBy/job_name")
//	@ApiOperation(value = " get by Jobs_name", notes = "This Rest Api fetch the Jobs detail by job name")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> getByName(HttpServletRequest request,
//			@ApiParam(value = "To access the Jobs service record by job_name", required = true) @RequestParam String Jobs_name) {
//
//		return jobs_service.fetchByName(request, Jobs_name);
//	}
//
//	@GetMapping("/getBy/client_id")
//	@ApiOperation(value = " get by Client Id", notes = "This Rest Api fetch the Jobs detail by Client Id")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> getByCid(HttpServletRequest request,
//			@ApiParam(value = "To access the Jobs service record by client_id number", required = true) @RequestParam int client_id,
//			@ApiParam(value = "To access the Jobs service record by jobs_name", required = true) @RequestParam String Jobs_name) {
//
//		return jobs_service.fetchByCid(request, client_id, Jobs_name);
//	}
//

//	
//	// domain,skill,min/maxexp,maxbudget,location,jobtype,client_id,workingmode,open.
//                                          

//	@DeleteMapping("/delete/jobs")
//	@ApiOperation(value = "delete jobs ", notes = "This Rest Api delete the jobs records by jobs_id ")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> deleteById(HttpServletRequest request,
//			@ApiParam(value = "Enter the job_id number", required = true) @RequestParam int jobs_id) {
//		return jobs_service.eraseByJobsId(request, jobs_id);
//	}

//	@PutMapping("/update")
//	@ApiOperation(value = "update the jobs", notes = "This Rest Api update  the jobs records by given jobs_id ")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> updateId(HttpServletRequest request,
//			@ApiParam(value = "To update the job by job_id ", required = true) @RequestParam long job_id,
//			@ApiParam(value = "update head_count of job", required = false) @RequestParam(required = false, defaultValue = "") String head_count,
//			@ApiParam(value = "update hired_count of job ", required = false) @RequestParam(required = false, defaultValue = "") String hired_count,
//			@ApiParam(value = "update max_exp of job", required = false) @RequestParam(required = false, defaultValue = "") String max_exp,
//			@ApiParam(value = "update max_salary of job ", required = false) @RequestParam(required = false, defaultValue = "") String max_salary,
//			@ApiParam(value = "update min_exp of job", required = false) @RequestParam(required = false, defaultValue = "") String min_exp,
//			@ApiParam(value = "update open of job", required = false) @RequestParam(required = false, defaultValue = "") String open,
//			@ApiParam(value = "update domain of job", required = false) @RequestParam(required = false, defaultValue = "") String domain,
//			@ApiParam(value = "update status of job", required = false) @RequestParam(required = false, defaultValue = "-1") int status
//
//	) {
//		return jobs_service.updateRecordById(request, job_id, head_count, hired_count, max_exp, max_salary, min_exp,
//				open, domain, status);
//	}

//	@PutMapping("/update")
//	@ApiOperation(value = "update the jobs", notes = "this Rest Api update  the jobs records by given jobs_id ")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> updateById(@RequestParam long job_id,
//			@RequestParam String head_count,
//			@RequestParam String hired_count,
//			@RequestParam String max_exp, 
//			@RequestParam String max_salary,
//			@RequestParam String min_exp,
//			@RequestParam String open, 
//			@RequestParam String domain, 
//			@RequestParam int status) {
//		return jobs_service.editRecordById(job_id, head_count, hired_count,
//				max_exp, max_salary, min_exp, open, domain, status);
//	}

//	@PostMapping("/add/jobs")
//	@ApiOperation(value = " add jobs data", notes = "this Rest Api  add new  jobs records in the database ")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> addJobs(@RequestBody Jobs job) {
//		return jobs_service.addNewrecord(job);
//	}

//	long job_id, int client_id, String domain, String job_role, String skills, String jd, double min_yr_exp,
//	double max_yr_exp, double max_yr_budget, String qualification, String location, String job_type,
//	String working_mode, int head_count, int hired_count, String open, String created_by, Date dt, int status

//	@GetMapping("/downloadBy/job_name")
//	@ApiOperation(value = " search and download jobs data by job name", notes = "This Rest Api  search  jobs records by job name and download file ")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<Resource> downloadCsv(HttpServletRequest request, HttpServletResponse response,
//			@ApiParam(value = "To access the Jobs service record by jobs_name", required = true) @RequestParam String Jobs_name)
//			throws IOException {
//
//		return jobs_service.downloadService(request, Jobs_name);
//	}

//	@GetMapping("/downloadAll/jobs")
//	@ApiOperation(value = " search and download jobs data", notes = "This Rest Api  search  jobs records by job name and download file ")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<Resource> downloadCsv(HttpServletRequest request, HttpServletResponse response)
//			throws IOException {
//
//		return jobs_service.downloadAllServices(request);
//	}

}
