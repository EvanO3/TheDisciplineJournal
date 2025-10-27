package com.journal.discipline.tracker.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;
    @NotBlank(message="Username is required")
    @Size(min=2, max =15, message="Username must be between 2 and 15 chars")
    private String username;

    @NotBlank(message="Password is required")
    @Size(min=8, max =20, message="Password must be between 8 and 20 characters")
    
    /*For small projects this is fine, but for enterprise grade this logic must
     * be upgraded into a class
     */

         /*Password requires :
     * 1 Uppercase
     * 1 lowercase
     * 1 number
     * and 1 special character
     * must also be between 8 and 30 chars long
     */
    @Pattern(regexp ="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,30}$",
    message = "Password must be at least 8 characters long, include 1 uppercase, 1 lowercase, 1 number, 1 special character, and contain no spaces")
    
    private String password;

    @NotBlank(message="Email cannot be blank")
    @Email(message="a valid email must be provided")
    private String email;

    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    private int streakCount;
    private int longestStreak;
    private double avgDisciplineScore;


    /*Cascade all will make sure all operations are propgated to the relevant relationships
    - FetchType.Lazy makes sure the relevant entites are loaded when asked for not loaded instantly
    - if eagar was used it would be loaded when user is loaded(only use egar when needed)
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<JournalEntry> journal = new ArrayList<>();
    
    
    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt= LocalDateTime.now();
    }

    @PreUpdate
    public void postPersist(){
        this.updatedAt = LocalDateTime.now();
    }
}
