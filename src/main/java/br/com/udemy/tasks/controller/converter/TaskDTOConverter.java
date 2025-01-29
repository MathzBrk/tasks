package br.com.udemy.tasks.controller.converter;

import br.com.udemy.tasks.controller.dto.TaskDTO;
import br.com.udemy.tasks.model.Task;
import br.com.udemy.tasks.model.TaskState;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TaskDTOConverter {

    public TaskDTO convert( Task task){
        return Optional.ofNullable(task)
                .map(source -> {
                    TaskDTO taskDTO = new TaskDTO();
                    taskDTO.setId(source.getId());
                    taskDTO.setTitle(source.getTitle());
                    taskDTO.setDescription(source.getDescription());
                    taskDTO.setState(source.getState());
                    taskDTO.setPriority(source.getPriority());
                    taskDTO.setAddress(source.getAddress());
                    return taskDTO;
                })
                .orElse(null);
    }

    public Task convert( String id, String title, String description, int priority, TaskState taskState ){
        return Task.builder()
                .withId(id)
                .withTitle(title)
                .withDescription(description)
                .withPriority(priority)
                .withState(taskState)
                .build();
    }

    public Task convert(TaskDTO taskDTO) {
        if (taskDTO == null) {
            return null;
        }
        return Task.builder()
                .withTitle(taskDTO.getTitle())
                .withDescription(taskDTO.getDescription())
                .withPriority(taskDTO.getPriority())
                .withState(taskDTO.getState())
                .build();
    }


}
