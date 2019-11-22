package com.crud.tasks.domain;

import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.trello.TrelloFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MapperTestSuite {
    @Autowired
    public TrelloMapper trelloMapper;
    @Autowired
    public TrelloFacade trelloFacade;
    @Autowired
    public TaskMapper taskMapper;

    @Test
    public void testMapToBoards() {
        //Given
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("name", "id", new ArrayList<>());
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("name2", "id2", new ArrayList<>());
        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoardDto);
        trelloBoards.add(trelloBoardDto2);
        //When
        List<TrelloBoard> mappedTrelloBoards = trelloMapper.mapToBoards(trelloBoards);
        //Then
        assertEquals("name", mappedTrelloBoards.get(0).getName());
        assertEquals(2, mappedTrelloBoards.size());
    }

    @Test
    public void testMapToBoardsDto() {
        TrelloBoard trelloBoard = new TrelloBoard("name", "id", new ArrayList<>());
        TrelloBoard trelloBoard2 = new TrelloBoard("name2", "id2", new ArrayList<>());
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard);
        trelloBoards.add(trelloBoard2);
        //When
        List<TrelloBoardDto> mappedTrelloBoardsDto = trelloMapper.mapToBoardsDto(trelloBoards);
        //Then
        assertEquals("name", mappedTrelloBoardsDto.get(0).getName());
        assertEquals(2, mappedTrelloBoardsDto.size());
    }

    @Test
    public void testMapToList() {
        //Given
        TrelloListDto trelloListDto = new TrelloListDto("id", "name", true);
        TrelloListDto trelloListDto2 = new TrelloListDto("id2", "name2", true);
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(trelloListDto);
        trelloListDtos.add(trelloListDto2);
        //When
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDtos);
        //Then
        assertEquals("id", trelloLists.get(0).getId());
        assertEquals(2, trelloLists.size());
    }

    @Test
    public void testMapToListDto() {
        //Given
        TrelloList trelloList = new TrelloList("id", "name", true);
        TrelloList trelloList2 = new TrelloList("id2", "name2", true);
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(trelloList);
        trelloLists.add(trelloList2);
        //When
        List<TrelloListDto> trelloListsDtos = trelloMapper.mapToListDto(trelloLists);
        //Then
        assertEquals("id", trelloListsDtos.get(0).getId());
        assertEquals(2, trelloListsDtos.size());
    }

    @Test
    public void testMapToTrelloCart() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("name", "description", "pos", "id");
        //When
        TrelloCard trelloCard = trelloMapper.mapToTrelloCard(trelloCardDto);
        //Then
        assertEquals(trelloCard.getDescription(), trelloCardDto.getDescription());
        assertEquals(trelloCard.getListId(), trelloCardDto.getListId());
        assertEquals(trelloCard.getName(), trelloCardDto.getName());
        assertEquals(trelloCard.getPos(), trelloCardDto.getPos());
    }

    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("name", "description", "pos", "id");
        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCartDto(trelloCard);
        //Then
        assertEquals(trelloCard.getDescription(), trelloCardDto.getDescription());
        assertEquals(trelloCard.getListId(), trelloCardDto.getListId());
        assertEquals(trelloCard.getName(), trelloCardDto.getName());
        assertEquals(trelloCard.getPos(), trelloCardDto.getPos());
    }

    @Test
    public void testMapToTask() {
        //Given
        TaskDto taskDto = new TaskDto("title", "content");
        //When
        Task task = taskMapper.mapToTask(taskDto);
        //Then
        assertEquals("title", task.getTitle());
        assertEquals("content", task.getContent());
    }

    @Test
    public void testMapToTaskDto() {
        //Given
        Task task = new Task(14L, "title", "content");
        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        //Then
        assertEquals("title", taskDto.getTitle());
        assertEquals("content", taskDto.getContent());
        assertEquals(task.getId(), taskDto.getId());
    }

    @Test
    public void testMapToTaskDtoList() {
        //Given
        Task task = new Task(14L, "title", "content");
        Task task2 = new Task(15L, "title2", "content2");
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        tasks.add(task2);
        //When
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(tasks);
        //Then
        assertEquals(2, taskDtos.size());
        assertEquals("content2", taskDtos.get(1).getContent());
        assertEquals("title2", taskDtos.get(1).getTitle());
        assertNotEquals(java.util.Optional.of(0), taskDtos.get(0).getId());
    }
}