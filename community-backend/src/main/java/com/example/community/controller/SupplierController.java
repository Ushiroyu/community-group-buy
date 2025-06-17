package com.example.community.controller;

import com.example.community.entity.Supplier;
import com.example.community.entity.Product;
import com.example.community.service.ISupplierService;
import com.example.community.service.IProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
    private final ISupplierService supplierService;
    private final IProductService productService;

    public SupplierController(ISupplierService supplierService, IProductService productService) {
        this.supplierService = supplierService;
        this.productService = productService;
    }

    @PostMapping("/register")
    public Supplier register(@RequestBody Supplier supplier) {
        return supplierService.register(supplier);
    }

    @GetMapping("/{sid}/products")
    public List<Product> products(@PathVariable Long sid) {
        return productService.listAll().stream()
                .filter(p -> p.getSupplierId().equals(sid))
                .toList();
    }

    @PostMapping("/{sid}/products")
    public Product add(@PathVariable Long sid, @RequestBody Product product) {
        product.setSupplierId(sid);
        productService.save(product);
        return product;
    }

    @PutMapping("/{sid}/products/{pid}")
    public Product update(@PathVariable Long sid,
                          @PathVariable Long pid,
                          @RequestBody Product product) {
        product.setId(pid);
        product.setSupplierId(sid);
        productService.updateById(product);
        return product;
    }

    @DeleteMapping("/{sid}/products/{pid}")
    public void delete(@PathVariable Long sid, @PathVariable Long pid) {
        productService.removeById(pid);
    }
}