package com.journal.discipline.tracker.Service;

import java.lang.StackWalker.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.journal.discipline.tracker.DTOs.JournalDTO;
import com.journal.discipline.tracker.DTOs.JournalResponse;
import com.journal.discipline.tracker.Exceptions.ApiException;
import com.journal.discipline.tracker.Model.JournalEntry;
import com.journal.discipline.tracker.Repository.JournalRepository;

@Service
public class JournalServiceImpl implements JournalService{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JournalRepository repository;

    /*TODO:
        - Currently Working
     * Refactor method when user is created to save journal entry with userId    
     */

    public JournalDTO createJournalEntry(JournalDTO journalDTO) {
        /*First map the DTO to 
         * to the Journal Model so you can query to see if the entry is present in the DB
         */
      JournalEntry entry = modelMapper.map(journalDTO, JournalEntry.class);
        
       JournalEntry savedEntry = repository.findByTitle(entry.getTitle());
       if(savedEntry !=null){
        throw new ApiException("Entry with title : " +entry.getTitle() + "is already present");
       }

       repository.save(entry);
       return modelMapper.map(entry, JournalDTO.class);

     
    }

    /*Get All entries is working
     * TODO:
     *  Add Pagination
     */
    @Override
    public JournalResponse getAllJournalEntry() {
        List<JournalEntry> journals = repository.findAll();
        if(journals.isEmpty()){
            throw new ApiException("No Journals Found");
        }

       List<JournalDTO> journalDTO= journals.stream()
       .map(journal -> modelMapper.map(journal, JournalDTO.class)).collect(Collectors.toList());

       JournalResponse response = new JournalResponse();

       response.setContent(journalDTO);

       return response;

    }


    /*To do */
    @Override
    public JournalDTO updateJournalEntry(JournalDTO journalDTO, UUID entryId){
        // 1) Search for the entry by Id
        Optional<JournalEntry> entry =repository.findById(entryId);
        JournalEntry foundEntry = entry.orElseThrow(()-> new ApiException("Cannot find Entry with Id: " +entryId));

        JournalEntry savedJournalEntry = modelMapper.map(journalDTO, JournalEntry.class);
        savedJournalEntry.setId(entryId);
        savedJournalEntry.setCreatedAt(LocalDateTime.now());
        foundEntry = repository.save(savedJournalEntry);
        return modelMapper.map(foundEntry, JournalDTO.class);
    }
  
    @Override
    public JournalDTO deleteJournalEntry(UUID id){
        JournalEntry entry = repository.findById(id).orElseThrow(
            ()-> new ApiException("Entry with Id :" + id + "not found"));

        repository.delete(entry);
        return modelMapper.map(entry, JournalDTO.class);
       

    }


}
