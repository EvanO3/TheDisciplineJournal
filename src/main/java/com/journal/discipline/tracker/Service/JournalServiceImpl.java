package com.journal.discipline.tracker.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.journal.discipline.tracker.DTOs.JournalDTO;
import com.journal.discipline.tracker.DTOs.JournalResponse;
import com.journal.discipline.tracker.Exceptions.ApiException;
import com.journal.discipline.tracker.Model.JournalEntry;
import com.journal.discipline.tracker.Model.User;
import com.journal.discipline.tracker.Repository.JournalRepository;
import com.journal.discipline.tracker.Repository.UserRespository;

import jakarta.transaction.Transactional;

@Service
public class JournalServiceImpl implements JournalService{
    private static final Logger logger = LoggerFactory.getLogger(JournalServiceImpl.class);
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JournalRepository repository;

    @Autowired
    private UserRespository userRespository;
    /*TODO:
       - Ensure 1 Journal PER DAY
     */

     @Transactional
    public JournalDTO createJournalEntry(JournalDTO journalDTO, UUID userId) {
        /*First map the DTO to 
         * to the Journal Model so you can query to see if the entry is present in the DB
         */

      User user = userRespository.findById(userId).orElseThrow(() -> new ApiException("Unable to save Entry as no user available"));
      JournalEntry entry = modelMapper.map(journalDTO, JournalEntry.class);
        
       Optional<JournalEntry> savedEntry = repository.findByUserIdAndTitle(userId, entry.getTitle());
       if(savedEntry.isPresent() ){
        throw new ApiException("Entry with title : " +entry.getTitle() + "is already present");
       }
       /*After finding the user, setting the user to be associated with their jounral */
       entry.setUser(user);

       repository.save(entry);
       return modelMapper.map(entry, JournalDTO.class);

     
    }

    

     /*Fix */
    @Override
    public JournalResponse getAllJournalEntry(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
       Sort sortAndOrderBy = sortOrder.equalsIgnoreCase("asc") ? 
       Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

       logger.info("finding out what info is Number: {} " , pageNumber);
       logger.info("finding out what info is Size: {} " + pageSize);
       logger.info("finding out what info is sortBy: {} " + sortBy);
       logger.info("finding out what info is Order: {} " + sortOrder);


       PageRequest pageDetails = PageRequest.of(pageNumber, pageSize, sortAndOrderBy);

       Page<JournalEntry> journalPage =repository.findAll(pageDetails);
       List<JournalEntry> journals = journalPage.getContent();

        if(journals.isEmpty()){
            throw new ApiException("No Journals Found");
        }

       List<JournalDTO> journalDTOs = journals.stream()
       .map(journal -> modelMapper.map(journal, JournalDTO.class)).collect(Collectors.toList());

       JournalResponse response = new JournalResponse();
       response.setContent(journalDTOs);
       response.setPageNumber(journalPage.getNumber());
       response.setTotalPages(journalPage.getTotalPages());
       response.setPageSize(journalPage.getSize());
       response.setLastPage(journalPage.isLast());


       return response;

    }


    /*To do */
    @Override
    public JournalDTO updateJournalEntry(JournalDTO journalDTO, UUID entryId){
        // 1) Search for the entry by Id
        Optional<JournalEntry> entry =repository.findById(entryId);
        JournalEntry foundEntry = entry.orElseThrow(()-> new ApiException("Cannot find Entry with Id: " +entryId));

        JournalEntry savedJournalEntry = modelMapper.map(journalDTO, JournalEntry.class);
        savedJournalEntry.setJournalId(entryId);
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
