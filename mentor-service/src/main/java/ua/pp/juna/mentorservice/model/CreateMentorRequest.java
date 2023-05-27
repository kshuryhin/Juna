package ua.pp.juna.mentorservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreateMentorRequest {
    private Mentor userDetails;
}
