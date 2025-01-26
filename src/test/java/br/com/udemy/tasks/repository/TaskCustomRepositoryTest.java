package br.com.udemy.tasks.repository;

import br.com.udemy.tasks.model.Task;
import br.com.udemy.tasks.utils.TestUtils;
import org.bson.assertions.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
class TaskCustomRepositoryTest {

    @Mock
    private ReactiveMongoOperations mongoOperations;

    @InjectMocks
    private TaskCustomRepository repository;


    @Test
    void customRepository_mustReturnPageWithOneElement_whenSandTask() {
        Task task = TestUtils.buildValidTask();

        Mockito.when(mongoOperations.find(any(), any())).thenReturn(Flux.just(task));
        Mockito.when(mongoOperations.count(any(Query.class), eq(Task.class))).thenReturn(Mono.just(1l));
        Mono<Page<Task>> result = repository.findPaginated(task, 0, 10);

        Assertions.assertNotNull(result);
        assertEquals(1, Objects.requireNonNull(result.block()).getNumberOfElements());
    }

}