package com.crud.basic.back.Controllers;

import com.crud.basic.back.Response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface BaseController<T, ID> {
    @GetMapping
    ResponseEntity<ApiResponse<List<T>>> getAll();

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<T>> getById(@PathVariable ID id);

    @PostMapping
    ResponseEntity<ApiResponse<T>> create(@RequestBody T dto);

    @PutMapping("/{id}")
    ResponseEntity<ApiResponse<T>> update(@PathVariable ID id, @RequestBody T dto);

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse<T>> deleteById(@PathVariable ID id);
}
