package br.com.udemy.tasks.utils;

import br.com.udemy.tasks.controller.dto.TaskDTO;
import br.com.udemy.tasks.controller.dto.TaskInsertDTO;
import br.com.udemy.tasks.model.Task;
import br.com.udemy.tasks.model.TaskState;

public class TestUtils {

    public static Task buildValidTask() {
        return Task.builder()
                .withId("123")
                .withTitle("title")
                .withDescription("description")
                .withPriority(1)
                .withState(TaskState.INSERT)
                .build();
    }


    public static Task buildValidTaskWithAttributes(String id, String title, String description, int priority, TaskState state) {
        return Task.builder()
                .withId(id)
                .withTitle(title)
                .withDescription(description)
                .withPriority(priority)
                .withState(state)
                .build();
    }

    public static TaskDTO buildValidTaskDTO() {
        return new TaskDTO("1","teste", "teste", 1, TaskState.INSERT);
    }


    public static TaskInsertDTO buildValidTaskInsertDTO() {
        return new TaskInsertDTO("teste", "teste", 1);
    }
}
