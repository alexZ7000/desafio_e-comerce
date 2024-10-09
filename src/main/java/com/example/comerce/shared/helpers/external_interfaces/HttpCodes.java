package com.example.comerce.shared.helpers.external_interfaces;

import com.example.comerce.shared.helpers.external_interfaces.HttpModels.HttpResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class HttpCodes {

    public static class OK extends HttpResponse {
        public OK(final Object body) {
            super(HttpStatus.OK, castToMap(body), null);
        }
    }

    public static class Created extends HttpResponse {
        public Created(final Object body) {
            super(HttpStatus.CREATED, castToMap(body), null);
        }
    }

    public static class NoContent extends HttpResponse {
        public NoContent() {
            super(HttpStatus.NO_CONTENT, null, null);
        }
    }

    public static class BadRequest extends HttpResponse {
        public BadRequest(final Object body) {
            super(HttpStatus.BAD_REQUEST, castToMap(body), null);
        }
    }

    public static class InternalServerError extends HttpResponse {
        public InternalServerError(final Object body) {
            super(HttpStatus.INTERNAL_SERVER_ERROR, castToMap(body), null);
        }
    }

    public static class NotFound extends HttpResponse {
        public NotFound(final Object body) {
            super(HttpStatus.NOT_FOUND, castToMap(body), null);
        }
    }

    public static class Conflict extends HttpResponse {
        public Conflict(final Object body) {
            super(HttpStatus.CONFLICT, castToMap(body), null);
        }
    }

    @Setter
    @Getter
    public static class RedirectResponse extends HttpResponse {
        private final Map<String, Object> location;

        public RedirectResponse(final Map<String, Object> location) {
            super(HttpStatus.PERMANENT_REDIRECT, null, null);
            this.location = location;
        }

    }

    public static class Forbidden extends HttpResponse {
        public Forbidden(final Object body) {
            super(HttpStatus.FORBIDDEN, castToMap(body), null);
        }
    }

    protected static Map<String, Object> castToMap(final Object body) {
        if (body instanceof Map<?, ?> rawMap) {
            final Map<String, Object> resultMap = new HashMap<>();
            for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
                if (entry.getKey() instanceof String) {
                    resultMap.put((String) entry.getKey(), entry);
                } else {
                    throw new IllegalArgumentException("Invalid map key: " + entry.getKey());
                }
            }
            return resultMap;
        }
        return new HashMap<>();
    }
}
