package org.srn.web.Recruiter.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.srn.web.Recruiter.dto.ResponseDto;

public interface BenchService {

	ResponseEntity<ResponseDto> addNewBench(HttpSession session, String token,long partner_id, String name,
			String contact, String alternateContact, String email, String alternateEmail, String exp, String domain, String bench_type,
			String primarySkill, String secondarySkill,String budget,String salary,String org_name,String org_url, String currentRole, String qualification,String is_shift_flexibility,String location,String working_mode,String notice_period,MultipartFile resume);

	ResponseEntity<ResponseDto> searchBench(HttpSession session, String token, String benchId, String partner_id,
			String name, String contact, String email, String min_exp,String max_exp, String domain, String bench_type, List<String> primary_skill,
			String secondary_skill,String min_budget,String max_budget,String salary,String org_name,String org_url, String current_role, String qualification,String is_shift_flexibility,String location, String working_mode,String bench_start_dt);

	ResponseEntity<ResponseDto> fetchAllBenches(HttpSession session, String token);

	//String bench_start_dt,String bench_end_dt,String bench_status, String created_by, String dt, String status
}
