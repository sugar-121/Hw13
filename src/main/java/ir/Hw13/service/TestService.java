package ir.Hw13.service;

import ir.Hw13.dto.TestDto;
import ir.Hw13.dto.mapper.TestMapper;
import ir.Hw13.repository.TestRepository;

public class TestService {

    private TestRepository testRepository;
    private TestMapper testMapper;

    public TestService(TestRepository testRepository, TestMapper testMapper) {
        this.testRepository = testRepository;
        this.testMapper = testMapper;
    }

    public void AddTest(TestDto dto) {
        testRepository.AddTest(testMapper.toEntity(dto));

    }

}
