package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskController taskController;

    @MockBean
    private DbService dbService;

    @Test
    public void shouldGetEmptyTasksList() throws Exception {
        //Given
        List<TaskDto> tasks = new ArrayList<>();
        when(taskController.getTasks()).thenReturn(tasks);

        //When & Then
        mockMvc.perform(get("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldGetTaskById() throws Exception {
        //Given
        Task task = new Task(112L, "title", "content");
        dbService.saveTask(task);
        TaskDto taskDto = new TaskDto(112L, "title", "content");
        when(taskController.getTask(112L)).thenReturn(taskDto);
        //When & Then
        mockMvc.perform(get("/v1/tasks/" + task.getId()).contentType(MediaType.APPLICATION_JSON)
                .param("taskId", "112"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("id").value("112"))
                .andExpect(jsonPath("title").value("title"))
                .andExpect(jsonPath("content").value("content"));
    }

    @Test
    public void ShouldDeleteTaskById() throws Exception {
        //Given
        Task task = new Task(888L, "title", "content");
        dbService.saveTask(task);
        //When & Then
        mockMvc.perform(delete("/v1/tasks/" + task.getId()).contentType(MediaType.APPLICATION_JSON)
                .param("taskId", "888"))
                .andExpect(status().is(200));
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //Given
        Task task = new Task(888L, "title", "content");
        dbService.saveTask(task);
        TaskDto taskDto = new TaskDto(888L, "updated title", "content");
        Gson gson = new Gson();
        String param = gson.toJson(taskDto);
        when(taskController.updateTask(any())).thenReturn(taskDto);
        //When & Then
        mockMvc.perform(put("/v1/tasks").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(param))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id").value("888"))
                .andExpect(jsonPath("$.title").value("updated title"))
                .andExpect(jsonPath("$.content").value("content"));
    }

    @Test
    public void createTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(333L, "title", "content");
        Gson gson = new Gson();
        String param = gson.toJson(taskDto);
        //When & Then
        mockMvc.perform(post("/v1/tasks").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(param))
                .andExpect(status().is(200));
        verify(taskController).createTask(any(TaskDto.class));
    }
}