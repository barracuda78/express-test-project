package ru.eforward.express_testing.testingProcess.questionHandlers;

public abstract class Handler {

    final String getQuestionName(String q){
        //1. get question name if exists:
        String questionName = q.contains("::") ? q.substring(q.indexOf("::") + 2, q.lastIndexOf("::")) : "Вопрос";
        return questionName + ":";
    }

    final String getQuestion(String q){
        //2. get the question itself:
        int blankSpacesIndex = -1;

        String questionItself = "";
        if(q.contains("{") && q.contains("}")){
            blankSpacesIndex = q.indexOf('{');
            questionItself = q.substring(0, q.indexOf('{')) + q.substring(q.indexOf('}') + 1);
        }else if(q.contains("::")){
            questionItself = q.substring(q.lastIndexOf("..") + 2);
        }else{
            questionItself = q;
        }

        //3. check if there is a question text after {...} variants block: insert "____" here.
        // ( ___ is not needed in case of TRUE_FALSE or COMPLIANCE QuestionType) :
        if(!q.endsWith("}")
                && !q.toLowerCase().contains("{t}")
                && !q.toLowerCase().contains("{true}")
                && !q.toLowerCase().contains("->")
                && !q.toLowerCase().contains("{#")
                && !q.contains("{}")){
            StringBuilder questSB = new StringBuilder(questionItself);
            if(blankSpacesIndex >=0) {
                questSB.insert(blankSpacesIndex, "_____");
            }
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
        String result = q;
        if(q.contains("{") && q.contains("}")){
            result = q.substring(q.indexOf('{') + 1, q.indexOf('}'));
        }
        return result;
    }

    final StringBuilder startBuildingHtml(String questionName, String questionItself){
        StringBuilder sb = new StringBuilder();
        sb.append("<p>");
        sb.append("<b>");
        sb.append(questionName);
        sb.append("</b>");
        sb.append("<br>");
        sb.append("<b>");
        sb.append(questionItself);
        sb.append("</b>");
        sb.append("<form method=\"get\" action=\"AnswerHandlerServlet\" method=\"GET\">" );
        return sb;
    }
}
