package org.srn.web.Recruiter.service_impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.srn.web.Recruiter.component.ExcelHelper;
import org.srn.web.Recruiter.dao.BenchSetsRepository;
import org.srn.web.Recruiter.entity.Bench;

@Service
public class ExcelService {
	
	@Autowired
	private BenchSetsRepository bench_repos;

	
	
	  public void saveRuleSets(MultipartFile file) {
	    try {
	     
	      List<Bench> benchSets = ExcelHelper.excelToBenchs(file.getInputStream());
	      bench_repos.saveAll(benchSets);
	      } catch (IOException e) {
	      throw new RuntimeException("fail to store excel data: " + e.getMessage());
	    }
	  }
	  
	
}
