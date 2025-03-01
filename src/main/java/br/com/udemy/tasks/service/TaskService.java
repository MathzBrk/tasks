package br.com.udemy.tasks.service;

import br.com.udemy.tasks.controller.dto.TaskDTO;
import br.com.udemy.tasks.exception.TaskNotFoundException;
import br.com.udemy.tasks.model.Address;
import br.com.udemy.tasks.model.Task;
import br.com.udemy.tasks.repository.TaskCustomRepository;
import br.com.udemy.tasks.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;


@Service
public class TaskService {

  private final TaskRepository taskRepository;
  private final TaskCustomRepository taskCustomRepository;
  private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);
  private final AddressService addressService;

    public TaskService( TaskRepository taskRepository, TaskCustomRepository taskCustomRepository, AddressService addressService ) {
        this.taskRepository = taskRepository;
        this.taskCustomRepository = taskCustomRepository;
        this.addressService = addressService;
    }

    public Mono<Task> insert( Task task){
      return Mono.just(task)
              .map(Task::insert)
              .flatMap(this::save)
              .doOnError(e -> LOGGER.error("Error during save task. Title: {}", task.getTitle(), e));
    }

    public Mono<Page<Task>> findPaginated( Task task, Integer pageNumber, Integer pageSize ) {
        return taskCustomRepository.findPaginated( task, pageNumber, pageSize );
    }

    public Mono<Void> deleteById(String id) {
        return Mono.fromRunnable( () -> taskRepository.deleteById( id ) );
    }

    public Mono<Task> update( Task task ) {
        return taskRepository.findById( task.getId())
                .map(task::update)
                .flatMap(taskRepository::save)
                .switchIfEmpty(Mono.error(TaskNotFoundException::new))
                .doOnError(e -> LOGGER.error("Error during update task. ID: {}", task.getId(), e.getMessage()));

    }

    public Mono<Task> doError(){
        return Mono.error(RuntimeException::new);
    }

    public Mono<Task> start(String id, String zipCode){
        return taskRepository.findById( id )
                .zipWhen(it -> addressService.getAddress(zipCode))
                .flatMap(it -> updateAddress(it.getT1(), it.getT2()))
                .map(Task::start)
                .flatMap(taskRepository::save)
                .switchIfEmpty(Mono.error(TaskNotFoundException::new))
                .doOnError(e -> LOGGER.error("Error during start task. ID: {}", id, e));
    }

    private Mono<Task> updateAddress(Task task, Address address) {
        return Mono.just(task)
                .map(it -> task.updateAddress(address));
    }

    private Mono<Task> save(Task task){
      return Mono.just(task)
              .doOnNext(t -> LOGGER.info("Saving task with title {}", task.getTitle()))
              .flatMap(taskRepository::save);
    }

}
