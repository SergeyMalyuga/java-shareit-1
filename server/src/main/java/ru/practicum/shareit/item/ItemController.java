package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping
    public ItemDto addItem(@RequestHeader("X-Sharer-User-Id") int userId,
                           @RequestBody ItemDto item) {
        return itemService.addItem(userId, item);
    }

    @GetMapping("/{id}")
    public ItemDto getItemById(@PathVariable(name = "id") int itemId, @RequestHeader("X-Sharer-User-Id") int userId) {
        return itemService.getItemByIdDto(itemId, userId);
    }

    @GetMapping
    public List<ItemDto> getAllItemForOwner(@RequestHeader("X-Sharer-User-Id") int userId,
                                            @RequestParam(name = "from", required = false)
                                            Optional<Integer> from,
                                            @RequestParam(name = "size", required = false)
                                            Optional<Integer> size) {
        return itemService.getAllItemForOwner(userId, from, size);
    }

    @PatchMapping("/{id}")
    public ItemDto updateItem(@RequestHeader("X-Sharer-User-Id") int userId,
                              @PathVariable(name = "id") int itemId,
                              @RequestBody Map<Object, Object> fields) {
        return itemService.updateItem(userId, itemId, fields);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItem(@RequestParam(name = "text") String request,
                                    @RequestParam(name = "from", required = false)
                                    Optional<Integer> from,
                                    @RequestParam(name = "size", required = false)
                                    Optional<Integer> size) {
        return itemService.searchItem(request, from, size);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@PathVariable int itemId, @RequestHeader("X-Sharer-User-Id")
    int userId, @RequestBody Comment comment) {
        return itemService.addComment(itemId, userId, comment);
    }
}
