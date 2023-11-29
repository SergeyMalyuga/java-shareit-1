package ru.practicum.shareit.booking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.LocalDateTimeAdapter;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = BookingController.class)
class BookingControllerTest {

    @MockBean
    private BookingService bookingService;
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mvc;
    private Booking booking;
    private Booking booking2;
    private Booking booking3;
    private List<BookingDto> bookingDtoList = new ArrayList<>();
    private BookingMapper bookingMapper;
    private Item item;
    private User user;
    private User user2;
    private Gson gson;

    @BeforeEach
    private void setUp() {
        gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
        bookingMapper = new BookingMapper();
        user = new User().setEmail("serg@mail.ru").setName("Sergey");
        user2 = new User().setEmail("galina@mail.ru").setName("Galina");
        item = new Item().setId(1).setAvailable(true).setDescription("Аккумуляторная дрель")
                .setName("Дрель").setOwnerId(1);
        booking = new Booking().setId(1).setStatus(BookingStatus.WAITING).setBooker(user).setItem(item)
                .setStart(LocalDateTime.now().plusMinutes(1)).setEnd(LocalDateTime.now()
                        .plusHours(1)).setItemId(1).setBookerId(1);
        booking2 = new Booking().setStatus(BookingStatus.WAITING).setBooker(user).setItem(item)
                .setStart(LocalDateTime.now().plusMinutes(1)).setEnd(LocalDateTime.now()
                        .plusHours(1)).setItemId(1).setBookerId(1);
        booking3 = new Booking().setStatus(BookingStatus.WAITING).setBooker(user).setItem(item)
                .setStart(LocalDateTime.now().plusMinutes(1)).setEnd(LocalDateTime.now()
                        .plusHours(1)).setItemId(1).setBookerId(1);
        bookingMapper.setUserMapper(new UserMapper());
        bookingMapper.setItemMapper(new ItemMapper());
        Collections.addAll(bookingDtoList, bookingMapper.bookingDto(booking),
                bookingMapper.bookingDto(booking2), bookingMapper.bookingDto(booking3));
    }

    @Test
    void addBooking() throws Exception {
        when(bookingService.addBooking(Mockito.any(Booking.class), Mockito.anyInt()))
                .thenReturn(bookingMapper.bookingDto(booking));
        Map<String, String> query = new HashMap();
        query.put("itemId", "2");
        query.put("start", "2023-11-29T13:52:38");
        query.put("end", "2023-11-30T13:52:38");
        mvc.perform(post("/bookings").content(gson.toJson(query))
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void confirmationOrRejectionBooking() {
    }

    @Test
    void getBookingById() {
    }

    @Test
    void getAllBookingCurrentUser() {
    }

    @Test
    void getAllBookingCurrentOwner() {
    }
}