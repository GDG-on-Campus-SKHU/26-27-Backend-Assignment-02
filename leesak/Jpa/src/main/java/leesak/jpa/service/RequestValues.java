package leesak.jpa.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

final class RequestValues {
    private RequestValues() {
    }

    static String text(String value, String field, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, field + "은(는) 필수입니다.");
        }
        String trimmed = value.trim();
        if (trimmed.length() > maxLength) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    field + "은(는) " + maxLength + "자 이하여야 합니다.");
        }
        return trimmed;
    }

    static Long genreId(Long value) {
        if (value == null || value <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "genreId는 양의 정수여야 합니다.");
        }
        return value;
    }
}
