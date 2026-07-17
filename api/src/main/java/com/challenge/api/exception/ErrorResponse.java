package com.challenge.api.exception;

import java.time.Instant;

/**
 * Standard error response body returned for every failed request, providing a consistent shape for API consumers.
 *
 * @param timestamp when the error occurred
 * @param status HTTP status code
 * @param error HTTP status reason phrase (e.g. "Not Found")
 * @param message human-readable detail describing what went wrong
 * @param path the request URI that produced the error
 */
public record ErrorResponse(Instant timestamp, int status, String error, String message, String path) {}
