package ia.inpowered;

import ia.inpowered.model.enums.Gender;
import ia.inpowered.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.InputMismatchException;
import java.util.Scanner;

@Slf4j
@Configuration
@ComponentScan(basePackages = "ia")
public class Application {
  private static final Scanner input = new Scanner(System.in);

  public static void main(String[] args) {
    try {
      FileService fileService = new FileService();

      int option;
      while(true) {
        try {
          System.out.println("------- ADDRESS BOOK -------");
          System.out.println("[1] How many males are in the address book?");
          System.out.println("[2] Who is the oldest person in the address book?");
          System.out.println("[3] How many days older is Bill than Paul?");
          System.out.println("[0] Exit");
          System.out.print("Option: ");
          option = input.nextInt();
          while (option < 0 || option > 4) {
            System.out.println("ERROR: choose one option from 0 to 3");
            System.out.print("Option: ");
            option = input.nextInt();
          }
          input.nextLine();

          switch (option) {
            case 0:
              System.exit(0);
              break;
            case 1:
              Long count = fileService.countContactsByGender(Gender.MALE);
              System.out.println(String.format("There are %s %ss in the address book\n", count,
                  Gender.MALE.toString().toLowerCase()));
              break;
            case 2:
              String oldestContactName = fileService.getOldestContact();
              if (StringUtils.hasText(oldestContactName)) {
                System.out.println(String.format("The oldest person in the address book is %s\n", oldestContactName));
              } else {
                System.out.println("There are no contacts in the address book\n");
              }
              break;
            case 3:
              Long daysBetween = fileService.getDifferenceOfAgesInDays("Bill McKnight", "Paul Robinson");
              if (daysBetween > 0) {
                System.out.println(String.format("Bill is %s days older than Paul\n", daysBetween));
              } else {
                System.out.println("Address book must contain Bill McKnight AND Paul Robinson for such operation\n");
              }
              break;
          }
        } catch (InputMismatchException ex) {
          System.out.println("ERROR: please type an integer value\n");
          input.nextLine();
        }
      }
    } catch (URISyntaxException | IOException ex) {
      System.out.println("AddressBook file not located at 'C:\\Temp\\'");
    }
  }
}
