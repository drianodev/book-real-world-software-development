import br.com.drianodev.BankStatementCSVParser;
import br.com.drianodev.BankStatementParser;
import br.com.drianodev.BankTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

public class BankStatementCSVParserTest {

    private BankStatementParser statementParser = new BankStatementCSVParser();

    @Test
    public void shouldParseOneCorrectline() throws Exception {
        final String line = "01-01-2024,1500,Electronics";

        final BankTransaction result = statementParser.parseFrom(line);

        final BankTransaction expected = new BankTransaction(LocalDate.of(2024, Month.JANUARY, 1), 1500, "Electronics");
        final double tolerance = 0.0d;

        Assertions.assertEquals(expected.getDate(), result.getDate());
        Assertions.assertEquals(expected.getAmount(), result.getAmount(), tolerance);
        Assertions.assertEquals(expected.getDescription(), result.getDescription());
    }
}
