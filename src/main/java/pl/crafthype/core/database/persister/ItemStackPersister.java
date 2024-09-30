package pl.crafthype.core.database.persister;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.BaseDataType;
import com.j256.ormlite.support.DatabaseResults;
import org.bukkit.inventory.ItemStack;
import pl.crafthype.core.shared.ItemUtil;

import java.sql.SQLException;

public class ItemStackPersister extends BaseDataType {

    public static final ItemStackPersister INSTANCE = new ItemStackPersister();

    private ItemStackPersister() {
        super(SqlType.LONG_STRING, new Class<?>[]{ ItemStack.class });
    }

    public static ItemStackPersister getSingleton() {
        return INSTANCE;
    }

    @Override
    public Object javaToSqlArg(FieldType fieldType, Object javaObject) throws SQLException {
        if (javaObject == null) {
            return null;
        }

        ItemStack itemStack = (ItemStack) javaObject;

        return ItemUtil.encode(itemStack);
    }

    @Override
    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
        return results.getString(columnPos);
    }

    @Override
    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        return defaultStr;
    }

    @Override
    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) throws SQLException {
        String itemData = (String) sqlArg;

        if (itemData == null) {
            return null;
        }

        return ItemUtil.decode(itemData);
    }
}