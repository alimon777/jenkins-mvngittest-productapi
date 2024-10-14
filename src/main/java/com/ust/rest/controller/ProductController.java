package com.ust.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ust.rest.model.Product;


@RestController
@RequestMapping("/product/api1.0") //root of resource
public class ProductController {
	
	List<Product> productList = new ArrayList<>();
	{
	productList.add(new Product(101,"Nike","Feather Walk",10,15000));
	productList.add(new Product(102,"Adidas","Comfort Walk",14,13000));
	productList.add(new Product(103,"Puma","Light Walk",12,12000));
	}
	@GetMapping(value = "/productsinfo",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> displayProducts(){
		if(productList.size()>0)
			return ResponseEntity.ok(productList);
		else
			return ResponseEntity.noContent().build();
	}
		
	@GetMapping(value="/product/{detail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> getByBrand(@PathVariable String detail){
		Optional<Product> optional = productList.stream().filter(product->product.getBrand().equalsIgnoreCase(detail)||(product.getProductId()+"").equalsIgnoreCase(detail)).findFirst();
		return ResponseEntity.status(HttpStatus.OK).body(optional.get());
	}
	
	@PostMapping(value="/addProduct",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> addProduct(@RequestBody Product product){
		boolean flag=false;
		if(product!=null) {
			productList.add(product);
			flag=true;
		}return flag?ResponseEntity.status(201).body(product):ResponseEntity.status(404).body(null);
	}
	
	@PutMapping(value="/modify")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product){
		for(int i=0;i<productList.size();i++) {
			if(productList.get(i).getProductId()==product.getProductId()) {
				productList.set(i, product);
				return ResponseEntity.accepted().body(null);
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping(value="/delete/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable long id){
		for(int i=0;i<productList.size();i++) {
			if(productList.get(i).getProductId()==id) {
				productList.remove(i);
				return ResponseEntity.ok().body(null);
			}
		}
		return ResponseEntity.notFound().build();
	}

}
