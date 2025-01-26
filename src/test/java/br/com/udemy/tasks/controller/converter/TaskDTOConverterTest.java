package br.com.udemy.tasks.controller.converter;

import br.com.udemy.tasks.controller.dto.TaskDTO;
import br.com.udemy.tasks.model.Task;
import br.com.udemy.tasks.model.TaskState;
import br.com.udemy.tasks.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TaskDTOConverterTest {

    @InjectMocks
    private TaskDTOConverter taskDTOConverter;

    @Test
    void converter_mustReturnTaskDTO_whenInputTask(){
        //arrange
        Task task = TestUtils.buildValidTask();

        //act
        TaskDTO taskDTO = taskDTOConverter.convert(task);

        //assert
        Assertions.assertEquals(taskDTO.getTitle(), task.getTitle());
        Assertions.assertEquals(taskDTO.getDescription(), task.getDescription());
        Assertions.assertEquals(taskDTO.getPriority(), task.getPriority());
        Assertions.assertEquals(taskDTO.getState(), task.getState());
    }

    @Test
    void converter_mustReturnTask_whenInputAttributes(){
        //arrange
        String id = "123";
        String title = "title";
        String description = "description";
        int priority = 1;
        TaskState state = TaskState.INSERT;

        //act
        Task task = taskDTOConverter.convert(id, title, description, priority, state);

        //assert
        Assertions.assertEquals(id, task.getId());
        Assertions.assertEquals(title, task.getTitle());
        Assertions.assertEquals(description, task.getDescription());
        Assertions.assertEquals(priority, task.getPriority());
        Assertions.assertEquals(state, task.getState());
    }

    @Test
    void converter_mustReturnTask_whenInputTaskDTO(){
        //arrange
        TaskDTO taskDTO = TestUtils.buildValidTaskDTO();

        //act
        Task task = taskDTOConverter.convert(taskDTO);

        //assert
        Assertions.assertEquals(taskDTO.getTitle(), task.getTitle());
        Assertions.assertEquals(taskDTO.getDescription(), task.getDescription());
        Assertions.assertEquals(taskDTO.getPriority(), task.getPriority());
        Assertions.assertEquals(taskDTO.getState(), task.getState());
    }


}