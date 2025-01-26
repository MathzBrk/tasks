package br.com.udemy.tasks.service;

import br.com.udemy.tasks.model.Task;
import br.com.udemy.tasks.repository.TaskCustomRepository;
import br.com.udemy.tasks.repository.TaskRepository;
import br.com.udemy.tasks.utils.TestUtils;
import org.bson.assertions.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskCustomRepository taskCustomRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void service_mustReturn_whenInsertSuccessfully(){
        Task task = TestUtils.buildValidTask();

        Mockito.when(taskRepository.save(any())).thenReturn(Mono.just(task));

        StepVerifier.create(taskService.insert(task))
                .then(() -> Mockito.verify(taskRepository, Mockito.times(1)).save(any()))
                .expectNext(task)
                .expectComplete();
    }

    @Test
    void service_mustReturnVoid_whenDeleteTaskSuccessfully(){
        Mockito.when(taskRepository.deleteById(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(taskService.deleteById("1"))
                .then(() -> Mockito.verify(taskRepository, Mockito.times(1)).deleteById(anyString()))
                .verifyComplete();

    }

    @Test
    void service_mustReturnTaskPage_whenFindPaginated(){
        Task task = TestUtils.buildValidTask();
        Mockito.when(taskCustomRepository.findPaginated(any(), anyInt(), anyInt())).thenReturn(Mono.just(Page.empty()));

        Mono<Page<Task>> result = taskService.findPaginated(task, 0, 10);

        Assertions.assertNotNull(result);
        Mockito.verify(taskCustomRepository, Mockito.times(1)).findPaginated(any(), anyInt(), anyInt());

    }


}