package org.home.tooling.backend.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.home.tooling.backend.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminService {

    @Autowired
    private TodoService todoService;

    /**
     * Exports todos to csv
     * 
     * @return StringBuilder containing csv representation of all todos
     * @deprecated use json export flavor, this object and others are getting too
     *             complicated to use csv
     */
    public StringBuilder exportTodosCsv() {
        log.info("Exporting todos");
        StringBuilder out = new StringBuilder();
        List<Todo> todos = todoService.findAll();
        try (CSVPrinter printer = new CSVPrinter(out,
                CSVFormat.DEFAULT.builder().setHeader("Name", "Description").build());) {
            for (var todo : todos) {
                printer.printRecord(todo.getName(), todo.getDescription());
            }
            return out;
        } catch (Exception e) {
            var msg = "Exception occurred while exporting todos";
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    /**
     * Exports todos via json
     * 
     * @return StringBuilder containing json representation of all todos
     */
    public StringBuilder exportTodosJson() {
        log.info("Exporting todos to json");
        List<Todo> todos = todoService.findAll();
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder out = new StringBuilder();
        try {
            out.append(mapper.writeValueAsString(todos));
            return out;
        } catch (Exception e) {
            var msg = "Exception occurred while exporting todos to json";
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    /**
     * Imports todos via csv input
     * 
     * @return int reporting back the number of todos imported
     * @deprecated use json flavor
     */
    public int importTodos(InputStream inputStream) {
        try (Reader reader = new InputStreamReader(inputStream);
                CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.builder().setHeader("Name", "Description")
                        .setSkipHeaderRecord(true).build());) {
            var todos = new ArrayList<Todo>();
            parser.forEach(record -> {
                try {
                    log.trace("Converting record to object: {}", record);
                    // @formatter:off
                    var todo = Todo.builder()
                                         .name(record.get("Name"))
                                         .description(record.get("Description"))
                                         .build();
                    // @formatter:on
                    log.trace("Converted record: {} to todo: {}", record, todo);
                    todos.add(todo);
                } catch (Exception e) {
                    var msg = "Exception occurred while converting csv record to Todo. Record: " + record;
                    log.error(msg, e);
                    throw new RuntimeException(msg, e);
                }
            });

            // sort by name
            todos.sort(Comparator.comparing(Todo::getName));
            todoService.saveAll(todos);
            log.info("Processed todo import file. Number of todos uploaded: {}", todos.size());
            return todos.size();
        } catch (Exception e) {
            var msg = "Exception occurred while parsing out bank file";
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    /**
     * Imports todos via json input
     * 
     * @return int reporting back the number of todos imported
     */
    public int importTodosJson(InputStream inputStream) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Todo> todos = mapper.readValue(inputStream, new TypeReference<List<Todo>>() {
            });
            todos.forEach(System.out::println);

            // sort by name
            todos.sort(Comparator.comparing(Todo::getName));
            todoService.saveAll(todos);
            log.info("Processed todo import file. Number of todos uploaded: {}", todos.size());
            return todos.size();
        } catch (Exception e) {
            String msg = "Exception occurred while attempting to import todos from json file";
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

}
