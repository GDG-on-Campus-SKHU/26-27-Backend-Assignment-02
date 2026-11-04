package com.gdg.jpa.component;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class WaitingNumberGenerator {
    private int currentNumber = 0;
    private LocalDate lastDate = LocalDate.now();

    private final ReentrantLock lock = new ReentrantLock(true);

    public int getNextWaitingNumber() {
        try {
            if (lock.tryLock(3, TimeUnit.SECONDS)) {
                try {
                    LocalDate today = LocalDate.now();

                    if (!today.equals(lastDate)) {
                        currentNumber = 0;
                        lastDate = today;
                    }

                    return ++currentNumber;
                } finally {
                    lock.unlock();
                }
            } else {
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "주문 밀림으로 인해 대기번호 발급에 실패했습니다. 다시 시도해주세요.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "대기번호 처리 중 스레드 중단이 발생했습니다.");
        }
    }
}
