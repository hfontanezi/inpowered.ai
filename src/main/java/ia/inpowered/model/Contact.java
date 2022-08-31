package ia.inpowered.model;

import ia.inpowered.model.enums.Gender;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
  private String fullName;
  private Gender gender;
  private LocalDate birthDate;
}
