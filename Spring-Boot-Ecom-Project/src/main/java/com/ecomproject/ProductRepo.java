package com.ecomproject;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepo extends JpaRepository<Product, Integer> {

	@Query("SELECT p from Product p WHERE "+
	"LOWER(p.name) Like LOWER(CONCAT('%',:Keyword,'%')) OR "+
			"LOWER(p.description) LIKE LOWER(CONCAT('%',:Keyword,'%'))")  //JPQL Query
	public List<Product> searchProduct(String Keyword);
}
