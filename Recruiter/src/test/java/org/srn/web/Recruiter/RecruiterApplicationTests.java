package org.srn.web.Recruiter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.srn.web.Recruiter.constant.VMessageConstant;
import org.srn.web.Recruiter.dao_impl.Application_dao_impl;
import org.srn.web.Recruiter.dao_impl.Company_dao_impl;
import org.srn.web.Recruiter.dao_impl.Profile_dao_impl;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.entity.Applications;
import org.srn.web.Recruiter.entity.Company;
import org.srn.web.Recruiter.entity.Profiles;

@SpringBootTest(
    classes = RecruiterApplication.class)
class RecruiterApplicationTests {

	@Autowired
	private Application_dao_impl application_repos;

	@Autowired
	private Profile_dao_impl profile_repos;
	
//	@MockBean
//	private Company_dao_impl company_repos;
//
//
//	@Test
//	public void getAllProfile() {
//		assertNotEquals(0, profile_repos.findAllProfile().size());
//	}
//
//	@Test
//	public void getProfileByProfile_idOne() {
//		assertNotEquals(null, profile_repos.fetchById(1));
//		assertEquals(1, profile_repos.fetchById(1).size());
//	}
//	
//	@Test
//	public void getProfileByContactAndEmail() {
//		String contact ="9109211205";
//		String email ="pankaj3214sharma@gmail.com";
//		assertNotNull("invalid email",email);
//		assertNotNull("invalid contact", contact);
//		assertEquals(1,profile_repos.getProfileByContactAndEmail(contact, email).size());
//		}
//	
//
//	@Test
//	public void addPankajProfile() {
//		Profiles prof = new Profiles();
//		prof.setName("pankaj sharma");
//		prof.setContact("9109211206");
//		prof.setAlternate_contact("55845125155");
//		prof.setEmail("pankaj32145sharma@gmail.com");
//		prof.setAlternate_email(null);
//		prof.setCurrent_role("java developer");
//		prof.setExp(2);
//		prof.setCtc(20000);
//		prof.setEctc(50000);
//		prof.setNotice_period(30);
//		prof.setDomain("java");
//		prof.setPrimary_skill("java,spring boot");
//		prof.setSecondary_skill("react js");
//		prof.setQualification("B.E.(I.T.)");
//		prof.setLocation("gwalior");
//		prof.setResume("pankaj.docx");
//		prof.setYear_of_passing(2013);
//		prof.setCompany_id(1);
//		prof.setCompany("sroniyan tech pvt ltd");
//		prof.setCreated_by("pankaj.sharma@sroniyan.com");
//		prof.setProf_creation_date(new Date());
//		prof.setStatus(1);
//		System.out.println(prof.toString());
//		boolean check =false;
//		try {
//		check= profile_repos.saveProfile(prof);
//		System.out.println(check);
//		}catch(Exception e) {
//			check =false;
//		}
//		assertEquals(false, check);
//	}
//
//	
//	@Test
//	public void findCompanyByName() {
////		Company_dao_impl company_repos = mock(Company_dao_impl.class);
//		Company company = new Company();
//	    company.setCompany_id(55);
//	    company.setCompany_name("Minglebox");
//	    company.setCreated_by("pankaj.sharma@sroniyan.com");
//	    company.setIndustry("IT");
//	    company.setLocation("new york USA");
//	    company.setMaximum_employees(1000);
//	    company.setMinimum_employees(500);
//	    company.setCompany_creation_date(new Date());
//	    company.setRanking("A");
//	    company.setStatus(1);
//	    company.setWebsite("www.minglebox.com");
//		List<Company> list = new ArrayList<Company>();
//		list.add(company);		
//		when(company_repos.findByCompanyName("Minglebox")).thenReturn(list);
//		assertEquals(1, company_repos.findByCompanyName("Minglebox").size());
//		
//	}
//	
//	@Test
//	public void fetchByWebsite() {
//		List mockList = mock(List.class);
//		Company company = new Company(4,new Date(), "srn","IT","Delhi",100,50,"srn.com","pankaj3214sharma@gmail.com","B",1 );
//		mockList.add(company);
//		when(company_repos.fetchCompanyByWebsite("http://10000startups.com/CodeForTheNextBillion/")).thenReturn(mockList);
//		assertEquals(1, mockList);
//	}
//
//	@Test
//	public void fetchById() {
//		long application_id = 1;
//		Application_dao_impl application_repos = new Application_dao_impl();
//		List<Applications> list = application_repos.fetchById(application_id);
//		try {
//			if (application_id <= 0) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.ID_MISSING);
//				response.setBody("unprocessable input");
//				response.setStatus(true);
//				System.out.println(response.getMessage());
//			} else if (list == null || list.isEmpty()) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.Not_Found);
//				response.setBody(null);
//				response.setStatus(true);
//				System.out.println(response.getMessage());
//			} else {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.MESSAGE);
//				response.setBody(list);
//				response.setStatus(true);
//				System.out.println(response.getMessage());
//			}
//		} catch (Exception e) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.DEFAULT);
//			response.setBody(null);
//			response.setStatus(false);
//			System.out.println(response.toString());
//		}
//
//	}
//
//	@Test
//	public void fetchAllRecords() {
//		List<Profiles> list = profile_repos.findAllProfile();
//		try {
//			if (list.isEmpty()) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.Not_Found);
//				response.setBody(null);
//				response.setStatus(true);
//				System.out.println(response.getMessage());
//			} else {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage(VMessageConstant.MESSAGE);
//				response.setBody(list);
//				response.setStatus(true);
//				System.out.println(response.getMessage());
//			}
//		} catch (Exception e) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.DEFAULT);
//			response.setBody(null);
//			response.setStatus(false);
//			System.out.println(response.getMessage());
//		}
//	}
//
//	@Test
//	void junitestdemo() {
//		System.out.println("Hello");
//	}
//	
//	
//	
	
}
