package com.ck.journalApp.service;

import com.ck.journalApp.entity.JournalEntry;
import com.ck.journalApp.entity.User;
import com.ck.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component
public class JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;
    private final UserService userService;

    public JournalEntryService(JournalEntryRepository journalEntryRepository, UserService userService) {
        this.journalEntryRepository = journalEntryRepository;
        this.userService = userService;
    }

    @Transactional
    public void saveEntry(JournalEntry journalentry, String userName ){
        User user = userService.findByUserName(userName);
        journalentry.setDate(LocalDateTime.now());
        JournalEntry savedJournalEntry= journalEntryRepository.save(journalentry);
        user.getJournalEntries().add(savedJournalEntry);
        userService.saveUser(user);
    }

    public void saveEntry(JournalEntry journalentry){
        journalEntryRepository.save(journalentry);
    }

    public List<JournalEntry> getAll(){
      return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id, String userName){
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(journalEntry -> journalEntry.getId().equals(id));
        userService.saveUser(user);
        journalEntryRepository.deleteById(id);
    }


}
