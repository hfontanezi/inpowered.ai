package ia.inpowered.service;

import ia.inpowered.exception.InvalidContactException;
import ia.inpowered.model.Contact;
import ia.inpowered.model.enums.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {

    private FileService fileService;

    @BeforeEach
    void init() {
        try {
            fileService = new FileService();
        } catch (URISyntaxException | IOException ex) {
            System.out.println("AddressBook file not located at 'C:\\Temp\\'");
        }
    }

    @Test
    void shouldConvertLineToContact() {
        Contact contact = fileService.convertToContact("Bill McKnight, Male, 16/03/77");
        assertInstanceOf(Contact.class, contact);
        assertEquals("Bill McKnight", contact.getFullName());
        assertEquals(Gender.MALE, contact.getGender());
        assertEquals(LocalDate.of(1977, 3, 16), contact.getBirthDate());
    }

    @Test
    void shouldThrowConvertingInvalidContact() {
        assertThrows(InvalidContactException.class, () -> fileService.convertToContact("Bill McKnight, Male"));
    }

    @Test
    void shouldCountContactsByGender() {
        assertEquals(2, fileService.countContactsByGender(Gender.FEMALE));
        assertEquals(3, fileService.countContactsByGender(Gender.MALE));
    }

    @Test
    void shouldGetOldestContact() {
        assertEquals("Wes Jackson", fileService.getOldestContact());
    }

    @Test
    void shouldGetDifferenceOfAgesInDays() {
        assertEquals(2862, fileService.getDifferenceOfAgesInDays("Bill McKnight", "Paul Robinson"));
        assertEquals(945, fileService.getDifferenceOfAgesInDays("Wes Jackson", "Bill McKnight"));
        assertEquals(4078, fileService.getDifferenceOfAgesInDays("Sarah Stone", "Gemma Lane"));
    }
}