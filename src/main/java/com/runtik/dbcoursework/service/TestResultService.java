package com.runtik.dbcoursework.service;

import com.runtik.dbcoursework.dto.TestResultDTO;
import com.runtik.dbcoursework.repository.TestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TestResultService {

    @Autowired
    private TestResultRepository testResultRepository;

    public List<TestResultDTO> getTestResult(Integer id, int limit, int offset) {
        return testResultRepository.getTestResults(id, limit, offset);
    }

    public void create(TestResultDTO testResult) {
        testResultRepository.create(testResult);
    }
}
