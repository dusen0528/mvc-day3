package com.nhnacademy.day3.student.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nhnacademy.day3.student.domain.Gender;
import com.nhnacademy.day3.student.domain.Student;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JsonStudentRepository implements StudentRepository {
    private final ObjectMapper objectMapper;
    // json file 저장 경로
    private static final String JSON_FILE_PATH = "/Users/inho/adminpage/web/day3/src/main/json/student.json";

    public JsonStudentRepository() {
        objectMapper = new ObjectMapper();
        // LocalDatetime json 직열화/역직렬화 가능하도록 설정
        objectMapper.registerModule(new JavaTimeModule());
        // JSON_FILE_PATH 경로에 json 파일이 존재하면 삭제 합니다.
        File file = new File(JSON_FILE_PATH);
        if (file.exists()) {
            file.delete();
        }

    }

    private synchronized List<Student> readJsonFile() {
        // json 파일이 존재하지 않다면 비어있는 List<Student> 리턴
        File file = new File(JSON_FILE_PATH);
        log.info("**********:{}", !file.exists());
        if (!file.exists()) {
            return new ArrayList<>();
        }

        List<Student> students;
        // json read & 역직렬화 ( json string -> Object )
        try (FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {
            students = objectMapper.readValue(bufferedReader, new TypeReference<List<Student>>() {

            });
            log.info(students.toString());
            return students;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void writeJsonFile(List<Student> studentList) {
        // List<Student> 객체를 -> json 파일로 저장 : 직렬화
        File file = new File(JSON_FILE_PATH);

        try (
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);) {
            objectMapper.writeValue(bufferedWriter, studentList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Student student) {
        // json String -> Object 형태로 변화 (직렬화)
        List<Student> students = readJsonFile();
        // List에 student 추가
        log.info("dasdads");
        students.add(student);
        log.info("asd");

        // List<Student>객체를 -> json String 형태로 저장(직렬화)
        writeJsonFile(students);
    }

    @Override
    public void update(Student student) {
        List<Student> students = readJsonFile();
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(student.getId())) {
                students.set(i, student);
                writeJsonFile(students);
                return;
            }
        }
    }

    @Override
    public void deleteById(String id) {
        List<Student> students = readJsonFile();
        boolean removed = students.removeIf(student -> student.getId().equals(id));
        if (removed) {
            writeJsonFile(students);
        }
    }

    @Override
    public Student getStudentById(String id) {
        List<Student> students = readJsonFile();
        return students.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Student> getStudents() {
        return readJsonFile();
    }

    @Override
    public boolean existById(String id) {
        List<Student> students = readJsonFile();
        return students.stream()
                .anyMatch(student -> student.getId().equals(id));
    }
}
