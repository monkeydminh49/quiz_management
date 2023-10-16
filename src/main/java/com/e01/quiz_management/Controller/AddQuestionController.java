package com.e01.quiz_management.Controller;

import com.e01.quiz_management.Entities.Question;
import com.e01.quiz_management.Entities.Quiz;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AddQuestionController implements  Initializable {

        @FXML
        private JFXTextArea question;
        @FXML
        private JFXTextField option1;
        @FXML
        private JFXTextField option2;
        @FXML
        private JFXTextField option3;
        @FXML
        private JFXTextField option4;
        @FXML
        private JFXRadioButton option1radio;
        @FXML
        private JFXRadioButton option2radio;
        @FXML
        private JFXRadioButton option3radio;
        @FXML
        private JFXRadioButton option4radio;
        @FXML
        private JFXButton addNextQuestion;
        @FXML
        private JFXButton submitQuiz;
        ToggleGroup radioGroup = new ToggleGroup();
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            radioButtonSetup();
        }
        public void radioButtonSetup(){
            option1radio.setToggleGroup(radioGroup);
            option2radio.setToggleGroup(radioGroup);
            option3radio.setToggleGroup(radioGroup);
            option4radio.setToggleGroup(radioGroup);

        }
    private Quiz quiz = null;
    private ArrayList<Question> questions = new ArrayList<>();

    //ham bao loi text field trong
    private boolean validateFields() {
        String qu = this.question.getText();
        String op1 = this.option1.getText();
        String op2 = this.option2.getText();
        String op3 = this.option3.getText();
        String op4 = this.option4.getText();
        Toggle selectedRadio = radioGroup.getSelectedToggle();
        System.out.println(selectedRadio);
        if (qu.trim().isEmpty() ||
                op1.trim().isEmpty() ||
                op2.trim().isEmpty() || op3.trim().isEmpty()
                || op4.trim().isEmpty()) {

            Notifications.create()
                    .title("Question").position(Pos.CENTER)
                    .darkStyle().text("All Fields Are Required.... \n [Question , Option1 , Option 2 , Option 3 , Option 4]")
                    .showError();
            return false;


        } else {
            if (selectedRadio == null) {
                Notifications.create()
                        .title("Question").position(Pos.CENTER)
                        .darkStyle().text("Please Select A Answer....")
                        .showError();
                return false;
            } else {
                return true;   // save Quistion and add next
            }
        }
    }
    // ham kiem tra text field co bi trong hay khong va thu nhap data
    private boolean addQuestions(){
        boolean valid = validateFields();
        Question question = new Question();
        if(valid){
            //luu cau hoi +cau tra loi+ cau tra loi dung
            question.setOption1(option1.getText().trim());
            question.setOption2(option2.getText().trim());
            question.setOption3(option3.getText().trim());
            question.setOption4(option4.getText().trim());
            //selected= cau tra loi dung
            Toggle selected = radioGroup.getSelectedToggle();
            String ans = null;
            if(selected == option1radio){
                ans = option1.getText().trim();
            }else if(selected == option2radio){
                ans = option2.getText().trim();
            }
            else if(selected == option3radio){
                ans = option3.getText().trim();
            }
            else if(selected == option4radio){
                ans = option4.getText().trim();
            }
            question.setAnswer(ans);
            question.setQuestion(this.question.getText().trim());
            // xoa cac text field moi khi add 1 cau hoi
            this.question.clear();
            option1.clear();
            option2.clear();
            option3.clear();
            option4.clear();
            questions.add(question);
//            question.setQuiz(quiz);
            System.out.println("Save Question...");
            System.out.println(questions);
            System.out.println(quiz);
        }
        return valid;
    }
    // them cau hoi
    @FXML
    public void addNextQuestion(ActionEvent event) {
       addQuestions();
    }

    //submit
    @FXML
    private void submitQuiz(ActionEvent event) {
        boolean flag = addQuestions();
        if(flag){
//            flag = quiz.save(questions);
            if(flag){
                // success

                Notifications.create()
                        .title("Success").position(Pos.CENTER)
                        .darkStyle().text("Quiz Successfully Saved...")
                        .showInformation();

            }else{
                // error
                Notifications.create()
                        .title("Fail..").position(Pos.CENTER)
                        .darkStyle().text("cant Save Quiz.. Try Again..")
                        .showError();
            }
        }
    }
}

