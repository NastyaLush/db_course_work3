package com.runtik.dbcoursework.service;

import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.Util;
import com.runtik.dbcoursework.dto.TestResultDTO;
import com.runtik.dbcoursework.dto.TestResultSelectDTO;
import com.runtik.dbcoursework.repository.TestResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TestResultService {

    @Autowired
    private TestResultRepository testResultRepository;

    public List<TestResultSelectDTO> getTestResult(Pageable pageable, String[]filter) {
        return testResultRepository.getTestResults(pageable, Util.getSortedFields(pageable.getSort(), Tables.TEST_RESULT), Util.getFilterFields(filter, Tables.TEST_RESULT));
    }

    public void create(TestResultDTO testResult) {
        testResultRepository.create(testResult);
    }
}
