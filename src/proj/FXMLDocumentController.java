/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj;

import java.io.File;
import java.util.ArrayList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import org.xml.sax.SAXException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Dydzio
 */
public class FXMLDocumentController
{
    private Test test; //loaded test
    private int questionIndex;
    private int points;
    
    private Test newTest; //new test used for test creation window
    
    @FXML
    private Parent root;
    
    @FXML
    private Label questionlabel;
    
    @FXML
    private CheckBox answerA;
    @FXML
    private CheckBox answerB;
    @FXML
    private CheckBox answerC;
    @FXML
    private CheckBox answerD;
    
    @FXML
    private Button confirm;
    

    
    
    //test create window variables
    @FXML
    private AnchorPane newtestWindow;
    
    @FXML
    private TextArea newQuestion;
    
    @FXML
    private TextField newAnswerA;
    
    @FXML
    private TextField newAnswerB;
        
    @FXML
    private TextField newAnswerC;
            
    @FXML
    private TextField newAnswerD;
    
    @FXML
    private CheckBox correctA;
    
    @FXML
    private CheckBox correctB;
    
    @FXML
    private CheckBox correctC;
    
    @FXML
    private CheckBox correctD;
    
    
    
    @FXML
    private void confirmButtonClicked(ActionEvent event)
    {
        String chosenAnswer = "";
        if(answerA.isSelected())
            chosenAnswer = chosenAnswer + "a";
        if(answerB.isSelected())
            chosenAnswer = chosenAnswer + "b";
        if(answerC.isSelected())
            chosenAnswer = chosenAnswer + "c";
        if(answerD.isSelected())
            chosenAnswer = chosenAnswer + "d";
        
        if(chosenAnswer.equals(test.Questions.get(questionIndex).getCorrectAnswer().toLowerCase()))
            points++;
        
        questionIndex++;
        
        if(questionIndex < test.Questions.size())
            setQuestion(questionIndex);
        else
        {
            setTestActive(false);
            Alert alert = new Alert(AlertType.INFORMATION, "Your score: " + points + "/" + test.Questions.size() + " points.");
            alert.showAndWait();
        }
        
    }
    
    @FXML
    private void OpenTest() throws JAXBException, SAXException
    {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter fileRestriction = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(fileRestriction);
        File fileToParse = fileChooser.showOpenDialog(root.getScene().getWindow());
        JAXBContext jaxbContext = JAXBContext.newInstance(Test.class);
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new StreamSource(new File("testschema.xsd")));
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        unmarshaller.setSchema(schema);
        test = (Test)unmarshaller.unmarshal(fileToParse);
        
        questionIndex = 0;
        points = 0;
        
        setQuestion(questionIndex);
            
        setTestActive(true);
    }
    
    @FXML
    public void OnNewtestClicked()
    {
        newtestWindow.setVisible(true);
        newTest = new Test();
        newTest.Questions = new ArrayList<Question>();
    }
    
    @FXML
    public void OnCloseButtonClicked()
    {
        newtestWindow.setVisible(false);
    }
    
    @FXML
    public void OnAddQuestionButtonClicked()
    {
        if(newQuestion.getText().isEmpty() || newAnswerA.getText().isEmpty() ||
            newAnswerB.getText().isEmpty() || newAnswerC.getText().isEmpty() || 
            newAnswerD.getText().isEmpty())
        {
            Alert alert = new Alert(AlertType.WARNING, "Question and all answer fields must be filled!");
            alert.showAndWait();
            return;
        }
        
        Question questionToAdd = new Question();
        questionToAdd.setContent(newQuestion.getText());
        questionToAdd.setAnswerA(newAnswerA.getText());
        questionToAdd.setAnswerB(newAnswerB.getText());
        questionToAdd.setAnswerC(newAnswerC.getText());
        questionToAdd.setAnswerD(newAnswerD.getText());
        
        String correctAnswer = "";
        if(correctA.isSelected())
            correctAnswer = correctAnswer + "a";
        if(correctB.isSelected())
            correctAnswer = correctAnswer + "b";
        if(correctC.isSelected())
            correctAnswer = correctAnswer + "c";
        if(correctD.isSelected())
            correctAnswer = correctAnswer + "d";
        questionToAdd.setCorrectAnswer(correctAnswer);
        
        newTest.Questions.add(questionToAdd);
        
        newQuestion.setText("");
        newAnswerA.setText("");
        newAnswerB.setText("");
        newAnswerC.setText("");
        newAnswerD.setText("");
        correctA.setSelected(false);
        correctB.setSelected(false);
        correctC.setSelected(false);
        correctD.setSelected(false);
    }
    
    @FXML
    public void OnRemoveQuestionButtonClicked()
    {
        int count = newTest.Questions.size();
        if(count > 0)
        {
            Alert alert = new Alert(AlertType.INFORMATION, "Question deleted: " + newTest.Questions.get(count-1).getContent());
            alert.showAndWait();
            newTest.Questions.remove(count - 1);
        }
        else
        {
            Alert alert = new Alert(AlertType.ERROR, "Nothing to delete.");
            alert.showAndWait();
        }
    }
    
    @FXML
    public void OnSaveTestButtonClicked() throws JAXBException, SAXException
    {
        JAXBContext context = JAXBContext.newInstance(Test.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter fileRestriction = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(fileRestriction);
        File fileToSave = fileChooser.showSaveDialog(root.getScene().getWindow());
        m.marshal(newTest, fileToSave);
    }
    
    @FXML
    public void About()
    {
        Alert alert = new Alert(AlertType.INFORMATION, "Test manager - application that allows users to create or fill tests and get score.");
        alert.showAndWait();
    }
    
    public void setQuestion(int index)
    {
        questionlabel.setText(test.Questions.get(index).getContent());
        answerA.setText(test.Questions.get(index).getAnswerA());
        answerB.setText(test.Questions.get(index).getAnswerB());
        answerC.setText(test.Questions.get(index).getAnswerC());
        answerD.setText(test.Questions.get(index).getAnswerD());
    }
    
    public void setTestActive(boolean toggle)
    {
        confirm.setDisable(!toggle);
        answerA.setDisable(!toggle);
        answerB.setDisable(!toggle);
        answerC.setDisable(!toggle);
        answerD.setDisable(!toggle);
    }
    
    public void initialize()
    {
    }       
}
