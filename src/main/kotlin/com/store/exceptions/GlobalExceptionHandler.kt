package com.store.exceptions

import org.openapitools.model.ErrorResponseBody
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.time.OffsetDateTime

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleTypeMismatch(ex: MethodArgumentTypeMismatchException, request: WebRequest): ResponseEntity<ErrorResponseBody> {
        val error = ErrorResponseBody(
            timestamp = OffsetDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            error = "Invalid type: ${ex.value} for parameter ${ex.name}. Expected type: ${ex.requiredType?.simpleName}",
            path = request.getDescription(false).substring(4)
        )
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException, request: WebRequest): ResponseEntity<ErrorResponseBody> {
        val errorMessages = ex.bindingResult.allErrors.joinToString { error ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.defaultMessage
            "$fieldName: $errorMessage"
        }
        val error = ErrorResponseBody(
            timestamp = OffsetDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            error = errorMessages,
            path = request.getDescription(false).substring(4)
        )
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }
}
