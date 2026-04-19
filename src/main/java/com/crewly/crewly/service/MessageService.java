package com.crewly.crewly.service;

import com.crewly.crewly.entity.*;
import com.crewly.crewly.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final PinnedMessageRepository pinnedMessageRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    public Message sendGroupMessage(Integer groupId,
                                    Integer senderId, String content) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found!"));
        Student sender = studentRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Student not found!"));

        Message message = new Message();
        message.setSender(sender);
        message.setReceiverGroup(group);
        message.setContent(content);

        return messageRepository.save(message);
    }
    public List<Message> getDMs(Integer studentId, Integer otherId) {
        return messageRepository
                .findDMsBetween(studentId, otherId);
    }


    public Message sendDM(Integer senderId, Integer receiverId, String content) {
        Student sender = studentRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        Student receiver = studentRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));
        Message msg = new Message();
        msg.setSender(sender);
        msg.setReceiverStudent(receiver);
        msg.setContent(content);
        return messageRepository.save(msg);
    }

    public List<Message> getGroupMessages(Integer groupId) {
        return messageRepository
                .findByReceiverGroupGroupIdOrderBySentAtAsc(groupId);
    }

    public String pinMessage(Integer groupId, Integer messageId,
                             Integer pinnedById) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found!"));

        if (!group.getCreator().getStudentId().equals(pinnedById)) {
            throw new RuntimeException("Only the group creator can pin messages!");
        }

        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found!"));
        Student pinnedBy = studentRepository.findById(pinnedById)
                .orElseThrow(() -> new RuntimeException("Student not found!"));

        PinnedMessage pinned = new PinnedMessage();
        pinned.setGroup(group);
        pinned.setMessage(message);
        pinned.setPinnedBy(pinnedBy);

        pinnedMessageRepository.save(pinned);
        return "Message pinned successfully!";
    }

    public List<PinnedMessage> getPinnedMessages(Integer groupId) {
        return pinnedMessageRepository.findByGroupGroupId(groupId);
    }

    public String clearChat(Integer groupId, Integer requesterId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found!"));

        if (!group.getCreator().getStudentId().equals(requesterId)) {
            throw new RuntimeException("Only the group creator can clear chat!");
        }

        group.setLastClearedAt(LocalDateTime.now());
        groupRepository.save(group);
        return "Chat cleared successfully!";
    }

    public void deleteMessage(Integer messageId, Integer studentId) {
        Message msg = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found."));
        if (!msg.getSender().getStudentId().equals(studentId)) {
            throw new RuntimeException("You can only delete your own messages.");
        }
        messageRepository.deleteById(messageId);
    }

    // Unpin a message — only group creator can unpin
    public void unpinMessage(Integer groupId, Integer pinnedId, Integer requesterId) {
        // Verify requester is the group creator
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found."));
        if (!group.getCreator().getStudentId().equals(requesterId)) {
            throw new RuntimeException("Only the group creator can unpin messages.");
        }
        pinnedMessageRepository.deleteById(pinnedId);
    }
}
