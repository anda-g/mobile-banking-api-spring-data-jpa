package kh.edu.cstad.mbapi.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AccountNumberGenerator {

    public String generate() {
        String prefix = "AC";
        String body = String.format("%010d", new Random().nextLong() % 1_000_000_0000L);
        return prefix + body;
    }

}
