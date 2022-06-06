package com.seoultech.dayofcm;

import com.google.firebase.messaging.FirebaseMessagingException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FcmSender {

  private final FcmMessageService messageService;

  @KafkaListener(topics = "HEART", groupId = "foo")
  public void fcmHeart(String message) throws FirebaseMessagingException {
    log.info("message -> {}", message);
    Note note = makeNote(message);
    messageService.sendMessage(note, "HEART");
  }

  @KafkaListener(topics = "COMMENT", groupId = "foo")
  public void fcmComment(String message) throws FirebaseMessagingException {
    log.info("message -> {}", message);
    Note note = makeNote(message);
    messageService.sendMessage(note, "COMMENT");
  }

  @KafkaListener(topics = "FOLLOW", groupId = "foo")
  public void fcmFollow(String message) throws FirebaseMessagingException {
    log.info("message -> {}", message);
    Note note = makeNote(message);
    messageService.sendMessage(note, "FOLLOW");
  }

  private Note makeNote(String message) {
    JSONObject jsonObject = new JSONObject(message);
    String subject = jsonObject.get("subject").toString();
    String body = jsonObject.get("body").toString();
    String deviceToken = jsonObject.get("deviceToken").toString();

    Map<String, String> data = new HashMap<>();
    data.put("body", body);
    return new Note(subject, null, data, null, deviceToken);
  }


}
