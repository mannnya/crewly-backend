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
}