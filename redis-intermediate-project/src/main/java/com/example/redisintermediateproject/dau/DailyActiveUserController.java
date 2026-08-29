package com.example.redisintermediateproject.dau;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dau")
@RequiredArgsConstructor
public class DailyActiveUserController {

  private final DailyActiveUserService dailyActiveUserService;

  // 활동 유저 기록 API
  @PostMapping("/record")
  public void recordActiveUser(
      @RequestBody RecordActiveUserRequestDto recordActiveUserRequestDto
  ) {
    dailyActiveUserService.recordActiveUser(recordActiveUserRequestDto.getUserId());
  }

  // DAU 조회 API
  @GetMapping("/count")
  public long getDau(
      @RequestParam("date")
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
      LocalDate date
  ) {
    return dailyActiveUserService.getDau(date);
  }

  // 활동 유저 기록 Redis API
  @PostMapping("/record/redis")
  public void recordActiveUserWithRedis(
      @RequestBody RecordActiveUserRequestDto recordActiveUserRequestDto
  ) {
    dailyActiveUserService.recordActiveUserWithRedis(recordActiveUserRequestDto.getUserId());
  }

  // DAU 조회 Redis API
  @GetMapping("/count/redis")
  public long getDauWithRedis(
      @RequestParam("date")
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
      LocalDate date
  ) {
    return dailyActiveUserService.getDauWithRedis(date);
  }

    // 활동 유저 기록 Redis API
  @PostMapping("/record/bitmap/redis")
  public void recordActiveUserWithBitmapRedis(
      @RequestBody RecordActiveUserRequestDto recordActiveUserRequestDto
  ) {
    dailyActiveUserService.recordActiveUserWithBitmapRedis(recordActiveUserRequestDto.getUserId());
  }

  // DAU 조회 Redis API
  @GetMapping("/count/bitmap/redis")
  public long getDauWithBitmapRedis(
      @RequestParam("date")
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
      LocalDate date
  ) {
    return dailyActiveUserService.getDauWithBitmapRedis(date);
  }
}
