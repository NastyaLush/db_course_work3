package com.runtik.dbcoursework.service;

import com.runtik.dbcoursework.Page;
import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.Util;
import com.runtik.dbcoursework.dto.TaskDTO;
import com.runtik.dbcoursework.dto.TaskCreateDTO;
import com.runtik.dbcoursework.enums.Status;
import com.runtik.dbcoursework.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    public void create(TaskCreateDTO task){
        taskRepository.create(task);
    }
    public void updateStatus(Integer taskId, Status newStatus){
        taskRepository.updateStatus(taskId, newStatus);
    }
    public Page<List<TaskDTO>> getTasks(Pageable pageable, String[]filter) {
        return taskRepository.getTask(pageable, Util.getSortedFields(pageable.getSort(), Tables.TASK), Util.getFilterFields(filter, Tables.TASK));
    }

    public TaskDTO getTaskById(int id){
        return taskRepository.getTaskById(id);
    }

}
