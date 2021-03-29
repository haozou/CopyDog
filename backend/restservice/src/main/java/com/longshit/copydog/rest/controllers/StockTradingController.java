package com.longshit.copydog.rest.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class StockTradingController {


    @GetMapping("/api/v1.0/stock/stockTradingDetail")
    public ResponseEntity<?> listFollowedUsersStocks(@RequestParam(value = "userName") String userName) {
        //TODO:
        return ResponseEntity.ok(Collections.singletonList(Collections.singletonMap("name", userName)));
    }

    @PostMapping("/api/v1.0/stock/{stock_label}")
    public ResponseEntity<?> tradeStock(@RequestBody Map<String, Object> body) {
        //TODO:
        return ResponseEntity.ok().build();
    }
}
