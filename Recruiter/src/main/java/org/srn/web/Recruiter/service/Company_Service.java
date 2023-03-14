package org.srn.web.Recruiter.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.entity.Company;

public interface Company_Service {

	public ResponseEntity<ResponseDto> addNewrecord(HttpServletRequest request, Company comp);

	public ResponseEntity<ResponseDto> fetchCompany(HttpServletRequest request, String company_name);

	public ResponseEntity<ResponseDto> eraseByCompanyId(HttpServletRequest request, long company_id);

	public ResponseEntity<ResponseDto> addCompanyFile(HttpServletRequest request, MultipartFile file);

	public ResponseEntity<ResponseDto> addrecord(HttpServletRequest request, String company_name, String website, long maximum_employees,
			long minimum_employees, String location, String industry, String ranking);

	public ResponseEntity<ResponseDto> edit(HttpServletRequest request, long company_id, String company_name, String website, long minimum_employees,
			long maximum_employees, String location, String industry, String ranking, int status);


}
