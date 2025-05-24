package com.ecomproject;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepo repo;

	public List<Product> products() {
		return repo.findAll();
	}

	public Product product(int id) {
		Product prod=repo.findById(id).get();
		return prod;
	}

	public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageType(imageFile.getContentType());
		product.setImageData(imageFile.getBytes());
		return repo.save(product);
	}

	public Product updateProduct(int id, MultipartFile imageFile, Product product) throws IOException {
		product.setId(id);
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageType(imageFile.getContentType());
		product.setImageData(imageFile.getBytes());
		return repo.save(product);
	}

	public void deleteProduct(int id) {
		repo.deleteById(id);
		
	}

	public List<Product> searchProducts(String keyword) {
		return repo.searchProduct(keyword);
	}
}
