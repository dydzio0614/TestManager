/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proj;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dydzio
 */

class Question
{
    private String content;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private String correctAnswer;   

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getCorrectAnswer()
    {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer)
    {
        this.correctAnswer = correctAnswer;
    }

    public String getAnswerA()
    {
        return answerA;
    }

    public void setAnswerA(String answerA)
    {
        this.answerA = answerA;
    }

    public String getAnswerB()
    {
        return answerB;
    }

    public void setAnswerB(String answerB)
    {
        this.answerB = answerB;
    }

    public String getAnswerC()
    {
        return answerC;
    }

    public void setAnswerC(String answerC)
    {
        this.answerC = answerC;
    }

    public String getAnswerD()
    {
        return answerD;
    }

    public void setAnswerD(String answerD)
    {
        this.answerD = answerD;
    }
}

@XmlRootElement
class Test
{
    public ArrayList<Question> Questions;
}
