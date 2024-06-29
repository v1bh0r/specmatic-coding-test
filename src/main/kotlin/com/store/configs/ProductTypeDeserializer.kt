package com.store.configs

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.openapitools.model.ProductType
import java.io.IOException

class ProductTypeDeserializer : JsonDeserializer<ProductType>() {
    @Throws(IOException::class)
    override fun deserialize(parser: JsonParser, ctxt: DeserializationContext): ProductType {
        val value = parser.text
        return ProductType.entries.first { it.name == value }
    }
}
