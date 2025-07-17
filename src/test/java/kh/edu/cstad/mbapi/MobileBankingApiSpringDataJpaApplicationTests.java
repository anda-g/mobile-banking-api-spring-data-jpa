package kh.edu.cstad.mbapi;

import kh.edu.cstad.mbapi.repository.CustomerSegmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MobileBankingApiSpringDataJpaApplicationTests {

    @Autowired
    private CustomerSegmentRepository segmentRepository;

    @Test
    void testFindCustomerSegment() {
        segmentRepository.findAll().forEach(System.out::println);
    }

}
