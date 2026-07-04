
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Extractor<R> {
    R run(PreparedStatement statement) throws SQLException;
}
