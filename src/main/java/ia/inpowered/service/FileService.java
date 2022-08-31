package ia.inpowered.service;

import ia.inpowered.exception.InvalidContactException;
import ia.inpowered.model.Contact;
import ia.inpowered.model.enums.Gender;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.stereotype.Service;

import static java.time.temporal.ChronoUnit.DAYS;

@Slf4j
@Service
public class FileService {

  private static final String ADDRESS_BOOK_FILE = "AddressBook.txt";
  private static final String COLUMN_SEPARATOR = ",";
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yy");

  private static HashSet<Contact> addressBook = null;

  public FileService() throws IOException, URISyntaxException {
    addressBook = new HashSet<>();
    URL resource = getClass().getClassLoader().getResource(ADDRESS_BOOK_FILE);

    try (LineIterator iterator = FileUtils.lineIterator(new File(resource.toURI()), "UTF-8")) {
      while (iterator.hasNext()) {
        try {
          addressBook.add(convertToContact(iterator.nextLine()));
        } catch (InvalidContactException ex) {
          // TODO: log invalid contacts for further investigation
        }
      }
    }
  }

  public Contact convertToContact(String line) {
    String[] fields = line.split(COLUMN_SEPARATOR);
    try {
      return Contact.builder()
          .fullName(fields[0].trim())
          .gender(Gender.valueOf(fields[1].trim().toUpperCase()))
          .birthDate(LocalDate.parse(fields[2].trim(), FORMATTER)
              .minusYears(100L)) // text file is sending yy format, so LocalDate convert 77 to 2077 instead of 1977
          .build();
    } catch (Exception ex) {
      throw new InvalidContactException(ex.getMessage());
    }
  }

  public Long countContactsByGender(Gender gender) {
    return addressBook.stream()
        .filter(contact -> gender.equals(contact.getGender()))
        .count();
  }

  public String getOldestContact() {
    return addressBook.stream()
        .sorted(Comparator.comparing(Contact::getBirthDate))
        .findFirst()
        .orElse(new Contact())
        .getFullName();
  }

  public Long getDifferenceOfAgesInDays(String firstContactFullName, String secondContactFullName) {
    Optional<Contact> firstContact = addressBook.stream()
        .filter(contact -> contact.getFullName().equalsIgnoreCase(firstContactFullName))
        .findAny();

    Optional<Contact> paulRobinson = addressBook.stream()
        .filter(contact -> contact.getFullName().equalsIgnoreCase(secondContactFullName))
        .findAny();

    return (firstContact.isPresent() && paulRobinson.isPresent())
        ? DAYS.between(firstContact.get().getBirthDate(), paulRobinson.get().getBirthDate())
        : 0;
  }
}
