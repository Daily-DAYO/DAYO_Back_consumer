package com.seoultech.dayoconsumer.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmMessageService {

  private final FirebaseMessaging firebaseMessaging;

  public void sendMessage(Note note, String topic)
      throws FirebaseMessagingException {

    Notification notification = Notification.builder()
        .setTitle(null)
        .setBody(note.getContent())
        .build();

    Message message = Message.builder()
        .setToken(note.getDeviceToken())
        .setNotification(notification)
        .putAllData(note.getData())
//        .setTopic(topic)
        .build();

    firebaseMessaging.send(message);
  }
}
