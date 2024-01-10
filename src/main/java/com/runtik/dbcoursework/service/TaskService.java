package com.runtik.dbcoursework.service;

import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.dto.TaskDTO;
import com.runtik.dbcoursework.enums.Status;
import com.runtik.dbcoursework.repository.TaskRepository;
import com.runtik.dbcoursework.tables.pojos.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    public void create(TaskDTO task){
        taskRepository.create(task);
    }
    public void updateStatus(Integer taskId, Status newStatus){
        taskRepository.updateStatus(taskId, newStatus);
    }
    public List<TaskDTO> get() {
        return taskRepository.get();
    }

    public List<TaskDTO> getMy(Integer id) {
        return taskRepository.getMy(id);
    }
}
