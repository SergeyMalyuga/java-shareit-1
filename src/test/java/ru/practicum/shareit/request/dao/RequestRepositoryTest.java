package ru.practicum.shareit.request.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.request.Request;
import ru.practicum.shareit.user.User;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class RequestRepositoryTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private RequestRepository requestRepository;
    private User user;
    private Request request;

    @BeforeEach
    private void setUp() {
        request = new Request().setDescription("description").setRequesterId(1);
        user = new User().setEmail("serg@mail.ru").setName("Sergey");
    }

    @Test
    void findAllRequests() {
        List<Request> requestList = new ArrayList<>();
        assertEquals(requestList.size(), 0);
        entityManager.persist(user);
        entityManager.persist(request);
        requestList = requestRepository.findAllRequests(1, PageRequest.of(0, 1)).getContent();
        assertEquals(requestList.size(), 1);
    }
}