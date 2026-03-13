package com.ck.journalApp.controller;

import com.ck.journalApp.entity.JournalEntry;
import com.ck.journalApp.entity.User;
import com.ck.journalApp.service.JournalEntryService;
import com.ck.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private final JournalEntryService journalEntryService;
    public final UserService userService;


    public JournalEntryController(JournalEntryService journalEntryService,UserService userService) {
        this.journalEntryService = journalEntryService;
        this.userService = userService;
    }


   // @GetMapping
//    public ResponseEntity<?> getAll(){
//        List<JournalEntry> allEntries= journalEntryService.getAll();
//
//        if(allEntries!=null && !allEntries.isEmpty()){
//            return new ResponseEntity<>(allEntries,HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        List<JournalEntry> allEntries = user.getJournalEntries();
        if(!allEntries.isEmpty()){
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myentry, @PathVariable String userName) {
        try {
            journalEntryService.saveEntry(myentry, userName);
            return new ResponseEntity<>(myentry, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myid}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myid){
       // return journalEntryService.findById(myid).orElse(null);

        Optional<JournalEntry> journalEntry = journalEntryService.findById(myid);

        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @DeleteMapping("id/{myid}")
//    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myid){
//        journalEntryService.deleteById(myid);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

@DeleteMapping("id/{userName}/{myId}")
public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String userName){
         journalEntryService.deleteById(myId, userName);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @PutMapping("id/{id}")
//    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry newentry){
//        JournalEntry oldEntry = journalEntryService.findById(id).orElse(null);
//
//        if(oldEntry!=null){
//            oldEntry.setTitle(newentry.getTitle()!=null && !newentry.getTitle().isEmpty()? newentry.getTitle():oldEntry.getTitle());
//            oldEntry.setContent(newentry.getContent()!=null && !newentry.getContent().isEmpty()? newentry.getContent():oldEntry.getContent());
//            journalEntryService.saveEntry(oldEntry);
//            return new ResponseEntity<>(oldEntry,HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity<?> updateJournalEntryById
            (@PathVariable ObjectId myId,
             @RequestBody JournalEntry newentry,
             @PathVariable String userName) {

        JournalEntry oldEntry = journalEntryService.findById(myId).orElse(null);

       if(oldEntry!=null){
            oldEntry.setTitle(newentry.getTitle()!=null && !newentry.getTitle().isEmpty()? newentry.getTitle():oldEntry.getTitle());
            oldEntry.setContent(newentry.getContent()!=null && !newentry.getContent().isEmpty()? newentry.getContent():oldEntry.getContent());
          journalEntryService.saveEntry(oldEntry);
           return new ResponseEntity<>(oldEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }










}
