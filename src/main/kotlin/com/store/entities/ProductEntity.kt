package com.store.entities

import org.openapitools.model.Product
import org.openapitools.model.ProductType
import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

@Entity
@Table(name = "products")
data class ProductEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(nullable = false)
    var name: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var type: ProductType? = null,

    @Min(1)
    @Max(9999)
    @Column(nullable = false)
    var inventory: Int? = null
) {
    constructor(product: Product) : this(
        id = product.id,
        name = product.name,
        type = product.type,
        inventory = product.inventory
    )

    fun toProduct(): Product {
        return Product(
            id = id!!,
            name = name!!,
            type = type!!,
            inventory = inventory!!
        )
    }
}
