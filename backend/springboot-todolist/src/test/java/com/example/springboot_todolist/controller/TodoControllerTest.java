package com.example.springboot_todolist.controller;

import static org.mockito.Mockito.*;
// 手動import
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;

// 自動import
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // JSON変換用

    @Test
    void testTodoCrudOperations() throws Exception {

        // --- ① POST: 新規作成 ---
        String json = """
            {
              "description": "テストTODO"
            }
            """;

        MvcResult postResult = mockMvc.perform(post("/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.description").value("テストTODO"))
                .andReturn();

        // 作成したIDを取得
        String response = postResult.getResponse().getContentAsString();
        Map<String, Object> createdTodo = objectMapper.readValue(response, Map.class);
        Integer id = (Integer) createdTodo.get("id");

        // --- ② GET: findById ---
        mockMvc.perform(get("/todo/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.description").value("テストTODO"));

        // GETで一覧取得 
        mockMvc.perform(get("/todo")) 
                .andExpect(status().isOk()) 
                .andExpect(jsonPath("$[0].description")
                .value("テストTODO"));

        // --- ③ PUT: 更新 ---
        String updateJson = String.format("""
            {
              "id": %d,
              "description": "更新後のTODO"
            }
            """, id);

        mockMvc.perform(put("/todo", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("更新後のTODO"));

        // --- ④ GET: 更新結果確認 ---
        mockMvc.perform(get("/todo/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("更新後のTODO"));

        // --- ⑤ DELETE: 削除 ---
        mockMvc.perform(delete("/todo/{id}", id))
                .andExpect(status().isOk());

        // --- ⑥ GET: 削除後の確認（存在しない）---
        mockMvc.perform(get("/todo/{id}", id))
                .andExpect(status().isNotFound());

        // --- ⑥ GET: 削除後の確認（存在しない）---
        mockMvc.perform(get("/todo"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}