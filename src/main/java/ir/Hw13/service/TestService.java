package ir.Hw13.service;

import ir.Hw13.dto.TestDto;
import ir.Hw13.dto.mapper.TestMapper;
import ir.Hw13.entity.*;
import ir.Hw13.repository.TestRepository;
import ir.Hw13.service.exceptions.TimeIsUp;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public String showTestQuestions(StudentTakeTestAttempt attempt, Tests test) {
        StringBuilder testQuestions = new StringBuilder();
        testQuestions.append("Attempt Id: ")
                .append(attempt.getId())
                .append("\n");
        for (TestQuestion question : test.getTestQuestions()) {
            testQuestions.append(question.getQuestions().buildQuestionText());
            testQuestions.append("\n");
        }
        return testQuestions.toString();
    }

    public Questions loadQuestionById(long questionId) {
        return testRepository.loadQuestionById(questionId);
    }

    public void insertAnswerToTest(StudentTakeTestAttempt attempt,
                                   long questionId,
                                   int correctChoice,
                                   String answerText) {
        Questions question = loadQuestionById(questionId);

        Duration remainingTime = getRemainingTime(attempt);
        if (remainingTime.isZero()) {
            attempt.setStatus(TakingStatus.FINISHED);
            finishTest(attempt);
            throw new TimeIsUp();
        } else {
            StudentAnswer answer = new StudentAnswer();
            answer.setAttempt(attempt);
            answer.setQuestion(question);
            if (correctChoice != -1) {
                answer.setAnsweredChoice(loadChoiceById(correctChoice));
            }
            if (!Objects.equals(answerText, "")) {
                answer.setAnsweredText(answerText);
            }
            testRepository.insertAnswerToTest(answer);
        }

    }


    public Choice loadChoiceById(long choiceId) {
        return testRepository.loadChoiceById(choiceId);

    }

    public Duration getRemainingTime(StudentTakeTestAttempt attempt) {
        LocalDateTime deadLine = attempt.getStartTime().plusMinutes(attempt.getTest().getDuration());
        Duration remaining = Duration.between(LocalDateTime.now(), deadLine);

        if (remaining.isNegative()) {
            return Duration.ZERO;
        }
        return remaining;
    }

    public void finishTest(StudentTakeTestAttempt attempt) {

        attempt.setStatus(TakingStatus.FINISHED);
        attempt.setEndTime(LocalDateTime.now());

        autoGradeMCQs(attempt);
        testRepository.finishTest(attempt);
    }

    public void autoGradeMCQs(StudentTakeTestAttempt attempt) {
        List<StudentAnswer> studentAnswers = testRepository.loadStudentAnswers(attempt);
        for (StudentAnswer studentAnswer : studentAnswers) {
            if (studentAnswer.getQuestion() instanceof MultipleChoiceQuestion mcq) {
                if (studentAnswer.getAnsweredChoice().equals(mcq.getAnswer())) {
                    studentAnswer.setScore((double)getQuestionScoreInTest(attempt.getTest().getId()
                            , studentAnswer.getQuestion().getId()));
                } else {
                    studentAnswer.setScore((double) 0);
                }
                studentAnswer.setGraded(true);
                testRepository.autoGradeQuestion(studentAnswer);
            }
        }
    }

    public List<StudentTakeTestAttempt> loadFinishedTestAttempts(long testId) {
        List<StudentTakeTestAttempt> attempts = testRepository.loadTestAttempts(testId);
        List<StudentTakeTestAttempt> finishedAttempts = new ArrayList<>();
        for (StudentTakeTestAttempt attempt : attempts) {
            if (attempt.getStatus().equals(TakingStatus.IN_PROGRESS)) {
                if (!getRemainingTime(attempt).isPositive()) {
                    finishTest(attempt);
                }
            }
            if (attempt.getStatus().equals(TakingStatus.FINISHED)) {
                finishedAttempts.add(attempt);
            }
        }
        return finishedAttempts;
    }

    public Long getQuestionScoreInTest(long testId, long questionId) {
        return testRepository.getQuestionScoreInTest(testId, questionId);
    }
}
