package jw.piano.database;

import jw.spigot_fluent_api.database.api.database_table.DbTable;
import jw.spigot_fluent_api.database.mysql_db.models.SqlDbContext;
import lombok.Getter;

@Getter
public class DbContext extends SqlDbContext
{
    private DbTable<SqlUser> users;

    private DbTable<SqlNotes> notes;
}
