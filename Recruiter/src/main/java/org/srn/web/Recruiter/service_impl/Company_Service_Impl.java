package org.srn.web.Recruiter.service_impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.multipart.MultipartFile;
import org.srn.web.Recruiter.Configuration.filters.MSRequestFilter;
import org.srn.web.Recruiter.component.FileHelper;
import org.srn.web.Recruiter.constant.VMessageConstant;
import org.srn.web.Recruiter.dao_impl.Company_dao_impl;
import org.srn.web.Recruiter.dto.ResponseDto;
import org.srn.web.Recruiter.entity.Company;
import org.srn.web.Recruiter.service.Company_Service;

@Service
@Transactional

public class Company_Service_Impl implements Company_Service {

	@Autowired
	private Company_dao_impl company_repos;
	
	@Autowired
	private MSRequestFilter msRequestFilter;

	@Override
	public ResponseEntity<ResponseDto> addNewrecord(HttpServletRequest request,  Company comp) {
//		try {
//			if (request.getSession().getAttribute("userName") == null) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage("Please login!");
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.DEFAULT);
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
		try {
			if (comp.getCompany_name().isBlank()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "Company name"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (comp.getRanking().isBlank()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "Ranking"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (comp.getWebsite().isBlank()) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.FIELD_MISSING.replace("<FIELD>", "Website"));
				response.setBody("unprocessable input");
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				Date date = new Date();
				comp.setCompany_creation_date(date);
				comp.setStatus(1);
				comp.setCreated_by((String) request.getSession().getAttribute("userName"));
				boolean check = company_repos.newCompany(comp);
				if (check == true) {
					System.out.println("New Company Saved with id " + comp.getCompany_id());
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.CREATED);
					response.setBody("Generated id : " + comp.getCompany_id());
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

				} else {
					System.out.println("This Company is not saved due to exception ");
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.Error);
					response.setBody(null);
					response.setStatus(false);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}

			}
		} catch (

		Exception e) {
			e.printStackTrace();
			System.out.println(e);
			ResponseDto response = new ResponseDto();
			response.setStatus(false);
			response.setMessage("Profile is not saved, due to exception");
			response.setBody(null);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

		}
	}

	@Override
	public ResponseEntity<ResponseDto> fetchCompany(HttpServletRequest request,  String company_name) {
//		try {
//			if (request.getSession().getAttribute("userName") == null) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage("Please login!");
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.DEFAULT);
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
		List<Company> list = company_repos.findByCompanyName(company_name);
		if (list.isEmpty() || list == null) {
			ResponseDto response = new ResponseDto();

			response.setMessage(VMessageConstant.Not_Found);
			response.setBody(null);
			response.setStatus(true);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else {

			ResponseDto response = new ResponseDto();

			response.setMessage(VMessageConstant.MESSAGE);
			response.setBody(list);
			response.setStatus(true);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<ResponseDto> eraseByCompanyId(HttpServletRequest request,  long company_id) {
		boolean check = company_repos.deleteCompanyById(company_id);
//		try {
//			if (request.getSession().getAttribute("userName") == null) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage("Please login!");
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.DEFAULT);
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
		try {
			if (company_id <= 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.ID_MISSING);
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else if (check == true) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.DELETE + company_id);
				response.setBody(null);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.Not_Found);
				response.setBody(null);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			ResponseDto response = new ResponseDto();

			response.setMessage(VMessageConstant.DEFAULT);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

	}

	@Override
	public ResponseEntity<ResponseDto> edit(HttpServletRequest request,  long company_id, String company_name, String website,
			long minimum_employees, long maximum_employees, String location, String industry, String ranking,
			int status) {
//		try {
//			if (request.getSession().getAttribute("userName") == null) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage("Please login!");
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.DEFAULT);
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
		try {
			if (company_id <= 0) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.ID_MISSING);
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				List<Company> list = company_repos.findByCompanyId(company_id);
				if (list == null || list.isEmpty()) {
					ResponseDto response = new ResponseDto();

					response.setMessage(VMessageConstant.Not_Found);
					response.setBody(null);
					response.setStatus(true);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} else {
					if (!company_name.isBlank() && website.isBlank() && minimum_employees == 0 && maximum_employees == 0
							&& location.isBlank() && industry.isBlank() && ranking.isBlank()
							&& (status != 0 || status != 1)) {
						boolean check = company_repos.editById(company_id, company_name, website, minimum_employees,
								maximum_employees, location, industry, ranking, status);

						if (check == true) {
							ResponseDto response = new ResponseDto();

							response.setMessage(VMessageConstant.UPDATE);
							response.setBody("updated id :" + company_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						} else {
							ResponseDto response = new ResponseDto();
							System.out.print(VMessageConstant.Not_Found);
							response.setMessage(VMessageConstant.Not_Found);
							response.setBody("identity :" + company_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						}
					} else if (company_name.isBlank() && !website.isBlank() && minimum_employees == 0
							&& maximum_employees == 0 && location.isBlank() && industry.isBlank() && ranking.isBlank()
							&& (status != 0 || status != 1)) {
						boolean check = company_repos.editById(company_id, company_name, website, minimum_employees,
								maximum_employees, location, industry, ranking, status);
						if (check == true) {
							ResponseDto response = new ResponseDto();

							response.setMessage(VMessageConstant.UPDATE);
							response.setBody("updated id :" + company_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						} else {
							ResponseDto response = new ResponseDto();
							System.out.print(VMessageConstant.Not_Found);
							response.setMessage(VMessageConstant.Not_Found);
							response.setBody("identity :" + company_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						}

					} else if (company_name.isBlank() && website.isBlank() && minimum_employees != 0
							&& maximum_employees == 0 && location.isBlank() && industry.isBlank() && ranking.isBlank()
							&& (status != 0 || status != 1)) {
						boolean check = company_repos.editById(company_id, company_name, website, minimum_employees,
								maximum_employees, location, industry, ranking, status);
						if (check == true) {
							ResponseDto response = new ResponseDto();

							response.setMessage(VMessageConstant.UPDATE);
							response.setBody("updated id :" + company_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						} else {
							ResponseDto response = new ResponseDto();
							System.out.print(VMessageConstant.Not_Found);
							response.setMessage(VMessageConstant.Not_Found);
							response.setBody("identity :" + company_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						}

					} else if (company_name.isBlank() && website.isBlank() && minimum_employees == 0
							&& maximum_employees != 0 && location.isBlank() && industry.isBlank() && ranking.isBlank()
							&& (status != 0 || status != 1)) {
						boolean check = company_repos.editById(company_id, company_name, website, minimum_employees,
								maximum_employees, location, industry, ranking, status);
						if (check == true) {
							ResponseDto response = new ResponseDto();

							response.setMessage(VMessageConstant.UPDATE);
							response.setBody("updated id :" + company_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						} else {
							ResponseDto response = new ResponseDto();
							System.out.print(VMessageConstant.Not_Found);
							response.setMessage(VMessageConstant.Not_Found);
							response.setBody("identity :" + company_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						}

					} else if (company_name.isBlank() && website.isBlank() && minimum_employees == 0
							&& maximum_employees == 0 && !location.isBlank() && industry.isBlank() && ranking.isBlank()
							&& (status != 0 || status != 1)) {
						boolean check = company_repos.editById(company_id, company_name, website, minimum_employees,
								maximum_employees, location, industry, ranking, status);
						if (check == true) {
							ResponseDto response = new ResponseDto();

							response.setMessage(VMessageConstant.UPDATE);
							response.setBody("updated id :" + company_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						} else {
							ResponseDto response = new ResponseDto();
							System.out.print(VMessageConstant.Not_Found);
							response.setMessage(VMessageConstant.Not_Found);
							response.setBody("identity :" + company_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						}

					} else if (company_name.isBlank() && website.isBlank() && minimum_employees == 0
							&& maximum_employees == 0 && location.isBlank() && !industry.isBlank() && ranking.isBlank()
							&& (status != 0 || status != 1)) {
						boolean check = company_repos.editById(company_id, company_name, website, minimum_employees,
								maximum_employees, location, industry, ranking, status);
						if (check == true) {
							ResponseDto response = new ResponseDto();

							response.setMessage(VMessageConstant.UPDATE);
							response.setBody("updated id :" + company_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						} else {
							ResponseDto response = new ResponseDto();
							System.out.print(VMessageConstant.Not_Found);
							response.setMessage(VMessageConstant.Not_Found);
							response.setBody("identity :" + company_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						}
					} else if (company_name.isBlank() && website.isBlank() && minimum_employees == 0
							&& maximum_employees == 0 && location.isBlank() && industry.isBlank() && !ranking.isBlank()
							&& (status != 0 || status != 1)) {
						boolean check = company_repos.editById(company_id, company_name, website, minimum_employees,
								maximum_employees, location, industry, ranking, status);
						if (check == true) {
							ResponseDto response = new ResponseDto();

							response.setMessage(VMessageConstant.UPDATE);
							response.setBody("updated id :" + company_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						} else {
							ResponseDto response = new ResponseDto();
							System.out.print(VMessageConstant.Not_Found);
							response.setMessage(VMessageConstant.Not_Found);
							response.setBody("identity :" + company_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						}
					} else if (company_name.isBlank() && website.isBlank() && minimum_employees == 0
							&& maximum_employees == 0 && location.isBlank() && industry.isBlank() && ranking.isBlank()
							&& (status == 0 || status == 1)) {
						boolean check = company_repos.editById(company_id, company_name, website, minimum_employees,
								maximum_employees, location, industry, ranking, status);
						if (check == true) {
							ResponseDto response = new ResponseDto();

							response.setMessage(VMessageConstant.UPDATE);
							response.setBody("updated id :" + company_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						} else {
							ResponseDto response = new ResponseDto();
							System.out.print(VMessageConstant.Not_Found);
							response.setMessage(VMessageConstant.Not_Found);
							response.setBody("identity :" + company_id);
							response.setStatus(true);
							return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
						}
					} else {
						ResponseDto response = new ResponseDto();

						response.setMessage(VMessageConstant.Not_Found);
						response.setBody("Invalid input");
						response.setStatus(false);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					}
				}
			}

		} catch (Exception e) {
			ResponseDto response = new ResponseDto();

			response.setMessage(VMessageConstant.DEFAULT);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<ResponseDto> addrecord(HttpServletRequest request, String company_name, String website, long maximum_employees,
			long minimum_employees, String location, String industry, String ranking) {
//		try {
//			if (request.getSession().getAttribute("userName") == null) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage("Please login!");
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.DEFAULT);
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
		
		try {

			Company comp = new Company();
			Date date = new Date();
			comp.setCompany_creation_date(date);
			comp.setStatus(1);
			comp.setCompany_name(company_name);
			comp.setIndustry(industry);
			comp.setLocation(location);
			comp.setCreated_by((String) request.getSession().getAttribute("userName"));
			comp.setMaximum_employees(maximum_employees);
			comp.setMinimum_employees(minimum_employees);
			comp.setRanking(ranking);
			comp.setWebsite(website);
			if (company_repos.getCompanyByWebsite(website) != null) {
				ResponseDto response = new ResponseDto();

				response.setMessage("This company is already exist");
				response.setBody(null);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
			boolean check = company_repos.newCompany(comp);
			if (check == true) {
				System.out.println("New Company Saved with id " + comp.getCompany_id());
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.CREATED);
				response.setBody("Generated id : " + comp.getCompany_id());
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

			} else {
				System.out.println("This Company is not saved due to exception ");
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.Error);
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			ResponseDto response = new ResponseDto();
			response.setStatus(false);
			response.setMessage("Company is not saved, due to exception");
			response.setBody(null);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

		}
	}

	@Override
	public ResponseEntity<ResponseDto> addCompanyFile(HttpServletRequest request,  MultipartFile file) {
		boolean check = false;
//		try {
//			if (request.getSession().getAttribute("userName") == null) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage("Please login!");
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.DEFAULT);
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
		try {
			if (file.getSize() == 0) {
				ResponseDto response = new ResponseDto();
				response.setMessage("Given file is empty !");
				response.setBody(null);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {
				check = FileHelper.checkFileFormat(file);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			ResponseDto response = new ResponseDto();
			response.setMessage("Exception occur in format of file");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

		if (check == false) {
			ResponseDto response = new ResponseDto();
			response.setMessage("This file is not of excel format");
			response.setBody(null);
			response.setStatus(true);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		} else {

			List<Company> list = new ArrayList<Company>();
			try {
				list = FileHelper.getCompExcelFile(request , file.getInputStream());
				System.out.println("HELLO");
			} catch (IOException e1) {
				list = null;
				e1.printStackTrace();
			}

			if (list.size() == 0) {

				ResponseDto response = new ResponseDto();
				response.setStatus(true);
				response.setMessage("file is empty");
				response.setBody(null);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {

				try {
					List<Company> locallist = new ArrayList<Company>();

					for (int i = 0; i < list.size(); i++) {
						List<Company> list1 = company_repos.fetchCompanyByWebsite(list.get(i).getWebsite());
						if (list1 == null || list1.isEmpty()) {
							check = company_repos.newCompany(list.get(i));
							System.out.println("New Comapany Saved with name  " + list.get(i).getCompany_name());
							locallist.add(list.get(i));
						} else {
							System.out.println(
									"This Company is already exist with name  " + list.get(i).getCompany_name());
							continue;
						}
					}
					if (locallist.size() == 0) {
						ResponseDto response = new ResponseDto();
						response.setStatus(true);
						response.setMessage("This file is already injected, so cannot be inject again");
						response.setBody(null);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					} else {
						ResponseDto response = new ResponseDto();
						response.setStatus(true);
						response.setMessage("All Company is saved");
						response.setBody(locallist);
						return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
					}

				} catch (InternalServerError ie) {
					ie.printStackTrace();
					ResponseDto response = new ResponseDto();
					response.setStatus(false);
					response.setMessage("file is not uploaded, due to internal server error");
					response.setBody(null);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				} catch (Exception e) {
					e.printStackTrace();

					ResponseDto response = new ResponseDto();
					response.setStatus(false);
					response.setMessage("file is not uploaded, due to exception");
					response.setBody(null);
					return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
				}
			}
		}
	}

	public ResponseEntity<ResponseDto> getCompany(HttpServletRequest request,  String company_name, String website) {
//		try {
//			if (request.getSession().getAttribute("userName") == null) {
//				ResponseDto response = new ResponseDto();
//
//				response.setMessage("Please login!");
//				response.setBody(null);
//				response.setStatus(true);
//				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage(VMessageConstant.DEFAULT);
//			response.setBody(null);
//			response.setStatus(false);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		}
		if (!company_name.isBlank() && website.isBlank()) {
			List<Company> list = company_repos.findByCompanyName(company_name);
			if (list.isEmpty() || list == null) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.Not_Found);
				response.setBody(null);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {

				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.MESSAGE);
				response.setBody(list);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} else if (company_name.isBlank() && !website.isBlank()) {
			List<Company> list = company_repos.fetchCompanyByWebsite(website);
			if (list.isEmpty() || list == null) {
				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.Not_Found);
				response.setBody(null);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			} else {

				ResponseDto response = new ResponseDto();

				response.setMessage(VMessageConstant.MESSAGE);
				response.setBody(list);
				response.setStatus(true);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} else {
			ResponseDto response = new ResponseDto();

			response.setMessage("Please enter either company name or website");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}

	}

	public ResponseEntity<ResponseDto> fetchAllCompany(HttpSession session, String token) {
		// TODO Auto-generated method stub
		msRequestFilter.validateToken(session, token);
		ResponseDto response = new ResponseDto();

		try {
			if (session.getAttribute("isSession") != "true") {
				System.out.println(session.getAttribute("isSession"));
				response.setMessage("Please login!");
				response.setBody(null);
				response.setStatus(false);
				return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			}
		} catch (Exception e) {

			response.setMessage(VMessageConstant.DEFAULT);
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}	
		
		List<Company> list =company_repos.getAllCompany();
		if(list.isEmpty() || list==null) {
			response.setMessage("Searched results !");
			response.setBody(null);
			response.setStatus(false);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
			
		}else {
			response.setMessage("Searched results !");
			response.setBody(list);
			response.setStatus(true);
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
		}
	
	}



}
