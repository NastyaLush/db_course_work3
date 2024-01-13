package com.runtik.dbcoursework.repository;

import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.dto.TaskDTO;
import com.runtik.dbcoursework.enums.Status;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SortField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class TaskRepository {

    @Autowired
    private DSLContext dslContext;

    public void create(TaskDTO task) {
        dslContext.insertInto(Tables.TASK, Tables.TASK.TASK_PERSON_TO, Tables.TASK.TASK_PERSON_FROM,
                              Tables.TASK.TASK_TEXT, Tables.TASK.TASK_STATUS,
                              Tables.TASK.TASK_TITLE)
                  .values(task.getTaskPersonTo(), task.getTaskPersonFrom(), task.getTaskText(), task.getTaskStatus(),
                          task.getTaskTitle())
                  .execute();

    }

    public void updateStatus(Integer taskId, Status newStatus) {
        try (var table = dslContext.update(Tables.TASK)
                                   .set(Tables.TASK.TASK_STATUS, newStatus)) {
            table
                    .where(Tables.TASK.TASK_ID.eq(taskId))
                    .execute();
        }
    }

    public List<TaskDTO> getTask(int limit, int offset, List<SortField<?>> sortFields, Condition condition) {
        try (var table = dslContext.selectFrom(Tables.TASK)) {
            return table.where(condition).orderBy(sortFields).limit(limit).offset(offset)
                    .fetchInto(TaskDTO.class);
        }
    }

}
