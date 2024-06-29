package com.store.controllers

import org.openapitools.api.ProductsApi
import org.openapitools.model.Product
import org.openapitools.model.ProductDetails
import org.openapitools.model.ProductId
import org.openapitools.model.ProductType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
open class Products : ProductsApi {
    override fun productsGet(type: ProductType?): ResponseEntity<List<Product>> {
        return ResponseEntity.ok(listOf(Product(1, "XYZ Phone", ProductType.gadget, 2)))
    }

    override fun productsPost(productDetails: ProductDetails?): ResponseEntity<ProductId> {
        // Return a 201
        return ResponseEntity.status(201).body(ProductId(1))
    }
}