package ir.Hw13.service;

import ir.Hw13.dto.TestDto;
import ir.Hw13.dto.mapper.TestMapper;
import ir.Hw13.entity.DescriptiveQuestion;
import ir.Hw13.entity.MultipleChoiceQuestion;
import ir.Hw13.entity.TestQuestion;
import ir.Hw13.entity.Tests;
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

    public Tests loadTestById(long id) {
        return testRepository.loadTestById(id);
    }

    public void showTestQuestions(Tests test) {
        for (TestQuestion question : test.getTestQuestions()) {
            if (question.getQuestions() instanceof MultipleChoiceQuestion mcq) {
                mcq.buildQuestionText();
            } else if (question.getQuestions() instanceof DescriptiveQuestion dq) {
                dq.buildQuestionText();
            }
        }
    }
}
