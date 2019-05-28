package com.excilys.cdb.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.Computer;

@Repository
public interface JpaComputerDao extends PagingAndSortingRepository<Computer, Integer>{
	
	Page<Computer> findAllByNameContains(String search, Pageable pageable);
	Computer findById(int id);

	int countByNameContains(String keyWord);
	
	
}
