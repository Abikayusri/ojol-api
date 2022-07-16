package abika.sinau.ojolapi.exception

import abika.sinau.ojolapi.utils.BaseResponse
import abika.sinau.ojolapi.utils.Empty
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class OjolExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [OjolException::class])
    fun handleThrowable(throwable: OjolException): ResponseEntity<BaseResponse<Empty>> {
        return ResponseEntity(BaseResponse.failure(throwable.message
                ?: "Failure"), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}