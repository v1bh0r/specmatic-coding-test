package com.store.controllers

import com.store.entities.ProductEntity
import com.store.repositories.ProductRepository
import jakarta.validation.Valid
import org.openapitools.api.ProductsApi
import org.openapitools.model.Product
import org.openapitools.model.ProductDetails
import org.openapitools.model.ProductId
import org.openapitools.model.ProductType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
open class Products(private val productRepository: ProductRepository) : ProductsApi {

    override fun productsGet(type: ProductType?): ResponseEntity<List<Product>> {
        val products = productRepository.findAll().map { it.toProduct() }
        return ResponseEntity.ok(products)
    }

    override fun productsPost(productDetails: ProductDetails?): ResponseEntity<ProductId> {
        val product = Product(
            id = 0, // Hibernate will generate the ID
            name = productDetails!!.name,
            type = productDetails.type,
            inventory = productDetails.inventory
        )
        val productEntity = ProductEntity(product)
        val savedProduct = productRepository.save(productEntity)
        return ResponseEntity.status(201).body(ProductId(savedProduct.id!!))
    }
}