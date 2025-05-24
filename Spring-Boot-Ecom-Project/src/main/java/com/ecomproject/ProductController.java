package com.ecomproject;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService service;
	
	@GetMapping("/products")
	public List<Product> getProducts(){
		return service.products();
	}
	
	@GetMapping("/product/{id}")
	public Product getProduct(@PathVariable int id) {
		return service.product(id);
	}
	
	@PostMapping("/product")
	public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
		Product prod=null;
		try {
			 prod = service.addProduct(product,imageFile);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		if(prod!=null) {
			return new ResponseEntity<>(prod,HttpStatus.OK);
		}
		else
			return new ResponseEntity<>("Product not found",HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/product/{id}/image")
	public ResponseEntity<byte[]> getImage(@PathVariable int id){
		Product prod=service.product(id);
		byte[] imageFile=prod.getImageData();
		return ResponseEntity.ok().contentType(MediaType.valueOf(prod.getImageType())).body(imageFile);
	}
	
	@PutMapping("/product/{id}")
	public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart MultipartFile imageFile, @RequestPart Product product){
		Product prod=null;
		try {
			prod = service.updateProduct(id,imageFile,product);
		} catch (IOException e) {
			return new ResponseEntity<>("Product not found",HttpStatus.BAD_REQUEST);
		}
		if(prod!=null) {
			return new ResponseEntity<>("Updated",HttpStatus.CREATED);
		}
		else
			return new ResponseEntity<>("Product not found",HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/product/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable int id){
		Product prod=service.product(id);
		if(prod!=null) {
			service.deleteProduct(id);
			return new ResponseEntity<>("Deleted",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Product not found",HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/products/search")
	public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
		List<Product> product=service.searchProducts(keyword);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
}
