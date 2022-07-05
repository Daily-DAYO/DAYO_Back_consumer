package com.seoultech.dayoconsumer.fcm;

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
    Note note = makeNoteWithPost(message);
    messageService.sendMessage(note, "HEART");
  }

  @KafkaListener(topics = "COMMENT", groupId = "foo")
  public void fcmComment(String message) throws FirebaseMessagingException {
    log.info("message -> {}", message);
    Note note = makeNoteWithPost(message);
    messageService.sendMessage(note, "COMMENT");
  }

  @KafkaListener(topics = "FOLLOW", groupId = "foo")
  public void fcmFollow(String message) throws FirebaseMessagingException {
    log.info("message -> {}", message);
    Note note = makeNoteWithMember(message);
    messageService.sendMessage(note, "FOLLOW");
  }

  private Note makeNoteWithPost(String message) {
    JSONObject jsonObject = new JSONObject(message);
    String subject = jsonObject.get("subject").toString();
    String body = jsonObject.get("body").toString();
    String deviceToken = jsonObject.get("deviceToken").toString();
    String content = jsonObject.get("content").toString();
    String postId = jsonObject.get("postId").toString();
    String topic = jsonObject.get("topic").toString();
    String image = jsonObject.get("image").toString();

    Map<String, String> data = new HashMap<>();
    data.put("body", body);
    data.put("content", content);
    data.put("postId", postId);
    data.put("topic", topic);
    data.put("image", image);

    return new Note(subject, body, data, image, deviceToken);
  }

  private Note makeNoteWithMember(String message) {
    JSONObject jsonObject = new JSONObject(message);
    String subject = jsonObject.get("subject").toString();
    String body = jsonObject.get("body").toString();
    String deviceToken = jsonObject.get("deviceToken").toString();
    String content = jsonObject.get("content").toString();
    String memberId = jsonObject.get("memberId").toString();
    String topic = jsonObject.get("topic").toString();

    Map<String, String> data = new HashMap<>();
    data.put("body", body);
    data.put("content", content);
    data.put("memberId", memberId);
    data.put("topic", topic);

    return new Note(subject, body, data, null, deviceToken);
  }


}
