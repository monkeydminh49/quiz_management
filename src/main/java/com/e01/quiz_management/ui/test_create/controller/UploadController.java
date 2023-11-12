package com.e01.quiz_management.ui.test_create.controller;

import com.e01.quiz_management.model.Choice;
import com.e01.quiz_management.model.MultipleChoice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;

public class UploadController {

    private static UploadController instance = null;

    public static UploadController getInstance() {
        if (instance == null) {
            instance = new UploadController();
        }
        return instance;
    }

    public UploadController() {
    }

    public List<MultipleChoice> createQuestionsFromFile(File file) {
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Sheet sheet = workbook.getSheetAt(0);
        List<MultipleChoice> questions = new ArrayList<>();
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            MultipleChoice question = new MultipleChoice();
            Row row = sheet.getRow(i);
            if (row == null || row.getCell(0) == null || row.getCell(0).getCellType() == CellType.BLANK) {
                break;
            }
            question.setQuestion(sheet.getRow(i).getCell(0).getStringCellValue());
            List<Choice> choices = new ArrayList<>();
            for (int j = 1; j < 5; j++) {
                Choice choice = new Choice();
                choice.setContent(sheet.getRow(i).getCell(j).getStringCellValue());
                choice.setCorrect(false);
                choices.add(choice);
            }
            choices.get((int) sheet.getRow(i).getCell(5).getNumericCellValue() - 1).setCorrect(true);
            question.setChoices(choices);
            questions.add(question);
            System.out.println(question.getQuestion() + " " + question.getType());
        }
        return questions;
    }
}
