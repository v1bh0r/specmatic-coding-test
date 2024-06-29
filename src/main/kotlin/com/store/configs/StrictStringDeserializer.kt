package com.store.configs

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import java.io.IOException

class StrictStringDeserializer : JsonDeserializer<String>() {
    @Throws(IOException::class)
    override fun deserialize(parser: JsonParser, ctxt: DeserializationContext): String {
        val node = parser.codec.readTree<com.fasterxml.jackson.databind.JsonNode>(parser)

        if (!node.isTextual) {
            throw InvalidFormatException(parser, "Expected a string, but got: ${node.nodeType}", node, String::class.java)
        }

        return node.asText()
    }
}
