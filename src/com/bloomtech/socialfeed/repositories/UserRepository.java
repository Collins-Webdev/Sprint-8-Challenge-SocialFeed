package com.bloomtech.socialfeed.repositories;

import com.bloomtech.socialfeed.models.User;
import com.bloomtech.socialfeed.validators.UserInfoValidator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private static final String USER_DATA_PATH = "src/resources/UserData.json";
    private static final UserInfoValidator userInfoValidator = new UserInfoValidator();

    public UserRepository() {
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        try (FileReader reader = new FileReader(USER_DATA_PATH)) {
            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            Gson gson = new Gson();
            allUsers = gson.fromJson(reader, userListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allUsers != null ? allUsers : new ArrayList<>();
    }

    public Optional<User> findByUsername(String username) {
        return getAllUsers()
                .stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    public void save(User user) {
        List<User> allUsers = getAllUsers();

        Optional<User> existingUser = allUsers.stream()
                .filter(u -> u.getUsername().equals(user.getUsername()))
                .findFirst();

        if (!existingUser.isEmpty()) {
            throw new RuntimeException("User with name: " + user.getUsername() + " already exists!");
        }
        allUsers.add(user);

        try (FileWriter writer = new FileWriter(USER_DATA_PATH)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(allUsers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
