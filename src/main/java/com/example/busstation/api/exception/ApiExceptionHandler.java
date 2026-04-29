package com.example.busstation.api.exception;

import com.example.busstation.api.dto.ApiErrorResponse;
import com.example.busstation.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.example.busstation.api.controller")
public class ApiExceptionHandler {

    // ── 404 Not Found ──────────────────────────────────────────────────────────

    @ExceptionHandler({
            BusNotFoundException.class,
            BusTripNotFoundException.class,
            PassengerNotFoundException.class,
            RouteNotFoundException.class,
            BusStationNotFoundException.class
    })
    public ResponseEntity<ApiErrorResponse> handleNotFound(RuntimeException ex, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI());
    }

    // ── 409 Conflict ───────────────────────────────────────────────────────────

    @ExceptionHandler({
            DuplicateVinException.class,
            DuplicateRegistrationException.class,
            DuplicateRouteException.class,
            DuplicateSeatException.class,
            DuplicateBusStationException.class
    })
    public ResponseEntity<ApiErrorResponse> handleConflict(RuntimeException ex, HttpServletRequest req) {
        return build(HttpStatus.CONFLICT, ex.getMessage(), req.getRequestURI());
    }

    // ── 422 Unprocessable Entity ───────────────────────────────────────────────

    @ExceptionHandler({
            BusCapacityInvalid.class,
            InvalidBusStatusException.class,
            InvalidStationException.class,
            EmptyFieldException.class,
            RouteNotFoundForTripException.class,
            BusNotFoundForTripException.class
    })
    public ResponseEntity<ApiErrorResponse> handleUnprocessable(RuntimeException ex, HttpServletRequest req) {
        return build(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(), req.getRequestURI());
    }

    // ── 400 Bad Request (Bean Validation) ──────────────────────────────────────

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex,
                                                              HttpServletRequest req) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .reduce((a, b) -> a + "; " + b)
                .orElse("Validation failed");
        return build(HttpStatus.BAD_REQUEST, message, req.getRequestURI());
    }

    // ── 500 Fallback ───────────────────────────────────────────────────────────

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex, HttpServletRequest req) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), req.getRequestURI());
    }

    // ── Helper ─────────────────────────────────────────────────────────────────

    private ResponseEntity<ApiErrorResponse> build(HttpStatus status, String message, String path) {
        return ResponseEntity.status(status)
                .body(new ApiErrorResponse(status.value(), status.getReasonPhrase(), message, path));
    }
}

