package com.challenge.api.exception;

import java.time.Instant;

// Standard error body returned for every failed request, giving API consumers a consistent shape.
public record ErrorResponse(Instant timestamp, int status, String error, String message, String path) {}
