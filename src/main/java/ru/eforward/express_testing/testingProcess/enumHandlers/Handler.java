package ru.eforward.express_testing.testingProcess.enumHandlers;

public abstract class Handler {

    final String getQuestionName(String q){
        //1. get question name if exists:
        String questionName = q.contains("::") ? q.substring(q.indexOf("::") + 2, q.lastIndexOf("::")) : "Вопрос";
        return questionName + ":";
    }

    final String getQuestion(String q){
        //2. get the question itself:
        int blankSpacesIndex = q.indexOf('{');

        String questionItself = q.substring(0, q.indexOf('{')) + q.substring(q.indexOf('}') + 1);
        //3. check if there is a question text after {...} variants block: insert "____" here:
        if(!q.endsWith("}")){
            StringBuilder questSB = new StringBuilder(questionItself);
            questSB.insert(blankSpacesIndex, "_____"); //
            questionItself = questSB.toString();
        }
        //4. remove '::abcd...::' block if exists:
        if(questionItself.contains("::")){
            questionItself = questionItself.substring(questionItself.lastIndexOf("::") + 2);
        }

        return questionItself;
    }

    final String getAllVariants(String q){
        //3. get all variants of answer:
        return q.substring(q.indexOf('{') + 1, q.indexOf('}'));
    }

}
