package jw.piano.database;

import jw.spigot_fluent_api.data.interfaces.Repository;
import jw.spigot_fluent_api.database.api.database_table.DbTable;
import jw.spigot_fluent_api.database.api.query_fluent.select.SelectFluentBridge;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;

import java.sql.SQLException;
import java.util.*;

@Injection
public class SqlUserRepository implements Repository<SqlUser, Integer>
{
    private final DbTable<SqlUser> users;

    @Inject
    public SqlUserRepository(DbContext dbContext) {
        this.users = dbContext.getUsers();
    }

    @Override
    public Class<SqlUser> getEntityClass() {
        return SqlUser.class;
    }

    public SelectFluentBridge<SqlUser> query()
    {
        return users.select();
    }


    @Override
    public Optional<SqlUser> findById(Integer id) {
        return users.select()
                .where()
                .isEqual("id", id)
                .first();
    }

    @Override
    public List<SqlUser> findAll() {
        return users.select().toList();
    }

    @Override
    public boolean insert(SqlUser data) {
        try {
            users.insert(data);
            users.saveChanges();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Integer id, SqlUser data) {
        try {
            data.id = id;
            users.update(data);
            users.saveChanges();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteOne(SqlUser data) {
        try {
            users.delete(data);
            users.saveChanges();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteMany(List<SqlUser> data) {
        try {
            for(var da : data)
            {
                users.delete(da);
            }
            users.saveChanges();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}
