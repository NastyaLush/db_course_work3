package com.runtik.dbcoursework.repository;

import com.runtik.dbcoursework.Page;
import com.runtik.dbcoursework.Tables;
import com.runtik.dbcoursework.dto.ReportDTO;
import com.runtik.dbcoursework.dto.TaskCreateDTO;
import com.runtik.dbcoursework.dto.TaskDTO;
import com.runtik.dbcoursework.enums.Status;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SortField;
import org.jooq.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepository {

    @Autowired
    private DSLContext dslContext;

    public void create(TaskCreateDTO task) {
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


    public Page<List<TaskDTO>> getTask(Pageable pageable, List<SortField<?>> sortFields, Condition condition) {
        try (var table = dslContext
                .select(Tables.TASK.TASK_ID,
                        Tables.TASK.TASK_TEXT,
                        Tables.TASK.TASK_STATUS,
                        Tables.TASK.TASK_TITLE,
                        Tables.PERSON.as("pf").PERSON_NAME.as("personNameFrom"),
                        Tables.PERSON.as("pt").PERSON_NAME.as("personNameTo")
                )
                .from(Tables.TASK)
                .join(Tables.PERSON.as("pf"))
                .on(Tables.TASK.TASK_PERSON_FROM.eq(Tables.PERSON.as("pf").PERSON_ID))
                .join(Tables.PERSON.as("pt"))
                .on(Tables.TASK.TASK_PERSON_TO.eq(Tables.PERSON.as("pt").PERSON_ID))
                .where(condition)
                .orderBy(Tables.TASK.TASK_ID.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getPageNumber() * pageable.getPageSize())) {


            List<TaskDTO> tasks = table.fetchInto(TaskDTO.class);
            int totalCount = dslContext.fetchCount(table);

            return new Page<>(tasks, totalCount);
        }
    }

    public TaskDTO getTaskById(int id) {
        try (var table = dslContext.select(Tables.TASK.TASK_ID, Tables.TASK.TASK_TEXT, Tables.TASK.TASK_STATUS, Tables.TASK.TASK_TITLE,
                        Tables.PERSON.as("pf").PERSON_NAME.as("personNameFrom"),
                        Tables.PERSON.as("pt").PERSON_NAME.as("personNameTo"))
                .from(Tables.TASK)) {
            return table
                    .join(Tables.PERSON.as("pf"))
                    .on(Tables.TASK.TASK_PERSON_FROM.eq(Tables.PERSON.as("pf").PERSON_ID))
                    .join(Tables.PERSON.as("pt"))
                    .on(Tables.TASK.TASK_PERSON_TO.eq(Tables.PERSON.as("pt").PERSON_ID))
                    .where(Tables.TASK.TASK_ID.eq(id))
                    .fetchInto(TaskDTO.class).get(0);
        }

    }

    public Optional<List<TaskDTO>> getNewTasks(int userId) {
        try (var q = dslContext.select(
                        Tables.TASK.TASK_ID,
                        Tables.PERSON.as("p1").PERSON_NAME.as("personFrom"),
                        Tables.PERSON.as("p2").PERSON_NAME.as("personTo"),
                        Tables.TASK.TASK_TITLE,
                        Tables.TASK.TASK_TEXT,
                        Tables.TASK.TASK_STATUS
                )
                .from(Tables.TASK)
        ) {
            var res = q.join(Tables.PERSON.as("p1"))
                    .on(Tables.TASK.TASK_PERSON_FROM.eq(Tables.PERSON.as("p1").PERSON_ID))
                    .join(Tables.PERSON.as("p2"))
                    .on(Tables.TASK.TASK_PERSON_TO.eq(Tables.PERSON.as("p2").PERSON_ID))
                    .where(Tables.TASK.TASK_PERSON_TO.eq(userId),
                            Tables.TASK.TASK_STATUS.eq(Status.created)
                                    .or(Tables.TASK.TASK_STATUS.eq(Status.need_check))
                    )
                    .orderBy(Tables.TASK.CREATED_AT.desc())
                    .limit(10)
                    .fetchInto(TaskDTO.class);

            if (res.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(res);
        }
    }
}
