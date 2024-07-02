package com.store.exceptions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

class GlobalExceptionHandlerTest {

    private val handler = GlobalExceptionHandler()

    @Test
    fun `handleTypeMismatch returns BadRequest status and error message`() {
        val ex = mock(MethodArgumentTypeMismatchException::class.java)
        val request = mock(WebRequest::class.java)
        `when`(ex.name).thenReturn("param")
        `when`(ex.value).thenReturn("value")
        `when`(ex.requiredType).thenReturn(String::class.java)
        `when`(request.getDescription(false)).thenReturn("/api/path")

        val response = handler.handleTypeMismatch(ex, request)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals("Invalid type: value for parameter param. Expected type: String", response.body?.error)
    }

    @Test
    fun `handleValidationExceptions returns BadRequest status and error message`() {
        val ex = mock(MethodArgumentNotValidException::class.java)
        val request = mock(WebRequest::class.java)
        val bindingResult = mock(BindingResult::class.java)
        val fieldError = FieldError("object", "field", "error message")
        `when`(ex.bindingResult).thenReturn(bindingResult)
        `when`(bindingResult.allErrors).thenReturn(listOf(fieldError))
        `when`(request.getDescription(false)).thenReturn("/api/path")

        val response = handler.handleValidationExceptions(ex, request)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals("field: error message", response.body?.error)
    }

    @Test
    fun `handleTypeMismatch handles null value`() {
        val ex = mock(MethodArgumentTypeMismatchException::class.java)
        val request = mock(WebRequest::class.java)
        `when`(ex.name).thenReturn("param")
        `when`(ex.value).thenReturn(null)
        `when`(ex.requiredType).thenReturn(String::class.java)
        `when`(request.getDescription(false)).thenReturn("/api/path")

        val response = handler.handleTypeMismatch(ex, request)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals("Invalid type: null for parameter param. Expected type: String", response.body?.error)
    }

    @Test
    fun `handleTypeMismatch handles null requiredType`() {
        val ex = mock(MethodArgumentTypeMismatchException::class.java)
        val request = mock(WebRequest::class.java)
        `when`(ex.name).thenReturn("param")
        `when`(ex.value).thenReturn("value")
        `when`(ex.requiredType).thenReturn(null)
        `when`(request.getDescription(false)).thenReturn("/api/path")

        val response = handler.handleTypeMismatch(ex, request)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals("Invalid type: value for parameter param. Expected type: null", response.body?.error)
    }
}