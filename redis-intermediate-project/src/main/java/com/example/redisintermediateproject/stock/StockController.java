package com.example.redisintermediateproject.stock;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {

  private final StockService stockService;
  private final StockLockService stockLockService;
  @PostMapping("/{id}/decrease")
  public void decrease(
      @PathVariable(name = "id") Long id
  ) {
    stockService.decrease(id);
  }

  @PostMapping("/{id}/decrease/redis")
  public void postMethodName(@PathVariable(name = "id") Long id) throws InterruptedException{
    stockLockService.decrease(id);
  }
  
}
