package ru.eforward.express_testing.testingProcess.questionHandlers;

import ru.eforward.express_testing.testingProcess.QuestionType;

public interface QuestionHandler {
    /**
     *Takes a QuestionType enum value, finds out if this value can be processed with this class.
     * @param enumValue - a QuestionType enum value.
     * @return true if this value can be processed with this class, otherwise returns false;
     */
    boolean canProcess(QuestionType enumValue);

    /**
     *Takes plain string as a parameter, parses it to html-formatted string
     * @param q - plain string (one whole question of the test.
     * @return html-formatted String created with given String q;
     */
    String process(String q);
}