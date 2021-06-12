<%@ page import="ru.eforward.express_testing.model.Student" %>
<%@ page import="java.nio.file.Paths" %>
<%@ page import="ru.eforward.express_testing.dao.TestDAOFakeDatabaseImpl" %>
<%@ page import="java.util.concurrent.atomic.AtomicReference" %>
<%@ page import="ru.eforward.express_testing.model.school.Test" %>
<%@ page import="java.util.List" %>
<%@ page import="java.nio.file.Path" %>
<%@ page import="ru.eforward.express_testing.testingProcess.TestingUnit" %>
<%@ page import="ru.eforward.express_testing.utils.LogHelper" %>
<%@ page import="java.util.Optional" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Тестирование</title>
    <link href="../../css/countdown.css" rel="stylesheet" type="text/css" >
    <!--script src="countdown.js"></script-->
</head>
<body>
    <h1>Тестирование</h1>

    <%
        LogHelper.writeMessage("---class testing.jsp : user entered.");

        Student student = (Student)session.getAttribute("user");
        ServletContext servletContext = request.getServletContext();
        @SuppressWarnings("unchecked")
        AtomicReference<List<TestingUnit>> testingUnitsListAtomicReference = (AtomicReference<List<TestingUnit>>)servletContext.getAttribute("testingUnitsListAtomicReference");
        LogHelper.writeMessage("---class testing.jsp : AtomicReference = " + testingUnitsListAtomicReference);
        LogHelper.writeMessage("---class testing.jsp : student = " + student);
        List<TestingUnit> list = null;

        //find out if there is an available test (TestingUnit) for this student (his group);
        boolean testIsAvailable = false;
        if(testingUnitsListAtomicReference != null && student != null){
            list = testingUnitsListAtomicReference.get();
            LogHelper.writeMessage("---===class testing.jsp : if statement:  list = " + list);
            testIsAvailable = list
                    .stream()
                    .anyMatch(testingUnit -> {
                        LogHelper.writeMessage("---class testing.jsp : stream:  test is available");
                        //here we should take testingUnit from list and run it -> html code!
                        return testingUnit.getGroupId() == student.getGroupId();
                    });
//same as stream above - for testing:
//            for(TestingUnit t : list){
//                int unitGroupId = t.getGroupId();
//                int studentGroupId = student.getGroupId();
//                LogHelper.writeMessage("---class testing.jsp : for cycle:  unitGroupId = " + unitGroupId + ", studentGroupId = " + studentGroupId);
//                if(t.getGroupId() == student.getGroupId()){
//                    testIsAvailable = true;
//                    break;
//                }
//            }
            LogHelper.writeMessage("---class testing.jsp : if statement:  testIsAvailable = " + testIsAvailable);
        }

        if(testIsAvailable){

            //get the first available test (TestingUnits) for this student (his group):
            Optional<TestingUnit> optionalTestingUnit = list
                    .stream()
                    .filter(testingUnit -> testingUnit.getGroupId() == student.getGroupId())
                    .findFirst();

            //get the available first/next question of this test (of this TestingUnit)
            String htmlString = "Извините. Файл с тестом не был подготовлен.";
            //add TestingUnit to student's own session:
            if(optionalTestingUnit.isPresent()){
                TestingUnit testingUnit = optionalTestingUnit.get();
                //todo: clone this object, and pass it to sessionAttribute after it.
                session.setAttribute("studentsTestingUnit", testingUnit);
            }

            //pull next question from TestingUnit: (iteration algorithm exists in TestingUnit entity)
            if(optionalTestingUnit.isPresent() && optionalTestingUnit.get().hasNextTest()){
                //todo: here we should take TestingUnit from session, not from context!!!
                htmlString = optionalTestingUnit.get().getNextTest();
            }

    %>
    <br/>
    <!--This is for countdown:-->
    <h2 class="countdown-title">Тестирование началось:</h2>
    <div id="deadline-message" class="deadline-message">
        Время тестирования закончилось!
    </div>
    <div id="countdown" class="countdown">
        <!--div class="countdown-number">
            <span class="days countdown-time"></span>
            <span class="countdown-text">Days</span>
        </div>
        <div class="countdown-number">
            <span class="hours countdown-time"></span>
            <span class="countdown-text">Hours</span>
        </div-->
        <div class="countdown-number">
            <span class="minutes countdown-time"></span>
            <span class="countdown-text">Минуты</span>
        </div>
        <div class="countdown-number">
            <span class="seconds countdown-time"></span>
            <span class="countdown-text">Секунды</span>
        </div>
    </div>
    <script>
        function getTimeRemaining(endtime) {
            var t = Date.parse(endtime) - Date.parse(new Date());
            var seconds = Math.floor((t / 1000) % 60);
            var minutes = Math.floor((t / 1000 / 60) % 60);
            //var hours = Math.floor((t / (1000 * 60 * 60)) % 24);
            //var days = Math.floor(t / (1000 * 60 * 60 * 24));
            return {
                total: t,
                //days: days,
                //hours: hours,
                minutes: minutes,
                seconds: seconds
            };
        }

        function initializeClock(id, endtime) {
            var clock = document.getElementById(id);
            //var daysSpan = clock.querySelector(".days");
            //var hoursSpan = clock.querySelector(".hours");
            var minutesSpan = clock.querySelector(".minutes");
            var secondsSpan = clock.querySelector(".seconds");

            function updateClock() {
                var t = getTimeRemaining(endtime);

                if (t.total <= 0) {
                    document.getElementById("countdown").className = "hidden";
                    document.getElementById("deadline-message").className = "visible";
                    clearInterval(timeinterval);
                    return true;
                }

                //daysSpan.innerHTML = t.days;
                //hoursSpan.innerHTML = ("0" + t.hours).slice(-2);
                minutesSpan.innerHTML = ("0" + t.minutes).slice(-2);
                secondsSpan.innerHTML = ("0" + t.seconds).slice(-2);
            }

            updateClock();
            var timeinterval = setInterval(updateClock, 1000);
        }

        var deadline = new Date(Date.parse(new Date()) + 1 * 1 *  3 * 60 * 1000);
        initializeClock("countdown", deadline);
    </script>
    <!--end of countdown code-->

     <%=htmlString%>

     <%
        }
        if(!testIsAvailable){
            %>
                <p><%="Нет доступных тестов."%></p>
            <%
        }
    %>


</body>
</html>
