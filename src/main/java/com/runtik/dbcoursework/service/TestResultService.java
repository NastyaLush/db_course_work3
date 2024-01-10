package com.runtik.dbcoursework.service;

import com.runtik.dbcoursework.dto.TestResultDTO;
import com.runtik.dbcoursework.repository.TestResultRepository;
import com.runtik.dbcoursework.tables.pojos.TestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TestResultService {

    @Autowired
    private TestResultRepository testResultRepository;

    public List<TestResultDTO> get(Integer id) {
        return testResultRepository.get(id);
    }

    public void create(TestResultDTO testResult) {
        testResultRepository.create(testResult);
    }
}
